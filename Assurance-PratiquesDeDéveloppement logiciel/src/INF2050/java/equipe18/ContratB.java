package equipe18;

public class ContratB {

    static public void remboursement50Max40(Monnaie montant){
        if(((montant.getTotalCents() * 50) /100) > (40*100)){
            montant.setMontant(40*100);
        }else{
            montant.pourcentage(50);
        }
    }

    static public void remboursement50Max50(Monnaie montant){
        if(((montant.getTotalCents() * 50) /100) > (50*100)){
            montant.setMontant(50*100);
        }else{
            montant.pourcentage(50);
        }
    }

    static public void remboursement100Max70(Monnaie montant){
        if(((montant.getTotalCents() * 100) /100) > (70*100)){
            montant.setMontant(70*100);
        }else {
            montant.pourcentage(100);
        }
    }

    static public int remboursement0 = 0;
    static public int remboursement50 = 50;
    static public int remboursement70 = 70;
    static public int remboursement75 = 75;
    static public int remboursement100 = 100;




    static public void calculerRemboursementB(Monnaie montantRemboursable, double categorieSoins){
        if (categorieSoins == 0){ remboursement50Max40(montantRemboursable);
        }else if(categorieSoins == 100 || categorieSoins == 500){remboursement50Max50(montantRemboursable);
        }else if(categorieSoins >299 && categorieSoins < 400){montantRemboursable.pourcentage(remboursement50);
        }else if(categorieSoins == 150 || categorieSoins == 400){montantRemboursable.pourcentage(remboursement0);
        }else if(categorieSoins == 700){montantRemboursable.pourcentage(remboursement70);
        }else if(categorieSoins== 175){montantRemboursable.pourcentage(remboursement75);
        }else if(categorieSoins == 200 || categorieSoins == 600){montantRemboursable.pourcentage(remboursement100); }
    }
}

