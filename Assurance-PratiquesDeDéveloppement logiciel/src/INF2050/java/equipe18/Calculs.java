package equipe18;

public class Calculs {

    static public void remboursementsDemandesA(double typeSoin, Monnaie montantDemande){
        ContratA.calculerRemboursementA(montantDemande,typeSoin);
    }

    static public void remboursementsDemandesB(double typeSoin, Monnaie montantDemande){
        ContratB.calculerRemboursementB(montantDemande,typeSoin);
    }

    static public void remboursementsDemandesC(double typeSoin, Monnaie montantDemande){
        ContratC.calculerRemboursementC(montantDemande, typeSoin);
    }

    static public void remboursementsDemandesD(double typeSoin, Monnaie montantDemande){
        ContratD.calculerRemboursementD(montantDemande,typeSoin);
    }
    static public void remboursementsDemandesE(double typeSoin, Monnaie montantDemande){
        ContratE.calculerRemboursementE(montantDemande,typeSoin);
    }


    public static void calculerRemboursement(String typeContrat,double typeSoin, Monnaie montantRemboursable){
        if("A".equals(typeContrat)){Calculs.remboursementsDemandesA(typeSoin,montantRemboursable);
        }else if("B".equals(typeContrat)){Calculs.remboursementsDemandesB(typeSoin,montantRemboursable);
        }else if("C".equals(typeContrat)){Calculs.remboursementsDemandesC(typeSoin,montantRemboursable);
        }else if("D".equals(typeContrat)){Calculs.remboursementsDemandesD(typeSoin,montantRemboursable);
        }else if("E".equals(typeContrat)){Calculs.remboursementsDemandesE(typeSoin,montantRemboursable);
        }
    }








}
