#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>

#define PORT 9000
#define BUF_SIZE 1024
void recvmsg(int sockfd, char* buf);
void sendmsg(int sockfd, char* buf);
int main(){
	int clnt_sock;
	int ret;
	char buf[BUF_SIZE];
	pid_t pid;
	
	struct sockaddr_in serv_addr;
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

	clnt_sock = socket(AF_INET, SOCK_STREAM, 0);
	if(clnt_sock < 0){
		std::cout << "Failed to creat a socket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ret = connect(clnt_sock, (struct sockaddr*) &serv_addr, sizeof(serv_addr));
	if(ret < 0){
		std::cout << "Failed to connect server!" << std::endl;
		exit(EXIT_FAILURE);
	}

	pid = fork();
	//child process
	if(pid == 0){
		sendmsg(clnt_sock, buf);
	}else{
		recvmsg(clnt_sock, buf);
	}
	close(clnt_sock);
	return 0;
}

void sendmsg(int sockfd, char* buf){
	while(1){
		memset(buf, 0, BUF_SIZE);
		fgets(buf, BUF_SIZE, stdin);
		if(!strcmp(buf, "quit\n")){
			shutdown(sockfd, SHUT_WR);
			return;
		}
		send(sockfd, buf, strlen(buf), 0);
	}
}

void recvmsg(int sockfd, char* buf){
	while(1){
		memset(buf, 0, BUF_SIZE);
		int len = recv(sockfd, buf, BUF_SIZE, 0);
		if(len <= 0)
			return;
		std::cout << "Message from server: " << buf << std::endl;
	}
}
