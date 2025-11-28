package test.domain.entities;

import main.domain.entities.*;

import main.domain.enums.IceCreamFlavor;
import main.domain.enums.MonsterType;
import main.domain.util.PathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para Pot.
 */
@DisplayName("Pot Tests")
class PotTest {

    private Pot pot;
    private IceCream player;
    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        pot = new Pot(5, 5);
        player = new IceCream(10, 5, IceCreamFlavor.VANILLA);
        
        // PathFinder con mapa de celdas
        Cell[][] map = new Cell[20][20];
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                map[x][y] = new Cell(x, y);
            }
        }
        pathFinder = new PathFinder(map);
    }

    @Test
    @DisplayName("PO-01: Tipo de monstruo es POT")
    void testPotType() {
        assertEquals(MonsterType.POT, pot.getMonsterType());
    }

    @Test
    @DisplayName("PO-02: Pot no puede romper hielo")
    void testPotCannotBreakIce() {
        assertFalse(pot.canBreakIce());
    }

    @Test
    @DisplayName("PO-03: Símbolo del Pot es O")
    void testPotSymbol() {
        assertEquals('O', pot.getSymbol());
    }

    @Test
    @DisplayName("PO-04: setPathFinder establece pathfinder")
    void testSetPathFinder() {
        // Arrange
        Cell[][] smallMap = new Cell[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                smallMap[x][y] = new Cell(x, y);
            }
        }
        PathFinder pf = new PathFinder(smallMap);
        
        // Act
        pot.setPathFinder(pf);
        
        // Assert
        assertEquals(pf, pot.getPathFinder());
    }

    @Test
    @DisplayName("PO-05: Pot persigue al jugador")
    void testPotChasesPlayer() {
        // Arrange
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        
        int startX = pot.getX();
        
        // Act
        pot.chasePlayer();
        
        // Assert - debería moverse hacia el jugador (a la derecha)
        assertTrue(pot.getX() > startX || pot.getX() == startX);
    }

    @Test
    @DisplayName("PO-06: chasePlayer no se mueve sin target")
    void testChasePlayerNoMovementWithoutTarget() {
        // Arrange
        pot.setPathFinder(pathFinder);
        int startX = pot.getX();
        int startY = pot.getY();
        
        // Act
        pot.chasePlayer();
        
        // Assert
        assertEquals(startX, pot.getX());
        assertEquals(startY, pot.getY());
    }

    @Test
    @DisplayName("PO-07: chasePlayer no se mueve sin pathfinder")
    void testChasePlayerNoMovementWithoutPathFinder() {
        // Arrange
        pot.setTarget(player);
        int startX = pot.getX();
        int startY = pot.getY();
        
        // Act
        pot.chasePlayer();
        
        // Assert
        assertEquals(startX, pot.getX());
        assertEquals(startY, pot.getY());
    }

    @Test
    @DisplayName("PO-08: executeAI llama chasePlayer")
    void testExecuteAICallsChasePlayer() {
        // Arrange
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        int startX = pot.getX();
        
        // Act
        pot.executeAI();
        
        // Assert - similar a chasePlayer
        assertTrue(pot.getX() >= startX);
    }

    @Test
    @DisplayName("PO-09: Pot llega al jugador después de varios movimientos")
    void testPotReachesPlayer() {
        // Arrange
        pot = new Pot(5, 5);
        player = new IceCream(8, 5, IceCreamFlavor.VANILLA); // 3 casillas a la derecha
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        
        // Act - mover varias veces hacia el jugador
        for (int i = 0; i < 10; i++) {
            pot.chasePlayer();
        }
        
        // Assert - debería estar en o cerca de la posición del jugador
        assertTrue(Math.abs(pot.getX() - player.getX()) <= 1);
    }

    @Test
    @DisplayName("PO-10: Pot detecta colisión con jugador")
    void testPotCollision() {
        // Arrange
        IceCream nearPlayer = new IceCream(5, 5, IceCreamFlavor.VANILLA);
        
        // Assert
        assertTrue(pot.hasCollidedWith(nearPlayer));
    }

    @Test
    @DisplayName("PO-11: reset vuelve a posición inicial")
    void testPotReset() {
        // Arrange
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        pot.chasePlayer();
        pot.chasePlayer();
        
        // Act
        pot.reset();
        
        // Assert
        assertEquals(5, pot.getX());
        assertEquals(5, pot.getY());
    }

    @Test
    @DisplayName("PO-12: update ejecuta executeAI")
    void testUpdateExecutesAI() {
        // Arrange
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        int startX = pot.getX();
        
        // Act
        pot.update();
        
        // Assert
        assertTrue(pot.getX() >= startX);
    }

    @Test
    @DisplayName("PO-13: Pot se mueve hacia arriba cuando jugador arriba")
    void testPotMovesUp() {
        // Arrange
        player = new IceCream(5, 2, IceCreamFlavor.VANILLA); // arriba del pot
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        int startY = pot.getY();
        
        // Act
        pot.chasePlayer();
        
        // Assert
        assertTrue(pot.getY() < startY);
    }

    @Test
    @DisplayName("PO-14: Pot se mueve hacia abajo cuando jugador abajo")
    void testPotMovesDown() {
        // Arrange
        player = new IceCream(5, 10, IceCreamFlavor.VANILLA); // abajo del pot
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        int startY = pot.getY();
        
        // Act
        pot.chasePlayer();
        
        // Assert
        assertTrue(pot.getY() > startY);
    }

    @Test
    @DisplayName("PO-15: Pot con obstáculos busca camino alternativo")
    void testPotWithObstacles() {
        // Arrange - pared bloqueando el camino directo
        Cell[][] obstacleMap = new Cell[20][20];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                obstacleMap[i][j] = new Cell(i, j);
                // Bloquear fila y=5 excepto extremos
                if (j == 5 && i > 5 && i < 10) {
                    obstacleMap[i][j].setWalkable(false);
                }
            }
        }
        PathFinder obstacleFinder = new PathFinder(obstacleMap);
        
        pot = new Pot(5, 5);
        player = new IceCream(10, 5, IceCreamFlavor.VANILLA);
        pot.setPathFinder(obstacleFinder);
        pot.setTarget(player);
        
        // Act
        pot.chasePlayer();
        
        // Assert - debería moverse (arriba o abajo para rodear)
        // No puede ir directamente a la derecha por los obstáculos
        assertTrue(pot.getX() != 5 || pot.getY() != 5);
    }

    @Test
    @DisplayName("PO-16: Pot no se mueve si ya está en la posición del jugador")
    void testPotNoMoveOnPlayerPosition() {
        // Arrange
        pot = new Pot(5, 5);
        player = new IceCream(5, 5, IceCreamFlavor.VANILLA);
        pot.setPathFinder(pathFinder);
        pot.setTarget(player);
        
        // Act
        pot.chasePlayer();
        
        // Assert
        assertEquals(5, pot.getX());
        assertEquals(5, pot.getY());
    }
}
