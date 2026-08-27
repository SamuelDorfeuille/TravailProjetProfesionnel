package equipe18test;

import org.junit.jupiter.api.Test;
import equipe18.Calculs;
import equipe18.Monnaie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculsTest {

    @Test
    public void testCalculerRemboursementA() {
        Monnaie montant = new Monnaie(100, 0);
        Calculs.calculerRemboursement("A", 100, montant);
        assertEquals(3500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementB() {
        Monnaie montant = new Monnaie(100, 0);
        Calculs.calculerRemboursement("B", 100, montant);
        assertEquals(5000, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementC() {
        Monnaie montant = new Monnaie(100, 0);
        Calculs.calculerRemboursement("C", 100, montant);
        assertEquals(8550, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementD() {
        Monnaie montant = new Monnaie(100, 0);
        Calculs.calculerRemboursement("D", 100, montant);
        assertEquals(7500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementE() {
        Monnaie montant = new Monnaie(100, 0);
        Calculs.calculerRemboursement("E", 100, montant);
        assertEquals(2500, montant.getTotalCents());
    }
}
