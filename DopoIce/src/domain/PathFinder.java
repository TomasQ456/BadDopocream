package domain;

import domain.enums.Direction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Breadth-first path finder operating on the cell matrix.
 */
public class PathFinder {

    /**
     * Finds a walkable path between the start and goal coordinates.
     *
     * @return ordered list of coordinates including start and goal, or an empty list if unreachable.
     */
    public List<Vector2> findPath(Cell[][] grid, Vector2 start, Vector2 goal) {
        int height = grid.length;
        int width = grid[0].length;
        boolean[][] visited = new boolean[height][width];
        Map<Vector2, Vector2> parent = new HashMap<>();
        Queue<Vector2> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start.getY()][start.getX()] = true;

        while (!queue.isEmpty()) {
            Vector2 current = queue.poll();
            if (current.equals(goal)) {
                break;
            }
            for (Direction direction : Direction.values()) {
                Vector2 next = current.plus(direction);
                if (isWalkable(grid, next, width, height) && !visited[next.getY()][next.getX()]) {
                    visited[next.getY()][next.getX()] = true;
                    parent.put(next, current);
                    queue.add(next);
                }
            }
        }
        if (!parent.containsKey(goal) && !start.equals(goal)) {
            return List.of();
        }
        return reconstructPath(parent, start, goal);
    }

    /**
     * Determines whether the provided coordinates can be traversed.
     */
    private boolean isWalkable(Cell[][] grid, Vector2 position, int width, int height) {
        int x = position.getX();
        int y = position.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        Cell cell = grid[y][x];
        if (cell.getStaticObject() != null && cell.getStaticObject().isSolid()) {
            return false;
        }
        return true;
    }

    /**
     * Rebuilds a BFS path using parent references.
     */
    private List<Vector2> reconstructPath(Map<Vector2, Vector2> parent, Vector2 start, Vector2 goal) {
        List<Vector2> path = new ArrayList<>();
        Vector2 current = goal;
        path.add(current);
        while (!current.equals(start)) {
            current = parent.get(current);
            if (current == null) {
                return List.of();
            }
            path.add(0, current);
        }
        return path;
    }
}
