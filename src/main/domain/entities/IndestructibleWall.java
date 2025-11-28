package main.domain.entities;

/**
 * Representa un muro indestructible.
 * Estos muros forman los límites del mapa y obstáculos permanentes.
 * 
 */
public class IndestructibleWall extends Wall {

    /** Símbolo del muro indestructible */
    public static final char SYMBOL = '#';

    /**
     * Constructor de IndestructibleWall.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public IndestructibleWall(int x, int y) {
        super(x, y, SYMBOL, false);
    }

    @Override
    public void update() {
        // Los muros indestructibles no necesitan actualización
    }

    @Override
    public String toString() {
        return "IndestructibleWall{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
