package test.domain.entities;

import main.domain.entities.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para GameObject.
 */
@DisplayName("GameObject Tests")
class GameObjectTest {

    // Clase concreta para testing
    private static class TestGameObject extends GameObject {
        private boolean updateCalled = false;
        
        public TestGameObject(int x, int y, char symbol, boolean isDestructible) {
            super(x, y, symbol, isDestructible);
        }
        
        @Override
        public void update() {
            updateCalled = true;
        }
        
        public boolean wasUpdateCalled() {
            return updateCalled;
        }
    }

    @Test
    @DisplayName("GO-01: Verificar posición inicial")
    void testGameObjectPosition() {
        // Arrange & Act
        TestGameObject obj = new TestGameObject(5, 10, 'T', true);
        
        // Assert
        assertEquals(5, obj.getX());
        assertEquals(10, obj.getY());
    }

    @Test
    @DisplayName("GO-02: Cambiar posición")
    void testSetPosition() {
        // Arrange
        TestGameObject obj = new TestGameObject(0, 0, 'T', true);
        
        // Act
        obj.setPosition(15, 20);
        
        // Assert
        assertEquals(15, obj.getX());
        assertEquals(20, obj.getY());
    }

    @Test
    @DisplayName("GO-03: Obtener símbolo")
    void testGetSymbol() {
        // Arrange & Act
        TestGameObject obj = new TestGameObject(0, 0, 'X', true);
        
        // Assert
        assertEquals('X', obj.getSymbol());
    }

    @Test
    @DisplayName("GO-04: Verificar destructibilidad - destructible")
    void testIsDestructibleTrue() {
        // Arrange & Act
        TestGameObject obj = new TestGameObject(0, 0, 'T', true);
        
        // Assert
        assertTrue(obj.isDestructible());
    }

    @Test
    @DisplayName("GO-04: Verificar destructibilidad - indestructible")
    void testIsDestructibleFalse() {
        // Arrange & Act
        TestGameObject obj = new TestGameObject(0, 0, 'T', false);
        
        // Assert
        assertFalse(obj.isDestructible());
    }

    @Test
    @DisplayName("GO-05: Verificar método abstracto update")
    void testAbstractUpdate() {
        // Arrange
        TestGameObject obj = new TestGameObject(0, 0, 'T', true);
        
        // Act
        obj.update();
        
        // Assert
        assertTrue(obj.wasUpdateCalled());
    }

    @Test
    @DisplayName("toString contiene información del objeto")
    void testToString() {
        // Arrange
        TestGameObject obj = new TestGameObject(5, 10, 'T', true);
        
        // Act
        String result = obj.toString();
        
        // Assert
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("T"));
    }

    @Test
    @DisplayName("equals compara correctamente objetos iguales")
    void testEqualsTrue() {
        // Arrange
        TestGameObject obj1 = new TestGameObject(5, 10, 'T', true);
        TestGameObject obj2 = new TestGameObject(5, 10, 'T', true);
        
        // Act & Assert
        assertEquals(obj1, obj2);
    }

    @Test
    @DisplayName("equals compara correctamente objetos diferentes")
    void testEqualsFalse() {
        // Arrange
        TestGameObject obj1 = new TestGameObject(5, 10, 'T', true);
        TestGameObject obj2 = new TestGameObject(5, 11, 'T', true);
        
        // Act & Assert
        assertNotEquals(obj1, obj2);
    }

    @Test
    @DisplayName("hashCode es consistente")
    void testHashCode() {
        // Arrange
        TestGameObject obj1 = new TestGameObject(5, 10, 'T', true);
        TestGameObject obj2 = new TestGameObject(5, 10, 'T', true);
        
        // Act & Assert
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    @DisplayName("Posición negativa es válida")
    void testNegativePosition() {
        // Arrange & Act
        TestGameObject obj = new TestGameObject(-5, -10, 'T', true);
        
        // Assert
        assertEquals(-5, obj.getX());
        assertEquals(-10, obj.getY());
    }
}
