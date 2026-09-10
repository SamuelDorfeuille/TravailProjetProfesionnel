#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    int file = open("./data", O_RDWR | O_CREAT, 0777);
    write(file, "CONFIDENTIEL", strlen("CONFIDENTIEL"));
    return 0;
}