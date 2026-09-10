package equipe18;

public class ContratC {

    public static int taux = 0;
    static public void calculerRemboursementC(Monnaie montantRemboursable,double categorieSoins) {
        if (categorieSoins ==100) {
            taux = 95;
            montantRemboursable.pourcentage(taux);
        }else if (categorieSoins == 150){
            taux = 85;
            montantRemboursable.pourcentage(taux);
        }else if (categorieSoins == 600){
            taux = 75;
            montantRemboursable.pourcentage(taux);
        }
        taux = 90;
        montantRemboursable.pourcentage(taux);
    }

}
