#include <iostream>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <pthread.h>

#define PORT 9000
#define BUF_SIZE 100
#define NAME_SIZE 20

void* send_msg(void* arg);
void* read_msg(void* arg);

char msg[BUF_SIZE];
char name[NAME_SIZE] = "[DEFAULT]";
int main(int argc, char* argv[]){
	int sock;
	int ret;

	struct sockaddr_in remote_addr; 
	remote_addr.sin_family = AF_INET;
	remote_addr.sin_port = htons(PORT);
	remote_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

	pthread_t snd_thread, rcv_thread;
	void* thread_return;

	if(argc != 2){
		std::cout << "Format: " << argv[0] << " [NAME]" << std::endl;
		exit(EXIT_FAILURE);
	}
	
	sprintf(name, "[%s]", argv[1]);
	sock = socket(AF_INET, SOCK_STREAM, 0);
	if(sock < 0){
		std::cout << "Failed to create socket!" << std::endl;
		exit(EXIT_FAILURE);
	}
	ret = connect(sock, (struct sockaddr*)&remote_addr, sizeof(remote_addr));
	if(ret < 0){
		std::cout << "Failed to connect the server!" << std::endl;
		exit(EXIT_FAILURE);
	}

	
	pthread_create(&snd_thread, NULL, send_msg, (void*)&sock);
	pthread_create(&rcv_thread, NULL, read_msg, (void*)&sock);
	pthread_join(snd_thread, &thread_return);
	pthread_join(rcv_thread, &thread_return);
	close(sock);
	return 0;
}


void* send_msg(void* arg){
	int sock = *((int*)arg);
	char name_msg[NAME_SIZE + BUF_SIZE];
	while(1){
		fgets(msg, BUF_SIZE, stdin);
		if(strcmp(msg,"quit\n") == 0){
			close(sock);
			exit(EXIT_SUCCESS);
		}
		sprintf(name_msg,"%s %s", name, msg);
		write(sock, name_msg, strlen(name_msg));
	}
	return NULL;
}

void* read_msg(void* arg){
	int sock = *((int*) arg);

	char name_msg[NAME_SIZE + BUF_SIZE];
	int str_len;
	while(1){
		str_len = read(sock, name_msg, NAME_SIZE + BUF_SIZE - 1);
		if(str_len == -1){
			return (void*)-1;
		}
		name_msg[str_len] = 0;
		fputs(name_msg, stdout);
	}
	return NULL;
}
