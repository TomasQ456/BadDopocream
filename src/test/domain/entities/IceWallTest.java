package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.IceCreamFlavor;
import main.domain.enums.PlayerType;
import main.domain.interfaces.Destructible;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para IceWall.
 */
@DisplayName("IceWall Tests")
class IceWallTest {

    private Player mockCreator;
    private IceWall iceWall;

    // Mock de Player para testing
    private static class MockPlayer extends Player {
        public MockPlayer(int x, int y) {
            super(x, y, IceCreamFlavor.VANILLA, PlayerType.HUMAN);
        }
        
        @Override
        public void update(Object game) {
            // No hace nada para testing
        }
    }

    @BeforeEach
    void setUp() {
        mockCreator = new MockPlayer(5, 5);
        iceWall = new IceWall(6, 5, mockCreator);
    }

    @Test
    @DisplayName("IW-01: Crear muro de hielo")
    void testIceWallCreation() {
        // Assert
        assertNotNull(iceWall);
        assertEquals(6, iceWall.getX());
        assertEquals(5, iceWall.getY());
    }

    @Test
    @DisplayName("IW-02: Obtener creador")
    void testIceWallCreator() {
        // Assert
        assertEquals(mockCreator, iceWall.getCreator());
    }

    @Test
    @DisplayName("IW-03: Verificar tiempo de creación")
    void testIceWallCreationTime() {
        // Arrange
        long before = System.currentTimeMillis();
        IceWall newWall = new IceWall(0, 0, mockCreator);
        long after = System.currentTimeMillis();
        
        // Assert
        assertTrue(newWall.getCreationTime() >= before);
        assertTrue(newWall.getCreationTime() <= after);
    }

    @Test
    @DisplayName("IW-04: Es destructible")
    void testIceWallIsDestructible() {
        // Assert
        assertTrue(iceWall.isDestructible());
    }

    @Test
    @DisplayName("IW-05: Destruir muro de hielo")
    void testIceWallDestroy() {
        // Arrange
        assertFalse(iceWall.isDestroyed());
        
        // Act
        iceWall.destroy();
        
        // Assert
        assertTrue(iceWall.isDestroyed());
    }

    @Test
    @DisplayName("IW-06: Actualizar muro sin tiempo límite")
    void testIceWallUpdateNoLimit() {
        // Arrange
        assertFalse(iceWall.isDestroyed());
        
        // Act
        iceWall.update();
        
        // Assert
        assertFalse(iceWall.isDestroyed()); // No debe destruirse sin límite de tiempo
    }

    @Test
    @DisplayName("IW-07: Símbolo correcto")
    void testIceWallSymbol() {
        // Assert
        assertEquals('I', iceWall.getSymbol());
        assertEquals(IceWall.SYMBOL, iceWall.getSymbol());
    }

    @Test
    @DisplayName("IW-08: Tiempo de vida")
    void testIceWallLifetime() throws InterruptedException {
        // Arrange
        IceWall newWall = new IceWall(0, 0, mockCreator);
        
        // Act
        Thread.sleep(50);
        
        // Assert
        assertTrue(newWall.getLifetime() >= 50);
    }

    @Test
    @DisplayName("IceWall implementa Destructible")
    void testImplementsDestructible() {
        // Assert
        assertTrue(iceWall instanceof Destructible);
    }

    @Test
    @DisplayName("IceWall es sólido")
    void testIceWallIsSolid() {
        // Assert
        assertTrue(iceWall.isSolid());
    }

    @Test
    @DisplayName("IceWall hereda de Wall")
    void testInheritsFromWall() {
        // Assert
        assertTrue(iceWall instanceof Wall);
    }

    @Test
    @DisplayName("Crear IceWall con tiempo límite")
    void testIceWallWithLifetime() {
        // Arrange & Act
        IceWall wallWithLimit = new IceWall(0, 0, mockCreator, 1000);
        
        // Assert
        assertEquals(1000, wallWithLimit.getMaxLifetime());
        assertFalse(wallWithLimit.hasExpired());
    }

    @Test
    @DisplayName("IceWall expira después del tiempo límite")
    void testIceWallExpires() throws InterruptedException {
        // Arrange
        IceWall wallWithLimit = new IceWall(0, 0, mockCreator, 50);
        
        // Act
        Thread.sleep(60);
        
        // Assert
        assertTrue(wallWithLimit.hasExpired());
    }

    @Test
    @DisplayName("Update destruye muro expirado")
    void testUpdateDestroysExpiredWall() throws InterruptedException {
        // Arrange
        IceWall wallWithLimit = new IceWall(0, 0, mockCreator, 50);
        
        // Act
        Thread.sleep(60);
        wallWithLimit.update();
        
        // Assert
        assertTrue(wallWithLimit.isDestroyed());
    }

    @Test
    @DisplayName("toString contiene información del muro")
    void testToString() {
        // Act
        String result = iceWall.toString();
        
        // Assert
        assertTrue(result.contains("IceWall"));
        assertTrue(result.contains("6"));
        assertTrue(result.contains("5"));
    }

    @Test
    @DisplayName("IceWall con creador null")
    void testIceWallWithNullCreator() {
        // Arrange & Act
        IceWall wallWithoutCreator = new IceWall(0, 0, null);
        
        // Assert
        assertNull(wallWithoutCreator.getCreator());
        assertNotNull(wallWithoutCreator.toString());
    }
}
