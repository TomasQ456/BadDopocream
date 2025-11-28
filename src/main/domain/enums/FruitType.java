package main.domain.enums;

/**
 * Representa los tipos de fruta en el juego.
 * Cada tipo tiene un valor de puntos asociado.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum FruitType {
    /** Uva - fruta estática básica */
    GRAPE(100, 'G'),
    /** Banana - fruta estática */
    BANANA(150, 'B'),
    /** Piña - fruta que se mueve */
    PINEAPPLE(200, 'P'),
    /** Cereza - fruta que se teletransporta */
    CHERRY(300, 'H');

    private final int points;
    private final char symbol;

    /**
     * Constructor de FruitType.
     * 
     * @param points puntos que otorga esta fruta
     * @param symbol símbolo que representa esta fruta
     */
    FruitType(int points, char symbol) {
        this.points = points;
        this.symbol = symbol;
    }

    /**
     * Obtiene los puntos de este tipo de fruta.
     * 
     * @return los puntos
     */
    public int getPoints() {
        return points;
    }

    /**
     * Obtiene el símbolo de este tipo de fruta.
     * 
     * @return el símbolo
     */
    public char getSymbol() {
        return symbol;
    }
}
