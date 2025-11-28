package main.domain.util;

import main.domain.entities.Cell;
import main.domain.enums.Direction;

import java.util.*;

/**
 * Implementa el algoritmo de búsqueda de caminos (A*).
 * Utilizado por los monstruos para encontrar rutas hacia el jugador.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class PathFinder {

    /** Mapa del juego */
    private Cell[][] map;
    
    /** Dimensiones del mapa */
    private int width;
    private int height;

    /**
     * Constructor de PathFinder.
     * 
     * @param map el mapa del juego
     */
    public PathFinder(Cell[][] map) {
        if (map == null || map.length == 0) {
            this.map = new Cell[0][0];
            this.width = 0;
            this.height = 0;
        } else {
            this.map = map;
            this.width = map.length;
            this.height = map[0].length;
        }
    }

    /**
     * Actualiza la referencia al mapa.
     * 
     * @param map el nuevo mapa
     */
    public void setMap(Cell[][] map) {
        if (map == null || map.length == 0) {
            this.map = new Cell[0][0];
            this.width = 0;
            this.height = 0;
        } else {
            this.map = map;
            this.width = map.length;
            this.height = map[0].length;
        }
    }

    /**
     * Encuentra el camino más corto entre dos puntos.
     * 
     * @param startX coordenada X de inicio
     * @param startY coordenada Y de inicio
     * @param endX coordenada X de destino
     * @param endY coordenada Y de destino
     * @return lista de direcciones para llegar al destino, o lista vacía si no hay camino
     */
    public List<Direction> findPath(int startX, int startY, int endX, int endY) {
        if (!isValidPosition(startX, startY) || !isValidPosition(endX, endY)) {
            return Collections.emptyList();
        }

        // Si el inicio es igual al destino
        if (startX == endX && startY == endY) {
            return Collections.emptyList();
        }

        // A* implementation
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        Map<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startX, startY, null, null, 0, calculateHeuristic(startX, startY, endX, endY));
        openSet.add(startNode);
        allNodes.put(startNode.getKey(), startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.x == endX && current.y == endY) {
                return reconstructPath(current);
            }

            closedSet.add(current.getKey());

            for (Direction dir : Direction.values()) {
                int newX = current.x + dir.getDeltaX();
                int newY = current.y + dir.getDeltaY();

                if (!isValidPosition(newX, newY) || !isWalkable(newX, newY)) {
                    continue;
                }

                String key = newX + "," + newY;
                if (closedSet.contains(key)) {
                    continue;
                }

                int newG = current.g + 1;
                int newH = calculateHeuristic(newX, newY, endX, endY);

                Node neighbor = allNodes.get(key);
                if (neighbor == null) {
                    neighbor = new Node(newX, newY, current, dir, newG, newH);
                    allNodes.put(key, neighbor);
                    openSet.add(neighbor);
                } else if (newG < neighbor.g) {
                    openSet.remove(neighbor);
                    neighbor.g = newG;
                    neighbor.parent = current;
                    neighbor.direction = dir;
                    openSet.add(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    /**
     * Verifica si una posición es válida (dentro del mapa).
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return true si es válida
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Verifica si una posición es transitable.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @return true si se puede caminar
     */
    public boolean isWalkable(int x, int y) {
        if (!isValidPosition(x, y)) {
            return false;
        }
        Cell cell = map[x][y];
        if (cell == null) {
            return true;
        }
        return cell.isWalkable();
    }

    /**
     * Calcula la heurística (distancia Manhattan).
     * 
     * @param x1 coordenada X de origen
     * @param y1 coordenada Y de origen
     * @param x2 coordenada X de destino
     * @param y2 coordenada Y de destino
     * @return la distancia estimada
     */
    public int calculateHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Reconstruye el camino desde el nodo final.
     * 
     * @param endNode el nodo final
     * @return lista de direcciones
     */
    private List<Direction> reconstructPath(Node endNode) {
        List<Direction> path = new ArrayList<>();
        Node current = endNode;
        
        while (current.parent != null) {
            path.add(0, current.direction);
            current = current.parent;
        }
        
        return path;
    }

    /**
     * Obtiene la primera dirección del camino hacia un destino.
     * 
     * @param startX coordenada X de inicio
     * @param startY coordenada Y de inicio
     * @param endX coordenada X de destino
     * @param endY coordenada Y de destino
     * @return la primera dirección, o null si no hay camino
     */
    public Direction getNextDirection(int startX, int startY, int endX, int endY) {
        List<Direction> path = findPath(startX, startY, endX, endY);
        if (path.isEmpty()) {
            return null;
        }
        return path.get(0);
    }

    /**
     * Clase interna para representar nodos en A*.
     */
    private static class Node implements Comparable<Node> {
        int x, y;
        Node parent;
        Direction direction;
        int g; // Costo desde el inicio
        int h; // Heurística al destino
        
        Node(int x, int y, Node parent, Direction direction, int g, int h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.direction = direction;
            this.g = g;
            this.h = h;
        }
        
        int getF() {
            return g + h;
        }
        
        String getKey() {
            return x + "," + y;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.getF(), other.getF());
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
