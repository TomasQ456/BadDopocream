package test.domain.util;

import main.domain.util.*;

import main.domain.entities.Cell;
import main.domain.entities.IndestructibleWall;
import main.domain.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para PathFinder.
 */
@DisplayName("PathFinder Tests")
class PathFinderTest {

    private PathFinder pathFinder;
    private Cell[][] map;

    @BeforeEach
    void setUp() {
        // Crear un mapa 5x5 vacío
        map = new Cell[5][5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                map[x][y] = new Cell(x, y);
            }
        }
        pathFinder = new PathFinder(map);
    }

    @Test
    @DisplayName("PF-01: Crear PathFinder")
    void testPathFinderCreation() {
        // Assert
        assertNotNull(pathFinder);
    }

    @Test
    @DisplayName("PF-02: Camino simple sin obstáculos")
    void testFindPathSimple() {
        // Act
        List<Direction> path = pathFinder.findPath(0, 0, 2, 0);
        
        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        assertEquals(2, path.size());
    }

    @Test
    @DisplayName("PF-03: Camino con obstáculos")
    void testFindPathWithObstacles() {
        // Arrange - añadir un muro en el camino directo
        map[1][0].setStaticObject(new IndestructibleWall(1, 0));
        pathFinder.setMap(map);
        
        // Act
        List<Direction> path = pathFinder.findPath(0, 0, 2, 0);
        
        // Assert
        assertNotNull(path);
        assertFalse(path.isEmpty());
        // El camino debe rodear el obstáculo
        assertTrue(path.size() > 2);
    }

    @Test
    @DisplayName("PF-04: Sin camino posible")
    void testFindPathNoPath() {
        // Arrange - bloquear completamente el destino
        map[1][0].setStaticObject(new IndestructibleWall(1, 0));
        map[0][1].setStaticObject(new IndestructibleWall(0, 1));
        map[1][1].setStaticObject(new IndestructibleWall(1, 1));
        pathFinder.setMap(map);
        
        // Intentar ir de (0,0) a (2,2) cuando está bloqueado
        map[2][1].setStaticObject(new IndestructibleWall(2, 1));
        map[1][2].setStaticObject(new IndestructibleWall(1, 2));
        map[2][2].setStaticObject(new IndestructibleWall(2, 2));
        pathFinder.setMap(map);
        
        // Act
        List<Direction> path = pathFinder.findPath(0, 0, 3, 3);
        
        // Assert - camino vacío porque está bloqueado
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("PF-05: Posición válida")
    void testIsValidPosition() {
        // Assert
        assertTrue(pathFinder.isValidPosition(0, 0));
        assertTrue(pathFinder.isValidPosition(4, 4));
        assertTrue(pathFinder.isValidPosition(2, 3));
    }

    @Test
    @DisplayName("PF-06: Posición fuera de límites")
    void testIsValidPositionOutOfBounds() {
        // Assert
        assertFalse(pathFinder.isValidPosition(-1, 0));
        assertFalse(pathFinder.isValidPosition(0, -1));
        assertFalse(pathFinder.isValidPosition(5, 0));
        assertFalse(pathFinder.isValidPosition(0, 5));
    }

    @Test
    @DisplayName("PF-07: Posición con muro")
    void testIsValidPositionWall() {
        // Arrange
        map[2][2].setStaticObject(new IndestructibleWall(2, 2));
        
        // Assert
        assertTrue(pathFinder.isValidPosition(2, 2)); // Es posición válida
        assertFalse(pathFinder.isWalkable(2, 2)); // Pero no es transitable
    }

    @Test
    @DisplayName("PF-08: Calcular heurística")
    void testCalculateHeuristic() {
        // Assert - distancia Manhattan
        assertEquals(4, pathFinder.calculateHeuristic(0, 0, 2, 2));
        assertEquals(0, pathFinder.calculateHeuristic(3, 3, 3, 3));
        assertEquals(5, pathFinder.calculateHeuristic(0, 0, 3, 2));
    }

    @Test
    @DisplayName("PF-09: Camino sin diagonales")
    void testFindPathNoDiagonal() {
        // Act
        List<Direction> path = pathFinder.findPath(0, 0, 2, 2);
        
        // Assert - no debe haber movimientos diagonales
        for (Direction dir : path) {
            assertTrue(dir == Direction.UP || dir == Direction.DOWN || 
                       dir == Direction.LEFT || dir == Direction.RIGHT);
        }
    }

    @Test
    @DisplayName("PF-10: Camino óptimo")
    void testFindPathOptimal() {
        // Act
        List<Direction> path = pathFinder.findPath(0, 0, 4, 4);
        
        // Assert - camino óptimo en mapa vacío es 8 movimientos
        assertEquals(8, path.size());
    }

    @Test
    @DisplayName("Mismo origen y destino retorna lista vacía")
    void testSameStartAndEnd() {
        // Act
        List<Direction> path = pathFinder.findPath(2, 2, 2, 2);
        
        // Assert
        assertTrue(path.isEmpty());
    }

    @Test
    @DisplayName("getNextDirection retorna primera dirección")
    void testGetNextDirection() {
        // Act
        Direction next = pathFinder.getNextDirection(0, 0, 2, 0);
        
        // Assert
        assertEquals(Direction.RIGHT, next);
    }

    @Test
    @DisplayName("getNextDirection retorna null sin camino")
    void testGetNextDirectionNoPath() {
        // Arrange - bloquear todo
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (x != 0 || y != 0) {
                    map[x][y].setStaticObject(new IndestructibleWall(x, y));
                }
            }
        }
        pathFinder.setMap(map);
        
        // Act
        Direction next = pathFinder.getNextDirection(0, 0, 4, 4);
        
        // Assert
        assertNull(next);
    }

    @Test
    @DisplayName("PathFinder con mapa null")
    void testPathFinderWithNullMap() {
        // Arrange
        PathFinder nullPathFinder = new PathFinder(null);
        
        // Assert
        assertFalse(nullPathFinder.isValidPosition(0, 0));
    }

    @Test
    @DisplayName("PathFinder con mapa vacío")
    void testPathFinderWithEmptyMap() {
        // Arrange
        PathFinder emptyPathFinder = new PathFinder(new Cell[0][0]);
        
        // Assert
        assertFalse(emptyPathFinder.isValidPosition(0, 0));
    }

    @Test
    @DisplayName("setMap actualiza el mapa")
    void testSetMap() {
        // Arrange
        Cell[][] newMap = new Cell[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                newMap[x][y] = new Cell(x, y);
            }
        }
        
        // Act
        pathFinder.setMap(newMap);
        
        // Assert
        assertTrue(pathFinder.isValidPosition(9, 9));
    }

    @Test
    @DisplayName("isWalkable retorna true para celda vacía")
    void testIsWalkableEmpty() {
        // Assert
        assertTrue(pathFinder.isWalkable(0, 0));
    }

    @Test
    @DisplayName("isWalkable retorna false fuera de límites")
    void testIsWalkableOutOfBounds() {
        // Assert
        assertFalse(pathFinder.isWalkable(-1, 0));
        assertFalse(pathFinder.isWalkable(10, 10));
    }

    @Test
    @DisplayName("Encontrar camino con rodeo")
    void testFindPathWithDetour() {
        // Arrange - crear un muro horizontal
        // ###
        map[1][1].setStaticObject(new IndestructibleWall(1, 1));
        map[2][1].setStaticObject(new IndestructibleWall(2, 1));
        map[3][1].setStaticObject(new IndestructibleWall(3, 1));
        pathFinder.setMap(map);
        
        // Act - ir de (2,0) a (2,2)
        List<Direction> path = pathFinder.findPath(2, 0, 2, 2);
        
        // Assert
        assertFalse(path.isEmpty());
        // El camino debe rodear el muro
    }
}
