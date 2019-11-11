#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <arpa/inet.h>
#include <pthread.h>

#define PORT 9000
#define BUF_SIZE 4
#define MAX_CLNT 256

void* handle_clnt(void *arg);
void send_msg(char* msg, int len);
int clnt_cnt = 0;
int clnt_socks[MAX_CLNT];
pthread_mutex_t mutex;

int main(int argc, char* arg[]){
	int serv_sock, clnt_sock;
	int ret;

	struct sockaddr_in serv_addr, clnt_addr;
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);

	socklen_t adr_sz;

	pthread_t t_id;

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

	pthread_mutex_init(&mutex, NULL);

	while(1){
		adr_sz = sizeof(clnt_addr);
		clnt_sock = accept(serv_sock, (struct sockaddr*) &clnt_addr, &adr_sz);

		pthread_mutex_lock(&mutex);
		clnt_socks[clnt_cnt++] = clnt_sock;
		pthread_mutex_unlock(&mutex);

		pthread_create(&t_id, NULL, handle_clnt, (void*)&clnt_sock);
		std::cout << "connected client: " << clnt_sock << std::endl;
		pthread_detach(t_id);
	}

	return 0;
}


void* handle_clnt(void* arg){
	int clnt_sock = *((int*)arg);
	int str_len = 0;
	char msg[BUF_SIZE];
	
	while((str_len = read(clnt_sock, msg, BUF_SIZE)) != 0){
		send_msg(msg, str_len);
	}
	
	pthread_mutex_lock(&mutex);
	for(int i = 0; i < clnt_cnt; i++){
		if(clnt_sock == clnt_socks[i]){
			while(i++ < clnt_cnt - 1){
				clnt_socks[i] = clnt_socks[i+1];
			}
			break;
		}
	}
	clnt_cnt--;
	pthread_mutex_unlock(&mutex);

	close(clnt_sock);
	return NULL;
}

void send_msg(char* msg, int len){
	pthread_mutex_lock(&mutex);
	for(int i = 0; i < clnt_cnt; i++){
		write(clnt_socks[i], msg, len);
	}
	pthread_mutex_unlock(&mutex);
}
