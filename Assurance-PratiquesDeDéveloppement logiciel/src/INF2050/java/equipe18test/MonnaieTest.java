package equipe18test;

import org.junit.Test;
import equipe18.Monnaie;

import static org.junit.Assert.assertEquals;

public class MonnaieTest {

    @Test
    public void testConstructeurAvecValeursPositives() {
        Monnaie monnaie = new Monnaie(10, 50);
        assertEquals(1050, monnaie.getTotalCents());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurAvecDollarsNegatif() {
        new Monnaie(-5, 30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurAvecCentsNegatif() {
        new Monnaie(10, -30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurAvecCentsSuperieur99() {
        new Monnaie(10, 100);
    }

    @Test
    public void testAdditioner() {
        Monnaie monnaie1 = new Monnaie(5, 75);
        Monnaie monnaie2 = new Monnaie(3, 50);
        monnaie1.additioner(monnaie2);
        assertEquals(925, monnaie1.getTotalCents());
    }

    @Test
    public void testPourcentage() {
        Monnaie monnaie = new Monnaie(10, 50);
        monnaie.pourcentage(50);
        assertEquals(525, monnaie.getTotalCents());
    }

    @Test
    public void testSetMontant() {
        Monnaie monnaie = new Monnaie(10, 50);
        monnaie.setMontant(2000);
        assertEquals(2000, monnaie.getTotalCents());
    }

    @Test
    public void testToString() {
        Monnaie monnaie = new Monnaie(10, 50);
        assertEquals("10.50$", monnaie.toString());
    }
}
