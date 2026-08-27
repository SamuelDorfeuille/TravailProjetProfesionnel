package equipe18;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TraitementJson {
    private final String inputFile;
    private final String outputFile;
    private static JSONObject jsonObj;

    public TraitementJson(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        jsonObj = null;
    }

    public boolean load() {
        boolean result = false;
        try (InputStream is = new FileInputStream(this.inputFile)) {
            String stringJson = IOUtils.toString(is, StandardCharsets.UTF_8);
            jsonObj = new JSONObject(stringJson);
            result = true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        }
        return result;
    }

    public JSONObject getJsonObj() {
        return jsonObj;
    }

    public static String obtenirDossierClient() {
        return jsonObj.getString("dossier");
    }

    public static String obtenirLettreContrat() {
        return obtenirDossierClient().substring(0, 1);
    }

    public static String obtenirNumeroClient() {
        return obtenirDossierClient().substring(1);
    }

    public static String obtenirMois() {
        return jsonObj.getString("mois");
    }

    public ArrayList<JSONObject> getListRec() {
        ArrayList<JSONObject> liste = new ArrayList<>();
        JSONArray collection = jsonObj.getJSONArray("reclamations");
        for (int i = 0; i < collection.length(); i++) {
            liste.add(collection.getJSONObject(i));
        }
        return liste;
    }
    private static void ensureJsonObjInitialized() {
        if (jsonObj == null) {
            throw new IllegalStateException("jsonObj is not initialized");
        }
    }

    public static void save(JSONObject fichierASortir) throws IOException {
        try (FileWriter file = new FileWriter("src/INF2050/resources/Output.json")) {
            file.write(fichierASortir.toString(2));
        }
    }
}
