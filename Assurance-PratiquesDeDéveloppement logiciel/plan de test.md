
Voici les différents types de soins et leurs catégories. Il est important de mentionner que si aucune des données n'est respectée lors de l'exécution du test, un message d'erreur suivra. Il est donc impératif que le numéro de soin, la catégorie de soin, le pourcentage respecte les données dans le tableau ci-dessous. En d'autres termes, ce tableau est la référence des tests des Contrats A-E et de leur vérification.

De plus, nous utilisons le plugin JaCoCo pour la couverture des tests unitaires. Les résultats sont disponibles sur : target/ici/jacoco/index.html.

| Numéro de soin | Catégorie de soin        | A    | B         | C    | D          | E    | Maximum mensuel |
|----------------|--------------------------|------|-----------|------|------------|------|-----------------|
| 0              | Massothérapie            | 25%  | 50% max 40$ | 90%  | 100% max 85$ | 15%  |                 |
| 100            | Ostéopathie              | 25%  | 50% max 50$ | 90%  | 100% max 75$ | 25%  | 250.00$         |
| 175            | Médecin généraliste privé|      |           |      |            |      | 200.00$         |
| 200            | Psychologie individuelle | 25%  | 100% max 70$ | 90%  | 100% max 100$ | 12%  | 250.00$         |
| [300..399]     | Soins dentaires          | 0%   | 50%       | 90%  | 100%       | 60%  |                 |
| 400            | Naturopathie, acupuncture| 0%   | 0%        | 90%  | 100% max 65$ | 25% max 15$ |           |
| 500            | Chiropratie              | 25%  | 50% max 50$ | 90%  | 100% max 75$ | 30% max 20$ | 150.00$         |
| 600            | Physiothérapie           | 40%  | 100%      | 90%  | 100% max 100$ | 15%  | 300.00$         |
| 700            | Orthophonie, ergothérapie| 0%  | 70%       | 90%  | 100% max 90$ | 22%  |                 |















| ID fonction 1  | Id test  | description test  | Préconditions  | Sortie attendue  | Priorit/  |
|:----------|:----------|:----------|:----------|:----------|:----------|
| EF-001   | CT-001    |CalculsTest   | Calcul remboursment    | le remboursement ou message d'erreur   | Haute  |
| EF-002   | CT-001   | CommandeStatistiqueTest   | Les Commande    | commande ou bien message d'erreur    |moyenne  |
| EF-003   | CT-001   | ContratATest    | nombre Valide   | montant du remboursement ou message erreur   | Haute  |
| EF-004   | CT-001   | ContratBTest   | nombre Valide    | montant du remboursement ou message erreur    | Haute   |
| EF-005   | CT-001   | ContratCTest    | nombre Valide    | montant du remboursement ou message erreur   | Haute    |
| EF-006    | CT-001    | ContratDTest    | nombre Valide   | montant du remboursement ou message erreur    | Haute    |
| EF-007    | CT-001    | ContratETest    | nombre Valide    | montant du remboursement ou message erreur   | Haute   |
| EF-008   | CT-001    | GestionnaireRemboursementsTest    | Type de remboursement    | le format ou bien message erreur    | faible  |
| EF-009   | CT-001    | MainTest    | Format du remboursement    | fonctionnement ou bien message erreur    | Haute
   |
| EF-010    | CT-001    |  MonnaieTest   | Objet    | objet de monaie ou message erreur    | moyenne    |
| EF-011    |           |  TraitementJsonTest         |   fichierJson        |  fichier json ou bien exeception(erreur)         |           |
| test |    VerificationsTest | Nb test |   categorie,date,montant,contrat  | renvoie categorie,date,montant,contrat oubien une erreur

| ID fonction 1  | Description des fonctionnalités|
|:----------|:---------- |
| EF-001   | calcule les tests  |
| EF-002   | test la commande pour les statistique  | 
| EF-003   | calcul du montant A  | 
| EF-004   | calcul du montant B    | 
| EF-005   | calcul du montant C   |
| EF-006    | calcul du montant D     | 
| EF-007    | calcul du montant R    | 
| EF-008   | un gestionnaire pour faciliter le format des et facilite la gestion des remboursement  | 
| EF-009   | test le main  | 
| EF-010    | creation dun objet Monnaie    | 
| EF-011    |  permet de tester les fichier Json         |         |           |
| test |    Verifie la validiter de  categorie,date,montant,contrat .  | 



| ID fonction 1  | dentifiants des suites de tests| Description des suites de tests|
|:----------|:---------- |:---------|
| EF-001   | ST-001 | CalculsTest|
| EF-002   | ST-002  | CommandeStatistiqueTest|
| EF-003   | ST-003  | ContratATest|
| EF-004   | ST-004   | ContratBTest|
| EF-005   | ST-005  | ContratCTest|
| EF-006    | ST-006    | ContratDTest|
| EF-007    | ST-007   | ContratETest|
| EF-008   | ST-008  | GestionnaireRemboursementsTest |
| EF-009   | ST-009  | MainTest |
| EF-010    | ST-010    | TraitementJsonTest |
| EF-011    |  ST-011   | VerificationsTest        |           |
| test |   ST-01§2  | 

