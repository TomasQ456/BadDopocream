package main.domain.entities;

/**
 * Clase abstracta base para todos los objetos del juego.
 * Define la posición, símbolo y destructibilidad de los objetos.
 * 
 */
public abstract class GameObject {

    /** Coordenada X del objeto */
    protected int x;
    
    /** Coordenada Y del objeto */
    protected int y;
    
    /** Símbolo que representa al objeto en el mapa */
    protected char symbol;
    
    /** Indica si el objeto puede ser destruido */
    protected boolean isDestructible;

    /**
     * Constructor de GameObject.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param symbol símbolo del objeto
     * @param isDestructible si puede ser destruido
     */
    protected GameObject(int x, int y, char symbol, boolean isDestructible) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.isDestructible = isDestructible;
    }

    /**
     * Obtiene la coordenada X.
     * 
     * @return la coordenada X
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y.
     * 
     * @return la coordenada Y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la posición del objeto.
     * 
     * @param x nueva coordenada X
     * @param y nueva coordenada Y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene el símbolo del objeto.
     * 
     * @return el símbolo
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Indica si el objeto puede ser destruido.
     * 
     * @return true si es destructible
     */
    public boolean isDestructible() {
        return isDestructible;
    }

    /**
     * Método abstracto para actualizar el estado del objeto.
     * Debe ser implementado por las subclases.
     */
    public abstract void update();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GameObject that = (GameObject) obj;
        return x == that.x && y == that.y && symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + symbol;
        return result;
    }
}
