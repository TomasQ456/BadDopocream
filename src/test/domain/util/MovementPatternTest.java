package test.domain.util;

import main.domain.util.*;

import main.domain.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para MovementPattern.
 */
@DisplayName("MovementPattern Tests")
class MovementPatternTest {

    private MovementPattern pattern;
    private Direction[] testDirections;

    @BeforeEach
    void setUp() {
        testDirections = new Direction[]{
            Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT
        };
        pattern = new MovementPattern(testDirections);
    }

    @Test
    @DisplayName("MP-01: Crear patrón de movimiento")
    void testMovementPatternCreation() {
        // Assert
        assertNotNull(pattern);
        assertEquals(4, pattern.getSize());
    }

    @Test
    @DisplayName("MP-02: Obtener siguiente dirección")
    void testGetNextDirection() {
        // Act & Assert
        assertEquals(Direction.UP, pattern.getNextDirection());
        assertEquals(Direction.RIGHT, pattern.getNextDirection());
        assertEquals(Direction.DOWN, pattern.getNextDirection());
        assertEquals(Direction.LEFT, pattern.getNextDirection());
    }

    @Test
    @DisplayName("MP-03: Reiniciar patrón")
    void testPatternReset() {
        // Arrange
        pattern.getNextDirection();
        pattern.getNextDirection();
        
        // Act
        pattern.reset();
        
        // Assert
        assertEquals(0, pattern.getCurrentIndex());
        assertEquals(Direction.UP, pattern.getNextDirection());
    }

    @Test
    @DisplayName("MP-04: Ciclo del patrón")
    void testPatternCycle() {
        // Act - recorrer todo el patrón
        for (int i = 0; i < testDirections.length; i++) {
            pattern.getNextDirection();
        }
        
        // Assert - debe volver al inicio
        assertEquals(Direction.UP, pattern.getNextDirection());
    }

    @Test
    @DisplayName("MP-05: Patrón vacío")
    void testEmptyPattern() {
        // Arrange
        MovementPattern emptyPattern = new MovementPattern(new Direction[0]);
        
        // Assert
        assertTrue(emptyPattern.isEmpty());
        assertNull(emptyPattern.getNextDirection());
        assertNull(emptyPattern.getCurrentDirection());
    }

    @Test
    @DisplayName("MP-05: Patrón con null")
    void testNullPattern() {
        // Arrange
        MovementPattern nullPattern = new MovementPattern(null);
        
        // Assert
        assertTrue(nullPattern.isEmpty());
        assertNull(nullPattern.getNextDirection());
    }

    @Test
    @DisplayName("getCurrentDirection no avanza el índice")
    void testGetCurrentDirection() {
        // Act
        Direction current1 = pattern.getCurrentDirection();
        Direction current2 = pattern.getCurrentDirection();
        
        // Assert
        assertEquals(current1, current2);
        assertEquals(0, pattern.getCurrentIndex());
    }

    @Test
    @DisplayName("getCurrentIndex retorna índice correcto")
    void testGetCurrentIndex() {
        // Assert inicial
        assertEquals(0, pattern.getCurrentIndex());
        
        // Act
        pattern.getNextDirection();
        
        // Assert
        assertEquals(1, pattern.getCurrentIndex());
    }

    @Test
    @DisplayName("getDirections retorna copia")
    void testGetDirectionsReturnsCopy() {
        // Act
        Direction[] copy = pattern.getDirections();
        copy[0] = Direction.DOWN;
        
        // Assert - el original no debe cambiar
        assertEquals(Direction.UP, pattern.getNextDirection());
    }

    @Test
    @DisplayName("Patrón con una sola dirección")
    void testSingleDirectionPattern() {
        // Arrange
        MovementPattern singlePattern = new MovementPattern(new Direction[]{Direction.UP});
        
        // Act & Assert
        assertEquals(Direction.UP, singlePattern.getNextDirection());
        assertEquals(Direction.UP, singlePattern.getNextDirection());
        assertEquals(Direction.UP, singlePattern.getNextDirection());
    }

    @Test
    @DisplayName("Patrón completo de 4 direcciones en ciclo")
    void testFullCyclePattern() {
        // Act & Assert - dos ciclos completos
        for (int cycle = 0; cycle < 2; cycle++) {
            assertEquals(Direction.UP, pattern.getNextDirection());
            assertEquals(Direction.RIGHT, pattern.getNextDirection());
            assertEquals(Direction.DOWN, pattern.getNextDirection());
            assertEquals(Direction.LEFT, pattern.getNextDirection());
        }
    }
}
