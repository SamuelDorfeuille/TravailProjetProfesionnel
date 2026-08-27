compilation: dlp
	mkdir -p tests
	gcc -std=c17 -std=gnu11 -o dlp dlp.c
	gcc -o tests/write src/write.c
	gcc -o tests/open src/open.c
	gcc -o tests/normal src/normal.c
	gcc -o tests/erreur src/erreur.c
	gcc -o tests/execve src/execve.c
test: dlp
	bats public.bats
clean:
	test -x dlp && rm dlp
	test -e ./tests && rm -fr ./tests
