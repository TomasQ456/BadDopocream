package domain.enums;

import domain.Vector2;

/**
 * Represents cardinal movement directions on the grid and exposes convenient offset helpers.
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * @return a {@link Vector2} describing the delta associated with the direction.
     */
    public Vector2 toOffset() {
        return new Vector2(dx, dy);
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
