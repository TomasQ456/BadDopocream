package main.domain.entities;

/**
 * Clase abstracta para objetos estáticos en el mapa.
 * Los objetos estáticos pueden ser sólidos (bloqueando el paso) o no.
 * 
 */
public abstract class StaticObject extends GameObject {

    /** Indica si el objeto es sólido y bloquea el paso */
    protected boolean isSolid;

    /**
     * Constructor de StaticObject.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param symbol símbolo del objeto
     * @param isDestructible si puede ser destruido
     * @param isSolid si bloquea el paso
     */
    protected StaticObject(int x, int y, char symbol, boolean isDestructible, boolean isSolid) {
        super(x, y, symbol, isDestructible);
        this.isSolid = isSolid;
    }

    /**
     * Indica si el objeto es sólido y bloquea el paso.
     * 
     * @return true si es sólido
     */
    public boolean isSolid() {
        return isSolid;
    }
}
