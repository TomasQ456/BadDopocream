package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración GameMode.
 */
@DisplayName("GameMode Enum Tests")
class GameModeTest {

    @Test
    @DisplayName("GM-01: Debe contener todos los modos de juego")
    void testGameModeValues() {
        // Arrange & Act
        GameMode[] modes = GameMode.values();
        
        // Assert
        assertEquals(4, modes.length);
        assertNotNull(GameMode.SINGLE_PLAYER);
        assertNotNull(GameMode.COOPERATIVE);
        assertNotNull(GameMode.VERSUS);
        assertNotNull(GameMode.MULTIPLAYER);
    }

    @Test
    @DisplayName("GM-01: SINGLE_PLAYER debe tener ordinal 0")
    void testSinglePlayerOrdinal() {
        assertEquals(0, GameMode.SINGLE_PLAYER.ordinal());
    }

    @Test
    @DisplayName("GM-01: MULTIPLAYER debe tener ordinal 3")
    void testMultiplayerOrdinal() {
        assertEquals(3, GameMode.MULTIPLAYER.ordinal());
    }

    @Test
    @DisplayName("valueOf debe retornar el modo correcto")
    void testValueOf() {
        assertEquals(GameMode.SINGLE_PLAYER, GameMode.valueOf("SINGLE_PLAYER"));
        assertEquals(GameMode.MULTIPLAYER, GameMode.valueOf("MULTIPLAYER"));
    }
}
