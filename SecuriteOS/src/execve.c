#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>

int main() {
    execlp("/usr/bin/cat", "/usr/bin/cat", "./confidentiel", NULL);
}