#include <iostream>
#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define PORT 8888
#define LEN 1024
int main(int argc, char* args[]){
	int sockfd;
	int ret;
	char buf[LEN] = {0,};

	struct sockaddr_in des_addr;

	sockfd = socket(AF_INET, SOCK_DGRAM, 0);
	if(sockfd < 0){
		std::cout << "Failed to create FD!" << std::endl;
		exit(EXIT_FAILURE);
	}

	des_addr.sin_family = AF_INET;
	des_addr.sin_port = PORT;
	des_addr.sin_addr.s_addr = inet_addr("127.0.0.1");
//	des_addr.sin_addr.s_addr = INADDR_LOOPBACK;
//	bzero(&(des_addr.sin_zero), 8);
	

	socklen_t addrlen = sizeof(struct sockaddr);

	while(1){
		memset((void *)buf, 0, LEN);
		
		gets(buf);

		ret = sendto(sockfd, 
					 (void *)buf, 
					 strlen(buf), 
					 0, 
					 (struct sockaddr *)&des_addr, 
					 addrlen);
	
		if(strcmp("quit", buf) == 0){
			break;
		}

		if(ret < 0){
			std::cout << "Failed to send message!" << std::endl;
			exit(EXIT_FAILURE);
		}
		
		memset((void *)buf, 0, LEN);
		socklen_t len = sizeof(struct sockaddr);
		recvfrom(sockfd, 
				 (void *)buf, 
				 LEN, 
				 0,
				 (struct sockaddr *)&des_addr,
				 &len);

		std::cout << "from server: " << buf << std::endl;
	}

	close(sockfd);
	return 0;
}
