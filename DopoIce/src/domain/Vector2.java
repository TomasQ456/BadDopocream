package domain;

import domain.enums.Direction;

import java.util.Objects;

/**
 * Immutable coordinate helper used for addressing cells.
 */
public final class Vector2 {
    private final int x;
    private final int y;

    /**
     * @param x x-coordinate.
     * @param y y-coordinate.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * @return y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Creates a new vector translated by the provided {@link Direction}.
     */
    public Vector2 plus(Direction direction) {
        return new Vector2(x + direction.getDx(), y + direction.getDy());
    }

    /**
     * Creates a new vector translated by another vector.
     */
    public Vector2 plus(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector2 vector2 = (Vector2) o;
        return x == vector2.x && y == vector2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
