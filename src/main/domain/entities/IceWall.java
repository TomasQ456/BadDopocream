package main.domain.entities;

import main.domain.interfaces.Destructible;

/**
 * Representa un muro de hielo creado por un jugador.
 * Los muros de hielo son destructibles y tienen un creador asociado.
 * 
 */
public class IceWall extends Wall implements Destructible {

    /** Símbolo del muro de hielo */
    public static final char SYMBOL = 'I';
    
    /** El jugador que creó este muro */
    private final Player creator;
    
    /** Tiempo de creación en milisegundos */
    private final long creationTime;
    
    /** Indica si el muro ha sido destruido */
    private boolean destroyed;
    
    /** Tiempo de vida máximo en milisegundos (0 = infinito) */
    private long maxLifetime;

    /**
     * Constructor de IceWall sin creador (para pruebas).
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public IceWall(int x, int y) {
        this(x, y, null);
    }

    /**
     * Constructor de IceWall.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param creator el jugador que creó el muro
     */
    public IceWall(int x, int y, Player creator) {
        super(x, y, SYMBOL, true);
        this.creator = creator;
        this.creationTime = System.currentTimeMillis();
        this.destroyed = false;
        this.maxLifetime = 0; // Sin límite de tiempo por defecto
    }

    /**
     * Constructor de IceWall con tiempo de vida.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param creator el jugador que creó el muro
     * @param maxLifetime tiempo de vida máximo en milisegundos
     */
    public IceWall(int x, int y, Player creator, long maxLifetime) {
        this(x, y, creator);
        this.maxLifetime = maxLifetime;
    }

    /**
     * Obtiene el creador del muro.
     * 
     * @return el jugador creador
     */
    public Player getCreator() {
        return creator;
    }

    /**
     * Obtiene el tiempo de creación.
     * 
     * @return el timestamp de creación
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Obtiene el tiempo de vida del muro.
     * 
     * @return tiempo en milisegundos desde la creación
     */
    public long getLifetime() {
        return System.currentTimeMillis() - creationTime;
    }

    /**
     * Obtiene el tiempo de vida máximo.
     * 
     * @return el tiempo máximo en milisegundos (0 = infinito)
     */
    public long getMaxLifetime() {
        return maxLifetime;
    }

    /**
     * Verifica si el muro ha expirado.
     * 
     * @return true si ha superado su tiempo de vida máximo
     */
    public boolean hasExpired() {
        if (maxLifetime <= 0) {
            return false;
        }
        return getLifetime() >= maxLifetime;
    }

    @Override
    public void update() {
        if (hasExpired() && !destroyed) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        this.destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public String toString() {
        return "IceWall{" +
                "x=" + x +
                ", y=" + y +
                ", creator=" + (creator != null ? creator.getFlavor() : "null") +
                ", destroyed=" + destroyed +
                '}';
    }
}
