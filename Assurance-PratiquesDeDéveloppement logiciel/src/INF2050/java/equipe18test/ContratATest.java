package equipe18test;

import org.junit.jupiter.api.Test;
import equipe18.ContratA;
import equipe18.Monnaie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContratATest {

    @Test
    public void testRemboursementCategory0() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratA.calculerRemboursementA(montant, 0);
        assertEquals(2500, montant.getTotalCents()); // Expected $75.00
    }

    @Test
    public void testRemboursementCategory100() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratA.calculerRemboursementA(montant, 100);
        assertEquals(3500, montant.getTotalCents()); // Expected $65.00
    }

    @Test
    public void testRemboursementCategory175() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratA.calculerRemboursementA(montant, 175);
        assertEquals(5000, montant.getTotalCents()); // Expected $50.00
    }

    @Test
    public void testRemboursementCategory300() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratA.calculerRemboursementA(montant, 300);
        assertEquals(0, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testRemboursementCategory600() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratA.calculerRemboursementA(montant, 600);
        assertEquals(4000, montant.getTotalCents()); // Expected $40.00
    }
}
