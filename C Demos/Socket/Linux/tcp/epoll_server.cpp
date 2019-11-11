#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <arpa/inet.h>
#include <sys/epoll.h>
//#include <sys/select.h>

#define PORT 9000
#define BUF_SIZE 1024
#define EPOLL_SIZE 50
int main(int argc, char* arg[]){
	int serv_sock, clnt_sock;
	int ret, str_len;

	char buf[BUF_SIZE];

//	int max_fd;
//	fd_set recv, cpy_recv;

	struct sockaddr_in serv_addr, clnt_addr;
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);

//	struct timeval timeout;

	struct epoll_event *ep_events;
	struct epoll_event event;
	int epfd, event_cnt;

	socklen_t adr_sz;

	serv_sock = socket(AF_INET, SOCK_STREAM, 0);
	if(serv_sock < 0){
		std::cout << "Failed to create a scoket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ret = bind(serv_sock, (struct sockaddr*) &serv_addr,sizeof(serv_addr));
	if(ret < 0){
		std::cout << "Failed to bind the socket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ret = listen(serv_sock,10);
	if(ret < 0){
		std::cout << "Failed to listen the socket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	/*
	FD_ZERO(&recv); //initial fd_set;
	FD_SET(serv_sock, &recv);
	max_fd = serv_sock;
	*/

	epfd = epoll_create(EPOLL_SIZE); //the size is ignored,but in order to ensure backward compatibility,the size must still be greater than zero.
	ep_events = (struct epoll_event*)malloc(sizeof(struct epoll_event) * EPOLL_SIZE);

	event.events = EPOLLIN;
	event.data.fd = serv_sock;
	epoll_ctl(epfd, EPOLL_CTL_ADD, serv_sock, &event);

	while(1){
	/*
		cpy_recv = recv;
		timeout.tv_sec = 5;
		timeout.tv_usec = 5000;
	

		ret = select(max_fd + 1, &cpy_recv, 0, 0, &timeout);
		if(ret == -1)
			break;
		if(ret == 0)
			continue;
		*/

		event_cnt = epoll_wait(epfd, ep_events, EPOLL_SIZE, -1);
		if(event_cnt == -1){
			puts("epoll_wait() error!");
			break;
		}

		for(int i = 0; i < event_cnt/*max_fd + 1*/; i++){
			/*if(FD_ISSET(i, &cpy_recv)){
				if(i == serv_sock){ */
			if(ep_events[i].data.fd == serv_sock){
				adr_sz = sizeof(clnt_addr);
				clnt_sock = accept(serv_sock, (struct sockaddr*) &clnt_addr, &adr_sz);
				if(clnt_sock < 0){
					std::cout << "Failed to accept the connection!" << std::endl;
					break;
				}
				/*
					FD_SET(clnt_sock, &recv);
					if(max_fd < clnt_sock)
						max_fd = clnt_sock;
					std::cout << "connected client: " << clnt_sock << std::endl;
				*/
				event.events = EPOLLIN;
				event.data.fd = clnt_sock;
				epoll_ctl(epfd, EPOLL_CTL_ADD, clnt_sock, &event);
				std::cout << "connected client: " << clnt_sock << std::endl;
			}else{
				//str_len = read(i, buf, BUF_SIZE);
				str_len = read(ep_events[i].data.fd, buf, BUF_SIZE);
				if(str_len == 0){
					//FD_CLR(i, &recv);
					epoll_ctl(epfd, EPOLL_CTL_DEL, ep_events[i].data.fd, NULL);
					//close(i);
					close(ep_events[i].data.fd);
					std::cout << "closed client: " << /*i*/ep_events[i].data.fd << std::endl;
				}else{
					send(/*i*/ep_events[i].data.fd,buf, str_len, 0);
				}	
			}
		}

	}

	close(serv_sock);
	close(epfd);
	return 0;
}
