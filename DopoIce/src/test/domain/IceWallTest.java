package test.domain;

import domain.IceException;
import domain.IceWall;
import domain.Vector2;
import domain.enums.GameEventType;
import domain.events.EventDispatcher;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validates that IceWall break mechanics conform to the blocking/destroy rules.
 */
class IceWallTest {

    /**
     * Ensures that repeatedly breaking the wall decrements HP and emits BLOCK_DESTROYED when depleted.
     */
    @Test
    void breakBlock_whenWallIsBreakable_publishesEvent() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        AtomicInteger destroyedEvents = new AtomicInteger();
        dispatcher.addListener(GameEventType.BLOCK_DESTROYED, event -> destroyedEvents.incrementAndGet());
        IceWall wall = new IceWall("ice-1", new Vector2(2, 2), 2, dispatcher);

        wall.breakBlock();
        wall.breakBlock();

        assertEquals(1, destroyedEvents.get(), "BLOCK_DESTROYED should be emitted once when HP reaches zero");
        assertTrue(wall.isDestroyed(), "Wall should report destroyed after HP depletion");
    }
}
