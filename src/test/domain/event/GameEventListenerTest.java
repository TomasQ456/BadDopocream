package test.domain.event;

import main.domain.event.*;

import main.domain.enums.EventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GameEventListener.
 */
@DisplayName("GameEventListener Tests")
class GameEventListenerTest {

    private List<GameEvent> receivedEvents;
    private GameEventListener listener;

    @BeforeEach
    void setUp() {
        receivedEvents = new ArrayList<>();
        listener = event -> receivedEvents.add(event);
    }

    @Test
    @DisplayName("GEL-01: Listener recibe evento")
    void testListenerReceivesEvent() {
        // Arrange
        GameEvent event = new GameEvent(EventType.GAME_STARTED);
        
        // Act
        listener.onEvent(event);
        
        // Assert
        assertEquals(1, receivedEvents.size());
        assertEquals(event, receivedEvents.get(0));
    }

    @Test
    @DisplayName("GEL-01: Listener recibe múltiples eventos")
    void testListenerReceivesMultipleEvents() {
        // Arrange
        GameEvent event1 = new GameEvent(EventType.GAME_STARTED);
        GameEvent event2 = new GameEvent(EventType.PLAYER_MOVED);
        GameEvent event3 = new GameEvent(EventType.FRUIT_COLLECTED);
        
        // Act
        listener.onEvent(event1);
        listener.onEvent(event2);
        listener.onEvent(event3);
        
        // Assert
        assertEquals(3, receivedEvents.size());
        assertEquals(event1, receivedEvents.get(0));
        assertEquals(event2, receivedEvents.get(1));
        assertEquals(event3, receivedEvents.get(2));
    }

    @Test
    @DisplayName("GEL-02: Múltiples listeners reciben el mismo evento")
    void testMultipleListenersReceiveEvent() {
        // Arrange
        List<GameEvent> events1 = new ArrayList<>();
        List<GameEvent> events2 = new ArrayList<>();
        GameEventListener listener1 = events1::add;
        GameEventListener listener2 = events2::add;
        GameEvent event = new GameEvent(EventType.SCORE_UPDATED, 1000);
        
        // Act
        listener1.onEvent(event);
        listener2.onEvent(event);
        
        // Assert
        assertEquals(1, events1.size());
        assertEquals(1, events2.size());
        assertEquals(event, events1.get(0));
        assertEquals(event, events2.get(0));
    }

    @Test
    @DisplayName("GEL-02: Listeners independientes no interfieren")
    void testListenersAreIndependent() {
        // Arrange
        List<GameEvent> events1 = new ArrayList<>();
        List<GameEvent> events2 = new ArrayList<>();
        GameEventListener listener1 = events1::add;
        GameEventListener listener2 = events2::add;
        
        GameEvent event1 = new GameEvent(EventType.GAME_STARTED);
        GameEvent event2 = new GameEvent(EventType.PLAYER_DIED);
        
        // Act
        listener1.onEvent(event1);
        listener2.onEvent(event2);
        
        // Assert
        assertEquals(1, events1.size());
        assertEquals(1, events2.size());
        assertEquals(EventType.GAME_STARTED, events1.get(0).getType());
        assertEquals(EventType.PLAYER_DIED, events2.get(0).getType());
    }

    @Test
    @DisplayName("GameEventListener es interfaz funcional")
    void testIsFunctionalInterface() {
        // Arrange & Act
        GameEventListener lambda = e -> { };
        
        // Assert
        assertNotNull(lambda);
    }

    @Test
    @DisplayName("Listener puede procesar datos del evento")
    void testListenerProcessesEventData() {
        // Arrange
        final int[] processedScore = {0};
        GameEventListener scoreListener = event -> {
            if (event.getType() == EventType.SCORE_UPDATED) {
                Integer score = event.getDataAs(Integer.class);
                if (score != null) {
                    processedScore[0] = score;
                }
            }
        };
        
        GameEvent event = new GameEvent(EventType.SCORE_UPDATED, 500);
        
        // Act
        scoreListener.onEvent(event);
        
        // Assert
        assertEquals(500, processedScore[0]);
    }

    @Test
    @DisplayName("Listener puede filtrar por tipo de evento")
    void testListenerFiltersEventType() {
        // Arrange
        List<GameEvent> filteredEvents = new ArrayList<>();
        GameEventListener filterListener = event -> {
            if (event.getType() == EventType.PLAYER_MOVED) {
                filteredEvents.add(event);
            }
        };
        
        // Act
        filterListener.onEvent(new GameEvent(EventType.GAME_STARTED));
        filterListener.onEvent(new GameEvent(EventType.PLAYER_MOVED));
        filterListener.onEvent(new GameEvent(EventType.SCORE_UPDATED));
        filterListener.onEvent(new GameEvent(EventType.PLAYER_MOVED));
        
        // Assert
        assertEquals(2, filteredEvents.size());
        assertTrue(filteredEvents.stream()
            .allMatch(e -> e.getType() == EventType.PLAYER_MOVED));
    }
}
