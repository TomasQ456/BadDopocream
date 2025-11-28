package main.domain.entities;

/**
 * Representa una celda del mapa del juego.
 * Una celda puede contener un objeto estático y/o un jugador.
 * 
 */
public class Cell {

    /** Objeto estático en la celda (muro, fruta, etc.) */
    private GameObject staticObject;
    
    /** Jugador en la celda */
    private Player player;

    /** Coordenada X de la celda */
    private final int x;
    
    /** Coordenada Y de la celda */
    private final int y;
    
    /** Indica si la celda está bloqueada manualmente */
    private boolean manuallyBlocked;

    /**
     * Constructor de Cell.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.staticObject = null;
        this.player = null;
        this.manuallyBlocked = false;
    }

    /**
     * Obtiene la coordenada X de la celda.
     * 
     * @return coordenada X
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y de la celda.
     * 
     * @return coordenada Y
     */
    public int getY() {
        return y;
    }

    /**
     * Establece el objeto estático en la celda.
     * 
     * @param staticObject el objeto a colocar
     */
    public void setStaticObject(GameObject staticObject) {
        this.staticObject = staticObject;
    }

    /**
     * Obtiene el objeto estático de la celda.
     * 
     * @return el objeto estático o null
     */
    public GameObject getStaticObject() {
        return staticObject;
    }

    /**
     * Establece el jugador en la celda.
     * 
     * @param player el jugador a colocar
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Obtiene el jugador de la celda.
     * 
     * @return el jugador o null
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Verifica si la celda está vacía (sin objeto ni jugador).
     * 
     * @return true si está vacía
     */
    public boolean isEmpty() {
        return staticObject == null && player == null;
    }

    /**
     * Verifica si la celda tiene un jugador.
     * 
     * @return true si tiene jugador
     */
    public boolean hasPlayer() {
        return player != null;
    }

    /**
     * Verifica si la celda tiene un objeto estático.
     * 
     * @return true si tiene objeto
     */
    public boolean hasStaticObject() {
        return staticObject != null;
    }

    /**
     * Limpia la celda removiendo objeto y jugador.
     */
    public void clear() {
        this.staticObject = null;
        this.player = null;
    }

    /**
     * Remueve el objeto estático de la celda.
     */
    public void removeStaticObject() {
        this.staticObject = null;
    }

    /**
     * Remueve el jugador de la celda.
     */
    public void removePlayer() {
        this.player = null;
    }

    /**
     * Verifica si la celda es transitable (se puede pasar).
     * 
     * @return true si se puede pasar
     */
    public boolean isWalkable() {
        if (manuallyBlocked) {
            return false;
        }
        if (staticObject == null) {
            return true;
        }
        if (staticObject instanceof StaticObject) {
            return !((StaticObject) staticObject).isSolid();
        }
        return true;
    }

    /**
     * Establece manualmente si la celda es transitable.
     * Útil para tests y configuración de mapas.
     * 
     * @param walkable true si es transitable, false si está bloqueada
     */
    public void setWalkable(boolean walkable) {
        this.manuallyBlocked = !walkable;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", staticObject=" + staticObject +
                ", player=" + player +
                '}';
    }
}
