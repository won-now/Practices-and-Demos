#include <iostream>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <signal.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define PORT 9000
#define BACKLOG 10
#define BUF_SIZE 1024
void read_childproc(int sig);

int main(){
	int ret;
	int listen_sock, conn_sock;
	char buf[BUF_SIZE];
	size_t msg_len;

	struct sigaction act;
	act.sa_handler = read_childproc;
	sigemptyset(&act.sa_mask);
	act.sa_flags = 0;

	struct sockaddr_in serv_addr, clnt_addr;
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	socklen_t adr_size;
	pid_t pid;

	sigaction(SIGCHLD, &act, 0);

	listen_sock = socket(AF_INET, SOCK_STREAM, 0);
	
	
	ret = bind(listen_sock, (struct sockaddr*) &serv_addr, sizeof(serv_addr));
	if(ret < 0){
		std::cout << "Failed to bind socket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	ret = listen(listen_sock, BACKLOG);
	if(ret < 0){
		std::cout << "Failed to listen socket!" << std::endl;
		exit(EXIT_FAILURE);
	}

	while(1){
		conn_sock = accept(listen_sock, (struct sockaddr*) &clnt_addr, &adr_size);
		if(conn_sock < 0)
			continue;

		pid = fork();
		if(pid < 0){
			close(conn_sock);
			continue;
		}
		//child process
		if(pid == 0){
			close(listen_sock);
			std::cout << "new connection..." << std::endl;
			while((msg_len = recv(conn_sock, buf, BUF_SIZE, 0)) != 0){
				send(conn_sock, buf, msg_len, 0);
			}
			close(conn_sock);
			std::cout << "diconnected..." << std::endl;
			return 0;
		}else{
			close(conn_sock);
		}
	}

	close(listen_sock);
	return 0;
}

void read_childproc(int sig){
	int status;
	pid_t id = waitpid(-1, &status, WNOHANG);
	if(WIFEXITED(status)){
		std::cout << "Removed process id: " << id << std::endl;
		std::cout << "Child send: " << WEXITSTATUS(status) << std::endl;
	}
}
