#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/select.h>
#include <arpa/inet.h>

#define PORT 9000
#define BUF_SIZE 1024
int main(int argc, char* arg[]){
	int serv_sock, clnt_sock;
	int ret, str_len;

	char buf[BUF_SIZE];

	int max_fd;
	fd_set recv, cpy_recv;

	struct sockaddr_in serv_addr, clnt_addr;
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);

	struct timeval timeout;
	
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

	FD_ZERO(&recv); //initial fd_set;
	FD_SET(serv_sock, &recv);
	max_fd = serv_sock;

	while(1){
		cpy_recv = recv;
		timeout.tv_sec = 5;
		timeout.tv_usec = 5000;

		ret = select(max_fd + 1, &cpy_recv, 0, 0, &timeout);
		if(ret == -1)
			break;
		if(ret == 0)
			continue;

		for(int i = 0; i < max_fd + 1; i++){
			if(FD_ISSET(i, &cpy_recv)){
				if(i == serv_sock){
					clnt_sock = accept(serv_sock, (struct sockaddr*) &clnt_addr, &adr_sz);
					if(clnt_sock < 0){
						std::cout << "Failed to accept the connection!" << std::endl;
						break;
					}
					FD_SET(clnt_sock, &recv);
					if(max_fd < clnt_sock)
						max_fd = clnt_sock;
					std::cout << "connected client: " << clnt_sock << std::endl;
				}else{
					str_len = read(i, buf, BUF_SIZE);

					if(str_len == 0){
						FD_CLR(i, &recv);
						close(i);
						std::cout << "closed client: " << i << std::endl;
					}else{
						send(i,buf, str_len, 0);
					}
				}
			}
		}

	}

	close(serv_sock);
	return 0;
}
