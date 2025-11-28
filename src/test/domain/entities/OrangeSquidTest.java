package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.Direction;
import main.domain.enums.IceCreamFlavor;
import main.domain.enums.MonsterType;
import main.domain.util.PathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para OrangeSquid.
 */
@DisplayName("OrangeSquid Tests")
class OrangeSquidTest {

    private OrangeSquid squid;
    private IceCream player;
    private PathFinder pathFinder;
    private Cell[][] map;

    @BeforeEach
    void setUp() {
        squid = new OrangeSquid(5, 5);
        player = new IceCream(10, 5, IceCreamFlavor.VANILLA);
        map = new Cell[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                map[i][j] = new Cell(i, j);
            }
        }
        pathFinder = new PathFinder(map);
    }

    @Test
    @DisplayName("OS-01: Tipo de monstruo es ORANGE_SQUID")
    void testSquidType() {
        assertEquals(MonsterType.ORANGE_SQUID, squid.getMonsterType());
    }

    @Test
    @DisplayName("OS-02: OrangeSquid puede romper hielo")
    void testSquidCanBreakIce() {
        assertTrue(squid.canBreakIce());
    }

    @Test
    @DisplayName("OS-03: Símbolo del OrangeSquid es Q")
    void testSquidSymbol() {
        assertEquals('Q', squid.getSymbol());
    }

    @Test
    @DisplayName("OS-04: setPathFinder establece pathfinder")
    void testSetPathFinder() {
        // Arrange
        Cell[][] smallMap = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                smallMap[i][j] = new Cell(i, j);
            }
        }
        PathFinder pf = new PathFinder(smallMap);
        
        // Act
        squid.setPathFinder(pf);
        
        // Assert
        assertEquals(pf, squid.getPathFinder());
    }

    @Test
    @DisplayName("OS-05: getLastBreakTime retorna tiempo de última ruptura")
    void testGetLastBreakTime() {
        // Assert - inicialmente 0 o valor negativo
        assertTrue(squid.getLastBreakTime() <= System.currentTimeMillis());
    }

    @Test
    @DisplayName("OS-06: getBreakCooldown retorna cooldown")
    void testGetBreakCooldown() {
        // Assert - cooldown positivo
        assertTrue(squid.getBreakCooldown() > 0);
    }

    @Test
    @DisplayName("OS-07: breakIceBlock registra tiempo")
    void testBreakIceBlockUpdatesTime() {
        // Arrange
        long beforeBreak = System.currentTimeMillis();
        
        // Act
        squid.breakIceBlock(Direction.RIGHT);
        
        // Assert
        assertTrue(squid.getLastBreakTime() >= beforeBreak);
    }

    @Test
    @DisplayName("OS-08: canBreakNow verifica cooldown")
    void testCanBreakNowChecksCooldown() {
        // Arrange - recién roto, no puede romper otra vez
        squid.breakIceBlock(Direction.RIGHT);
        
        // Assert
        assertFalse(squid.canBreakNow());
    }

    @Test
    @DisplayName("OS-09: OrangeSquid persigue al jugador")
    void testSquidChasesPlayer() {
        // Arrange
        squid.setPathFinder(pathFinder);
        squid.setTarget(player);
        int startX = squid.getX();
        
        // Act
        squid.executeAI();
        
        // Assert - debería moverse hacia el jugador
        assertTrue(squid.getX() >= startX);
    }

    @Test
    @DisplayName("OS-10: breakIceBlock retorna dirección")
    void testBreakIceBlockReturnsDirection() {
        // Act
        Direction result = squid.breakIceBlock(Direction.UP);
        
        // Assert
        assertEquals(Direction.UP, result);
    }

    @Test
    @DisplayName("OS-11: reset vuelve a posición inicial")
    void testSquidReset() {
        // Arrange
        squid.setPathFinder(pathFinder);
        squid.setTarget(player);
        squid.executeAI();
        squid.executeAI();
        
        // Act
        squid.reset();
        
        // Assert
        assertEquals(5, squid.getX());
        assertEquals(5, squid.getY());
    }

    @Test
    @DisplayName("OS-12: OrangeSquid detecta colisión")
    void testSquidCollision() {
        // Arrange
        IceCream nearPlayer = new IceCream(5, 5, IceCreamFlavor.VANILLA);
        
        // Assert
        assertTrue(squid.hasCollidedWith(nearPlayer));
    }

    @Test
    @DisplayName("OS-13: update ejecuta executeAI")
    void testUpdateExecutesAI() {
        // Arrange
        squid.setPathFinder(pathFinder);
        squid.setTarget(player);
        int startX = squid.getX();
        
        // Act
        squid.update();
        
        // Assert
        assertTrue(squid.getX() >= startX);
    }

    @Test
    @DisplayName("OS-14: getTargetDirection calcula dirección hacia jugador")
    void testGetTargetDirection() {
        // Arrange
        squid.setTarget(player); // player está a la derecha
        
        // Act
        Direction dir = squid.getTargetDirection();
        
        // Assert
        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    @DisplayName("OS-15: getTargetDirection hacia arriba")
    void testGetTargetDirectionUp() {
        // Arrange
        player = new IceCream(5, 2, IceCreamFlavor.VANILLA);
        squid.setTarget(player);
        
        // Act
        Direction dir = squid.getTargetDirection();
        
        // Assert
        assertEquals(Direction.UP, dir);
    }

    @Test
    @DisplayName("OS-16: getTargetDirection hacia abajo")
    void testGetTargetDirectionDown() {
        // Arrange
        player = new IceCream(5, 10, IceCreamFlavor.VANILLA);
        squid.setTarget(player);
        
        // Act
        Direction dir = squid.getTargetDirection();
        
        // Assert
        assertEquals(Direction.DOWN, dir);
    }

    @Test
    @DisplayName("OS-17: getTargetDirection hacia izquierda")
    void testGetTargetDirectionLeft() {
        // Arrange
        player = new IceCream(2, 5, IceCreamFlavor.VANILLA);
        squid.setTarget(player);
        
        // Act
        Direction dir = squid.getTargetDirection();
        
        // Assert
        assertEquals(Direction.LEFT, dir);
    }

    @Test
    @DisplayName("OS-18: getTargetDirection sin target")
    void testGetTargetDirectionNoTarget() {
        // Act
        Direction dir = squid.getTargetDirection();
        
        // Assert - dirección por defecto
        assertNotNull(dir);
    }

    @Test
    @DisplayName("OS-19: breakIceBlock en todas las direcciones")
    void testBreakIceBlockAllDirections() {
        // Test cada dirección
        for (Direction dir : Direction.values()) {
            OrangeSquid freshSquid = new OrangeSquid(5, 5);
            Direction result = freshSquid.breakIceBlock(dir);
            assertEquals(dir, result);
        }
    }

    @Test
    @DisplayName("OS-20: setBreakCooldown modifica cooldown")
    void testSetBreakCooldown() {
        // Arrange
        long newCooldown = 5000;
        
        // Act
        squid.setBreakCooldown(newCooldown);
        
        // Assert
        assertEquals(newCooldown, squid.getBreakCooldown());
    }

    @Test
    @DisplayName("OS-21: OrangeSquid sin pathfinder no se mueve en executeAI")
    void testExecuteAINoPathFinder() {
        // Arrange
        squid.setTarget(player);
        int startX = squid.getX();
        int startY = squid.getY();
        
        // Act
        squid.executeAI();
        
        // Assert
        assertEquals(startX, squid.getX());
        assertEquals(startY, squid.getY());
    }

    @Test
    @DisplayName("OS-22: reset resetea lastBreakTime")
    void testResetResetsLastBreakTime() {
        // Arrange
        squid.breakIceBlock(Direction.RIGHT);
        assertTrue(squid.getLastBreakTime() > 0);
        
        // Act
        squid.reset();
        
        // Assert - después de reset puede romper otra vez
        assertTrue(squid.canBreakNow());
    }
}
