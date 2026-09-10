package equipe18test;


import equipe18.GestionnaireRemboursements;
import equipe18.TraitementJson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GestionnaireRemboursementsTest {

    @Test
    public void testTraitementRemboursementsAucuneReclamation() {
        ArrayList<JSONObject> reclamationJSONArray = new ArrayList<>();
        JSONObject resultat = GestionnaireRemboursements.traiterRemboursements(reclamationJSONArray, "A", "123456", "2023-01");

        assertNotNull(resultat);
        assertEquals("123456", resultat.getString("dossier"));
        assertEquals("2023-01", resultat.getString("mois"));
        assertEquals(0, resultat.getJSONArray("remboursements").length());
        assertEquals("0,00$", resultat.getString("total"));
    }

    @Test
    public void testTraitementRemboursements() {
        // Création des données de test
        ArrayList<JSONObject> reclamationJSONArray = new ArrayList<>();
        JSONObject reclamation1 = new JSONObject();
        reclamation1.put("soin", 175);
        reclamation1.put("date", "2023-01-11");
        reclamation1.put("montant", "65.00$");
        reclamationJSONArray.add(reclamation1);

        JSONObject reclamation2 = new JSONObject();
        reclamation2.put("soin", 100);
        reclamation2.put("date", "2023-01-17");
        reclamation2.put("montant", "45.50$");
        reclamationJSONArray.add(reclamation2);

        // Appel de la méthode à tester
        TraitementJson traitementJson = new TraitementJson("src/INF2050/resources/Assurance.json", "src/INF2050/resources/Output.json");
        traitementJson.load();

        JSONObject resultat = GestionnaireRemboursements.traiterRemboursements(reclamationJSONArray, "A", "123456", "2023-01");

        // Vérification des résultats
        assertNotNull(resultat);
        assertEquals("123456", resultat.getString("dossier"));
        assertEquals("2023-01", resultat.getString("mois"));
        JSONArray remboursements = resultat.getJSONArray("remboursements");
        assertEquals(2, remboursements.length());

        // Vérification du total des remboursements
        double totalRemboursements = 0.0;
        for (int i = 0; i < remboursements.length(); i++) {
            JSONObject remboursement = remboursements.getJSONObject(i);
            String montant = remboursement.getString("montant").replace("$", "").replace(",", ".");
            totalRemboursements += Double.parseDouble(montant);
        }
        assertEquals("48,42$", String.format("%.2f$", totalRemboursements));

        }
    }

