package main.domain.entities;

import main.domain.enums.FruitType;

/**
 * Representa una uva en el juego.
 * Las uvas son frutas estáticas básicas.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class Grape extends Fruit {

    /**
     * Constructor de Grape.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Grape(int x, int y) {
        super(x, y, FruitType.GRAPE);
    }

    @Override
    public void update() {
        // Las uvas son estáticas, no necesitan actualización
    }
}
