package equipe18test;

import equipe18.Verifications;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VerificationsTest {

    @Test
    void verifierCategorieValid() {
        assertTrue(Verifications.verifierCategorie(100));
        assertTrue(Verifications.verifierCategorie(350));
    }

    @Test
    void verifierCategorieInvalid() {
        assertThrows(Exception.class, () -> Verifications.verifierCategorie(800));
    }

    @Test
    void verifierContratClientValid() {
        assertDoesNotThrow(() -> Verifications.verifierContratClient("A"));
    }

    @Test
    void verifierContratClientInvalid() {
        assertThrows(Exception.class, () -> Verifications.verifierContratClient("Z"));
    }

    @Test
    void verifierDateReclamationValid() {
        assertDoesNotThrow(() -> Verifications.verifierDateReclamation("20240101", "202401"));
    }

    @Test
    void verifierDateReclamationInvalid() {
        assertThrows(Exception.class, () -> Verifications.verifierDateReclamation("20240101", "202402"));
    }

    @Test
    void verifierMontantAvec$Valid() {
        assertDoesNotThrow(() -> Verifications.verifierMontantAvec$("100$"));
    }

    @Test
    void verifierMontantAvec$Invalid() {
        assertThrows(Exception.class, () -> Verifications.verifierMontantAvec$("100"));
    }

    @Test
    void verifierNumeroClientValid() {
        assertDoesNotThrow(() -> Verifications.verifierNumeroClient("123456"));
    }

    @Test
    void verifierNumeroClientInvalid() {
        assertThrows(Exception.class, () -> Verifications.verifierNumeroClient("12345"));
        assertThrows(Exception.class, () -> Verifications.verifierNumeroClient("abcdef"));
    }
}
