package equipe18;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestionnaireRemboursements {

    private static final Map<Integer, Double> MAX_REMBOURSEMENTS = new HashMap<>();

    static {
        MAX_REMBOURSEMENTS.put(100, 250.0);
        MAX_REMBOURSEMENTS.put(175, 200.0);
        MAX_REMBOURSEMENTS.put(200, 250.0);
        MAX_REMBOURSEMENTS.put(500, 150.0);
        MAX_REMBOURSEMENTS.put(600, 300.0);
    }

    public static JSONObject traiterRemboursements(ArrayList<JSONObject> reclamationJSONArray, String lettreContrat, String client, String mois) {
        JSONArray remboursementsArray = new JSONArray();
        Map<Integer, Double> totalParSoin = new HashMap<>();
        Map<Integer, Double> totalRembourseParSoin = new HashMap<>();

        for (JSONObject reclamation : reclamationJSONArray) {
            int soin = reclamation.getInt("soin");
            String date = reclamation.getString("date");

            Verifications.verifierDateReclamation(date,TraitementJson.obtenirMois() );
            Verifications.verifierCategorie(soin);
            CommandeStatistique.compteurSoinsDeclares(soin);

            if (MAX_REMBOURSEMENTS.containsKey(soin)) {
                double montantRembourse = calculerMontantRembourse(reclamation, lettreContrat, totalParSoin.getOrDefault(soin, 0.0), soin);
                totalParSoin.put(soin, totalParSoin.getOrDefault(soin, 0.0) + montantRembourse);
                ajouterRemboursement(remboursementsArray, reclamation, montantRembourse);

                totalRembourseParSoin.put(soin, totalParSoin.get(soin));

                if (totalParSoin.get(soin) >= MAX_REMBOURSEMENTS.get(soin)) {

                }
            }
        }

        return construireResultat(client, mois, remboursementsArray, totalRembourseParSoin);
    }

    private static double calculerMontantRembourse(JSONObject reclamation, String lettreContrat, double totalActuel, int soin) {
        String montantAVerifier = reclamation.getString("montant");
        Verifications.verifierMontantAvec$(montantAVerifier);
        double montant = Double.parseDouble(reclamation.getString("montant").replace("$", ""));
        Monnaie monnaieRemboursement = new Monnaie((int) montant, (int) ((montant - (int) montant) * 100));
        Calculs.calculerRemboursement(lettreContrat, soin, monnaieRemboursement);

        double montantActuel = monnaieRemboursement.getTotalCents() / 100.0;
        if (totalActuel + montantActuel > MAX_REMBOURSEMENTS.get(soin)) {
            montantActuel = MAX_REMBOURSEMENTS.get(soin) - totalActuel;
            monnaieRemboursement.setMontant((int) (montantActuel * 100));
        }
        return montantActuel;
    }

    private static void ajouterRemboursement(JSONArray remboursementsArray, JSONObject reclamation, double montantRembourse) {
        JSONObject rembourse = new JSONObject();
        rembourse.put("soin", reclamation.getInt("soin"));
        rembourse.put("date", reclamation.getString("date"));
        rembourse.put("montant", String.format("%.2f$", montantRembourse));
        remboursementsArray.put(rembourse);
    }

    private static JSONObject construireResultat(String client, String mois, JSONArray remboursementsArray, Map<Integer, Double> totalRembourseParSoin) {
        JSONObject resultat = new JSONObject();
        resultat.put("dossier", client);
        resultat.put("mois", mois);
        resultat.put("remboursements", remboursementsArray);
        resultat.put("total", String.format("%.2f$", totalRembourseParSoin.values().stream().mapToDouble(Double::doubleValue).sum()));
        return resultat;
    }

}
