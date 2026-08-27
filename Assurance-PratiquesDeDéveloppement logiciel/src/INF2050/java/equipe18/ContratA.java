package equipe18;

public class ContratA {
    static public int remboursement0 = 0;
    static public int remboursement25 = 25;
    static public int remboursement35 = 35;
    static public int remboursement40 = 40;
    static public int remboursement50 = 50;



    static public void calculerRemboursementA(Monnaie montantRemboursable, double categorieSoins) {
        if (categorieSoins ==0|| categorieSoins ==200|| categorieSoins == 500){montantRemboursable.pourcentage(remboursement25);
        }else if (categorieSoins == 100) {montantRemboursable.pourcentage(remboursement35);
        }else if (categorieSoins == 175) {montantRemboursable.pourcentage(remboursement50);
        }else if (categorieSoins>299 && categorieSoins<400 || categorieSoins == 150|| categorieSoins ==400 || categorieSoins ==700){
            montantRemboursable.pourcentage(remboursement0);
        } else if (categorieSoins == 600) {
            montantRemboursable.pourcentage(remboursement40);
        }
    }


}

