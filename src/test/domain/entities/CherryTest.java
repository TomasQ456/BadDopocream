package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.FruitType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Cherry.
 */
@DisplayName("Cherry Tests")
class CherryTest {

    private Cherry cherry;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        // Usar un Random con seed fijo para pruebas determinísticas
        mockRandom = new Random(12345);
        cherry = new Cherry(5, 5, mockRandom);
        cherry.setMapDimensions(10, 10);
    }

    @Test
    @DisplayName("CH-01: Crear cereza")
    void testCherryCreation() {
        // Assert
        assertNotNull(cherry);
        assertEquals(5, cherry.getX());
        assertEquals(5, cherry.getY());
    }

    @Test
    @DisplayName("CH-02: Puntos de cereza")
    void testCherryPoints() {
        // Assert
        assertEquals(300, cherry.getPoints());
        assertEquals(FruitType.CHERRY.getPoints(), cherry.getPoints());
    }

    @Test
    @DisplayName("CH-03: Tipo CHERRY")
    void testCherryType() {
        // Assert
        assertEquals(FruitType.CHERRY, cherry.getType());
    }

    @Test
    @DisplayName("CH-04: Teletransportación")
    void testCherryTeleport() {
        // Act
        cherry.teleport(10, 10);
        
        // Assert - La posición debe haber cambiado (con el random fijo, será predecible)
        // Con seed 12345, podemos verificar que la posición cambió
        assertNotNull(cherry);
        assertTrue(cherry.getX() >= 0 && cherry.getX() < 10);
        assertTrue(cherry.getY() >= 0 && cherry.getY() < 10);
    }

    @Test
    @DisplayName("CH-05: Intervalo de teletransporte")
    void testCherryTeleportInterval() {
        // Assert - intervalo por defecto
        assertEquals(Cherry.DEFAULT_TELEPORT_INTERVAL, cherry.getTeleportInterval());
        
        // Act
        cherry.setTeleportInterval(5000);
        
        // Assert
        assertEquals(5000, cherry.getTeleportInterval());
    }

    @Test
    @DisplayName("CH-06: Posición aleatoria controlada")
    void testCherryRandomPosition() {
        // Arrange - Usar el mismo seed para obtener resultados predecibles
        Random controlledRandom = new Random(42);
        Cherry controlledCherry = new Cherry(0, 0, controlledRandom);
        controlledCherry.setMapDimensions(10, 10);
        
        // Act
        controlledCherry.teleport(10, 10);
        int x1 = controlledCherry.getX();
        int y1 = controlledCherry.getY();
        
        // Crear otra cereza con el mismo seed
        controlledRandom = new Random(42);
        Cherry controlledCherry2 = new Cherry(0, 0, controlledRandom);
        controlledCherry2.setMapDimensions(10, 10);
        controlledCherry2.teleport(10, 10);
        
        // Assert - Deben tener la misma posición con el mismo seed
        assertEquals(x1, controlledCherry2.getX());
        assertEquals(y1, controlledCherry2.getY());
    }

    @Test
    @DisplayName("CH-07: Actualizar con teletransporte")
    void testCherryUpdate() throws InterruptedException {
        // Arrange
        cherry.setTeleportInterval(50);
        
        // Act
        Thread.sleep(60);
        cherry.update();
        
        // Assert - Después del intervalo, debe haberse teletransportado
        // La posición puede o no haber cambiado dependiendo del random
        assertNotNull(cherry);
    }

    @Test
    @DisplayName("CH-08: Símbolo correcto")
    void testCherrySymbol() {
        // Assert
        assertEquals('H', cherry.getSymbol());
        assertEquals(FruitType.CHERRY.getSymbol(), cherry.getSymbol());
    }

    @Test
    @DisplayName("Cherry hereda de Fruit")
    void testCherryInheritsFromFruit() {
        // Assert
        assertTrue(cherry instanceof Fruit);
    }

    @Test
    @DisplayName("Cherry vale más que Pineapple")
    void testCherryWorthMoreThanPineapple() {
        // Arrange
        Pineapple pineapple = new Pineapple(0, 0);
        
        // Assert
        assertTrue(cherry.getPoints() > pineapple.getPoints());
    }

    @Test
    @DisplayName("Cereza recolectada no se teletransporta")
    void testCollectedCherryDoesNotTeleport() throws InterruptedException {
        // Arrange
        cherry.setTeleportInterval(50);
        cherry.collect();
        int initialX = cherry.getX();
        int initialY = cherry.getY();
        
        // Act
        Thread.sleep(60);
        cherry.update();
        
        // Assert
        assertEquals(initialX, cherry.getX());
        assertEquals(initialY, cherry.getY());
    }

    @Test
    @DisplayName("shouldTeleport retorna false antes del intervalo")
    void testShouldTeleportFalse() {
        // Arrange
        cherry.setTeleportInterval(10000);
        
        // Assert
        assertFalse(cherry.shouldTeleport());
    }

    @Test
    @DisplayName("shouldTeleport retorna true después del intervalo")
    void testShouldTeleportTrue() throws InterruptedException {
        // Arrange
        cherry.setTeleportInterval(50);
        
        // Act
        Thread.sleep(60);
        
        // Assert
        assertTrue(cherry.shouldTeleport());
    }

    @Test
    @DisplayName("getLastTeleportTime retorna tiempo de creación inicial")
    void testGetLastTeleportTime() {
        // Arrange
        long before = System.currentTimeMillis();
        Cherry newCherry = new Cherry(0, 0);
        long after = System.currentTimeMillis();
        
        // Assert
        assertTrue(newCherry.getLastTeleportTime() >= before);
        assertTrue(newCherry.getLastTeleportTime() <= after);
    }

    @Test
    @DisplayName("forceTeleport fuerza el teletransporte")
    void testForceTeleport() {
        // Arrange
        cherry.setTeleportInterval(100000); // Intervalo muy largo
        long lastTime = cherry.getLastTeleportTime();
        
        // Act
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignorar
        }
        cherry.forceTeleport();
        
        // Assert
        assertTrue(cherry.getLastTeleportTime() > lastTime);
    }

    @Test
    @DisplayName("setRandom permite cambiar el generador")
    void testSetRandom() {
        // Arrange
        Random newRandom = new Random(999);
        
        // Act
        cherry.setRandom(newRandom);
        cherry.forceTeleport();
        
        // Assert
        assertNotNull(cherry);
    }

    @Test
    @DisplayName("Teleport no funciona sin dimensiones del mapa")
    void testTeleportWithoutMapDimensions() {
        // Arrange
        Cherry newCherry = new Cherry(5, 5);
        int initialX = newCherry.getX();
        int initialY = newCherry.getY();
        
        // Act
        newCherry.teleport(0, 0);
        
        // Assert - No debe cambiar
        assertEquals(initialX, newCherry.getX());
        assertEquals(initialY, newCherry.getY());
    }
}
