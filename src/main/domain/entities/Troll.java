package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.MonsterType;
import main.domain.util.MovementPattern;

/**
 * Representa un Troll - monstruo que sigue un patrón predefinido.
 * Los Trolls no pueden romper bloques de hielo.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class Troll extends Monster {

    /** Índice actual en el patrón */
    private int patternIndex;
    
    /** Direcciones del patrón */
    private Direction[] patternDirections;

    /**
     * Constructor de Troll.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     */
    public Troll(int x, int y) {
        super(x, y, MonsterType.TROLL);
        this.patternIndex = 0;
        // Patrón por defecto: cuadrado
        this.patternDirections = new Direction[]{
            Direction.RIGHT, Direction.RIGHT,
            Direction.DOWN, Direction.DOWN,
            Direction.LEFT, Direction.LEFT,
            Direction.UP, Direction.UP
        };
        this.pattern = new MovementPattern(patternDirections);
    }

    /**
     * Constructor de Troll con patrón personalizado.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param patternDirections patrón de movimiento
     */
    public Troll(int x, int y, Direction[] patternDirections) {
        super(x, y, MonsterType.TROLL);
        this.patternIndex = 0;
        this.patternDirections = patternDirections != null ? patternDirections.clone() : new Direction[0];
        this.pattern = new MovementPattern(this.patternDirections);
    }

    /**
     * Obtiene el índice actual del patrón.
     * 
     * @return el índice
     */
    public int getPatternIndex() {
        return pattern != null ? pattern.getCurrentIndex() : 0;
    }

    /**
     * Obtiene las direcciones del patrón.
     * 
     * @return array de direcciones
     */
    public Direction[] getPatternDirections() {
        return patternDirections.clone();
    }

    /**
     * Sigue el patrón de movimiento.
     * 
     * @return la siguiente dirección del patrón
     */
    public Direction followPattern() {
        if (pattern == null || pattern.isEmpty()) {
            return null;
        }
        Direction nextDir = pattern.getNextDirection();
        if (nextDir != null) {
            move(nextDir);
        }
        return nextDir;
    }

    @Override
    public void executeAI() {
        followPattern();
    }

    /**
     * Establece el índice del patrón.
     * 
     * @param index el nuevo índice
     */
    public void setPatternIndex(int index) {
        if (pattern != null) {
            pattern.setCurrentIndex(index);
        }
    }

    /**
     * Intenta mover el Troll según su patrón.
     * 
     * @return la dirección del movimiento
     */
    public Direction getNextPatternMove() {
        if (pattern == null || pattern.isEmpty()) {
            return null;
        }
        return pattern.getCurrentDirection();
    }

    @Override
    public void reset() {
        super.reset();
        if (pattern != null) {
            pattern.reset();
        }
    }

    @Override
    public String toString() {
        return "Troll{" +
                "x=" + x +
                ", y=" + y +
                ", patternIndex=" + getPatternIndex() +
                ", patternSize=" + patternDirections.length +
                '}';
    }
}
