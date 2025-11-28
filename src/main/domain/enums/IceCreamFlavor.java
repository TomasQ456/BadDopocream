package main.domain.enums;

/**
 * Representa los sabores de helado disponibles para los jugadores.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum IceCreamFlavor {
    /** Sabor vainilla */
    VANILLA('V'),
    /** Sabor chocolate */
    CHOCOLATE('C'),
    /** Sabor fresa */
    STRAWBERRY('S'),
    /** Sabor menta */
    MINT('M'),
    /** Sabor caramelo */
    CARAMEL('A');

    private final char symbol;

    /**
     * Constructor de IceCreamFlavor.
     * 
     * @param symbol el símbolo que representa este sabor
     */
    IceCreamFlavor(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Obtiene el símbolo del sabor.
     * 
     * @return el símbolo
     */
    public char getSymbol() {
        return symbol;
    }
}
