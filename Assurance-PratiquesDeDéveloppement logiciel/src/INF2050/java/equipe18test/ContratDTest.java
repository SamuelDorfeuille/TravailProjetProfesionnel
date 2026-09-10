package equipe18test;

import org.junit.jupiter.api.Test;
import equipe18.ContratD;
import equipe18.Monnaie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContratDTest {

    @Test
    public void testRemboursement100Max65() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max65(montant);
        assertEquals(6500, montant.getTotalCents()); // Expected $65.00
    }

    @Test
    public void testRemboursement100Max75() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max75(montant);
        assertEquals(7500, montant.getTotalCents()); // Expected $75.00
    }

    @Test
    public void testRemboursement100Max85() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max85(montant);
        assertEquals(8500, montant.getTotalCents()); // Expected $85.00
    }

    @Test
    public void testRemboursement100Max90() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max90(montant);
        assertEquals(9000, montant.getTotalCents()); // Expected $90.00
    }

    @Test
    public void testRemboursement100Max100() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max100(montant);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testRemboursement100Max150() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.remboursement100Max150(montant);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory0() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 0);
        assertEquals(8500, montant.getTotalCents()); // Expected $85.00
    }

    @Test
    public void testCalculerRemboursementDCategory100() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 100);
        assertEquals(7500, montant.getTotalCents()); // Expected $75.00
    }

    @Test
    public void testCalculerRemboursementDCategory150() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 150);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory175() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 175);
        assertEquals(9500, montant.getTotalCents()); // Expected $95.00
    }

    @Test
    public void testCalculerRemboursementDCategory200() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 200);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory300() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 300);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory400() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 400);
        assertEquals(6500, montant.getTotalCents()); // Expected $65.00
    }

    @Test
    public void testCalculerRemboursementDCategory500() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 500);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory600() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 600);
        assertEquals(10000, montant.getTotalCents()); // Expected $100.00
    }

    @Test
    public void testCalculerRemboursementDCategory700() {
        Monnaie montant = new Monnaie(100, 0); // $100.00
        ContratD.calculerRemboursementD(montant, 700);
        assertEquals(9000, montant.getTotalCents()); // Expected $90.00
    }
}
