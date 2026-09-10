package equipe18test;

import org.junit.jupiter.api.Test;
import equipe18.ContratE;
import equipe18.Monnaie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContratETest {

    @Test
    public void testRemboursement25Max15() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.remboursement25Max15(montant);
        assertEquals(1500, montant.getTotalCents());
    }

    @Test
    public void testRemboursement30Max20() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.remboursement30Max20(montant);
        assertEquals(2000, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory0() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 0);
        assertEquals(1500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory100() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 100);
        assertEquals(2500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory200() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 200);
        assertEquals(1200, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory300() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 300);
        assertEquals(6000, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory400() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 400);
        assertEquals(1500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory500() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 500);
        assertEquals(2000, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory600() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 600);
        assertEquals(1500, montant.getTotalCents());
    }

    @Test
    public void testCalculerRemboursementECategory700() {
        Monnaie montant = new Monnaie(100, 0);
        ContratE.calculerRemboursementE(montant, 700);
        assertEquals(2200, montant.getTotalCents());
    }
}
