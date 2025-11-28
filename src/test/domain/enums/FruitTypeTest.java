package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración FruitType.
 */
@DisplayName("FruitType Enum Tests")
class FruitTypeTest {

    @Test
    @DisplayName("FT-01: Debe contener los 4 tipos de fruta")
    void testFruitTypeValues() {
        // Arrange & Act
        FruitType[] types = FruitType.values();
        
        // Assert
        assertEquals(4, types.length);
        assertNotNull(FruitType.GRAPE);
        assertNotNull(FruitType.BANANA);
        assertNotNull(FruitType.PINEAPPLE);
        assertNotNull(FruitType.CHERRY);
    }

    @Test
    @DisplayName("FT-02: GRAPE otorga 100 puntos")
    void testGrapePoints() {
        assertEquals(100, FruitType.GRAPE.getPoints());
    }

    @Test
    @DisplayName("FT-02: BANANA otorga 150 puntos")
    void testBananaPoints() {
        assertEquals(150, FruitType.BANANA.getPoints());
    }

    @Test
    @DisplayName("FT-02: PINEAPPLE otorga 200 puntos")
    void testPineapplePoints() {
        assertEquals(200, FruitType.PINEAPPLE.getPoints());
    }

    @Test
    @DisplayName("FT-02: CHERRY otorga 300 puntos")
    void testCherryPoints() {
        assertEquals(300, FruitType.CHERRY.getPoints());
    }

    @Test
    @DisplayName("FT-01: GRAPE tiene símbolo 'G'")
    void testGrapeSymbol() {
        assertEquals('G', FruitType.GRAPE.getSymbol());
    }

    @Test
    @DisplayName("FT-01: BANANA tiene símbolo 'B'")
    void testBananaSymbol() {
        assertEquals('B', FruitType.BANANA.getSymbol());
    }

    @Test
    @DisplayName("FT-01: PINEAPPLE tiene símbolo 'P'")
    void testPineappleSymbol() {
        assertEquals('P', FruitType.PINEAPPLE.getSymbol());
    }

    @Test
    @DisplayName("FT-01: CHERRY tiene símbolo 'H'")
    void testCherrySymbol() {
        assertEquals('H', FruitType.CHERRY.getSymbol());
    }

    @Test
    @DisplayName("Las frutas más difíciles de obtener valen más puntos")
    void testPointsProgression() {
        assertTrue(FruitType.BANANA.getPoints() > FruitType.GRAPE.getPoints());
        assertTrue(FruitType.PINEAPPLE.getPoints() > FruitType.BANANA.getPoints());
        assertTrue(FruitType.CHERRY.getPoints() > FruitType.PINEAPPLE.getPoints());
    }
}
