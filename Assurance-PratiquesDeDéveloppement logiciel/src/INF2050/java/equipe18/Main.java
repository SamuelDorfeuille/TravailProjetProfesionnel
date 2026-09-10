package equipe18;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 1) {
            if (args[0].equals("-S")) {
                equipe18.CommandeStatistique.afficherStatistiques();
                System.exit(0);
            } else if (args[0].equals("-SR")) {
                equipe18.CommandeStatistique.reinitialiserStatistiques();
                System.exit(0);
            }
        }
        if (args.length != 2) {
            throw new IllegalArgumentException("le nombre d'argument est incorrect");
        }

        File jsonFile = new File("src/INF2050/resources/Assurance.json");
        equipe18.TraitementJson traitementOutputJson = new equipe18.TraitementJson(jsonFile.getAbsolutePath(), "src/INF2050/resources/Output.json");
        traitementOutputJson.load();

        equipe18.Verifications.verifierContratClient(equipe18.TraitementJson.obtenirLettreContrat());
        equipe18.Verifications.verifierNumeroClient(equipe18.TraitementJson.obtenirNumeroClient());

        ArrayList<JSONObject> reclamationJSONArray = traitementOutputJson.getListRec();

        JSONObject fichierSortie = equipe18.GestionnaireRemboursements.traiterRemboursements(reclamationJSONArray, equipe18.TraitementJson.obtenirLettreContrat(), equipe18.TraitementJson.obtenirNumeroClient(), equipe18.TraitementJson.obtenirMois());
        equipe18.TraitementJson.save(fichierSortie);
        CommandeStatistique.augmenterStatValide();


    }
}
