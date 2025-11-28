package main.domain.enums;

/**
 * Representa las direcciones de movimiento en el juego.
 * Cada dirección tiene asociados los deltas de movimiento en X e Y.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    /**
     * Constructor de Direction.
     * 
     * @param deltaX cambio en el eje X
     * @param deltaY cambio en el eje Y
     */
    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Obtiene el cambio en el eje X para esta dirección.
     * 
     * @return delta X
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Obtiene el cambio en el eje Y para esta dirección.
     * 
     * @return delta Y
     */
    public int getDeltaY() {
        return deltaY;
    }

    /**
     * Obtiene la dirección opuesta.
     * 
     * @return la dirección opuesta
     */
    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return this;
        }
    }
}
