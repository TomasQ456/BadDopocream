package main.domain.event;

/**
 * Interfaz para escuchar eventos del juego.
 * Implementa el patrón Observer.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
@FunctionalInterface
public interface GameEventListener {

    /**
     * Método llamado cuando ocurre un evento.
     * 
     * @param event el evento que ocurrió
     */
    void onEvent(GameEvent event);
}
