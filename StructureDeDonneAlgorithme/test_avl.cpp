#include "arbreAVL.h"
#include <cassert>
#include <iostream>

void test_arbre_vide() {
    ArbreAVL<int> arbre;

    assert(arbre.vide());
    assert(arbre.taille() == 0);
    assert(!arbre.contient(10));

    std::cout << "[OK] arbre vide\n";
}

void test_insertion() {
    ArbreAVL<int> arbre;

    arbre.inserer(10);
    arbre.inserer(5);
    arbre.inserer(15);
    arbre.inserer(3);
    arbre.inserer(7);

    assert(!arbre.vide());
    assert(arbre.taille() == 5);
    assert(arbre.contient(10));
    assert(arbre.contient(5));
    assert(arbre.contient(15));
    assert(arbre.contient(3));
    assert(arbre.contient(7));
    assert(!arbre.contient(99));

    // Les doublons ne doivent pas augmenter la taille.
    arbre.inserer(10);
    assert(arbre.taille() == 5);

    std::cout << "[OK] insertion / contient / doublons\n";
}

void test_rotations() {
    // Rotation gauche-droite (cas gauche-gauche)
    {
        ArbreAVL<int> arbre;
        arbre.inserer(30);
        arbre.inserer(20);
        arbre.inserer(10);

        assert(arbre.taille() == 3);
        assert(arbre.contient(10));
        assert(arbre.contient(20));
        assert(arbre.contient(30));
    }

    // Rotation droite-gauche (cas droite-droite)
    {
        ArbreAVL<int> arbre;
        arbre.inserer(10);
        arbre.inserer(20);
        arbre.inserer(30);

        assert(arbre.taille() == 3);
        assert(arbre.contient(10));
        assert(arbre.contient(20));
        assert(arbre.contient(30));
    }

    // Double rotation gauche-droite.
    {
        ArbreAVL<int> arbre;
        arbre.inserer(30);
        arbre.inserer(10);
        arbre.inserer(20);

        assert(arbre.taille() == 3);
        assert(arbre.contient(10));
        assert(arbre.contient(20));
        assert(arbre.contient(30));
    }

    // Double rotation droite-gauche.
    {
        ArbreAVL<int> arbre;
        arbre.inserer(10);
        arbre.inserer(30);
        arbre.inserer(20);

        assert(arbre.taille() == 3);
        assert(arbre.contient(10));
        assert(arbre.contient(20));
        assert(arbre.contient(30));
    }

    std::cout << "[OK] rotations AVL\n";
}

void test_suppression() {
    ArbreAVL<int> arbre;

    for (int x : {50, 30, 70, 20, 40, 60, 80})
        arbre.inserer(x);

    assert(arbre.taille() == 7);

    // Feuille
    arbre.supprimer(20);
    assert(arbre.taille() == 6);
    assert(!arbre.contient(20));

    // Noeud avec un enfant / restructuration possible
    arbre.supprimer(30);
    assert(arbre.taille() == 5);
    assert(!arbre.contient(30));

    // Racine avec deux enfants
    arbre.supprimer(50);
    assert(arbre.taille() == 4);
    assert(!arbre.contient(50));

    // Élément inexistant : rien ne doit changer.
    arbre.supprimer(999);
    assert(arbre.taille() == 4);

    std::cout << "[OK] suppression\n";
}

void test_vider() {
    ArbreAVL<int> arbre;

    for (int i = 0; i < 20; ++i)
        arbre.inserer(i);

    assert(arbre.taille() == 20);

    arbre.vider();

    assert(arbre.vide());
    assert(arbre.taille() == 0);
    assert(!arbre.contient(10));

    // On doit pouvoir réutiliser l'arbre après vider().
    arbre.inserer(42);
    assert(!arbre.vide());
    assert(arbre.taille() == 1);
    assert(arbre.contient(42));

    std::cout << "[OK] vider\n";
}

void test_copie() {
    ArbreAVL<int> original;

    for (int x : {40, 20, 60, 10, 30, 50, 70})
        original.inserer(x);

    // Constructeur de copie
    ArbreAVL<int> copie(original);

    assert(copie == original);
    assert(copie.taille() == original.taille());

    // Modifier la copie ne doit pas modifier l'original.
    copie.inserer(25);
    assert(copie.contient(25));
    assert(!original.contient(25));
    assert(copie.taille() == original.taille() + 1);

    std::cout << "[OK] constructeur de copie\n";
}

void test_affectation() {
    ArbreAVL<int> a;
    ArbreAVL<int> b;

    for (int x : {10, 5, 15})
        a.inserer(x);

    for (int x : {100, 50})
        b.inserer(x);

    b = a;

    assert(b == a);
    assert(b.taille() == 3);
    assert(b.contient(5));
    assert(b.contient(10));
    assert(b.contient(15));
    assert(!b.contient(100));

    // Test d'auto-affectation.
    b = b;
    assert(b.taille() == 3);
    assert(b.contient(10));

    std::cout << "[OK] opérateur =\n";
}

void test_egalite() {
    ArbreAVL<int> a;
    ArbreAVL<int> b;
    ArbreAVL<int> c;

    for (int x : {20, 10, 30})
        a.inserer(x);

    for (int x : {20, 10, 30})
        b.inserer(x);

    // Même structure et mêmes valeurs.
    assert(a == b);

    c.inserer(10);
    c.inserer(20);
    c.inserer(30);

    // Ces insertions produisent aussi une structure équilibrée.
    assert(c == a);

    c.inserer(40);
    assert(!(c == a));

    std::cout << "[OK] opérateur ==\n";
}

void test_types_generiques() {
    ArbreAVL<double> arbre;

    arbre.inserer(3.14);
    arbre.inserer(1.5);
    arbre.inserer(9.8);

    assert(arbre.taille() == 3);
    assert(arbre.contient(3.14));
    assert(!arbre.contient(2.0));

    std::cout << "[OK] template avec double\n";
}

int main() {
    std::cout << "===== TESTS ArbreAVL =====\n";

    test_arbre_vide();
    test_insertion();
    test_rotations();
    test_suppression();
    test_vider();
    test_copie();
    test_affectation();
    test_egalite();
    test_types_generiques();

    std::cout << "\n===== TOUS LES TESTS PASSENT =====\n";
    return 0;
}
