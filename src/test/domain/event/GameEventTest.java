package test.domain.event;

import main.domain.event.*;

import main.domain.enums.EventType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GameEvent.
 */
@DisplayName("GameEvent Tests")
class GameEventTest {

    @Test
    @DisplayName("GE-01: Crear evento con tipo y datos")
    void testGameEventCreation() {
        // Arrange
        EventType type = EventType.PLAYER_MOVED;
        String data = "Player moved to (5,5)";
        
        // Act
        GameEvent event = new GameEvent(type, data);
        
        // Assert
        assertEquals(type, event.getType());
        assertEquals(data, event.getData());
    }

    @Test
    @DisplayName("GE-01: Crear evento solo con tipo")
    void testGameEventCreationWithoutData() {
        // Arrange
        EventType type = EventType.GAME_STARTED;
        
        // Act
        GameEvent event = new GameEvent(type);
        
        // Assert
        assertEquals(type, event.getType());
        assertNull(event.getData());
    }

    @Test
    @DisplayName("GE-02: Verificar timestamp automático")
    void testGameEventTimestamp() {
        // Arrange
        long before = System.currentTimeMillis();
        
        // Act
        GameEvent event = new GameEvent(EventType.GAME_STARTED);
        long after = System.currentTimeMillis();
        
        // Assert
        assertTrue(event.getTimestamp() >= before);
        assertTrue(event.getTimestamp() <= after);
    }

    @Test
    @DisplayName("GE-03: Verificar getters")
    void testGameEventGetters() {
        // Arrange
        EventType type = EventType.SCORE_UPDATED;
        Integer score = 1000;
        
        // Act
        GameEvent event = new GameEvent(type, score);
        
        // Assert
        assertEquals(type, event.getType());
        assertEquals(score, event.getData());
        assertTrue(event.getTimestamp() > 0);
    }

    @Test
    @DisplayName("GE-03: getDataAs devuelve datos correctamente tipados")
    void testGetDataAs() {
        // Arrange
        Integer score = 500;
        GameEvent event = new GameEvent(EventType.SCORE_UPDATED, score);
        
        // Act
        Integer result = event.getDataAs(Integer.class);
        
        // Assert
        assertEquals(score, result);
    }

    @Test
    @DisplayName("GE-03: getDataAs devuelve null para tipo incorrecto")
    void testGetDataAsWrongType() {
        // Arrange
        Integer score = 500;
        GameEvent event = new GameEvent(EventType.SCORE_UPDATED, score);
        
        // Act
        String result = event.getDataAs(String.class);
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("GE-04: Evento GAME_STARTED")
    void testGameStartedEvent() {
        // Arrange & Act
        GameEvent event = new GameEvent(EventType.GAME_STARTED);
        
        // Assert
        assertEquals(EventType.GAME_STARTED, event.getType());
    }

    @Test
    @DisplayName("GE-05: Evento PLAYER_MOVED")
    void testPlayerMovedEvent() {
        // Arrange
        int[] position = {5, 5};
        
        // Act
        GameEvent event = new GameEvent(EventType.PLAYER_MOVED, position);
        
        // Assert
        assertEquals(EventType.PLAYER_MOVED, event.getType());
        assertArrayEquals(position, (int[]) event.getData());
    }

    @Test
    @DisplayName("GE-06: Evento ICE_CREATED")
    void testIceCreatedEvent() {
        // Arrange
        int[] position = {3, 4};
        
        // Act
        GameEvent event = new GameEvent(EventType.ICE_CREATED, position);
        
        // Assert
        assertEquals(EventType.ICE_CREATED, event.getType());
    }

    @Test
    @DisplayName("GE-07: Evento ICE_DESTROYED")
    void testIceDestroyedEvent() {
        // Arrange
        int[] position = {3, 4};
        
        // Act
        GameEvent event = new GameEvent(EventType.ICE_DESTROYED, position);
        
        // Assert
        assertEquals(EventType.ICE_DESTROYED, event.getType());
    }

    @Test
    @DisplayName("GE-08: Evento FRUIT_COLLECTED")
    void testFruitCollectedEvent() {
        // Arrange
        String fruitData = "GRAPE:100";
        
        // Act
        GameEvent event = new GameEvent(EventType.FRUIT_COLLECTED, fruitData);
        
        // Assert
        assertEquals(EventType.FRUIT_COLLECTED, event.getType());
        assertEquals(fruitData, event.getData());
    }

    @Test
    @DisplayName("GE-09: Evento LEVEL_COMPLETED")
    void testLevelCompletedEvent() {
        // Arrange
        Integer level = 3;
        
        // Act
        GameEvent event = new GameEvent(EventType.LEVEL_COMPLETED, level);
        
        // Assert
        assertEquals(EventType.LEVEL_COMPLETED, event.getType());
        assertEquals(level, event.getData());
    }

    @Test
    @DisplayName("GE-10: Evento PLAYER_DIED")
    void testPlayerDiedEvent() {
        // Arrange
        String deathCause = "Hit by Troll";
        
        // Act
        GameEvent event = new GameEvent(EventType.PLAYER_DIED, deathCause);
        
        // Assert
        assertEquals(EventType.PLAYER_DIED, event.getType());
        assertEquals(deathCause, event.getData());
    }

    @Test
    @DisplayName("GE-11: Evento SCORE_UPDATED")
    void testScoreUpdatedEvent() {
        // Arrange
        Integer newScore = 2500;
        
        // Act
        GameEvent event = new GameEvent(EventType.SCORE_UPDATED, newScore);
        
        // Assert
        assertEquals(EventType.SCORE_UPDATED, event.getType());
        assertEquals(newScore, event.getData());
    }

    @Test
    @DisplayName("GE-12: Evento TIME_UPDATED")
    void testTimeUpdatedEvent() {
        // Arrange
        Integer timeRemaining = 45;
        
        // Act
        GameEvent event = new GameEvent(EventType.TIME_UPDATED, timeRemaining);
        
        // Assert
        assertEquals(EventType.TIME_UPDATED, event.getType());
        assertEquals(timeRemaining, event.getData());
    }

    @Test
    @DisplayName("toString contiene información del evento")
    void testToString() {
        // Arrange
        GameEvent event = new GameEvent(EventType.GAME_STARTED, "test");
        
        // Act
        String result = event.toString();
        
        // Assert
        assertTrue(result.contains("GAME_STARTED"));
        assertTrue(result.contains("test"));
    }
}
