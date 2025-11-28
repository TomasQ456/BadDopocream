package main.domain.enums;

/**
 * Representa los estados posibles del juego.
 * Define las transiciones válidas entre estados.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum GameState {
    /** Menú principal */
    MENU,
    /** Juego en progreso */
    PLAYING,
    /** Juego pausado */
    PAUSED,
    /** Victoria - nivel completado */
    VICTORY,
    /** Derrota - game over */
    DEFEAT;

    /**
     * Verifica si la transición a otro estado es válida.
     * 
     * @param newState el nuevo estado deseado
     * @return true si la transición es válida
     */
    public boolean canTransitionTo(GameState newState) {
        switch (this) {
            case MENU:
                return newState == PLAYING;
            case PLAYING:
                return newState == PAUSED || newState == VICTORY || newState == DEFEAT;
            case PAUSED:
                return newState == PLAYING || newState == MENU;
            case VICTORY:
                return newState == MENU || newState == PLAYING;
            case DEFEAT:
                return newState == MENU || newState == PLAYING;
            default:
                return false;
        }
    }
}
