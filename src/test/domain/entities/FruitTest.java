package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.FruitType;
import main.domain.interfaces.Collectible;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Fruit (usando Grape como implementación concreta).
 */
@DisplayName("Fruit Tests")
class FruitTest {

    @Test
    @DisplayName("FR-01: Obtener puntos de fruta")
    void testFruitPoints() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertEquals(FruitType.GRAPE.getPoints(), grape.getPoints());
    }

    @Test
    @DisplayName("FR-02: Obtener tipo de fruta")
    void testFruitType() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertEquals(FruitType.GRAPE, grape.getType());
    }

    @Test
    @DisplayName("FR-03: Recolectar fruta")
    void testFruitCollect() {
        // Arrange
        Grape grape = new Grape(0, 0);
        assertFalse(grape.isCollected());
        
        // Act
        grape.collect();
        
        // Assert
        assertTrue(grape.isCollected());
    }

    @Test
    @DisplayName("FR-04: Fruta implementa Collectible")
    void testFruitIsCollectible() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertTrue(grape instanceof Collectible);
    }

    @Test
    @DisplayName("Fruta no es sólida")
    void testFruitNotSolid() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertFalse(grape.isSolid());
    }

    @Test
    @DisplayName("Fruta hereda de StaticObject")
    void testFruitInheritsStaticObject() {
        // Arrange
        Grape grape = new Grape(0, 0);
        
        // Assert
        assertTrue(grape instanceof StaticObject);
    }

    @Test
    @DisplayName("toString contiene información de la fruta")
    void testFruitToString() {
        // Arrange
        Grape grape = new Grape(5, 10);
        
        // Act
        String result = grape.toString();
        
        // Assert
        assertTrue(result.contains("Grape"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("10"));
    }
}
