package main.domain.util;

import main.domain.enums.Direction;

/**
 * Representa un patrón de movimiento para monstruos.
 * Los patrones son secuencias de direcciones que se repiten cíclicamente.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class MovementPattern {

    /** Secuencia de direcciones del patrón */
    private final Direction[] directions;
    
    /** Índice actual en el patrón */
    private int currentIndex;

    /**
     * Constructor de MovementPattern.
     * 
     * @param directions secuencia de direcciones
     */
    public MovementPattern(Direction[] directions) {
        if (directions == null || directions.length == 0) {
            this.directions = new Direction[0];
        } else {
            this.directions = directions.clone();
        }
        this.currentIndex = 0;
    }

    /**
     * Obtiene la siguiente dirección del patrón.
     * 
     * @return la siguiente dirección, o null si el patrón está vacío
     */
    public Direction getNextDirection() {
        if (directions.length == 0) {
            return null;
        }
        
        Direction next = directions[currentIndex];
        currentIndex = (currentIndex + 1) % directions.length;
        return next;
    }

    /**
     * Obtiene la dirección actual sin avanzar el índice.
     * 
     * @return la dirección actual, o null si el patrón está vacío
     */
    public Direction getCurrentDirection() {
        if (directions.length == 0) {
            return null;
        }
        return directions[currentIndex];
    }

    /**
     * Reinicia el patrón al inicio.
     */
    public void reset() {
        this.currentIndex = 0;
    }

    /**
     * Obtiene el índice actual.
     * 
     * @return el índice
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Establece el índice actual.
     * 
     * @param index el nuevo índice
     */
    public void setCurrentIndex(int index) {
        if (directions.length > 0 && index >= 0) {
            this.currentIndex = index % directions.length;
        }
    }

    /**
     * Obtiene el tamaño del patrón.
     * 
     * @return el número de direcciones
     */
    public int getSize() {
        return directions.length;
    }

    /**
     * Verifica si el patrón está vacío.
     * 
     * @return true si está vacío
     */
    public boolean isEmpty() {
        return directions.length == 0;
    }

    /**
     * Obtiene una copia de las direcciones.
     * 
     * @return array de direcciones
     */
    public Direction[] getDirections() {
        return directions.clone();
    }
}
