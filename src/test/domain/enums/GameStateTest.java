package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración GameState.
 */
@DisplayName("GameState Enum Tests")
class GameStateTest {

    @Test
    @DisplayName("GS-01: Debe contener los 5 estados del juego")
    void testGameStateValues() {
        // Arrange & Act
        GameState[] states = GameState.values();
        
        // Assert
        assertEquals(5, states.length);
        assertNotNull(GameState.MENU);
        assertNotNull(GameState.PLAYING);
        assertNotNull(GameState.PAUSED);
        assertNotNull(GameState.VICTORY);
        assertNotNull(GameState.DEFEAT);
    }

    @Test
    @DisplayName("GS-02: MENU puede transicionar a PLAYING")
    void testMenuToPlaying() {
        assertTrue(GameState.MENU.canTransitionTo(GameState.PLAYING));
    }

    @Test
    @DisplayName("GS-02: MENU no puede transicionar a PAUSED")
    void testMenuToPausedInvalid() {
        assertFalse(GameState.MENU.canTransitionTo(GameState.PAUSED));
    }

    @Test
    @DisplayName("GS-02: MENU no puede transicionar a VICTORY")
    void testMenuToVictoryInvalid() {
        assertFalse(GameState.MENU.canTransitionTo(GameState.VICTORY));
    }

    @Test
    @DisplayName("GS-02: MENU no puede transicionar a DEFEAT")
    void testMenuToDefeatInvalid() {
        assertFalse(GameState.MENU.canTransitionTo(GameState.DEFEAT));
    }

    @Test
    @DisplayName("GS-02: PLAYING puede transicionar a PAUSED")
    void testPlayingToPaused() {
        assertTrue(GameState.PLAYING.canTransitionTo(GameState.PAUSED));
    }

    @Test
    @DisplayName("GS-02: PLAYING puede transicionar a VICTORY")
    void testPlayingToVictory() {
        assertTrue(GameState.PLAYING.canTransitionTo(GameState.VICTORY));
    }

    @Test
    @DisplayName("GS-02: PLAYING puede transicionar a DEFEAT")
    void testPlayingToDefeat() {
        assertTrue(GameState.PLAYING.canTransitionTo(GameState.DEFEAT));
    }

    @Test
    @DisplayName("GS-02: PLAYING no puede transicionar a MENU directamente")
    void testPlayingToMenuInvalid() {
        assertFalse(GameState.PLAYING.canTransitionTo(GameState.MENU));
    }

    @Test
    @DisplayName("GS-02: PAUSED puede transicionar a PLAYING")
    void testPausedToPlaying() {
        assertTrue(GameState.PAUSED.canTransitionTo(GameState.PLAYING));
    }

    @Test
    @DisplayName("GS-02: PAUSED puede transicionar a MENU")
    void testPausedToMenu() {
        assertTrue(GameState.PAUSED.canTransitionTo(GameState.MENU));
    }

    @Test
    @DisplayName("GS-02: VICTORY puede transicionar a MENU")
    void testVictoryToMenu() {
        assertTrue(GameState.VICTORY.canTransitionTo(GameState.MENU));
    }

    @Test
    @DisplayName("GS-02: VICTORY puede transicionar a PLAYING (siguiente nivel)")
    void testVictoryToPlaying() {
        assertTrue(GameState.VICTORY.canTransitionTo(GameState.PLAYING));
    }

    @Test
    @DisplayName("GS-02: DEFEAT puede transicionar a MENU")
    void testDefeatToMenu() {
        assertTrue(GameState.DEFEAT.canTransitionTo(GameState.MENU));
    }

    @Test
    @DisplayName("GS-02: DEFEAT puede transicionar a PLAYING (reintentar)")
    void testDefeatToPlaying() {
        assertTrue(GameState.DEFEAT.canTransitionTo(GameState.PLAYING));
    }

    @ParameterizedTest
    @DisplayName("GS-02: Estados no pueden transicionar a sí mismos")
    @CsvSource({
        "MENU, MENU",
        "PLAYING, PLAYING",
        "PAUSED, PAUSED",
        "VICTORY, VICTORY",
        "DEFEAT, DEFEAT"
    })
    void testNoSelfTransition(String from, String to) {
        GameState fromState = GameState.valueOf(from);
        GameState toState = GameState.valueOf(to);
        assertFalse(fromState.canTransitionTo(toState));
    }
}
