package equipe18;

public class ContratD {

    static public int remboursement95 = 95;

    static public int remboursement100 = 100;

    static public void remboursement100Max65(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (65*100)){
            montant.setMontant(65*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public void remboursement100Max75(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (75*100)){
            montant.setMontant(75*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public void remboursement100Max85(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (85*100)){
            montant.setMontant(85*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public void remboursement100Max90(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (90*100)){
            montant.setMontant(90*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public void remboursement100Max100(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (100*100)){
            montant.setMontant(100*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public void remboursement100Max150(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (150*100)){
            montant.setMontant(150*100);
        }else {
            montant.pourcentage(100);
        }
    }


    static public void calculerRemboursementD(Monnaie montantRemboursable, double categorieSoins) {
        if (categorieSoins == 0) {remboursement100Max85(montantRemboursable);
        } else if (categorieSoins == 100) {remboursement100Max75(montantRemboursable);
        } else if (categorieSoins == 150) {remboursement100Max150(montantRemboursable);
        } else if (categorieSoins == 175) {montantRemboursable.pourcentage(remboursement95);
        } else if (categorieSoins == 200 || categorieSoins == 600) {remboursement100Max100(montantRemboursable);
        } else if (categorieSoins > 299 && categorieSoins < 400 || categorieSoins == 500) {montantRemboursable.pourcentage(remboursement100);
        } else if (categorieSoins == 400) {remboursement100Max65(montantRemboursable);
        } else if (categorieSoins == 700) {remboursement100Max90(montantRemboursable);}
    }
}
