document.getElementById("validationFormulaire").addEventListener("submit", function(event) {
    event.preventDefault();

    document.querySelectorAll(".erreur").forEach(e => e.textContent = "");

    let nomAnimal = document.getElementById("nomAnimal").value.trim().toUpperCase();
    let espece = document.getElementById("espece").value.trim().toUpperCase();
    let race = document.getElementById("race").value.trim().toUpperCase();
    let age = parseInt(document.getElementById("age").value.trim()); 
    let description = document.getElementById("description").value.trim();
    let courriel = document.getElementById("courriel").value.trim();
    let adresse = document.getElementById("adresse").value.trim();
    let ville = document.getElementById("ville").value.trim().toUpperCase();
    let cp = document.getElementById("cp").value.trim().toUpperCase();

    let regexCourriel = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let regexCodePostal = /^[A-Za-z]\d[A-Za-z][ ]?\d[A-Za-z]\d$/;

    let champsAScanner = [nomAnimal, espece, race, description, courriel, adresse, ville, cp];
    for (let champ of champsAScanner) {
        if (champ.includes(',')) {
            document.getElementById("messageErreurVirgule").textContent = "Aucun champ ne peut contenir une virgule.";
            return;
        }
    }

    console.log("Âge de l'animal :", age);


    if (isNaN(age) || age < 0 || age > 30) {
        document.getElementById("messageErreurNaissance").textContent = "L'âge de l'animal doit être un nombre entre 0 et 30.";
        return;
    }

    if (nomAnimal.length < 3 || nomAnimal.length > 20) {
        document.getElementById("messageErreurNom").textContent = "Le nom de l'animal doit contenir entre 3 et 20 caractères.";
        return;
    }

    if (!regexCourriel.test(courriel)) {
        document.getElementById("messageErreurCourriel").textContent = "L'adresse courriel doit avoir un format valide.";
        return;
    }

    if (!regexCodePostal.test(cp)) {
        document.getElementById("messageErreurCP").textContent = "Le code postal doit respecter le format canadien (ex: H2X 1Y4).";
        return;
    }

    console.log("Âge de l'animal :", age);


    alert("Formulaire soumis avec succès !");
});