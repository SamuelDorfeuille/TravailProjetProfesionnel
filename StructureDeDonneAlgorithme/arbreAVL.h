
#ifndef __ARBREAVL2_H__
#define __ARBREAVL2_H__

#include <iostream>
#include <cassert>

//Samuel Dorfeuille DORS19109607
//Alibay Patel Houssen ALIH68270406
template <class T>
class ArbreAVL {
public:
    // Constructeurs / destructeur
    ArbreAVL() : racine(nullptr) {}

    ArbreAVL(const ArbreAVL& autre) : racine(nullptr) {
        copier(autre.racine, racine);
    }

    ~ArbreAVL() { vider(racine); }

    // Fonctions publiques
    void inserer(const T& element); 
    void supprimer(const T& element); 
    bool contient(const T& element) const;

    bool vide() const {
        return racine == nullptr;
    }
    void vider(); 
    void afficher() const;

    int taille() const; 
    ArbreAVL<T>& operator = (const ArbreAVL<T>& autre); 
    bool operator == (const ArbreAVL<T>& autre) const;  

    ArbreAVL<T> operator |  (const ArbreAVL<T>& autre) const;   // Union de 2 arbres 
    ArbreAVL<T> operator &  (const ArbreAVL<T>& autre) const;   // intersection de 2 arbres 

private:
    class Noeud {
    public:
        T valeur;
        int equilibre;
        Noeud* gauche;
        Noeud* droite;

        Noeud(const T& val) : valeur(val), equilibre(0), gauche(nullptr), droite(nullptr) {}
    };

    Noeud* racine;

    // ------------------- Fonctions internes -------------------
    void copier(const Noeud* src, Noeud*& dest) const;
    void vider(Noeud*& noeud);
    bool inserer(Noeud*& noeud, const T& element);
    bool supprimer(Noeud*& noeud, const T& element);

    void rotationGaucheDroite(Noeud*& noeud);
    void rotationDroiteGauche(Noeud*& noeud);

    int compter(const Noeud* noeud) const;
    bool comparer(const Noeud* noeud_a, const Noeud* noeud_b) const;

    void preparer_afficher(const Noeud* n, int profondeur, int& rang, T* elements, int* profondeurs) const;

    static int max(int a, int b) { return (a > b) ? a : b; }
    static int min(int a, int b) { return (a < b) ? a : b; }
};

// --------------------------- Implémentations ------------------------------
// Insertion d'un élément dans l'arbre 
//
template <class T>
void ArbreAVL<T>::inserer(const T& element) {
    inserer(racine, element);
}

// retourne true si la hauteur du sous-arbre a augmenté
// equilibre = h(gauche) - h(droite)
template <class T>
bool ArbreAVL<T>::inserer(Noeud*& noeud, const T& element) {
// si le noeud est null ajoute le noed avec l element
    if (noeud == nullptr) {
        noeud = new Noeud(element);
        return true;
    }
// hauteur si l arbre change de hauter
    bool hauteurChangee = false;


// recherche l element pour inserer et faire augmenter la hauter
    if (element < noeud->valeur) {
        if (inserer(noeud->gauche, element)) {
            noeud->equilibre++;
            hauteurChangee = (noeud->equilibre != 0);
        }
    } else if (element > noeud->valeur) {
        if (inserer(noeud->droite, element)) {
            noeud->equilibre--;
            hauteurChangee = (noeud->equilibre != 0);
        }
    }
    else {
        return false;
    }

    // Les rotation pour depassement equilibre
    if (noeud->equilibre == 2) {
        if (noeud->gauche &&noeud->gauche->equilibre >= 0)
           //cas Gauche gauche 
            rotationGaucheDroite(noeud);
        else {
            // cas gauche droite
            rotationDroiteGauche(noeud->gauche);
            rotationGaucheDroite(noeud);
        }
     return false;
    } else if (noeud->equilibre == -2) {
        if (noeud->droite &&noeud->droite->equilibre <= 0)
        //cas droite droite
            rotationDroiteGauche(noeud);
        else {
            // cas droite gauchce
            rotationGaucheDroite(noeud->droite);
            rotationDroiteGauche(noeud);
        }
        return false;
    }

    return hauteurChangee;
}


// ----------------------------------------------------------------------------
// Supression d'un élément 
// 
// Implémentation complète - ne pas modifier 
template <class T>
void ArbreAVL<T>::supprimer(const T& element) {
    supprimer(racine, element);
}

// ----------------------------------------------------------------------------
//
template <class T>
bool ArbreAVL<T>::contient(const T& element) const {
    // pointeur sur la racine
    Noeud* courant = racine;
    bool elementTrouve = false;

    while (courant != nullptr) {  
        //si l'element contient l element
        if (element == courant->valeur) {  
            return true;          
        }
        //recherche l'éelement iteratif a gauche
        else if (element < courant->valeur) {
            courant = courant->gauche;
        }
        //recherche l'éelement iteratif a droite
        else { 
            courant = courant->droite;
        }
    }
//si lelement pas trouver ou racine vide
    return elementTrouve; 
}
// ---------------------------------------------------------------------------
// Copie le noeud dest dans src, puis lance récursivement la copies des 2 
// sous-arbres (gauche et droit) 
//
template <class T>
void ArbreAVL<T>::copier(const Noeud* src, Noeud*& dest) const {

//case de base pour recursion (si noeud vide rien a retouner)
    if (src ==nullptr){
    dest =nullptr;
    return ;
    }


//copie les valeur de la destinatiopn a la source
dest = new Noeud(src->valeur); 
dest->equilibre=src->equilibre;

//appel recursif pour parcourir les element de chaque branche
copier(src->gauche,dest->gauche);
copier(src->droite,dest->droite);
}


// ----------------------------------------------------------------------------
// vide l'arbre en libérant l'ensemble des objets (noeuds) 
//
template <class T>
void ArbreAVL<T>::vider() {
    
vider(racine);
    

}

// ----------------------------------------------------------------------------
// méthode interne pour vider l'arbre en permettant les appels récursifs 
// 
//
template <class T>
void ArbreAVL<T>::vider(Noeud*& noeud) {
//cas de base si pointeur null alors rien a retoouner
if (noeud == nullptr){
    return;
}
//vider les element de l'arbre a gauche et a droite
vider(noeud->gauche);
vider(noeud->droite);

delete noeud;
//supprime le pointeur de la reference null
noeud =nullptr;
}
// ----------------------------------------------------------------------------
// méthode interne de comparaison de 2 arbres en permettant les appels récursifs
// noeud_a et noeud_b : racines des 2 arbres 
//
// retourne true si égalité, false sinon 
//
template<class T>
bool ArbreAVL<T>::comparer(const Noeud* noeud_a, const Noeud* noeud_b) const {

//les cas de base
// si labre est null
    if (noeud_a == nullptr && noeud_b == nullptr) {
        return true;
    }
    //si un des deux arbres est null
    if (noeud_a == nullptr || noeud_b == nullptr) {
        return false;
    }
    //si valeur diffente
    if (noeud_a->valeur != noeud_b->valeur) {
        return false;
    }
    return comparer(noeud_a->gauche, noeud_b->gauche) &&
           comparer(noeud_a->droite, noeud_b->droite);
}
// -------------------------------------------------------------------------------
// Détermine la taille de l'arbre 
// Implémentation compléte - Ne pas modifier 
// 
template <class T>
int ArbreAVL<T>::taille() const {
    return compter(racine);
}

// -------------------------------------------------------------------------------
// fait le compte des éléments de l'arbre 
//
template <class T>
int ArbreAVL<T>::compter(const Noeud* noeud) const {
    int cpt =0;
     if (noeud == nullptr) {
        return 0;
    }
// increment du compteur avec recusion
    cpt = 1 + compter(noeud->droite) + compter(noeud->gauche);
    return cpt;
}

// ----------------------------------------------------------------------------
// Opérateur d'affectation 
//
template <class T> ArbreAVL<T>& ArbreAVL<T>::operator=(const ArbreAVL& autre) {
    //vide et copie
 if (this != &autre){    
    vider();
    copier(autre.racine, racine);

 }
 return *this;
}

// ----------------------------------------------------------------------------
// Opérateur de comparaison 
// Implémentation complète - ne pas modifier 
//
template <class T> bool ArbreAVL<T>::operator==(const ArbreAVL& autre) const {
    
    return comparer(racine, autre.racine);
}

// -------------------------------------------------------------------------------
// Opérateur réalise la fusion (union) de l'arbre courant avec l'arbre recu en 
// paramètre 
// 
template<class T>
ArbreAVL<T> ArbreAVL<T>::operator | (const ArbreAVL<T>& autre) const {
    ArbreAVL<T> nouveau;

//si racinde vide retourne l autre arbre
    if (racine == nullptr) {
        return autre;
    }
//inserer la racine valeur dans la arbre nouveau
    nouveau.inserer(racine->valeur);
//creation arbre temporaire 2 arbres/branche
    ArbreAVL<T> droite, gauche;
    gauche.racine = racine->gauche;
    droite.racine = racine->droite;
//recursion sur les 2 abres/branches 
    nouveau = nouveau | gauche.operator|(autre);
    nouveau = nouveau | droite.operator|(autre);

// retire les gauche droite racine pour quand il y a destruction
    gauche.racine = nullptr;
    droite.racine = nullptr;

    return nouveau;
}

// -------------------------------------------------------------------------------
// Opérateur détermine les éléments en commun (intersection) entre l'arbre courant et 
// l'arbre reçu en paramètre. 
//
template<class T>
ArbreAVL<T> ArbreAVL<T>::operator & (const ArbreAVL<T>& autre) const {
    ArbreAVL<T> nouveau;

// regarde si la abre courant est le meme que larbre autre
    if (this == &autre) {
        return *this;
    }
// si arbre vide alors retoune arbre vide
    if (racine == nullptr) {
        return nouveau;
    }

//ajoute si les valeur sont similaire
    if (autre.contient(racine->valeur)) {
        nouveau.inserer(racine->valeur);
    }
//creation arbre temporaire 2 arbres/branche
    ArbreAVL<T> gauche, droite;
    droite.racine = racine->droite;
    gauche.racine = racine->gauche;
//recursion sur les 2 abres/branches
    nouveau = nouveau | gauche.operator&(autre);
    nouveau = nouveau | droite.operator&(autre);

// retire les gauche droite racine pour quand il y a destruction
    droite.racine = nullptr;
    gauche.racine = nullptr;

    return nouveau;
}

// ------------------------ FIN IMPLÉMENTATION----------------------------------
// =============================================================================

// ---------------------------------- Suppression AVL --------------------------
// Méthode interne pour la suppression 
//
// Implémentation complète : ne pas modifier 
template <class T>
bool ArbreAVL<T>::supprimer(Noeud*& noeud, const T& element) {
    if (!noeud) return false;

    bool hauteurChangee = false;

    if (element < noeud->valeur) {
        if (supprimer(noeud->gauche, element)) {
            noeud->equilibre--;
            hauteurChangee = true;
        }
    }
    else if (element > noeud->valeur) {
        if (supprimer(noeud->droite, element)) {
            noeud->equilibre++; hauteurChangee = true;
        }
    }
    else {
        if (!noeud->gauche || !noeud->droite) {
            Noeud* temp = noeud;
            noeud = (noeud->gauche) ? noeud->gauche : noeud->droite;
            delete temp;
            return true;
        }
        else {
            Noeud* succ = noeud->droite;
            while (succ->gauche) succ = succ->gauche;
            noeud->valeur = succ->valeur;
            if (supprimer(noeud->droite, succ->valeur)) {
                noeud->equilibre++;
                hauteurChangee = true;
            }
        }
    }

    if (noeud) {
        if (noeud->equilibre == 2) {

            if (noeud->gauche->equilibre >= 0)
                rotationGaucheDroite(noeud);
            else {
                rotationDroiteGauche(noeud->gauche);
                rotationGaucheDroite(noeud);
            }
        }
        else if (noeud->equilibre == -2) {
            if (noeud->droite && noeud->droite->equilibre <= 0)
                rotationDroiteGauche(noeud);
            else {
                rotationGaucheDroite(noeud->droite);
                rotationDroiteGauche(noeud);
            }
        }
        else {
            if (noeud->equilibre != 0) hauteurChangee = false;
        }
    }

    return hauteurChangee;
}

// ------------------------------ Rotations --------------------------------
// Rotation Gauche-Droite
// Implémentation complète : ne pas modifier 
//
template <class T>
void ArbreAVL<T>::rotationGaucheDroite(Noeud*& racineSousArbre) {
    Noeud* temp = racineSousArbre->gauche;
    int ea = temp->equilibre;
    int eb = racineSousArbre->equilibre;
    int neb = -max(ea, 0) - 1 + eb;
    int nea = ea + min(neb, 0) - 1;
    temp->equilibre = nea;
    racineSousArbre->equilibre = neb;
    racineSousArbre->gauche = temp->droite;
    temp->droite = racineSousArbre;
    racineSousArbre = temp;
}
// ----------------------------------------------------------------------------
// Rotation Droite-Gauche
// Implémentation complète : ne pas modifier 
//
template <class T>
void ArbreAVL<T>::rotationDroiteGauche(Noeud*& racineSousArbre) {
    Noeud* temp = racineSousArbre->droite;
    int ea = temp->equilibre;
    int eb = racineSousArbre->equilibre;
    int neb = eb - min(ea, 0) + 1;
    int nea = ea + max(neb, 0) + 1;
    temp->equilibre = nea;
    racineSousArbre->equilibre = neb;
    racineSousArbre->droite = temp->gauche;
    temp->gauche = racineSousArbre;
    racineSousArbre = temp;
}

// ----------------------------------------------------------------------------
// Méthode utilitaire pour l'affichage d'un arbre 
// Implémentation complète - ne pas modifier 
//
template <class T> void ArbreAVL<T>::afficher() const {
    std::cout << "Contenu de l'arbre AVL (";
    int n = taille();
    std::cout << n << " noeuds) :\n";

    T* elements = new T[n];
    int* profondeurs = new int[n];

    n = 0;
    preparer_afficher(racine, 0, n, elements, profondeurs);
    for (int p = 0;; p++) {
        bool derniereprofondeur = true;
        for (int i = 0; i < n; i++) {
            if (profondeurs[i] == p) {
                std::cout << elements[i];
                derniereprofondeur = false;
            }
            std::cout << '\t';
        }
        std::cout << '\n';
        if (derniereprofondeur)
            break;
    }

    delete[] elements;
    delete[] profondeurs;
    std::cout << "--------------" << std::endl;
}


// ----------------------------------------------------------------------------
// Méthode utilitaire utilisée pour l'affichage d'un arbre 
// Implémentation complète - ne pas modifier 
//
template <class T>
void ArbreAVL<T>::preparer_afficher(const Noeud* n, int profondeur, int& rang,
    T* elements, int* profondeurs) const {
    if (n == nullptr)
        return;

    preparer_afficher(n->gauche, profondeur + 1, rang, elements, profondeurs);
    elements[rang] = n->valeur;
    profondeurs[rang] = profondeur;
    rang++;
    preparer_afficher(n->droite, profondeur + 1, rang, elements, profondeurs);
}
// ----------------------------------------------------------------------------
#endif

