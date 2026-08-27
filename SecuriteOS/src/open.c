#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
    char buffer[1024];
    int file = open("./confidentiel", O_RDWR | O_CREAT, 0777);
    int bytes = read(file, buffer, sizeof(buffer));
    close(file);
}