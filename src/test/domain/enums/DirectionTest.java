package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración Direction.
 * Siguiendo TDD: Estas pruebas fueron escritas primero.
 */
@DisplayName("Direction Enum Tests")
class DirectionTest {

    @Test
    @DisplayName("DIR-01: Debe contener las 4 direcciones básicas")
    void testDirectionValues() {
        // Arrange & Act
        Direction[] directions = Direction.values();
        
        // Assert
        assertEquals(4, directions.length);
        assertNotNull(Direction.UP);
        assertNotNull(Direction.DOWN);
        assertNotNull(Direction.LEFT);
        assertNotNull(Direction.RIGHT);
    }

    @Test
    @DisplayName("DIR-02: UP debe tener como opuesto DOWN")
    void testUpOpposite() {
        assertEquals(Direction.DOWN, Direction.UP.getOpposite());
    }

    @Test
    @DisplayName("DIR-02: DOWN debe tener como opuesto UP")
    void testDownOpposite() {
        assertEquals(Direction.UP, Direction.DOWN.getOpposite());
    }

    @Test
    @DisplayName("DIR-02: LEFT debe tener como opuesto RIGHT")
    void testLeftOpposite() {
        assertEquals(Direction.RIGHT, Direction.LEFT.getOpposite());
    }

    @Test
    @DisplayName("DIR-02: RIGHT debe tener como opuesto LEFT")
    void testRightOpposite() {
        assertEquals(Direction.LEFT, Direction.RIGHT.getOpposite());
    }

    @Test
    @DisplayName("DIR-03: UP debe tener deltaX = 0")
    void testUpDeltaX() {
        assertEquals(0, Direction.UP.getDeltaX());
    }

    @Test
    @DisplayName("DIR-03: DOWN debe tener deltaX = 0")
    void testDownDeltaX() {
        assertEquals(0, Direction.DOWN.getDeltaX());
    }

    @Test
    @DisplayName("DIR-03: LEFT debe tener deltaX = -1")
    void testLeftDeltaX() {
        assertEquals(-1, Direction.LEFT.getDeltaX());
    }

    @Test
    @DisplayName("DIR-03: RIGHT debe tener deltaX = 1")
    void testRightDeltaX() {
        assertEquals(1, Direction.RIGHT.getDeltaX());
    }

    @Test
    @DisplayName("DIR-04: UP debe tener deltaY = -1")
    void testUpDeltaY() {
        assertEquals(-1, Direction.UP.getDeltaY());
    }

    @Test
    @DisplayName("DIR-04: DOWN debe tener deltaY = 1")
    void testDownDeltaY() {
        assertEquals(1, Direction.DOWN.getDeltaY());
    }

    @Test
    @DisplayName("DIR-04: LEFT debe tener deltaY = 0")
    void testLeftDeltaY() {
        assertEquals(0, Direction.LEFT.getDeltaY());
    }

    @Test
    @DisplayName("DIR-04: RIGHT debe tener deltaY = 0")
    void testRightDeltaY() {
        assertEquals(0, Direction.RIGHT.getDeltaY());
    }

    @Test
    @DisplayName("La dirección opuesta de la opuesta debe ser la original")
    void testDoubleOpposite() {
        for (Direction dir : Direction.values()) {
            assertEquals(dir, dir.getOpposite().getOpposite());
        }
    }
}
