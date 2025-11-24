package test.domain;

import domain.Cell;
import domain.Entity;
import domain.GameObject;
import domain.IceException;
import domain.Vector2;
import domain.enums.GameEventType;
import domain.events.EventDispatcher;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Exercises the Cell aggregate to ensure set/update semantics obey the rules from the specification.
 */
class CellTest {

    /**
     * Verifies that assigning a static object triggers the dispatcher hook.
     */
    @Test
    void setStaticObject_triggersCellUpdatedEvent() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        AtomicBoolean cellUpdated = new AtomicBoolean(false);
        dispatcher.addListener(GameEventType.CELL_UPDATED, event -> cellUpdated.set(true));
        Cell cell = new Cell(0, 0, dispatcher);

        cell.setStaticObject(new StubObject("wall", true));

        assertTrue(cellUpdated.get(), "Setting a static object should publish CELL_UPDATED");
    }

    /**
     * Ensures that attempting to place two dynamic objects raises an exception.
     */
    @Test
    void setDynamicObject_rejectsOccupiedCell() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        Cell cell = new Cell(0, 0, dispatcher);
        cell.setDynamicObject(new StubEntity("player"));

        assertThrows(IceException.class, () -> cell.setDynamicObject(new StubEntity("enemy")));
    }

    /**
     * Checks that a solid static object blocks dynamic placement.
     */
    @Test
    void isFreeForDynamic_falseWhenSolidStaticPresent() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        Cell cell = new Cell(0, 0, dispatcher);
        cell.setStaticObject(new StubObject("wall", true));

        assertFalse(cell.isFreeForDynamic(), "Solid static object should block dynamic placement");
    }

    private static final class StubObject extends GameObject {
        private StubObject(String id, boolean solid) {
            super(id, new Vector2(0, 0), solid, "stub");
        }

        @Override
        public void update() {
            // No-op
        }
    }

    private static final class StubEntity extends Entity {
        private StubEntity(String id) {
            super(id, new Vector2(0, 0), 1, 1);
        }

        @Override
        public void update() {
            // No-op for tests
        }
    }
}
