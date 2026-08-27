package equipe18;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class Verifications {
    // Method to verify if the category number is within the defined values
    public static boolean verifierCategorie(int cat) {
        if((Arrays.asList(0, 100, 150, 175, 200, 400, 500, 600, 700).contains(cat)) || (cat < 400 && cat > 299)){
            return true;
        } else {
            augmenterStatInvalide();
            throw new IllegalArgumentException(MSG_ERR_DONNEES_INVALIDES_CATEGORIE);

        }
    }

    // Method to verify the client contract
    public static void verifierContratClient(String contratClient) throws IllegalArgumentException {
        if (!contratClient.matches("[ABCDE]")) {
            augmenterStatInvalide();
            throw new IllegalArgumentException(MSG_ERR_DONNEES_INVALIDES_LETTRE);
        }
    }

    // Method to verify the claim date
    public static void verifierDateReclamation(String anneeMoisJourDemandes, String anneeMoisReclamation) throws IllegalArgumentException {
        if (!anneeMoisJourDemandes.contains(anneeMoisReclamation)) {
            augmenterStatInvalide();
            throw new IllegalArgumentException(MSG_ERR_DONNEES_INVALIDES_DATE);
        }
    }

    // Method to verify the amount with a dollar sign
    public static void verifierMontantAvec$(String montant) throws IllegalArgumentException {
        if (!montant.endsWith("$")) {
            augmenterStatInvalide();
            throw new IllegalArgumentException(MSG_ERR_DONNEES_INVALIDES_MONTANT);
        }
    }

    // Method to verify the client number
    public static void verifierNumeroClient(String numeroClient) throws IllegalArgumentException {
        if (!(numeroClient.length() == 6 && numeroClient.matches("[0-9]+"))) {
            augmenterStatInvalide();
            throw new IllegalArgumentException(MSG_ERR_DONNEES_INVALIDES_NUMERO_CLIENT);
        }
    }

    //method to increase the stats counter when there's an error message
    public static void augmenterStatInvalide(){
        try {
            JSONObject statJson = equipe18.CommandeStatistique.lireStatistiquesExistantes();
            int statInvalide =  statJson.getInt("Nombre de réclamation rejetées");
            statInvalide = statInvalide + 1;
            statJson.put("Nombre de réclamation rejetées", statInvalide);
            equipe18.CommandeStatistique.ecrireDansFichier(statJson);
        } catch (IOException e) {
            System.err.println("Unable to read the statistique.json file: " + e.getMessage());
            System.exit(1);
        }
    }


    // Error messages
    public static final String MSG_ERR_DONNEES_INVALIDES_LETTRE = "La lettre entrée est invalide, car elle ne figure pas exactement dans les options.";
    public static final String MSG_ERR_DONNEES_INVALIDES_DATE = "La date entrée est invalide.";
    public static final String MSG_ERR_DONNEES_INVALIDES_MONTANT = "Le Montant ne peut être traité, car il est invalide.";
    public static final String MSG_ERR_DONNEES_INVALIDES_NUMERO_CLIENT = "Le numéro du client n'est pas valide.";
    public static final String MSG_ERR_DONNEES_INVALIDES_CATEGORIE = "La catégorie entrée n'est pas dans les options mentionnées.";
}
