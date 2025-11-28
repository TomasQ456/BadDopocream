package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.Direction;
import main.domain.enums.FruitType;
import main.domain.interfaces.Movable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Pineapple.
 */
@DisplayName("Pineapple Tests")
class PineappleTest {

    private Pineapple pineapple;

    @BeforeEach
    void setUp() {
        pineapple = new Pineapple(5, 5);
        pineapple.setMapDimensions(10, 10);
    }

    @Test
    @DisplayName("PI-01: Crear piña")
    void testPineappleCreation() {
        // Assert
        assertNotNull(pineapple);
        assertEquals(5, pineapple.getX());
        assertEquals(5, pineapple.getY());
    }

    @Test
    @DisplayName("PI-02: Puntos de piña")
    void testPineapplePoints() {
        // Assert
        assertEquals(200, pineapple.getPoints());
        assertEquals(FruitType.PINEAPPLE.getPoints(), pineapple.getPoints());
    }

    @Test
    @DisplayName("PI-03: Tipo PINEAPPLE")
    void testPineappleType() {
        // Assert
        assertEquals(FruitType.PINEAPPLE, pineapple.getType());
    }

    @Test
    @DisplayName("PI-04: Movimiento de piña")
    void testPineappleMove() {
        // Arrange
        int initialX = pineapple.getX();
        
        // Act
        pineapple.move(Direction.RIGHT);
        
        // Assert
        assertEquals(initialX + 1, pineapple.getX());
    }

    @Test
    @DisplayName("PI-05: Dirección actual")
    void testPineappleDirection() {
        // Assert - dirección inicial
        assertEquals(Direction.RIGHT, pineapple.getCurrentDirection());
        
        // Act
        pineapple.setCurrentDirection(Direction.LEFT);
        
        // Assert
        assertEquals(Direction.LEFT, pineapple.getCurrentDirection());
    }

    @Test
    @DisplayName("PI-05: Dirección inicial personalizada")
    void testPineappleInitialDirection() {
        // Arrange & Act
        Pineapple customPineapple = new Pineapple(0, 0, Direction.UP);
        
        // Assert
        assertEquals(Direction.UP, customPineapple.getCurrentDirection());
    }

    @Test
    @DisplayName("PI-06: Velocidad de piña")
    void testPineappleSpeed() {
        // Assert - velocidad inicial
        assertEquals(1, pineapple.getSpeed());
        
        // Act
        pineapple.setSpeed(2);
        
        // Assert
        assertEquals(2, pineapple.getSpeed());
    }

    @Test
    @DisplayName("PI-07: Colisión con límite del mapa")
    void testPineappleCollisionWithBounds() {
        // Arrange
        Pineapple edgePineapple = new Pineapple(9, 5, Direction.RIGHT);
        edgePineapple.setMapDimensions(10, 10);
        
        // Assert
        assertFalse(edgePineapple.canMoveTo(10, 5)); // Fuera del límite
        assertTrue(edgePineapple.canMoveTo(9, 5));   // Dentro del límite
    }

    @Test
    @DisplayName("PI-08: Cambio de dirección (rebote)")
    void testPineappleChangeDirection() {
        // Arrange
        pineapple.setCurrentDirection(Direction.RIGHT);
        
        // Act
        pineapple.reverseDirection();
        
        // Assert
        assertEquals(Direction.LEFT, pineapple.getCurrentDirection());
    }

    @Test
    @DisplayName("PI-09: Actualizar posición")
    void testPineappleUpdate() {
        // Arrange
        pineapple.setCurrentDirection(Direction.RIGHT);
        int initialX = pineapple.getX();
        
        // Act
        pineapple.update();
        
        // Assert
        assertEquals(initialX + 1, pineapple.getX());
    }

    @Test
    @DisplayName("Update rebota en el límite")
    void testPineappleUpdateBounce() {
        // Arrange
        Pineapple edgePineapple = new Pineapple(9, 5, Direction.RIGHT);
        edgePineapple.setMapDimensions(10, 10);
        
        // Act
        edgePineapple.update();
        
        // Assert
        assertEquals(Direction.LEFT, edgePineapple.getCurrentDirection());
    }

    @Test
    @DisplayName("Pineapple implementa Movable")
    void testPineappleImplementsMovable() {
        // Assert
        assertTrue(pineapple instanceof Movable);
    }

    @Test
    @DisplayName("Pineapple hereda de Fruit")
    void testPineappleInheritsFromFruit() {
        // Assert
        assertTrue(pineapple instanceof Fruit);
    }

    @Test
    @DisplayName("Piña recolectada no se mueve")
    void testCollectedPineappleDoesNotMove() {
        // Arrange
        int initialX = pineapple.getX();
        pineapple.collect();
        
        // Act
        pineapple.update();
        
        // Assert
        assertEquals(initialX, pineapple.getX());
    }

    @Test
    @DisplayName("Piña vale más que Banana")
    void testPineappleWorthMoreThanBanana() {
        // Arrange
        Banana banana = new Banana(0, 0);
        
        // Assert
        assertTrue(pineapple.getPoints() > banana.getPoints());
    }

    @Test
    @DisplayName("Símbolo correcto")
    void testPineappleSymbol() {
        // Assert
        assertEquals('P', pineapple.getSymbol());
    }

    @Test
    @DisplayName("canMoveTo sin dimensiones del mapa retorna true")
    void testCanMoveToWithoutMapDimensions() {
        // Arrange
        Pineapple newPineapple = new Pineapple(0, 0);
        
        // Assert
        assertTrue(newPineapple.canMoveTo(100, 100));
    }

    @Test
    @DisplayName("Movimiento en todas las direcciones")
    void testMoveAllDirections() {
        // UP
        Pineapple p1 = new Pineapple(5, 5);
        p1.move(Direction.UP);
        assertEquals(4, p1.getY());
        
        // DOWN
        Pineapple p2 = new Pineapple(5, 5);
        p2.move(Direction.DOWN);
        assertEquals(6, p2.getY());
        
        // LEFT
        Pineapple p3 = new Pineapple(5, 5);
        p3.move(Direction.LEFT);
        assertEquals(4, p3.getX());
        
        // RIGHT
        Pineapple p4 = new Pineapple(5, 5);
        p4.move(Direction.RIGHT);
        assertEquals(6, p4.getX());
    }
}
