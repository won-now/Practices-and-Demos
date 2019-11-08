#include <iostream>
#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define PORT 8888
#define LEN 1024
int main(int argc, char* args[]){
	int sockfd;
	int ret;
	char buf[LEN] = {0,};

	struct sockaddr_in ser_addr, clt_addr;

	sockfd = socket(AF_INET, SOCK_DGRAM, 0);
	if(sockfd < 0){
		std::cout << "Failed to create FD!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ser_addr.sin_family = AF_INET;
	ser_addr.sin_port = PORT;
	ser_addr.sin_addr.s_addr = INADDR_ANY;
	bzero(&(ser_addr.sin_zero), 8);
	
	ret = bind(sockfd, 
			   (struct sockaddr *)&ser_addr, 
			   sizeof(struct sockaddr_in));

	if(ret < 0){
		std::cout << "Failed to bind address!" << std::endl;
		exit(EXIT_FAILURE);
	}

	socklen_t addrlen = sizeof(struct sockaddr);

	while(1){
		memset((void *)buf, 0, LEN);
		recvfrom(sockfd, 
				 (void *)buf, 
				 LEN, 
				 0, 
				 (struct sockaddr *)&clt_addr, 
				 &addrlen);
		
		std::cout << "from client: " << buf << std::endl;

		sendto(sockfd, 
			   (void *)buf, 
			   LEN, 
			   0, 
			   (struct sockaddr *)&clt_addr,
			   addrlen);
	}
	close(sockfd);
	return 0;
}
