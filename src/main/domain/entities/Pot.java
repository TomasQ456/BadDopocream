package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.MonsterType;
import main.domain.util.PathFinder;

/**
 * Representa un Pot - monstruo que usa pathfinding para perseguir.
 * Los Pots no pueden romper bloques de hielo.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class Pot extends Monster {

    /** Buscador de caminos */
    private PathFinder pathFinder;
    
    /** Última dirección calculada */
    private Direction lastCalculatedDirection;

    /**
     * Constructor de Pot.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     */
    public Pot(int x, int y) {
        super(x, y, MonsterType.POT);
        this.pathFinder = null;
        this.lastCalculatedDirection = null;
    }

    /**
     * Constructor de Pot con PathFinder.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param pathFinder el buscador de caminos
     */
    public Pot(int x, int y, PathFinder pathFinder) {
        super(x, y, MonsterType.POT);
        this.pathFinder = pathFinder;
        this.lastCalculatedDirection = null;
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
     * Persigue al jugador objetivo.
     * 
     * @return la dirección hacia el jugador, o null si no hay camino
     */
    public Direction chasePlayer() {
        if (target == null || pathFinder == null) {
            return null;
        }
        
        // Si ya está en la posición del jugador, no moverse
        if (x == target.getX() && y == target.getY()) {
            return null;
        }
        
        Direction nextDir = pathFinder.getNextDirection(x, y, target.getX(), target.getY());
        if (nextDir != null) {
            lastCalculatedDirection = nextDir;
            move(nextDir);
        }
        return nextDir;
    }

    /**
     * Obtiene la última dirección calculada.
     * 
     * @return la última dirección
     */
    public Direction getLastCalculatedDirection() {
        return lastCalculatedDirection;
    }

    @Override
    public void executeAI() {
        chasePlayer();
    }

    /**
     * Verifica si hay un camino disponible hacia el objetivo.
     * 
     * @return true si hay camino
     */
    public boolean hasPathToTarget() {
        if (target == null || pathFinder == null) {
            return false;
        }
        return pathFinder.getNextDirection(x, y, target.getX(), target.getY()) != null;
    }

    @Override
    public String toString() {
        return "Pot{" +
                "x=" + x +
                ", y=" + y +
                ", hasPathFinder=" + (pathFinder != null) +
                ", hasTarget=" + (target != null) +
                '}';
    }
}
