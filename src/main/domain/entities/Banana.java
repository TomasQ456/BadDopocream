package main.domain.entities;

import main.domain.enums.FruitType;

/**
 * Representa una banana en el juego.
 * Las bananas son frutas estáticas.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class Banana extends Fruit {

    /**
     * Constructor de Banana.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Banana(int x, int y) {
        super(x, y, FruitType.BANANA);
    }

    @Override
    public void update() {
        // Las bananas son estáticas, no necesitan actualización
    }
}
