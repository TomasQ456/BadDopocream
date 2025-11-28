package test.domain.entities;

import main.domain.entities.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Wall.
 */
@DisplayName("Wall Tests")
class WallTest {

    // Clase concreta para testing
    private static class TestWall extends Wall {
        public TestWall(int x, int y, char symbol, boolean isDestructible) {
            super(x, y, symbol, isDestructible);
        }
        
        @Override
        public void update() {
            // No hace nada para testing
        }
    }

    @Test
    @DisplayName("WA-01: Muro es sólido")
    void testWallIsSolid() {
        // Arrange & Act
        TestWall wall = new TestWall(0, 0, '#', false);
        
        // Assert
        assertTrue(wall.isSolid());
    }

    @Test
    @DisplayName("WA-02: Muro es StaticObject")
    void testWallIsStaticObject() {
        // Arrange & Act
        TestWall wall = new TestWall(0, 0, '#', false);
        
        // Assert
        assertTrue(wall instanceof StaticObject);
    }

    @Test
    @DisplayName("Muro hereda de GameObject")
    void testWallIsGameObject() {
        // Arrange & Act
        TestWall wall = new TestWall(0, 0, '#', false);
        
        // Assert
        assertTrue(wall instanceof GameObject);
    }

    @Test
    @DisplayName("Muro mantiene posición correcta")
    void testWallPosition() {
        // Arrange & Act
        TestWall wall = new TestWall(5, 10, '#', false);
        
        // Assert
        assertEquals(5, wall.getX());
        assertEquals(10, wall.getY());
    }

    @Test
    @DisplayName("Muro mantiene símbolo correcto")
    void testWallSymbol() {
        // Arrange & Act
        TestWall wall = new TestWall(0, 0, 'W', true);
        
        // Assert
        assertEquals('W', wall.getSymbol());
    }

    @Test
    @DisplayName("Muro destructible es marcado correctamente")
    void testWallDestructible() {
        // Arrange & Act
        TestWall destructible = new TestWall(0, 0, 'D', true);
        TestWall indestructible = new TestWall(0, 0, 'I', false);
        
        // Assert
        assertTrue(destructible.isDestructible());
        assertFalse(indestructible.isDestructible());
    }
}
