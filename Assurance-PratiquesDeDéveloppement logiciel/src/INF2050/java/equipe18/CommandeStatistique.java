package equipe18;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
public class CommandeStatistique {
    public static String PATH_TO_FILE = "src/INF2050/resources/Statistique.json";

    public static void afficherStatistiques() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(PATH_TO_FILE)), StandardCharsets.UTF_8);
            System.out.println(content);
        } catch (IOException e) {
            System.err.println("Unable to read the statistique.json file: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void reinitialiserStatistiques() {
        try {
            JSONObject json = lireStatistiquesExistantes();
            reinitialiserDonnees(json);
            ecrireDansFichier(json);
            afficherConfirmation();
        } catch (IOException e) {
            gererErreurIO(e);
        }
    }

    public static JSONObject lireStatistiquesExistantes() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(PATH_TO_FILE)), StandardCharsets.UTF_8);
        return new JSONObject(content);
    }

    public static void compteurSoinsDeclares(int typeDeSoin) {
        try {
            JSONObject statJson = equipe18.CommandeStatistique.lireStatistiquesExistantes();
            JSONArray nombreSoinsDeclares = statJson.getJSONArray("Nombre de soins déclarés");
            boolean soinDeclare = false;
            for (int i = 0; i < nombreSoinsDeclares.length(); i++) {
                JSONObject soin = nombreSoinsDeclares.getJSONObject(i);
                if (soin.getInt("soin") == typeDeSoin) {
                    int nombreDeclare = soin.getInt("Nombre déclaré") + 1;
                    soin.put("Nombre déclaré", nombreDeclare);

                    soinDeclare = true;
                    break;
                }
            }
            if (!soinDeclare) {
                JSONObject nouveauSoinDeclare = new JSONObject();
                nouveauSoinDeclare.put("soin", typeDeSoin);
                nouveauSoinDeclare.put("Nombre déclaré", 1);
                nombreSoinsDeclares.put(nouveauSoinDeclare);
            }
            equipe18.CommandeStatistique.ecrireDansFichier(statJson);
        } catch (IOException e) {
            System.err.println("Error reading/writing the statistique.json file: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void augmenterStatValide() throws IOException {
        JSONObject statJson = equipe18.CommandeStatistique.lireStatistiquesExistantes();
        int statistiqueReclamationValide = statJson.getInt("Nombre de réclamation valide traitées");
        statistiqueReclamationValide = statistiqueReclamationValide + 1;
        statJson.put("Nombre de réclamation valide traitées", statistiqueReclamationValide);
        equipe18.CommandeStatistique.ecrireDansFichier(statJson);

    }



    public static void reinitialiserDonnees(JSONObject json) {
        json.put("Nombre de réclamation valide traitées", "0");
        json.put("Nombre de réclamation rejetées", "0");

        JSONArray soinsArray = json.getJSONArray("Nombre de soins déclarés");
        for (int i = 0; i < soinsArray.length(); i++) {
            JSONObject soin = soinsArray.getJSONObject(i);
            soin.put("Nombre déclaré", "0");  // Réinitialiser chaque soin à "0"
        }
    }


    public static void ecrireDansFichier(JSONObject json) throws IOException {
        Files.write(Paths.get(PATH_TO_FILE), json.toString(4).getBytes(StandardCharsets.UTF_8));
    }

    private static void afficherConfirmation() {
        System.out.println("Les statistiques ont été réinitialisées.");
    }

    private static void gererErreurIO(IOException e) {
        System.err.println("Impossible de réinitialiser les statistiques : " + e.getMessage());
        System.exit(1);
    }


}
