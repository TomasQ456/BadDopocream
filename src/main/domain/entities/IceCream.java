package main.domain.entities;

import main.domain.enums.IceCreamFlavor;
import main.domain.enums.PlayerType;

/**
 * Representa al jugador principal del juego - un helado.
 * El IceCream puede recolectar frutas y acumular puntos.
 * 
 */
public class IceCream extends Player {

    /** Puntuación del jugador */
    private int score;
    
    /** Número de frutas recolectadas */
    private int fruitsCollected;

    /**
     * Constructor de IceCream.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param flavor sabor del helado
     */
    public IceCream(int x, int y, IceCreamFlavor flavor) {
        super(x, y, flavor, PlayerType.HUMAN);
        this.score = 0;
        this.fruitsCollected = 0;
    }

    /**
     * Constructor de IceCream con tipo de jugador.
     * 
     * @param x coordenada X inicial
     * @param y coordenada Y inicial
     * @param flavor sabor del helado
     * @param playerType tipo de jugador
     */
    public IceCream(int x, int y, IceCreamFlavor flavor, PlayerType playerType) {
        super(x, y, flavor, playerType);
        this.score = 0;
        this.fruitsCollected = 0;
    }

    /**
     * Recolecta una fruta y actualiza puntuación.
     * 
     * @param fruit la fruta a recolectar
     */
    public void collectFruit(Fruit fruit) {
        if (fruit != null && !fruit.isCollected()) {
            fruit.collect();
            this.score += fruit.getPoints();
            this.fruitsCollected++;
        }
    }

    /**
     * Obtiene la puntuación del jugador.
     * 
     * @return la puntuación
     */
    public int getScore() {
        return score;
    }

    /**
     * Obtiene el número de frutas recolectadas.
     * 
     * @return el número de frutas
     */
    public int getFruitsCollected() {
        return fruitsCollected;
    }

    /**
     * Incrementa el contador de frutas recolectadas.
     */
    public void incrementFruitsCollected() {
        this.fruitsCollected++;
    }

    /**
     * Añade puntos a la puntuación.
     * 
     * @param points puntos a añadir
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Reinicia la puntuación y frutas del jugador.
     */
    public void resetStats() {
        this.score = 0;
        this.fruitsCollected = 0;
    }

    @Override
    public void update(Object game) {
        // El IceCream es controlado por input, no necesita lógica de actualización automática
    }

    @Override
    public void respawn() {
        super.respawn();
        // No reiniciamos el score al reaparecer
    }

    @Override
    public String toString() {
        return "IceCream{" +
                "x=" + x +
                ", y=" + y +
                ", flavor=" + flavor +
                ", score=" + score +
                ", fruitsCollected=" + fruitsCollected +
                ", lives=" + lives +
                ", isAlive=" + isAlive +
                '}';
    }
}
