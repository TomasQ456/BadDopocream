package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.IceCreamFlavor;
import main.domain.enums.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para IceCream.
 */
@DisplayName("IceCream Tests")
class IceCreamTest {

    private IceCream iceCream;

    @BeforeEach
    void setUp() {
        iceCream = new IceCream(5, 5, IceCreamFlavor.VANILLA);
    }

    @Test
    @DisplayName("IC-01: Crear IceCream")
    void testIceCreamCreation() {
        // Assert
        assertNotNull(iceCream);
        assertEquals(5, iceCream.getX());
        assertEquals(5, iceCream.getY());
        assertEquals(IceCreamFlavor.VANILLA, iceCream.getFlavor());
    }

    @Test
    @DisplayName("IC-02: Puntuación inicial es 0")
    void testIceCreamScore() {
        // Assert
        assertEquals(0, iceCream.getScore());
    }

    @Test
    @DisplayName("IC-03: Frutas recolectadas inicial es 0")
    void testIceCreamFruitsCollected() {
        // Assert
        assertEquals(0, iceCream.getFruitsCollected());
    }

    @Test
    @DisplayName("IC-04: Recolectar fruta")
    void testIceCreamCollectFruit() {
        // Arrange
        Grape grape = new Grape(5, 5);
        
        // Act
        iceCream.collectFruit(grape);
        
        // Assert
        assertTrue(grape.isCollected());
        assertEquals(1, iceCream.getFruitsCollected());
        assertEquals(grape.getPoints(), iceCream.getScore());
    }

    @Test
    @DisplayName("IC-05: Actualizar puntuación")
    void testIceCreamScoreUpdate() {
        // Arrange
        Grape grape = new Grape(0, 0);
        Banana banana = new Banana(1, 1);
        
        // Act
        iceCream.collectFruit(grape);
        iceCream.collectFruit(banana);
        
        // Assert
        assertEquals(grape.getPoints() + banana.getPoints(), iceCream.getScore());
    }

    @Test
    @DisplayName("IC-06: Método update no lanza excepción")
    void testIceCreamUpdate() {
        // Act & Assert
        assertDoesNotThrow(() -> iceCream.update(null));
    }

    @Test
    @DisplayName("IC-07: IceCream es un Player")
    void testIceCreamIsPlayer() {
        // Assert
        assertTrue(iceCream instanceof Player);
    }

    @Test
    @DisplayName("No recolectar fruta ya recolectada")
    void testDoNotCollectAlreadyCollectedFruit() {
        // Arrange
        Grape grape = new Grape(0, 0);
        grape.collect();
        
        // Act
        iceCream.collectFruit(grape);
        
        // Assert
        assertEquals(0, iceCream.getFruitsCollected());
        assertEquals(0, iceCream.getScore());
    }

    @Test
    @DisplayName("No recolectar fruta null")
    void testDoNotCollectNullFruit() {
        // Act
        iceCream.collectFruit(null);
        
        // Assert
        assertEquals(0, iceCream.getFruitsCollected());
        assertEquals(0, iceCream.getScore());
    }

    @Test
    @DisplayName("addScore añade puntos")
    void testAddScore() {
        // Act
        iceCream.addScore(500);
        
        // Assert
        assertEquals(500, iceCream.getScore());
    }

    @Test
    @DisplayName("resetStats reinicia estadísticas")
    void testResetStats() {
        // Arrange
        iceCream.collectFruit(new Grape(0, 0));
        iceCream.collectFruit(new Banana(1, 1));
        
        // Act
        iceCream.resetStats();
        
        // Assert
        assertEquals(0, iceCream.getScore());
        assertEquals(0, iceCream.getFruitsCollected());
    }

    @Test
    @DisplayName("IceCream con tipo HUMAN por defecto")
    void testIceCreamDefaultPlayerType() {
        // Assert
        assertEquals(PlayerType.HUMAN, iceCream.getPlayerType());
    }

    @Test
    @DisplayName("IceCream puede ser tipo AI")
    void testIceCreamAIType() {
        // Arrange
        IceCream aiIceCream = new IceCream(0, 0, IceCreamFlavor.CHOCOLATE, PlayerType.AI);
        
        // Assert
        assertEquals(PlayerType.AI, aiIceCream.getPlayerType());
    }

    @Test
    @DisplayName("respawn no reinicia el score")
    void testRespawnDoesNotResetScore() {
        // Arrange
        iceCream.collectFruit(new Grape(0, 0));
        int scoreBeforeDeath = iceCream.getScore();
        iceCream.kill();
        
        // Act
        iceCream.respawn();
        
        // Assert
        assertEquals(scoreBeforeDeath, iceCream.getScore());
    }

    @Test
    @DisplayName("Recolectar múltiples tipos de fruta")
    void testCollectMultipleFruitTypes() {
        // Arrange
        Grape grape = new Grape(0, 0);
        Banana banana = new Banana(1, 1);
        Pineapple pineapple = new Pineapple(2, 2);
        Cherry cherry = new Cherry(3, 3);
        
        // Act
        iceCream.collectFruit(grape);
        iceCream.collectFruit(banana);
        iceCream.collectFruit(pineapple);
        iceCream.collectFruit(cherry);
        
        // Assert
        assertEquals(4, iceCream.getFruitsCollected());
        assertEquals(100 + 150 + 200 + 300, iceCream.getScore());
    }

    @Test
    @DisplayName("toString contiene información del IceCream")
    void testToString() {
        // Act
        String result = iceCream.toString();
        
        // Assert
        assertTrue(result.contains("IceCream"));
        assertTrue(result.contains("VANILLA"));
        assertTrue(result.contains("score"));
    }

    @Test
    @DisplayName("IceCream hereda propiedades de Player")
    void testIceCreamInheritsPlayer() {
        // Assert
        assertEquals(3, iceCream.getLives());
        assertTrue(iceCream.isAlive());
    }
}
