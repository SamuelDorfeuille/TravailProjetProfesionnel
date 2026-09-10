#include "ArbreAVL.h"
#include <cassert>
#include <iostream>

void test_union() {
    ArbreAVL<int> a;
    ArbreAVL<int> b;

    for (int x : {1, 3, 5, 7})
        a.inserer(x);

    for (int x : {2, 3, 6, 7, 8})
        b.inserer(x);

    ArbreAVL<int> u = a | b;

    assert(u.taille() == 7);

    for (int x : {1, 2, 3, 5, 6, 7, 8})
        assert(u.contient(x));

    std::cout << "[OK] union |\n";
}

void test_intersection() {
    ArbreAVL<int> a;
    ArbreAVL<int> b;

    for (int x : {1, 3, 5, 7, 9})
        a.inserer(x);

    for (int x : {2, 3, 5, 6, 9})
        b.inserer(x);

    ArbreAVL<int> inter = a & b;

    assert(inter.taille() == 3);

    for (int x : {3, 5, 9})
        assert(inter.contient(x));

    for (int x : {1, 2, 6, 7})
        assert(!inter.contient(x));

    std::cout << "[OK] intersection &\n";
}

void test_union_intersection_arbres_vides() {
    ArbreAVL<int> vide;
    ArbreAVL<int> plein;

    for (int x : {10, 20, 30})
        plein.inserer(x);

    ArbreAVL<int> u1 = vide | plein;
    ArbreAVL<int> u2 = plein | vide;
    ArbreAVL<int> i1 = vide & plein;
    ArbreAVL<int> i2 = plein & vide;

    assert(u1.taille() == 3);
    assert(u2.taille() == 3);
    assert(i1.vide());
    assert(i2.vide());

    std::cout << "[OK] union/intersection avec arbres vides\n";
}

int main() {
    std::cout << "===== TESTS UNION / INTERSECTION =====\n";
    test_union();
    test_intersection();
    test_union_intersection_arbres_vides();
    std::cout << "===== TESTS UNION / INTERSECTION PASSENT =====\n";
    return 0;
}
