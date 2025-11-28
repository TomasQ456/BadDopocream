package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.MonsterType;
import main.domain.interfaces.AIControlled;
import main.domain.interfaces.Movable;
import main.domain.util.MovementPattern;

/**
 * Clase abstracta base para monstruos del juego.
 * Los monstruos son entidades controladas por IA que persiguen al jugador.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public abstract class Monster extends GameObject implements Movable, AIControlled {

    /** Tipo de monstruo */
    protected MonsterType monsterType;
    
    /** Indica si puede romper bloques de hielo */
    protected boolean canBreakIce;
    
    /** Patrón de movimiento (opcional) */
    protected MovementPattern pattern;
    
    /** Objetivo actual (jugador) */
    protected Player target;
    
    /** Dirección actual */
    protected Direction currentDirection;
    
    /** Posición inicial para respawn */
    protected int initialX;
    protected int initialY;

    /**
     * Constructor de Monster.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param monsterType tipo de monstruo
     */
    protected Monster(int x, int y, MonsterType monsterType) {
        super(x, y, monsterType.getSymbol(), false);
        this.monsterType = monsterType;
        this.canBreakIce = monsterType.canBreakIce();
        this.currentDirection = Direction.RIGHT;
        this.initialX = x;
        this.initialY = y;
    }

    /**
     * Obtiene el tipo de monstruo.
     * 
     * @return el tipo
     */
    public MonsterType getMonsterType() {
        return monsterType;
    }

    /**
     * Indica si puede romper bloques de hielo.
     * 
     * @return true si puede romper hielo
     */
    public boolean canBreakIce() {
        return canBreakIce;
    }

    /**
     * Obtiene el patrón de movimiento.
     * 
     * @return el patrón o null
     */
    public MovementPattern getPattern() {
        return pattern;
    }

    /**
     * Establece el patrón de movimiento.
     * 
     * @param pattern el nuevo patrón
     */
    public void setPattern(MovementPattern pattern) {
        this.pattern = pattern;
    }

    /**
     * Obtiene el objetivo actual.
     * 
     * @return el jugador objetivo
     */
    @Override
    public Player getTarget() {
        return target;
    }

    /**
     * Establece el objetivo.
     * 
     * @param target el jugador objetivo
     */
    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    /**
     * Obtiene la dirección actual.
     * 
     * @return la dirección
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Establece la dirección actual.
     * 
     * @param direction la nueva dirección
     */
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    @Override
    public void move(Direction direction) {
        this.currentDirection = direction;
        this.x += direction.getDeltaX();
        this.y += direction.getDeltaY();
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        // La validación real se hace externamente
        return true;
    }

    /**
     * Reinicia el monstruo a su posición inicial.
     */
    public void reset() {
        this.x = initialX;
        this.y = initialY;
        this.currentDirection = Direction.RIGHT;
        if (pattern != null) {
            pattern.reset();
        }
    }

    @Override
    public void update() {
        executeAI();
    }

    /**
     * Verifica si el monstruo ha colisionado con un jugador.
     * 
     * @param player el jugador a verificar
     * @return true si hay colisión
     */
    public boolean hasCollidedWith(Player player) {
        return player != null && this.x == player.getX() && this.y == player.getY();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + monsterType +
                ", canBreakIce=" + canBreakIce +
                '}';
    }
}
