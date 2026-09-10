#!/usr/bin/env bats

load test_helper

TMP_DIR=`mktemp -d`
TMP_FICHIER_CONFIDENTIEL="$TMP_DIR/confidentiel"
TMP_EXE_OPEN="$TMP_DIR/open"
TMP_EXE_WRITE="$TMP_DIR/write"
TMP_EXE_NORMAL="$TMP_DIR/normal"
TMP_EXE_ERREUR="$TMP_DIR/erreur"
TMP_EXE_EXECVE="$TMP_DIR/execve"
TMP_EXE_DLP="$TMP_DIR/dlp"
TMP_DATA="$TMP_DIR/data"

@test "fichier-confidentiel" {
    run bash -c "echo 'CONFIDENTIEL' > $TMP_FICHIER_CONFIDENTIEL"
    run bash -c "cp tests/open $TMP_EXE_OPEN"
    run bash -c "cp ./dlp $TMP_EXE_DLP"
    run bash -c "cd $TMP_DIR && $TMP_EXE_DLP $TMP_EXE_OPEN"
    checki 137 <<FIN
Le programme $TMP_EXE_OPEN a ouvert le fichier interdit $TMP_FICHIER_CONFIDENTIEL.
FIN
}

@test "ecriture-confidentiel" {
    run bash -c "cp tests/write $TMP_EXE_WRITE"
    run bash -c "cp ./dlp $TMP_EXE_DLP"
    run bash -c "cd $TMP_DIR && $TMP_EXE_DLP $TMP_EXE_WRITE"
    checki 137 <<FIN
Le programme $TMP_EXE_WRITE a fait une écriture interdite dans le fichier $TMP_DATA.
FIN
}

@test "normal" {
    run bash -c "cp tests/normal $TMP_EXE_NORMAL"
    run bash -c "cp ./dlp $TMP_EXE_DLP"
    run bash -c "cd $TMP_DIR && $TMP_EXE_DLP $TMP_EXE_NORMAL"
    checki 0 <<FIN
FIN
}

@test "erreur" {
    run bash -c "cp tests/erreur $TMP_EXE_ERREUR"
    run bash -c "cp ./dlp $TMP_EXE_DLP"
    run bash -c "cd $TMP_DIR && $TMP_EXE_DLP $TMP_EXE_ERREUR"
    checki 1 <<FIN
FIN
}

@test "execve" {
    run bash -c "echo 'CONFIDENTIEL' > $TMP_FICHIER_CONFIDENTIEL"
    run bash -c "cp tests/execve $TMP_EXE_EXECVE"
    run bash -c "cp ./dlp $TMP_EXE_DLP"
    run bash -c "cd $TMP_DIR && $TMP_EXE_DLP $TMP_EXE_EXECVE"
    checki 137 <<FIN
Le programme /usr/bin/cat a ouvert le fichier interdit $TMP_FICHIER_CONFIDENTIEL.
FIN
}