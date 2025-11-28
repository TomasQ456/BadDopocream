package test.domain.entities;

import main.domain.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para IndestructibleWall.
 */
@DisplayName("IndestructibleWall Tests")
class IndestructibleWallTest {

    private IndestructibleWall wall;

    @BeforeEach
    void setUp() {
        wall = new IndestructibleWall(5, 10);
    }

    @Test
    @DisplayName("IDW-01: Crear muro indestructible")
    void testIndestructibleWallCreation() {
        // Assert
        assertNotNull(wall);
        assertEquals(5, wall.getX());
        assertEquals(10, wall.getY());
    }

    @Test
    @DisplayName("IDW-02: No es destructible")
    void testIndestructibleWallNotDestructible() {
        // Assert
        assertFalse(wall.isDestructible());
    }

    @Test
    @DisplayName("IDW-03: Update no hace nada")
    void testIndestructibleWallUpdate() {
        // Arrange
        int initialX = wall.getX();
        int initialY = wall.getY();
        
        // Act
        wall.update();
        
        // Assert
        assertEquals(initialX, wall.getX());
        assertEquals(initialY, wall.getY());
    }

    @Test
    @DisplayName("IDW-04: Símbolo correcto")
    void testIndestructibleWallSymbol() {
        // Assert
        assertEquals('#', wall.getSymbol());
        assertEquals(IndestructibleWall.SYMBOL, wall.getSymbol());
    }

    @Test
    @DisplayName("IndestructibleWall es sólido")
    void testIndestructibleWallIsSolid() {
        // Assert
        assertTrue(wall.isSolid());
    }

    @Test
    @DisplayName("IndestructibleWall hereda de Wall")
    void testInheritsFromWall() {
        // Assert
        assertTrue(wall instanceof Wall);
    }

    @Test
    @DisplayName("IndestructibleWall hereda de StaticObject")
    void testInheritsFromStaticObject() {
        // Assert
        assertTrue(wall instanceof StaticObject);
    }

    @Test
    @DisplayName("IndestructibleWall hereda de GameObject")
    void testInheritsFromGameObject() {
        // Assert
        assertTrue(wall instanceof GameObject);
    }

    @Test
    @DisplayName("toString contiene información del muro")
    void testToString() {
        // Act
        String result = wall.toString();
        
        // Assert
        assertTrue(result.contains("IndestructibleWall"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
    }

    @Test
    @DisplayName("setPosition funciona correctamente")
    void testSetPosition() {
        // Act
        wall.setPosition(20, 30);
        
        // Assert
        assertEquals(20, wall.getX());
        assertEquals(30, wall.getY());
    }
}
