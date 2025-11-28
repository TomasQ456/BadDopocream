package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.MonsterType;
import main.domain.util.PathFinder;

/**
 * Representa un OrangeSquid - monstruo que puede romper bloques de hielo.
 * Los OrangeSquid usan pathfinding y pueden destruir muros de hielo.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class OrangeSquid extends Monster {

    /** Buscador de caminos */
    private PathFinder pathFinder;
    
    /** Último tiempo que rompió un bloque */
    private long lastBreakTime;
    
    /** Cooldown para romper bloques en milisegundos */
    private long breakCooldown;
    
    /** Cooldown por defecto (1 segundo) */
    public static final long DEFAULT_BREAK_COOLDOWN = 1000;

    /**
     * Constructor de OrangeSquid.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     */
    public OrangeSquid(int x, int y) {
        super(x, y, MonsterType.ORANGE_SQUID);
        this.pathFinder = null;
        this.lastBreakTime = 0;
        this.breakCooldown = DEFAULT_BREAK_COOLDOWN;
    }

    /**
     * Constructor de OrangeSquid con PathFinder.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param pathFinder el buscador de caminos
     */
    public OrangeSquid(int x, int y, PathFinder pathFinder) {
        this(x, y);
        this.pathFinder = pathFinder;
    }

    /**
     * Obtiene el PathFinder.
     * 
     * @return el buscador de caminos
     */
    public PathFinder getPathFinder() {
        return pathFinder;
    }

    /**
     * Establece el PathFinder.
     * 
     * @param pathFinder el nuevo buscador de caminos
     */
    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    /**
     * Obtiene el último tiempo de ruptura.
     * 
     * @return el timestamp
     */
    public long getLastBreakTime() {
        return lastBreakTime;
    }

    /**
     * Obtiene el cooldown de ruptura.
     * 
     * @return el cooldown en milisegundos
     */
    public long getBreakCooldown() {
        return breakCooldown;
    }

    /**
     * Establece el cooldown de ruptura.
     * 
     * @param cooldown el nuevo cooldown en milisegundos
     */
    public void setBreakCooldown(long cooldown) {
        this.breakCooldown = cooldown;
    }

    /**
     * Verifica si puede romper un bloque (cooldown).
     * 
     * @return true si puede romper
     */
    public boolean canBreakNow() {
        return System.currentTimeMillis() - lastBreakTime >= breakCooldown;
    }

    /**
     * Persigue al jugador y rompe hielo si es necesario.
     * 
     * @return la dirección del movimiento
     */
    public Direction chaseAndBreak() {
        if (target == null || pathFinder == null) {
            return null;
        }
        
        Direction nextDir = pathFinder.getNextDirection(x, y, target.getX(), target.getY());
        if (nextDir != null) {
            move(nextDir);
        }
        return nextDir;
    }

    /**
     * Rompe un bloque de hielo en una dirección.
     * 
     * @param direction la dirección donde romper
     * @return la dirección en la que se rompió
     */
    public Direction breakIceBlock(Direction direction) {
        lastBreakTime = System.currentTimeMillis();
        return direction;
    }

    /**
     * Obtiene la dirección hacia el objetivo.
     * 
     * @return la dirección hacia el jugador
     */
    public Direction getTargetDirection() {
        if (target == null) {
            return currentDirection;
        }
        
        int dx = target.getX() - x;
        int dy = target.getY() - y;
        
        // Priorizar la distancia más grande
        if (Math.abs(dx) >= Math.abs(dy)) {
            return dx > 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return dy > 0 ? Direction.DOWN : Direction.UP;
        }
    }

    /**
     * Intenta romper hielo frente a él.
     * 
     * @return la posición del bloque a romper, o null
     */
    public int[] tryBreakIceAhead() {
        if (!canBreakNow() || currentDirection == null) {
            return null;
        }
        
        lastBreakTime = System.currentTimeMillis();
        int targetX = x + currentDirection.getDeltaX();
        int targetY = y + currentDirection.getDeltaY();
        return new int[]{targetX, targetY};
    }

    @Override
    public void executeAI() {
        chaseAndBreak();
    }

    @Override
    public void reset() {
        super.reset();
        this.lastBreakTime = 0;
    }

    @Override
    public String toString() {
        return "OrangeSquid{" +
                "x=" + x +
                ", y=" + y +
                ", canBreakIce=" + canBreakIce +
                ", canBreakNow=" + canBreakNow() +
                ", hasPathFinder=" + (pathFinder != null) +
                '}';
    }
}
