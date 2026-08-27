package equipe18test;

import equipe18.TraitementJson;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class TraitementJsonTest {

    private static final String INPUT_FILE = "test_input.json";
    private static final String OUTPUT_FILE = "test_output.json";

    @Before
    public void setUp() throws IOException {
        // Créer un fichier d'entrée JSON pour les tests
        String jsonInput = "{\"dossier\": \"A123\", \"mois\": \"janvier\", \"reclamations\": []}";
        try (FileWriter fileWriter = new FileWriter(INPUT_FILE)) {
            fileWriter.write(jsonInput);
        }
    }

    @Test
    public void testLoadJsonFile() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        assertTrue(traitementJson.load());
    }

    @Test
    public void testGetJsonObjNotNullAfterLoad() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertNotNull(traitementJson.getJsonObj());
    }

    @Test
    public void testObtenirDossierClient() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertEquals("A123", traitementJson.obtenirDossierClient());
    }

    @Test
    public void testObtenirLettreContrat() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertEquals("A", traitementJson.obtenirLettreContrat());
    }

    @Test
    public void testObtenirNumeroClient() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertEquals("123", traitementJson.obtenirNumeroClient());
    }

    @Test
    public void testObtenirMois() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertEquals("janvier", traitementJson.obtenirMois());
    }

    @Test
    public void testGetListRec() {
        TraitementJson traitementJson = new TraitementJson(INPUT_FILE, OUTPUT_FILE);
        traitementJson.load();
        assertEquals(0, traitementJson.getListRec().size());
    }

}
