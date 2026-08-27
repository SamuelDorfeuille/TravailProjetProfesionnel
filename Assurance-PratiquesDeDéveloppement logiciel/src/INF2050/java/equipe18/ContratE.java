package equipe18;

public class ContratE{
    static public int remboursement12 = 12;
    static public int remboursement15 = 15;
    static public int remboursement22 = 22;
    static public int remboursement25 = 25;
    static public int remboursement60 = 60;



    static public void remboursement25Max15(Monnaie montant){
        if(((montant.getTotalCents() * 25) /100) > (15*100)){
            montant.setMontant(15*100);
        }else {
            montant.pourcentage(25);
        }
    }

    static public void remboursement30Max20(Monnaie montant){
        if(((montant.getTotalCents() * 30) /100) > (20*100)){
            montant.setMontant(20*100);
        }else {
            montant.pourcentage(30);
        }
    }


    static public void calculerRemboursementE(Monnaie montantRemboursable, double categorieSoins) {
        if (categorieSoins == 0){montantRemboursable.pourcentage(remboursement15);
        }else if(categorieSoins == 100 ){montantRemboursable.pourcentage(remboursement25);
        }else if(categorieSoins == 200){montantRemboursable.pourcentage(remboursement12);
        }else if(categorieSoins >299 && categorieSoins < 400){montantRemboursable.pourcentage(remboursement60);
        }else if(categorieSoins == 400){remboursement25Max15(montantRemboursable);
        }else if(categorieSoins == 500){remboursement30Max20(montantRemboursable);
        }else if(categorieSoins == 600){montantRemboursable.pourcentage(remboursement15);
        }else if(categorieSoins == 700){montantRemboursable.pourcentage(remboursement22);
        }
    }

}

