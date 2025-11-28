package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración PlayerType.
 */
@DisplayName("PlayerType Enum Tests")
class PlayerTypeTest {

    @Test
    @DisplayName("PT-01: Debe contener HUMAN y AI")
    void testPlayerTypeValues() {
        // Arrange & Act
        PlayerType[] types = PlayerType.values();
        
        // Assert
        assertEquals(2, types.length);
        assertNotNull(PlayerType.HUMAN);
        assertNotNull(PlayerType.AI);
    }

    @Test
    @DisplayName("PT-01: HUMAN tiene ordinal 0")
    void testHumanOrdinal() {
        assertEquals(0, PlayerType.HUMAN.ordinal());
    }

    @Test
    @DisplayName("PT-01: AI tiene ordinal 1")
    void testAIOrdinal() {
        assertEquals(1, PlayerType.AI.ordinal());
    }

    @Test
    @DisplayName("valueOf debe retornar el tipo correcto")
    void testValueOf() {
        assertEquals(PlayerType.HUMAN, PlayerType.valueOf("HUMAN"));
        assertEquals(PlayerType.AI, PlayerType.valueOf("AI"));
    }
}
