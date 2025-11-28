package main.domain.entities;

import main.domain.enums.FruitType;
import java.util.Random;

/**
 * Representa una cereza en el juego.
 * Las cerezas son frutas que se teletransportan periódicamente.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class Cherry extends Fruit {

    /** Generador de números aleatorios */
    private Random random;
    
    /** Último tiempo de teletransporte */
    private long lastTeleportTime;
    
    /** Intervalo de teletransporte en milisegundos */
    private int teleportInterval;
    
    /** Dimensiones del mapa */
    private int mapWidth;
    private int mapHeight;
    
    /** Intervalo por defecto (3 segundos) */
    public static final int DEFAULT_TELEPORT_INTERVAL = 3000;

    /**
     * Constructor de Cherry.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Cherry(int x, int y) {
        super(x, y, FruitType.CHERRY);
        this.random = new Random();
        this.lastTeleportTime = System.currentTimeMillis();
        this.teleportInterval = DEFAULT_TELEPORT_INTERVAL;
        this.mapWidth = 0;
        this.mapHeight = 0;
    }

    /**
     * Constructor de Cherry con random controlado (para testing).
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param random generador de números aleatorios
     */
    public Cherry(int x, int y, Random random) {
        this(x, y);
        this.random = random;
    }

    /**
     * Establece las dimensiones del mapa.
     * 
     * @param width ancho del mapa
     * @param height alto del mapa
     */
    public void setMapDimensions(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
    }

    /**
     * Obtiene el intervalo de teletransporte.
     * 
     * @return el intervalo en milisegundos
     */
    public int getTeleportInterval() {
        return teleportInterval;
    }

    /**
     * Establece el intervalo de teletransporte.
     * 
     * @param interval el nuevo intervalo en milisegundos
     */
    public void setTeleportInterval(int interval) {
        this.teleportInterval = interval;
    }

    /**
     * Obtiene el último tiempo de teletransporte.
     * 
     * @return el timestamp
     */
    public long getLastTeleportTime() {
        return lastTeleportTime;
    }

    /**
     * Teletransporta la cereza a una posición aleatoria.
     * 
     * @param maxX límite X del mapa
     * @param maxY límite Y del mapa
     */
    public void teleport(int maxX, int maxY) {
        if (maxX > 0 && maxY > 0) {
            this.x = random.nextInt(maxX);
            this.y = random.nextInt(maxY);
            this.lastTeleportTime = System.currentTimeMillis();
        }
    }

    /**
     * Verifica si debe teletransportarse.
     * 
     * @return true si ha pasado el intervalo
     */
    public boolean shouldTeleport() {
        return System.currentTimeMillis() - lastTeleportTime >= teleportInterval;
    }

    @Override
    public void update() {
        if (collected) {
            return;
        }
        
        if (shouldTeleport() && mapWidth > 0 && mapHeight > 0) {
            teleport(mapWidth, mapHeight);
        }
    }

    /**
     * Fuerza el teletransporte (útil para testing).
     */
    public void forceTeleport() {
        if (mapWidth > 0 && mapHeight > 0) {
            teleport(mapWidth, mapHeight);
        }
    }

    /**
     * Establece el generador de random (útil para testing).
     * 
     * @param random el nuevo generador
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public String toString() {
        return "Cherry{" +
                "x=" + x +
                ", y=" + y +
                ", teleportInterval=" + teleportInterval +
                ", collected=" + collected +
                '}';
    }
}
