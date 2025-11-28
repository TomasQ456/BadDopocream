package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración EventType.
 */
@DisplayName("EventType Enum Tests")
class EventTypeTest {

    @Test
    @DisplayName("ET-01: Debe contener todos los tipos de evento")
    void testEventTypeValues() {
        // Arrange & Act
        EventType[] types = EventType.values();
        
        // Assert
        assertTrue(types.length >= 12, "Debe haber al menos 12 tipos de evento");
    }

    @Test
    @DisplayName("ET-01: GAME_STARTED existe")
    void testGameStartedExists() {
        assertNotNull(EventType.GAME_STARTED);
    }

    @Test
    @DisplayName("ET-01: PLAYER_MOVED existe")
    void testPlayerMovedExists() {
        assertNotNull(EventType.PLAYER_MOVED);
    }

    @Test
    @DisplayName("ET-01: ICE_CREATED existe")
    void testIceCreatedExists() {
        assertNotNull(EventType.ICE_CREATED);
    }

    @Test
    @DisplayName("ET-01: ICE_DESTROYED existe")
    void testIceDestroyedExists() {
        assertNotNull(EventType.ICE_DESTROYED);
    }

    @Test
    @DisplayName("ET-01: FRUIT_COLLECTED existe")
    void testFruitCollectedExists() {
        assertNotNull(EventType.FRUIT_COLLECTED);
    }

    @Test
    @DisplayName("ET-01: LEVEL_COMPLETED existe")
    void testLevelCompletedExists() {
        assertNotNull(EventType.LEVEL_COMPLETED);
    }

    @Test
    @DisplayName("ET-01: PLAYER_DIED existe")
    void testPlayerDiedExists() {
        assertNotNull(EventType.PLAYER_DIED);
    }

    @Test
    @DisplayName("ET-01: SCORE_UPDATED existe")
    void testScoreUpdatedExists() {
        assertNotNull(EventType.SCORE_UPDATED);
    }

    @Test
    @DisplayName("ET-01: TIME_UPDATED existe")
    void testTimeUpdatedExists() {
        assertNotNull(EventType.TIME_UPDATED);
    }

    @Test
    @DisplayName("ET-01: GAME_PAUSED existe")
    void testGamePausedExists() {
        assertNotNull(EventType.GAME_PAUSED);
    }

    @Test
    @DisplayName("ET-01: GAME_RESUMED existe")
    void testGameResumedExists() {
        assertNotNull(EventType.GAME_RESUMED);
    }

    @Test
    @DisplayName("ET-01: GAME_OVER existe")
    void testGameOverExists() {
        assertNotNull(EventType.GAME_OVER);
    }

    @Test
    @DisplayName("valueOf funciona correctamente")
    void testValueOf() {
        assertEquals(EventType.GAME_STARTED, EventType.valueOf("GAME_STARTED"));
        assertEquals(EventType.PLAYER_MOVED, EventType.valueOf("PLAYER_MOVED"));
        assertEquals(EventType.FRUIT_COLLECTED, EventType.valueOf("FRUIT_COLLECTED"));
    }
}
