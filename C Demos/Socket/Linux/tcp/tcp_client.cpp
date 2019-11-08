#include <iostream>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdlib.h>
#include <strings.h>
#include <string.h>
#include <stdio.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>

#define PORT 8111
#define MESSAGE_LEN 1024

int main(int argc, char* args[]){
	int socket_client;
	int ret;
	struct sockaddr_in ser_addr;
	char sendbuf[MESSAGE_LEN] = {0,};
	char recvbuf[MESSAGE_LEN] = {0,};

	socket_client = socket(AF_INET, SOCK_STREAM, 0);
	if(socket_client < 0){
		std::cout << "Failed to created socket file descriptor!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ser_addr.sin_family = AF_INET;
	ser_addr.sin_port = PORT;
	ser_addr.sin_addr.s_addr = INADDR_LOOPBACK;
	bzero(&(ser_addr.sin_sero), 8);
//	ser_addr.sin_addr.s_addr = inet_addr("127.0.0.1");
	socklen_t addrlen = sizeof(struct sockaddr);
	ret = connect(socket_client, 
				  (struct sockaddr *)&ser_addr, 
				  addrlen);
							  
	if(ret < 0){
		std::cout << "Failed to connect the server!" << std::endl;
		exit(EXIT_FAILURE);
	}
	
	while(1){
		memset(sendbuf, 0, MESSAGE_LEN);
		gets(sendbuf);
		ret = send(socket_client, (void *)sendbuf, strlen(sendbuf), 0);

		if(ret <= 0){
			std::cout << "Failed to send Message!" << std::endl;
			break;
		}
		
		if(strcmp(sendbuf, "quit") == 0){
			break;
		}
		ret = recv(socket_client, (void *)recvbuf, MESSAGE_LEN, 0);
		recvbuf[ret] = '\0';
		std::cout << "recv:" << recvbuf << std::endl;

	}
	
	close(socket_client);
	return 0;
}
