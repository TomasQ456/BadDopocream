package main.domain.enums;

/**
 * Representa los tipos de eventos del juego.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum EventType {
    /** El juego ha comenzado */
    GAME_STARTED,
    /** Un jugador se ha movido */
    PLAYER_MOVED,
    /** Se ha creado un bloque de hielo */
    ICE_CREATED,
    /** Se ha destruido un bloque de hielo */
    ICE_DESTROYED,
    /** Se ha recolectado una fruta */
    FRUIT_COLLECTED,
    /** Se ha completado un nivel */
    LEVEL_COMPLETED,
    /** Un jugador ha muerto */
    PLAYER_DIED,
    /** Un jugador ha sido golpeado */
    PLAYER_HIT,
    /** La puntuación ha sido actualizada */
    SCORE_UPDATED,
    /** El tiempo ha sido actualizado */
    TIME_UPDATED,
    /** El juego ha sido pausado */
    GAME_PAUSED,
    /** El juego ha sido reanudado */
    GAME_RESUMED,
    /** El juego ha sido reiniciado */
    GAME_RESET,
    /** El jugador ha ganado */
    GAME_WON,
    /** El jugador ha perdido */
    GAME_LOST,
    /** Game over */
    GAME_OVER
}

