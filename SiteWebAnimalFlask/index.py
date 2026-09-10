# Copyright 2024 <Samuel Dorfeuille dors19109607>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


from flask import Flask
from flask import render_template
from flask import g
from flask import redirect
from flask import url_for
from flask import abort
#from .database import Database
from database import Database
from flask import request
import random  



app = Flask(__name__, static_url_path="", static_folder="static")


def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        g._database = Database()
    return g._database


@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.disconnect()

@app.route('/')
def home():
    db = get_db()
    all_animaux = db.get_animaux()
    animaux_aleatoires = random.sample(all_animaux, min(5, len(all_animaux)))
    return render_template('PageAcceuil.html', animaux=animaux_aleatoires)

@app.route('/results')
def search_results():
    db = get_db()
    query = request.args.get('q', '').strip()
    results = []

    if query:
        all_animaux = db.get_animaux()
        ql = query.lower()
        results = [
            a for a in all_animaux
            if ql in a['nom'].lower()
        ]
    return render_template('PageResultat.html', results=results, query=query)

@app.route('/animal/<int:animal_id>')
def animal_page(animal_id):
    db = get_db()
    animal = db.get_animal(animal_id)
    if animal is None:
        abort(404)
    return render_template('PageAnimale.html', animal=animal)

@app.route('/formulaire', methods=['GET', 'POST'])
def acceuil():
    if request.method == 'GET':
        return render_template('formulaire.html') 
    nom         = request.form.get('nomAnimal',      '').strip()
    espece      = request.form.get('espece',         '').strip()
    race        = request.form.get('race',           '').strip()
    age         = request.form.get('age',            '').strip()
    description = request.form.get('description',    '').strip()
    courriel    = request.form.get('courriel',       '').strip()
    adresse     = request.form.get('adresse',        '').strip()
    ville       = request.form.get('ville',          '').strip()
    cp          = request.form.get('cp',             '').strip()

  
    if not all([nom, espece, race, age, description, courriel, adresse, ville, cp]):
        return redirect(url_for('home'))

    db = get_db()
    db.add_animal(
        nom,
        espece,
        race,
        age,      
        description,
        courriel,
        adresse,
        ville,
        cp
    )
    return redirect(url_for('home'))


@app.errorhandler(404)
def page_not_found(e):
    return render_template("PageErreur.html", message="La page voulu est pas trouver (404)"), 404
if __name__ == '__main__':
    app.run(debug=True)
