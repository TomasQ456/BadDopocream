package main.domain.enums;

/**
 * Representa los tipos de monstruos en el juego.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum MonsterType {
    /** Troll - sigue un patrón predefinido */
    TROLL('T', false),
    /** Pot - usa pathfinding para perseguir */
    POT('O', false),
    /** OrangeSquid - puede romper bloques de hielo */
    ORANGE_SQUID('Q', true);

    private final char symbol;
    private final boolean canBreakIce;

    /**
     * Constructor de MonsterType.
     * 
     * @param symbol símbolo que representa este monstruo
     * @param canBreakIce si puede romper bloques de hielo
     */
    MonsterType(char symbol, boolean canBreakIce) {
        this.symbol = symbol;
        this.canBreakIce = canBreakIce;
    }

    /**
     * Obtiene el símbolo del tipo de monstruo.
     * 
     * @return el símbolo
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Indica si este tipo de monstruo puede romper hielo.
     * 
     * @return true si puede romper hielo
     */
    public boolean canBreakIce() {
        return canBreakIce;
    }
}
