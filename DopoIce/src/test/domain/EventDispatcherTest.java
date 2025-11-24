package test.domain;

import domain.events.EventDispatcher;
import domain.events.GameEvent;
import domain.enums.GameEventType;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifies listener registration and dispatch semantics.
 */
class EventDispatcherTest {

    /**
     * Ensures registered listeners receive events when dispatch is invoked.
     */
    @Test
    void addListener_andDispatch_invokesListener() {
        EventDispatcher dispatcher = new EventDispatcher();
        AtomicInteger invocations = new AtomicInteger();
        dispatcher.addListener(GameEventType.PLAYER_MOVED, event -> invocations.incrementAndGet());

        dispatcher.dispatch(new GameEvent(GameEventType.PLAYER_MOVED, this, Map.of("x", 1)));

        assertEquals(1, invocations.get());
    }

    /**
     * Confirms that one failing listener does not block remaining listeners from running.
     */
    @Test
    void dispatch_listenerThrows_exceptionIsSwallowedAndOthersRun() {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.addListener(GameEventType.PLAYER_MOVED, event -> {
            throw new IllegalStateException("boom");
        });
        AtomicInteger safeListenerCount = new AtomicInteger();
        dispatcher.addListener(GameEventType.PLAYER_MOVED, event -> safeListenerCount.incrementAndGet());

        dispatcher.dispatch(new GameEvent(GameEventType.PLAYER_MOVED, this, Map.of()));

        assertEquals(1, safeListenerCount.get());
    }
}
