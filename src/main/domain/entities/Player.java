package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.IceCreamFlavor;
import main.domain.enums.PlayerType;
import main.domain.interfaces.Movable;

/**
 * Clase abstracta que representa a un jugador en el juego.
 * Los jugadores pueden moverse, crear y destruir bloques de hielo.
 * 
 */
public abstract class Player implements Movable {

    /** Coordenada X del jugador */
    protected int x;
    
    /** Coordenada Y del jugador */
    protected int y;
    
    /** Dirección actual del jugador */
    protected Direction direction;
    
    /** Sabor del helado del jugador */
    protected IceCreamFlavor flavor;
    
    /** Indica si el jugador está vivo */
    protected boolean isAlive;
    
    /** Número de vidas restantes */
    protected int lives;
    
    /** Tipo de jugador (HUMAN o AI) */
    protected PlayerType playerType;

    /** Posición inicial X para respawn */
    protected int initialX;
    
    /** Posición inicial Y para respawn */
    protected int initialY;

    /** Vidas iniciales */
    protected static final int DEFAULT_LIVES = 3;

    /**
     * Constructor de Player.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param flavor sabor del helado
     * @param playerType tipo de jugador
     */
    protected Player(int x, int y, IceCreamFlavor flavor, PlayerType playerType) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.direction = Direction.RIGHT;
        this.flavor = flavor;
        this.isAlive = true;
        this.lives = DEFAULT_LIVES;
        this.playerType = playerType;
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
     * Establece la posición del jugador.
     * 
     * @param x nueva coordenada X
     * @param y nueva coordenada Y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la dirección actual.
     * 
     * @return la dirección
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Establece la dirección del jugador.
     * 
     * @param direction la nueva dirección
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Obtiene el sabor del helado.
     * 
     * @return el sabor
     */
    public IceCreamFlavor getFlavor() {
        return flavor;
    }

    /**
     * Indica si el jugador está vivo.
     * 
     * @return true si está vivo
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Obtiene el número de vidas restantes.
     * 
     * @return las vidas
     */
    public int getLives() {
        return lives;
    }

    /**
     * Obtiene el tipo de jugador.
     * 
     * @return el tipo
     */
    public PlayerType getPlayerType() {
        return playerType;
    }

    /**
     * Mueve al jugador en una dirección.
     * 
     * @param direction la dirección del movimiento
     */
    @Override
    public void move(Direction direction) {
        this.direction = direction;
        this.x += direction.getDeltaX();
        this.y += direction.getDeltaY();
    }

    /**
     * Verifica si el jugador puede moverse a una posición.
     * La validación real debe hacerse en el controlador del juego.
     * 
     * @param x coordenada X destino
     * @param y coordenada Y destino
     * @return true (la validación real es externa)
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        return true; // La validación real se hace en DopoIceCream
    }

    /**
     * Crea un bloque de hielo frente al jugador.
     * 
     * @return la posición donde crear el bloque [x, y]
     */
    public int[] createIceBlock() {
        int targetX = x + direction.getDeltaX();
        int targetY = y + direction.getDeltaY();
        return new int[]{targetX, targetY};
    }

    /**
     * Destruye un bloque de hielo frente al jugador.
     * 
     * @return la posición del bloque a destruir [x, y]
     */
    public int[] destroyIceBlock() {
        int targetX = x + direction.getDeltaX();
        int targetY = y + direction.getDeltaY();
        return new int[]{targetX, targetY};
    }

    /**
     * Reduce las vidas del jugador.
     */
    public void decreaseLives() {
        this.lives--;
        if (this.lives <= 0) {
            this.isAlive = false;
        }
    }

    /**
     * Mata al jugador y reduce sus vidas.
     */
    public void kill() {
        this.isAlive = false;
        this.lives--;
    }

    /**
     * Reaparece al jugador en su posición inicial.
     */
    public void respawn() {
        if (lives > 0) {
            this.x = initialX;
            this.y = initialY;
            this.isAlive = true;
            this.direction = Direction.RIGHT;
        }
    }

    /**
     * Verifica si el jugador puede reaparecer.
     * 
     * @return true si tiene vidas restantes
     */
    public boolean canRespawn() {
        return lives > 0;
    }

    /**
     * Método abstracto para actualizar el estado del jugador.
     * 
     * @param game referencia al juego
     */
    public abstract void update(Object game);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", flavor=" + flavor +
                ", isAlive=" + isAlive +
                ", lives=" + lives +
                '}';
    }
}
