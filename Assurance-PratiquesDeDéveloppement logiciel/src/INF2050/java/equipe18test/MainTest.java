package equipe18test;

import equipe18.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testMainAvecArgumentsInvalides() {
        // Test en cas d'arguments invalides
        String[] args = {};
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Main.main(args));
        Assertions.assertEquals("le nombre d'argument est incorrect", exception.getMessage());
    }

    @Test
    public void testMainAvecArgumentsValides() {
        // Test en cas d'arguments valides
        String[] args = {"arg1", "arg2"}; // Changer pour des arguments valides
        Assertions.assertDoesNotThrow(() -> Main.main(args));
    }
}
