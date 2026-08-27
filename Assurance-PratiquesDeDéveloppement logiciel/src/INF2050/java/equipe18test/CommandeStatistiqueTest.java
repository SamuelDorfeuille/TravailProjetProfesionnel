package equipe18test;

import equipe18.CommandeStatistique;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandeStatistiqueTest {

    @BeforeEach
    void setUp() {
        CommandeStatistique.reinitialiserStatistiques();
    }


    @Test
    void testLireStatistiquesExistantes() {
        JSONObject json = null;
        try {
            json = CommandeStatistique.lireStatistiquesExistantes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(json);
        assertTrue(json.has("Nombre de réclamation valide traitées"));
        assertTrue(json.has("Nombre de réclamation rejetées"));
        assertTrue(json.has("Nombre de soins déclarés"));
    }

    @Test
    void testReinitialiserDonnees() {
        JSONObject json = new JSONObject();
        json.put("Nombre de réclamation valide traitées", "10");
        json.put("Nombre de réclamation rejetées", "5");
        JSONArray soinsArray = new JSONArray();
        JSONObject soin1 = new JSONObject();
        soin1.put("Type", "Consultation");
        soin1.put("Nombre déclaré", "3");
        JSONObject soin2 = new JSONObject();
        soin2.put("Type", "Pharmacie");
        soin2.put("Nombre déclaré", "2");
        soinsArray.put(soin1);
        soinsArray.put(soin2);
        json.put("Nombre de soins déclarés", soinsArray);

        CommandeStatistique.reinitialiserDonnees(json);

        assertEquals("0", json.getString("Nombre de réclamation valide traitées"));
        assertEquals("0", json.getString("Nombre de réclamation rejetées"));
        JSONArray newSoinsArray = json.getJSONArray("Nombre de soins déclarés");
        for (int i = 0; i < newSoinsArray.length(); i++) {
            JSONObject soin = newSoinsArray.getJSONObject(i);
            assertEquals("0", soin.getString("Nombre déclaré"));
        }
    }

    @Test
    public void testCompteurSoinsDeclares() throws IOException {
        // Ajoute un nouveau type de soin
        CommandeStatistique.compteurSoinsDeclares(200);

        // Vérifie si le nouveau type de soin a été ajouté avec succès
        JSONObject result = CommandeStatistique.lireStatistiquesExistantes();
        JSONArray resultSoins = result.getJSONArray("Nombre de soins déclarés");
        boolean newTypeFound = false;
        for (int i = 0; i < resultSoins.length(); i++) {
            JSONObject soin = resultSoins.getJSONObject(i);
            if (soin.getInt("soin") == 200) {
                assertEquals(1, soin.getInt("Nombre déclaré"));
                newTypeFound = true;
                break;
            }
        }
        assertTrue(newTypeFound);
    }

    @Test
    public void testAugmenterStatValide() throws IOException {
        // Augmente le nombre de réclamations valides
        CommandeStatistique.augmenterStatValide();

        // Vérifie si le nombre de réclamations valides a été augmenté avec succès
        JSONObject result = CommandeStatistique.lireStatistiquesExistantes();
        assertEquals(1, result.getInt("Nombre de réclamation valide traitées"));
    }
}
