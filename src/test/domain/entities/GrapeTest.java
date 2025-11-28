package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.FruitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Grape.
 */
@DisplayName("Grape Tests")
class GrapeTest {

    private Grape grape;

    @BeforeEach
    void setUp() {
        grape = new Grape(5, 10);
    }

    @Test
    @DisplayName("GR-01: Crear uva")
    void testGrapeCreation() {
        // Assert
        assertNotNull(grape);
        assertEquals(5, grape.getX());
        assertEquals(10, grape.getY());
    }

    @Test
    @DisplayName("GR-02: Puntos de uva")
    void testGrapePoints() {
        // Assert
        assertEquals(100, grape.getPoints());
        assertEquals(FruitType.GRAPE.getPoints(), grape.getPoints());
    }

    @Test
    @DisplayName("GR-03: Tipo GRAPE")
    void testGrapeType() {
        // Assert
        assertEquals(FruitType.GRAPE, grape.getType());
    }

    @Test
    @DisplayName("GR-04: Actualizar (estática)")
    void testGrapeUpdate() {
        // Arrange
        int initialX = grape.getX();
        int initialY = grape.getY();
        
        // Act
        grape.update();
        
        // Assert - la posición no debe cambiar
        assertEquals(initialX, grape.getX());
        assertEquals(initialY, grape.getY());
    }

    @Test
    @DisplayName("GR-05: Símbolo correcto")
    void testGrapeSymbol() {
        // Assert
        assertEquals('G', grape.getSymbol());
        assertEquals(FruitType.GRAPE.getSymbol(), grape.getSymbol());
    }

    @Test
    @DisplayName("Uva puede ser recolectada")
    void testGrapeCanBeCollected() {
        // Arrange
        assertFalse(grape.isCollected());
        
        // Act
        grape.collect();
        
        // Assert
        assertTrue(grape.isCollected());
    }

    @Test
    @DisplayName("Uva hereda de Fruit")
    void testGrapeInheritsFromFruit() {
        // Assert
        assertTrue(grape instanceof Fruit);
    }
}
