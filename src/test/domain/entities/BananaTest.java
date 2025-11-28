package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.FruitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Banana.
 */
@DisplayName("Banana Tests")
class BananaTest {

    private Banana banana;

    @BeforeEach
    void setUp() {
        banana = new Banana(5, 10);
    }

    @Test
    @DisplayName("BA-01: Crear banana")
    void testBananaCreation() {
        // Assert
        assertNotNull(banana);
        assertEquals(5, banana.getX());
        assertEquals(10, banana.getY());
    }

    @Test
    @DisplayName("BA-02: Puntos de banana")
    void testBananaPoints() {
        // Assert
        assertEquals(150, banana.getPoints());
        assertEquals(FruitType.BANANA.getPoints(), banana.getPoints());
    }

    @Test
    @DisplayName("BA-03: Tipo BANANA")
    void testBananaType() {
        // Assert
        assertEquals(FruitType.BANANA, banana.getType());
    }

    @Test
    @DisplayName("BA-04: Actualizar (estática)")
    void testBananaUpdate() {
        // Arrange
        int initialX = banana.getX();
        int initialY = banana.getY();
        
        // Act
        banana.update();
        
        // Assert - la posición no debe cambiar
        assertEquals(initialX, banana.getX());
        assertEquals(initialY, banana.getY());
    }

    @Test
    @DisplayName("BA-05: Símbolo correcto")
    void testBananaSymbol() {
        // Assert
        assertEquals('B', banana.getSymbol());
        assertEquals(FruitType.BANANA.getSymbol(), banana.getSymbol());
    }

    @Test
    @DisplayName("Banana puede ser recolectada")
    void testBananaCanBeCollected() {
        // Arrange
        assertFalse(banana.isCollected());
        
        // Act
        banana.collect();
        
        // Assert
        assertTrue(banana.isCollected());
    }

    @Test
    @DisplayName("Banana hereda de Fruit")
    void testBananaInheritsFromFruit() {
        // Assert
        assertTrue(banana instanceof Fruit);
    }

    @Test
    @DisplayName("Banana vale más que Grape")
    void testBananaWorthMoreThanGrape() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertTrue(banana.getPoints() > grape.getPoints());
    }
}
