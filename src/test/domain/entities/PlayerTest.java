package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.Direction;
import main.domain.enums.IceCreamFlavor;
import main.domain.enums.PlayerType;
import main.domain.interfaces.Movable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Player (usando implementación concreta para testing).
 */
@DisplayName("Player Tests")
class PlayerTest {

    private Player player;

    // Clase concreta para testing
    private static class TestPlayer extends Player {
        public TestPlayer(int x, int y, IceCreamFlavor flavor, PlayerType type) {
            super(x, y, flavor, type);
        }
        
        @Override
        public void update(Object game) {
            // No hace nada para testing
        }
    }

    @BeforeEach
    void setUp() {
        player = new TestPlayer(5, 5, IceCreamFlavor.VANILLA, PlayerType.HUMAN);
    }

    @Test
    @DisplayName("PL-01: Posición inicial")
    void testPlayerPosition() {
        // Assert
        assertEquals(5, player.getX());
        assertEquals(5, player.getY());
    }

    @Test
    @DisplayName("PL-02: Dirección inicial")
    void testPlayerDirection() {
        // Assert
        assertEquals(Direction.RIGHT, player.getDirection());
    }

    @Test
    @DisplayName("PL-03: Sabor de helado")
    void testPlayerFlavor() {
        // Assert
        assertEquals(IceCreamFlavor.VANILLA, player.getFlavor());
    }

    @Test
    @DisplayName("PL-04: Estado vivo inicial")
    void testPlayerIsAlive() {
        // Assert
        assertTrue(player.isAlive());
    }

    @Test
    @DisplayName("PL-05: Vidas iniciales")
    void testPlayerLives() {
        // Assert
        assertEquals(3, player.getLives());
    }

    @Test
    @DisplayName("PL-06: Tipo de jugador")
    void testPlayerType() {
        // Assert
        assertEquals(PlayerType.HUMAN, player.getPlayerType());
    }

    @Test
    @DisplayName("PL-07: Movimiento básico")
    void testPlayerMove() {
        // Arrange
        int initialX = player.getX();
        
        // Act
        player.move(Direction.RIGHT);
        
        // Assert
        assertEquals(initialX + 1, player.getX());
        assertEquals(Direction.RIGHT, player.getDirection());
    }

    @Test
    @DisplayName("PL-08: Mover arriba")
    void testPlayerMoveUp() {
        // Arrange
        int initialY = player.getY();
        
        // Act
        player.move(Direction.UP);
        
        // Assert
        assertEquals(initialY - 1, player.getY());
        assertEquals(Direction.UP, player.getDirection());
    }

    @Test
    @DisplayName("PL-09: Mover abajo")
    void testPlayerMoveDown() {
        // Arrange
        int initialY = player.getY();
        
        // Act
        player.move(Direction.DOWN);
        
        // Assert
        assertEquals(initialY + 1, player.getY());
        assertEquals(Direction.DOWN, player.getDirection());
    }

    @Test
    @DisplayName("PL-10: Mover izquierda")
    void testPlayerMoveLeft() {
        // Arrange
        int initialX = player.getX();
        
        // Act
        player.move(Direction.LEFT);
        
        // Assert
        assertEquals(initialX - 1, player.getX());
        assertEquals(Direction.LEFT, player.getDirection());
    }

    @Test
    @DisplayName("PL-11: Mover derecha")
    void testPlayerMoveRight() {
        // Arrange
        int initialX = player.getX();
        
        // Act
        player.move(Direction.RIGHT);
        
        // Assert
        assertEquals(initialX + 1, player.getX());
        assertEquals(Direction.RIGHT, player.getDirection());
    }

    @Test
    @DisplayName("PL-12: Crear bloque de hielo")
    void testPlayerCreateIceBlock() {
        // Arrange
        player.setDirection(Direction.RIGHT);
        
        // Act
        int[] position = player.createIceBlock();
        
        // Assert
        assertEquals(player.getX() + 1, position[0]);
        assertEquals(player.getY(), position[1]);
    }

    @Test
    @DisplayName("PL-13: Destruir bloque de hielo")
    void testPlayerDestroyIceBlock() {
        // Arrange
        player.setDirection(Direction.UP);
        
        // Act
        int[] position = player.destroyIceBlock();
        
        // Assert
        assertEquals(player.getX(), position[0]);
        assertEquals(player.getY() - 1, position[1]);
    }

    @Test
    @DisplayName("PL-14: Matar jugador")
    void testPlayerKill() {
        // Arrange
        int initialLives = player.getLives();
        
        // Act
        player.kill();
        
        // Assert
        assertFalse(player.isAlive());
        assertEquals(initialLives - 1, player.getLives());
    }

    @Test
    @DisplayName("PL-15: Reaparecer jugador")
    void testPlayerRespawn() {
        // Arrange
        player.move(Direction.RIGHT);
        player.move(Direction.DOWN);
        player.kill();
        
        // Act
        player.respawn();
        
        // Assert
        assertTrue(player.isAlive());
        assertEquals(5, player.getX()); // Posición inicial
        assertEquals(5, player.getY());
    }

    @Test
    @DisplayName("PL-16: Cambiar posición")
    void testPlayerSetPosition() {
        // Act
        player.setPosition(10, 15);
        
        // Assert
        assertEquals(10, player.getX());
        assertEquals(15, player.getY());
    }

    @Test
    @DisplayName("Player implementa Movable")
    void testPlayerImplementsMovable() {
        // Assert
        assertTrue(player instanceof Movable);
    }

    @Test
    @DisplayName("canMoveTo retorna true por defecto")
    void testCanMoveTo() {
        // Assert
        assertTrue(player.canMoveTo(100, 100));
    }

    @Test
    @DisplayName("canRespawn retorna true si tiene vidas")
    void testCanRespawnTrue() {
        // Assert
        assertTrue(player.canRespawn());
    }

    @Test
    @DisplayName("canRespawn retorna false sin vidas")
    void testCanRespawnFalse() {
        // Arrange
        player.kill();
        player.kill();
        player.kill();
        
        // Assert
        assertFalse(player.canRespawn());
    }

    @Test
    @DisplayName("respawn no funciona sin vidas")
    void testRespawnWithoutLives() {
        // Arrange
        player.kill();
        player.kill();
        player.kill();
        player.move(Direction.RIGHT);
        int currentX = player.getX();
        
        // Act
        player.respawn();
        
        // Assert
        assertFalse(player.isAlive());
        assertEquals(currentX, player.getX());
    }

    @Test
    @DisplayName("setDirection cambia la dirección")
    void testSetDirection() {
        // Act
        player.setDirection(Direction.LEFT);
        
        // Assert
        assertEquals(Direction.LEFT, player.getDirection());
    }

    @Test
    @DisplayName("Jugador AI tiene tipo correcto")
    void testAIPlayerType() {
        // Arrange
        Player aiPlayer = new TestPlayer(0, 0, IceCreamFlavor.CHOCOLATE, PlayerType.AI);
        
        // Assert
        assertEquals(PlayerType.AI, aiPlayer.getPlayerType());
    }

    @Test
    @DisplayName("toString contiene información del jugador")
    void testToString() {
        // Act
        String result = player.toString();
        
        // Assert
        assertTrue(result.contains("5"));
        assertTrue(result.contains("VANILLA"));
    }
}
