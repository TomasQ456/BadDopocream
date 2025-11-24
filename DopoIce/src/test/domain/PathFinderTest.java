package test.domain;

import domain.Cell;
import domain.IceException;
import domain.PathFinder;
import domain.Vector2;
import domain.events.EventDispatcher;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validates the BFS path-finding helper.
 */
class PathFinderTest {

    /**
     * Ensures that the computed path routes around solid cells rather than traversing through them.
     */
    @Test
    void findPath_avoidsSolidCells() throws IceException {
        EventDispatcher dispatcher = new EventDispatcher();
        Cell[][] grid = new Cell[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                grid[y][x] = new Cell(x, y, dispatcher);
            }
        }
        grid[1][1].setStaticObject(new TestWall());

        PathFinder finder = new PathFinder();
        List<Vector2> path = finder.findPath(grid, new Vector2(0, 0), new Vector2(2, 2));

        assertFalse(path.contains(new Vector2(1, 1)));
        assertEquals(new Vector2(2, 2), path.get(path.size() - 1));
    }

    private static final class TestWall extends domain.GameObject {
        private TestWall() {
            super("wall", new Vector2(0, 0), true, "wall");
        }

        @Override
        public void update() {
        }
    }
}
