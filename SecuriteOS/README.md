# TP1 - DLP (Automne 2025)

En sécurité informatique un DLP (Data Loss Preventation) est un utilitaire qui limite les fuites de données confidentielles. Ce TP vous demandera de coder un DLP pour syscall qui bloque l'ouverture de fichier contenant le mot "CONFIDENTIEL" et l'écriture de données qui contiennent "CONFIDENTIEL".

Lorsqu'un processus surveillé fait un de ces accès non autorisés, le processus doit être tué et la violation de la politique doit être rapportée dans la sortie du programme `dlp`.

## Description de l'outil

```
./dlp EXECUTABLE
```

L'appel à l'outil doit lancer le programme `EXECUTABLE` et le surveiller avec l'aide de `ptrace`. Le programme va être lancé comme un processus fils de `dlp`. Une fois lancé, les actions suivantes doivent être effectuées:

 - Lorque `EXECUTABLE` fait un appel système pour ouvrir un fichier (`open` ou `openat`), `dlp` vérifie si le fichier demandé contient le mot "CONFIDENTIEL". Si une violation est détectée, vous devez terminé le processus (SIG_KILL) en violation et écrire dans la sortie `Le programme EXECUTABLE a ouvert le fichier interdit FICHIER.` où `EXECUTABLE` est le nom du programme qui a fait l'accès interdit et `FICHIER` est le fichier qui contient "CONFIDENTIEL".
 - Lorque `EXECUTABLE` fait un appel système pour écrire des données (`write`), `dlp` vérifie si le contenu contient le mot "CONFIDENTIEL". Si une violation est détectée, vous devez terminé le processus (SIG_KILL) en violation et écrire dans la sortie `Le programme EXECUTABLE a fait une écriture interdite dans le fichier FICHIER.` où `EXECUTABLE` est le nom du programme qui a fait l'accès interdit et `FICHIER` est le fichier dans lequel l'écriture est tentée.
 - Lorsque `EXECUTABLE` crée un processus fils, celui-ci doit aussi être suivi par `dlp`.
 - Lorsque `EXECUTABLE` se termine normalement, `dlp` doit se terminer avec le même code d'erreur que `EXECUTABLE`.
 - Lorsque `EXECUTABLE` se termine avec un signal, `dlp` doit se terminer avec comme code d'erreur 128 + `code du signal`.

## Directives d'implémentation

### Appels système

**Vous devez utiliser** les appels système suivants.

- `fork` pour créer un nouveau processus et l'appel système `execve` pour le recouvrement.
  - Vous pouvez également utiliser l'une des fonctions de librairies `execl`, `execlp`, `execle`, `execv` ou `execvp` pour le recouvrement.
- `wait` ou `waitpid` pour surveiller les processus enfants.
  - Vous devez également utiliser leur macro pour récupérer les informations communiquées par le système d'exploitation.
- `ptrace` pour surveiller des processus enfants (voir plus bas pour plus d'explications).
- Noubliez pas de traiter les erreurs de chaque appel système, s'il y a lieu.

### Appel système `ptrace`

L'appel système principal que vous devez utiliser est `ptrace`.
C'est un appel système bas niveau offert par le noyau Linux (et la plupart des systèmes Unix) qui sert à implémenter les débogueurs (comme `gdb`) ou d'autres outils comme `strace`.
C'est un appel système qui fait beaucoup de choses et est assez complexe.
Dans le cadre du TP on reste à une utilisation très superficielle de `ptrace`.

`ptrace` est un appel système très puissant, car il permet à un processus de prendre le contrôle d'un autre processus, y compris sa mémoire et le code machine qu'il exécute.

La logique principale de `ptrace` c'est que le processus surveillé (*ptracé* ou *observé*) s'exécute normalement jusqu'à ce qu'il se passe un événement particulier (réception d'un signal, appel système, etc.).
À ce moment-là, le processus surveillé passe dans un état bloqué particulier (*ptrace stopped*) qui apparait sous un `t` avec la commande `ps`.

Le processus qui surveille (le *traceur*) est notifié que le tracé est arrêté avec l'appel système `wait`.
Plutôt que de créer un appel système dédié, les concepteurs ont réutilisé cet appel système, ce qui peut être déroutant.

#### Directives d'utilisation de ptrace

Dans le cadre du TP, seul le sous-ensemble suivant des requêtes `ptrace` devra être utilisé

- `PTRACE_TRACEME` pour tracer la commande.
- `PTRACE_SETOPTIONS` avec les flags `PTRACE_O_EXITKILL` et `PTRACE_O_TRACEFORK`.
- `PTRACE_GETREGS` pour récupérer les valeurs des registres y compris celle du registre.
  - En particulier le registre `RAX` qui contiendra, lors d'un appel système, le numéro de l'appel système utilisé à l'appel et la valeur retourné à la fin. Vous aurez besoin d'une structure `struct user_regs_struct` pour récupérer cette valeur.
  - Attention, ça fonctionne seulement sur des processeurs avec l'architecture `x86_64`.
- `PTRACE_SYSCALL` pour que le processus qui surveille soit prévenu à chaque début ou fin d'un appel système des processus surveillés et faire continuer l'exécution du processus surveillé stoppé.
- `PTRACE_PEEKDATA` pour lire des données dans le processus surveillé.

Pour déterminer le chemin des exécutables, consultez `/proc/PID/exe` avec l'appel système `readlink`.
Avec `PTRACE_O_TRACEEXEC`, le processus surveillé est stoppé après que l'exécutable a été chargé (et donc `/proc/PID/exe` est celui du nouvel exécutable).

Voici quelques conseils pour réussir à développer ce programme.

- Lisez la page de manuel de `ptrace`. Elle est dense et tout ne vous sera pas utile, mais il ne faut pas ignoner les parties qui le seront.
- Commencez par votre développement avec `PTRACE_O_EXITKILL` et ajoutez `PTRACE_O_TRACEFORK` dans un second temps.
- Attention, `PTRACE_TRACEME` ne stoppe pas l'appelant, vous pouvez donc faire `raise(SIGSTOP);` juste après.
- Attention, remettez le signal éventuel dans `PTRACE_SYSCALL`.

### Précisions

 - Vous devez utiliser les appels bas niveau pour faire la lecture de fichier (`open`, `read` et `close`).
 - Vous pouvez utiliser les fonctions de la libc pour
    - La gestion de la mémoire (ex.: `malloc`, `free`, `memset`, `memcpy`).
    - Les fonctions utilitaires de string (ex.: `strlen`, `strstr`, etc.)
    - La résolution de chemin relatif peut utiliser `realpath`. Les liens symboliques doivent être résolus avec `readlink`.

### Éléments à faire attention

Les éléments suivants sont à faire attention lors de l'implémentation:

 - Le dossier courant d'exécution d'un processus enfant n'est pas nécessairement le même que celui du processus parent.
 - Si un processus enfant fait un appel à `exec*`, le programme qui est exécuté par ce processus change.
 - Les descripteurs de fichier sont propres à chaque processus.

## Acceptation et remise du TP

Pour la remise, vous devez

 - Faire un fork de ce dépôt et y pousser votre code.
 - Indiquez votre nom et code permanent dans le fichier `dlp.c`.
 - Si vous avez fait le travail en équipe de 2, indiquez le nom des coéquipiers dans `dlp.c`.
 - Ajouter Olivier Arteau (enseignant) comme contributeur.
 - Ajouter Jaber Absaoui (groupe 020) ou Sublime Tshimanga Tshimpangila (groupe 030) comme contributeur.

### Tests

Les tests publics seront validés avec les commandes suivantes dans l'environnement labunix.

```
make
make test
```

Le tout réalisé en collaboration de mon cours Outils et pratiques de développement logiciel de l'Université du Québec à Montréal

