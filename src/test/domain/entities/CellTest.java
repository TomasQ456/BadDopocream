package test.domain.entities;

import main.domain.entities.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Cell.
 */
@DisplayName("Cell Tests")
class CellTest {

    private Cell cell;

    // Clase concreta para testing de StaticObject
    private static class TestStaticObject extends StaticObject {
        public TestStaticObject(int x, int y, char symbol, boolean isDestructible, boolean isSolid) {
            super(x, y, symbol, isDestructible, isSolid);
        }
        
        @Override
        public void update() {
            // No hace nada para testing
        }
    }

    // Mock de Player para testing
    private static class MockPlayer extends Player {
        public MockPlayer(int x, int y) {
            super(x, y, null, null);
        }
        
        @Override
        public void update(Object game) {
            // No hace nada para testing
        }
    }

    @BeforeEach
    void setUp() {
        cell = new Cell(5, 10);
    }

    @Test
    @DisplayName("CE-01: Crear celda vacía")
    void testCellCreation() {
        // Assert
        assertNotNull(cell);
        assertEquals(5, cell.getX());
        assertEquals(10, cell.getY());
        assertTrue(cell.isEmpty());
    }

    @Test
    @DisplayName("CE-02: Asignar objeto estático")
    void testSetStaticObject() {
        // Arrange
        TestStaticObject wall = new TestStaticObject(5, 10, '#', false, true);
        
        // Act
        cell.setStaticObject(wall);
        
        // Assert
        assertEquals(wall, cell.getStaticObject());
        assertTrue(cell.hasStaticObject());
    }

    @Test
    @DisplayName("CE-03: Obtener objeto estático")
    void testGetStaticObject() {
        // Arrange
        TestStaticObject wall = new TestStaticObject(5, 10, '#', false, true);
        cell.setStaticObject(wall);
        
        // Act
        GameObject result = cell.getStaticObject();
        
        // Assert
        assertEquals(wall, result);
    }

    @Test
    @DisplayName("CE-03: Obtener objeto estático cuando no hay")
    void testGetStaticObjectNull() {
        // Act
        GameObject result = cell.getStaticObject();
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("CE-04: Asignar jugador")
    void testSetPlayer() {
        // Arrange
        MockPlayer player = new MockPlayer(5, 10);
        
        // Act
        cell.setPlayer(player);
        
        // Assert
        assertEquals(player, cell.getPlayer());
        assertTrue(cell.hasPlayer());
    }

    @Test
    @DisplayName("CE-05: Obtener jugador")
    void testGetPlayer() {
        // Arrange
        MockPlayer player = new MockPlayer(5, 10);
        cell.setPlayer(player);
        
        // Act
        Player result = cell.getPlayer();
        
        // Assert
        assertEquals(player, result);
    }

    @Test
    @DisplayName("CE-05: Obtener jugador cuando no hay")
    void testGetPlayerNull() {
        // Act
        Player result = cell.getPlayer();
        
        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("CE-06: Verificar celda vacía - verdadero")
    void testIsEmptyTrue() {
        // Assert
        assertTrue(cell.isEmpty());
    }

    @Test
    @DisplayName("CE-06: Verificar celda vacía - falso con objeto")
    void testIsEmptyFalseWithObject() {
        // Arrange
        cell.setStaticObject(new TestStaticObject(5, 10, '#', false, true));
        
        // Assert
        assertFalse(cell.isEmpty());
    }

    @Test
    @DisplayName("CE-06: Verificar celda vacía - falso con jugador")
    void testIsEmptyFalseWithPlayer() {
        // Arrange
        cell.setPlayer(new MockPlayer(5, 10));
        
        // Assert
        assertFalse(cell.isEmpty());
    }

    @Test
    @DisplayName("CE-07: Verificar si tiene jugador - verdadero")
    void testHasPlayerTrue() {
        // Arrange
        cell.setPlayer(new MockPlayer(5, 10));
        
        // Assert
        assertTrue(cell.hasPlayer());
    }

    @Test
    @DisplayName("CE-07: Verificar si tiene jugador - falso")
    void testHasPlayerFalse() {
        // Assert
        assertFalse(cell.hasPlayer());
    }

    @Test
    @DisplayName("CE-08: Verificar si tiene objeto - verdadero")
    void testHasStaticObjectTrue() {
        // Arrange
        cell.setStaticObject(new TestStaticObject(5, 10, '#', false, true));
        
        // Assert
        assertTrue(cell.hasStaticObject());
    }

    @Test
    @DisplayName("CE-08: Verificar si tiene objeto - falso")
    void testHasStaticObjectFalse() {
        // Assert
        assertFalse(cell.hasStaticObject());
    }

    @Test
    @DisplayName("CE-09: Limpiar celda")
    void testClear() {
        // Arrange
        cell.setStaticObject(new TestStaticObject(5, 10, '#', false, true));
        cell.setPlayer(new MockPlayer(5, 10));
        
        // Act
        cell.clear();
        
        // Assert
        assertTrue(cell.isEmpty());
        assertFalse(cell.hasPlayer());
        assertFalse(cell.hasStaticObject());
    }

    @Test
    @DisplayName("CE-10: Celda con muro es sólida")
    void testCellWithWall() {
        // Arrange
        TestStaticObject wall = new TestStaticObject(5, 10, '#', false, true);
        cell.setStaticObject(wall);
        
        // Assert
        assertTrue(cell.hasStaticObject());
        assertFalse(cell.isWalkable());
    }

    @Test
    @DisplayName("CE-11: Celda con fruta es transitable")
    void testCellWithFruit() {
        // Arrange
        TestStaticObject fruit = new TestStaticObject(5, 10, 'F', false, false);
        cell.setStaticObject(fruit);
        
        // Assert
        assertTrue(cell.hasStaticObject());
        assertTrue(cell.isWalkable());
    }

    @Test
    @DisplayName("Celda vacía es transitable")
    void testEmptyCellIsWalkable() {
        // Assert
        assertTrue(cell.isWalkable());
    }

    @Test
    @DisplayName("Celda puede tener objeto y jugador simultáneamente")
    void testCellWithObjectAndPlayer() {
        // Arrange
        TestStaticObject fruit = new TestStaticObject(5, 10, 'F', false, false);
        MockPlayer player = new MockPlayer(5, 10);
        
        // Act
        cell.setStaticObject(fruit);
        cell.setPlayer(player);
        
        // Assert
        assertTrue(cell.hasStaticObject());
        assertTrue(cell.hasPlayer());
        assertFalse(cell.isEmpty());
    }

    @Test
    @DisplayName("removeStaticObject remueve solo el objeto")
    void testRemoveStaticObject() {
        // Arrange
        cell.setStaticObject(new TestStaticObject(5, 10, '#', false, true));
        cell.setPlayer(new MockPlayer(5, 10));
        
        // Act
        cell.removeStaticObject();
        
        // Assert
        assertFalse(cell.hasStaticObject());
        assertTrue(cell.hasPlayer());
    }

    @Test
    @DisplayName("removePlayer remueve solo el jugador")
    void testRemovePlayer() {
        // Arrange
        cell.setStaticObject(new TestStaticObject(5, 10, '#', false, true));
        cell.setPlayer(new MockPlayer(5, 10));
        
        // Act
        cell.removePlayer();
        
        // Assert
        assertTrue(cell.hasStaticObject());
        assertFalse(cell.hasPlayer());
    }

    @Test
    @DisplayName("toString contiene información de la celda")
    void testToString() {
        // Act
        String result = cell.toString();
        
        // Assert
        assertTrue(result.contains("Cell"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
    }
}
