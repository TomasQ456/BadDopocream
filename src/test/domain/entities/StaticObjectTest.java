package test.domain.entities;

import main.domain.entities.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para StaticObject.
 */
@DisplayName("StaticObject Tests")
class StaticObjectTest {

    // Clase concreta para testing
    private static class TestStaticObject extends StaticObject {
        public TestStaticObject(int x, int y, char symbol, boolean isDestructible, boolean isSolid) {
            super(x, y, symbol, isDestructible, isSolid);
        }
        
        @Override
        public void update() {
            // No hace nada para testing
        }
    }

    @Test
    @DisplayName("SO-01: Crear objeto estático")
    void testStaticObjectCreation() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(5, 10, '#', false, true);
        
        // Assert
        assertNotNull(obj);
        assertEquals(5, obj.getX());
        assertEquals(10, obj.getY());
    }

    @Test
    @DisplayName("SO-02: Verificar solidez - sólido")
    void testIsSolidTrue() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(0, 0, '#', false, true);
        
        // Assert
        assertTrue(obj.isSolid());
    }

    @Test
    @DisplayName("SO-02: Verificar solidez - no sólido")
    void testIsSolidFalse() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(0, 0, 'F', false, false);
        
        // Assert
        assertFalse(obj.isSolid());
    }

    @Test
    @DisplayName("SO-03: StaticObject hereda de GameObject")
    void testInheritsFromGameObject() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(0, 0, 'S', true, true);
        
        // Assert
        assertTrue(obj instanceof GameObject);
    }

    @Test
    @DisplayName("StaticObject mantiene propiedades de GameObject")
    void testMaintainsGameObjectProperties() {
        // Arrange
        TestStaticObject obj = new TestStaticObject(3, 4, 'W', true, true);
        
        // Assert
        assertEquals(3, obj.getX());
        assertEquals(4, obj.getY());
        assertEquals('W', obj.getSymbol());
        assertTrue(obj.isDestructible());
    }

    @Test
    @DisplayName("setPosition funciona correctamente")
    void testSetPosition() {
        // Arrange
        TestStaticObject obj = new TestStaticObject(0, 0, 'S', true, true);
        
        // Act
        obj.setPosition(10, 20);
        
        // Assert
        assertEquals(10, obj.getX());
        assertEquals(20, obj.getY());
    }

    @Test
    @DisplayName("Objeto no sólido y no destructible")
    void testNonSolidNonDestructible() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(0, 0, 'F', false, false);
        
        // Assert
        assertFalse(obj.isSolid());
        assertFalse(obj.isDestructible());
    }

    @Test
    @DisplayName("Objeto sólido y destructible")
    void testSolidDestructible() {
        // Arrange & Act
        TestStaticObject obj = new TestStaticObject(0, 0, 'I', true, true);
        
        // Assert
        assertTrue(obj.isSolid());
        assertTrue(obj.isDestructible());
    }
}
