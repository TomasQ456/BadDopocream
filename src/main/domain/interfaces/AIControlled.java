package main.domain.interfaces;

import main.domain.entities.Player;

/**
 * Interfaz para entidades controladas por IA.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public interface AIControlled {

    /**
     * Ejecuta la lógica de IA.
     */
    void executeAI();

    /**
     * Establece el objetivo de la IA.
     * 
     * @param target el jugador objetivo
     */
    void setTarget(Player target);

    /**
     * Obtiene el objetivo actual de la IA.
     * 
     * @return el jugador objetivo
     */
    Player getTarget();
}
