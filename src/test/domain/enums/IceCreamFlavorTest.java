package test.domain.enums;

import main.domain.enums.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la enumeración IceCreamFlavor.
 */
@DisplayName("IceCreamFlavor Enum Tests")
class IceCreamFlavorTest {

    @Test
    @DisplayName("ICF-01: Debe contener todos los sabores")
    void testFlavorValues() {
        // Arrange & Act
        IceCreamFlavor[] flavors = IceCreamFlavor.values();
        
        // Assert
        assertEquals(5, flavors.length);
        assertNotNull(IceCreamFlavor.VANILLA);
        assertNotNull(IceCreamFlavor.CHOCOLATE);
        assertNotNull(IceCreamFlavor.STRAWBERRY);
        assertNotNull(IceCreamFlavor.MINT);
        assertNotNull(IceCreamFlavor.CARAMEL);
    }

    @Test
    @DisplayName("ICF-01: VANILLA tiene símbolo 'V'")
    void testVanillaSymbol() {
        assertEquals('V', IceCreamFlavor.VANILLA.getSymbol());
    }

    @Test
    @DisplayName("ICF-01: CHOCOLATE tiene símbolo 'C'")
    void testChocolateSymbol() {
        assertEquals('C', IceCreamFlavor.CHOCOLATE.getSymbol());
    }

    @Test
    @DisplayName("ICF-01: STRAWBERRY tiene símbolo 'S'")
    void testStrawberrySymbol() {
        assertEquals('S', IceCreamFlavor.STRAWBERRY.getSymbol());
    }

    @Test
    @DisplayName("ICF-01: MINT tiene símbolo 'M'")
    void testMintSymbol() {
        assertEquals('M', IceCreamFlavor.MINT.getSymbol());
    }

    @Test
    @DisplayName("ICF-01: CARAMEL tiene símbolo 'A'")
    void testCaramelSymbol() {
        assertEquals('A', IceCreamFlavor.CARAMEL.getSymbol());
    }

    @Test
    @DisplayName("Todos los símbolos son únicos")
    void testUniqueSymbols() {
        IceCreamFlavor[] flavors = IceCreamFlavor.values();
        for (int i = 0; i < flavors.length; i++) {
            for (int j = i + 1; j < flavors.length; j++) {
                assertNotEquals(flavors[i].getSymbol(), flavors[j].getSymbol(),
                    "Los símbolos de " + flavors[i] + " y " + flavors[j] + " no deberían ser iguales");
            }
        }
    }
}
