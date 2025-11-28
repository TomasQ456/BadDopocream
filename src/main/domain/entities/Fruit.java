package main.domain.entities;

import main.domain.enums.FruitType;
import main.domain.interfaces.Collectible;

/**
 * Clase abstracta base para las frutas del juego.
 * Las frutas son objetos recolectables que otorgan puntos.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public abstract class Fruit extends StaticObject implements Collectible {

    /** Tipo de fruta */
    protected FruitType type;
    
    /** Indica si la fruta ha sido recolectada */
    protected boolean collected;

    /**
     * Constructor de Fruit.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param type tipo de fruta
     */
    protected Fruit(int x, int y, FruitType type) {
        super(x, y, type.getSymbol(), false, false); // Las frutas no son sólidas
        this.type = type;
        this.collected = false;
    }

    /**
     * Obtiene los puntos que otorga la fruta.
     * 
     * @return los puntos
     */
    @Override
    public int getPoints() {
        return type.getPoints();
    }

    /**
     * Obtiene el tipo de fruta.
     * 
     * @return el tipo
     */
    public FruitType getType() {
        return type;
    }

    /**
     * Recolecta la fruta.
     */
    @Override
    public void collect() {
        this.collected = true;
    }

    /**
     * Verifica si la fruta ha sido recolectada.
     * 
     * @return true si fue recolectada
     */
    @Override
    public boolean isCollected() {
        return collected;
    }

    /**
     * Reinicia la fruta a su estado inicial.
     */
    public void reset() {
        this.collected = false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", points=" + getPoints() +
                ", collected=" + collected +
                '}';
    }
}
