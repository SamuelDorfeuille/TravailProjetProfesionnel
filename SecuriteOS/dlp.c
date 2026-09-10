//Nom:Samuel Dorfeuille
//CodePermanent: Dors19109607
#define _POSIX_SOURCE
#define _GNU_SOURCE
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ptrace.h>
#include <sys/syscall.h>
#include <sys/user.h>
#include <signal.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
//ajout biliotheque
#include <errno.h>
#include <sys/stat.h>

#define CHEMIN_MAXIMUM 4096
#define TAMPON_LECTURE_MAXIMUM 7000

//-------------------Fonctions utilitaires -------------------
/**
 * lit et Copie le Chemin pour le chemin mettre du Tampon pour lenfant
 * Recontruit le chemnin
 * @param a le premier entier
 * @param b le deuxième entier
 * @return si ça bien été copie dans le tamtpon
 */
int litCopieCheminPourTamponEnfant(pid_t pid, unsigned long addresse, char *tampon, size_t tailleTampon) {
    size_t i = 0;

    while (i < tailleTampon) {
        // lit le chemin/path dans un mot
        errno = 0;
        long mot = ptrace(PTRACE_PEEKDATA, pid, (void *)(addresse + i), NULL);

        if (mot == -1 && errno != 0) {
            return -1;
        }

        // réécrit le chemin/path dans le tampon
        for (size_t j = 0; j < sizeof(long) && i < tailleTampon - 1; j++, i++) {
            tampon[i] = ((char*)&mot)[j];
            if (tampon[i] == '\0') {
                return 0;
            }
        }
    }

    // met un '\0' à la fin du tampon
    tampon[tailleTampon - 1] = '\0';
    return 0;
}

/**
 * le chemin pour le descripteur du fichier
 *
 * @param pid le premier entier
 * @param fd le deuxième entier
 * @param cheminLien lien chemin
 * @return  si le chemin pour le descripteur du fichier a bien été copie (lien symbolique)
 */
int cheminPourDescripteurDeFIchier(pid_t pid, int fd, char *cheminLien, size_t outlen) {
    char procFd[64];
    //creer le chemin sybolique pour former  "/proc/%d/fd/%d"
    snprintf(procFd, sizeof(procFd), "/proc/%d/fd/%d", (int)pid, fd);
    ssize_t lienSymbolique = readlink(procFd, cheminLien, outlen - 1);

    if (lienSymbolique == -1) {
        return -1;
    }

    cheminLien[lienSymbolique] = '\0';
    return 0;
}

//   -------------------Fonction principale-------------------

int main(int argc, char **argv) {
    if (argc != 2) {
        fprintf(stderr, "Erreur il a pas le bon nombre argumentt\n");
        exit(EXIT_FAILURE);
    }

    // fork pour le premier porcessus
    pid_t pid = fork();

    if (pid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    }

    // fork enfant
    if (pid == 0) {
        // enfant se fait suivre par le parent
        ptrace(PTRACE_TRACEME, 0, NULL, NULL);
        raise(SIGSTOP);
        execlp(argv[1], argv[1], NULL);
        perror("execlp");
        exit(EXIT_FAILURE);
    }

    // -------------------fork parent-------------------
    int status;


    char programmeExeNomEnfant[CHEMIN_MAXIMUM];
    strncpy(programmeExeNomEnfant, argv[1], CHEMIN_MAXIMUM - 1);
    programmeExeNomEnfant[CHEMIN_MAXIMUM - 1] = '\0';

    // attente pour l'enfant
    if (waitpid(pid, &status, 0) == -1) {
        perror("waitpid");
        exit(EXIT_FAILURE);
    }

    // trace pour suivre si lenfant fait un fork
    if (ptrace(PTRACE_SETOPTIONS, pid, 0,
               PTRACE_O_EXITKILL | PTRACE_O_TRACEFORK | PTRACE_O_TRACEEXEC) == -1) {
        perror("ptrace SETOPTIONS");
        exit(EXIT_FAILURE);
    }

    if (ptrace(PTRACE_SYSCALL, pid, 0, 0) == -1) {
        perror("ptrace SYSCALL");
        exit(EXIT_FAILURE);
    }

// variabale si on est dans le syscall
    bool syscalEntrer = true; 
    // -------------------boucle principale pour surveillance sys call-------------------
    while (1) {
        pid_t wpid = waitpid(-1, &status, __WALL);

        if (wpid == -1) {
            if (errno == EINTR) {
                continue; 
            }

            if (errno == ECHILD) {
                break; 
            }

            perror("waitpid");
            exit(EXIT_FAILURE);
        }

        // si l'enfant s'est terminé normalement alorson retourne son code 
        if (WIFEXITED(status)) {
            return WEXITSTATUS(status);
        }

        //si l'enfant a été tué par un signal alors on  retourne 128+
        if (WIFSIGNALED(status)) {
            return 128 + WTERMSIG(status);
        }

        // si l'enfant est stoppé  
        if (WIFSTOPPED(status)) {

             // vérifier si c'est un événement ptrace
    if ((status >> 16) == PTRACE_EVENT_EXEC) {
        // récupérer le chemin de l'exécutable
        char cheminExecutable[CHEMIN_MAXIMUM] = {0};
        char executableProc[64];
        snprintf(executableProc, sizeof(executableProc), "/proc/%d/exe", (int)wpid);

        ssize_t len = readlink(executableProc, cheminExecutable, sizeof(cheminExecutable) - 1);
        if (len != -1) {
            cheminExecutable[len] = '\0';
            strncpy(programmeExeNomEnfant, cheminExecutable, sizeof(programmeExeNomEnfant) - 1);
            programmeExeNomEnfant[sizeof(programmeExeNomEnfant) - 1] = '\0';
        }

        // continuer l'enfant après l'EXEC
        if (ptrace(PTRACE_SYSCALL, wpid, 0, 0) == -1) {
            perror("le sys call continue après l execution");
            break;
        }
        continue; 
    }
            // lire les registres puis ensuite traiter l'événement 
            struct user_regs_struct regs;

            if (ptrace(PTRACE_GETREGS, wpid, 0, &regs) == -1) {
                perror("GETREGS");
                ptrace(PTRACE_SYSCALL, wpid, 0, 0);
                continue;
            }

            if (syscalEntrer) {
        
                 
     // ------------------- Intercepter execve -------------------
    if (regs.orig_rax == SYS_execve) {
        char cheminExecutable[CHEMIN_MAXIMUM] = {0};
        // premier argument = chemin du programme à exécuter
        unsigned long adresse = regs.rdi; 

        if (litCopieCheminPourTamponEnfant(wpid, adresse, cheminExecutable, sizeof(cheminExecutable)) == 0) {
            // mettre à jour le nom du programme suivi
            strncpy(programmeExeNomEnfant, cheminExecutable, sizeof(programmeExeNomEnfant) - 1);
            programmeExeNomEnfant[sizeof(programmeExeNomEnfant) - 1] = '\0';
        }
    }


                //-----------------------write------------------- 
                // --- interception du syscall write rax  ---
                if (regs.orig_rax == SYS_write) {
                    char chemin[CHEMIN_MAXIMUM] = {0};
                    // adresse du buffer dans le processus enfant
                    unsigned long addr = regs.rsi;
                    // nombre d'octets à écrire
                    size_t count = regs.rdx;
                    // tampon local pour lire les données
                    char buffer[256] = {0};
                    size_t i = 0;

                    // lecture par blocs de sizeof(long) octets
                    while (i < sizeof(buffer) - sizeof(long) && i < count) {
                        errno = 0;
                        long word = ptrace(PTRACE_PEEKDATA, wpid, addr + i, NULL);

                        if (word == -1 && errno != 0) {
                            break;
                        }

                        memcpy(buffer + i, &word, sizeof(long));

                        // arrêt si on rencontre un '\0'
                        if (memchr(&word, '\0', sizeof(long))) {
                            break;
                        }

                        i += sizeof(long);
                    }

                    // vérifier la présence du mot "CONFIDENTIEL"
                    if (strstr(buffer, "CONFIDENTIEL")) {
                        char cheminDescripteurFIchier[CHEMIN_MAXIMUM] = {0};
                        // le descripteur de fichier envoyé a write
                        int descirpteurFichier = (int)regs.rdi; 

                        // récupérer le vrai chemin du fd depuis /proc/<pid>/fd/<fd>
                        if (cheminPourDescripteurDeFIchier(wpid, descirpteurFichier, cheminDescripteurFIchier, sizeof(cheminDescripteurFIchier)) == -1) {
                            strcpy(cheminDescripteurFIchier, "erreur donc inconnu");
                        }

                        printf("Le programme %s a fait une écriture interdite dans le fichier %s.\n",
                               programmeExeNomEnfant, cheminDescripteurFIchier);

                            //signal kill
                            kill(wpid, SIGKILL);
                            // 128 + signal kill(9) 
                            return 137;
                    }
                }

                //-----------------------open / openat -------------------
                else if (regs.orig_rax == SYS_open || regs.orig_rax == SYS_openat) {
                    char chemin[CHEMIN_MAXIMUM] = {0};
                    int dirfd = AT_FDCWD;
                    unsigned long addresse = (regs.orig_rax == SYS_open) ? regs.rdi : regs.rsi;

                    if (regs.orig_rax == SYS_openat) {
                        dirfd = regs.rdi;
                    }

                    if (litCopieCheminPourTamponEnfant(wpid, addresse, chemin, sizeof(chemin)) == 0) {
                        // Vérifier seulement le fichier "confidentiel" ou relatif à TMP_DIR
                        if (strstr(chemin, "confidentiel")) {
                            char chemin_reel[CHEMIN_MAXIMUM];

                            //si c'est un chemin absolu pour open 
                            if (realpath(chemin, chemin_reel) != NULL) {
                                printf("Le programme %s a ouvert le fichier interdit %s.\n", programmeExeNomEnfant, chemin_reel);
                                //si c'est un chemin relatif
                            } else {
                                printf("Le programme %s a ouvert le fichier interdit %s.\n", programmeExeNomEnfant, chemin);
                            }

                            //signal kill
                            kill(wpid, SIGKILL);
                            // 128 + signal kill(9) 
                            return 137;
                        }
                    }
                }
            }

            // changement de entrée sortie
            syscalEntrer = !syscalEntrer; 

            // Continuer l'enfant
            if (ptrace(PTRACE_SYSCALL, wpid, 0, 0) == -1) {
                perror(" le sys call continue");
                break;
            }
        }
    }

    return 0;
}
