package equipe18test;

import org.junit.jupiter.api.Test;
import equipe18.ContratB;
import equipe18.Monnaie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContratBTest {

    @Test
    public void testRemboursement50Max40() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.remboursement50Max40(montant);
        assertEquals(4000, montant.getTotalCents()); // Expected $40.00
    }

    @Test
    public void testRemboursement50Max50() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.remboursement50Max50(montant);
        assertEquals(5000, montant.getTotalCents()); // Expected $50.00
    }

    @Test
    public void testRemboursement100Max70() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.remboursement100Max70(montant);
        assertEquals(7000, montant.getTotalCents()); // Expected $70.00
    }

    @Test
    public void testCalculerRemboursementBCategory0() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 0);
        assertEquals(4000, montant.getTotalCents()); // Expected $40.00
    }

    @Test
    public void testCalculerRemboursementBCategory100() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 100);
        assertEquals(5000, montant.getTotalCents()); // Expected $50.00
    }

    @Test
    public void testCalculerRemboursementBCategory175() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 175);
        assertEquals(7500, montant.getTotalCents()); // Expected $75.00
    }

    @Test
    public void testCalculerRemboursementBCategory300() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 300);
        assertEquals(5000, montant.getTotalCents()); // Expected $50.00
    }

    @Test
    public void testCalculerRemboursementBCategory400() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 400);
        assertEquals(0, montant.getTotalCents()); // Expected $0.00
    }

    @Test
    public void testCalculerRemboursementBCategory600() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 600);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementBCategory700() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratB.calculerRemboursementB(montant, 700);
        assertEquals(7000, montant.getTotalCents()); // Expected $70.00
    }
}
