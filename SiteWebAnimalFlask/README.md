## Installation

1. Cloner le repo
```bash
   git clone <url-du-repo>
   cd <nom-du-dossier-cree>
```

2. Créer et activer l'environnement virtuel
```bash
   python3 -m venv venv
   source venv/bin/activate      # Mac/Linux
   venv\Scripts\activate         # Windows
```

3. Installer les dépendances
```bash
   pip install -r requirements.txt
```

4. Lancer l'application
```bash
   make run
```
   ou
```bash
   export FLASK_APP=index.py
   flask run
```

5. Ouvrir dans le navigateur
```
   http://127.0.0.1:5000
```
Le tout réalisé en collaboration de mon cours programmation Web de l'Université du Québec à Montréal