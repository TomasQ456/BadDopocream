package main.domain.entities;

import main.domain.enums.Direction;
import main.domain.enums.FruitType;
import main.domain.interfaces.Movable;

/**
 * Representa una piña en el juego.
 * Las piñas son frutas que se mueven en una dirección.
 * 
 */
public class Pineapple extends Fruit implements Movable {

    /** Dirección actual de movimiento */
    private Direction currentDirection;
    
    /** Velocidad de movimiento (celdas por actualización) */
    private int speed;
    
    /** Contador para controlar la velocidad */
    private int moveCounter;
    
    /** Referencia a las dimensiones del mapa */
    private int mapWidth;
    private int mapHeight;

    /**
     * Constructor de Pineapple.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Pineapple(int x, int y) {
        super(x, y, FruitType.PINEAPPLE);
        this.currentDirection = Direction.RIGHT;
        this.speed = 1;
        this.moveCounter = 0;
        this.mapWidth = 0;
        this.mapHeight = 0;
    }

    /**
     * Constructor de Pineapple con dirección inicial.
     * 
     * @param x coordenada X
     * @param y coordenada Y
     * @param initialDirection dirección inicial
     */
    public Pineapple(int x, int y, Direction initialDirection) {
        this(x, y);
        this.currentDirection = initialDirection;
    }

    /**
     * Establece las dimensiones del mapa para control de límites.
     * 
     * @param width ancho del mapa
     * @param height alto del mapa
     */
    public void setMapDimensions(int width, int height) {
        this.mapWidth = width;
        this.mapHeight = height;
    }

    /**
     * Obtiene la dirección actual.
     * 
     * @return la dirección
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Establece la dirección de movimiento.
     * 
     * @param direction la nueva dirección
     */
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    /**
     * Obtiene la velocidad.
     * 
     * @return la velocidad
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Establece la velocidad.
     * 
     * @param speed la nueva velocidad
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void move(Direction direction) {
        this.currentDirection = direction;
        int newX = x + direction.getDeltaX();
        int newY = y + direction.getDeltaY();
        setPosition(newX, newY);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        // La validación real se hace externamente
        if (mapWidth > 0 && mapHeight > 0) {
            return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
        }
        return true;
    }

    /**
     * Cambia a la dirección opuesta (rebote).
     */
    public void reverseDirection() {
        this.currentDirection = currentDirection.getOpposite();
    }

    @Override
    public void update() {
        if (collected) {
            return;
        }
        
        moveCounter++;
        if (moveCounter >= speed) {
            moveCounter = 0;
            
            int newX = x + currentDirection.getDeltaX();
            int newY = y + currentDirection.getDeltaY();
            
            // Si no puede moverse, rebota
            if (!canMoveTo(newX, newY)) {
                reverseDirection();
            } else {
                setPosition(newX, newY);
            }
        }
    }

    @Override
    public String toString() {
        return "Pineapple{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + currentDirection +
                ", speed=" + speed +
                ", collected=" + collected +
                '}';
    }
}
