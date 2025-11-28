package main.domain.interfaces;

import main.domain.enums.Direction;

/**
 * Interfaz para entidades que pueden moverse en el juego.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public interface Movable {

    /**
     * Mueve la entidad en una dirección.
     * 
     * @param direction la dirección del movimiento
     */
    void move(Direction direction);

    /**
     * Verifica si la entidad puede moverse a una posición.
     * 
     * @param x coordenada X destino
     * @param y coordenada Y destino
     * @return true si puede moverse a esa posición
     */
    boolean canMoveTo(int x, int y);
}
