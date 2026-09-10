package equipe18test;

import equipe18.Monnaie;
import equipe18.ContratC;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContratCTest {

    @Test
    public void testCalculerRemboursementCCategory100() {
        Monnaie montant = new Monnaie(100, 0);
        ContratC.calculerRemboursementC(montant, 100);
        assertEquals(90, ContratC.taux);
        assertEquals(8550, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementCCategory150() {
        Monnaie montant = new Monnaie(100, 0);
        ContratC.calculerRemboursementC(montant, 150);
        assertEquals(90, ContratC.taux);
        assertEquals(7650, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementCCategory600() {
        Monnaie montant = new Monnaie(100, 0);
        ContratC.calculerRemboursementC(montant, 600);
        assertEquals(90, ContratC.taux);
        assertEquals(6750, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementCDefault() {
        Monnaie montant = new Monnaie(100, 0);
        ContratC.calculerRemboursementC(montant, 200);
        assertEquals(90, ContratC.taux);
        assertEquals(9000, montant.getTotalCents());
    }
}
