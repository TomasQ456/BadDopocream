package main.domain.enums;

/**
 * Códigos de error para las excepciones del juego.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public enum ErrorCode {
    /** Posición inválida en el mapa */
    INVALID_POSITION("La posición especificada es inválida"),
    /** Dimensión inválida */
    INVALID_DIMENSION("Las dimensiones especificadas son inválidas"),
    /** Movimiento inválido */
    INVALID_MOVE("El movimiento no es válido"),
    /** No se puede crear bloque de hielo */
    CANNOT_CREATE_ICE("No se puede crear un bloque de hielo en esta posición"),
    /** No se puede destruir bloque de hielo */
    CANNOT_DESTROY_ICE("No se puede destruir el bloque de hielo"),
    /** Error al cargar nivel */
    LEVEL_LOAD_ERROR("Error al cargar el nivel"),
    /** Error al cargar/guardar partida */
    SAVE_LOAD_ERROR("Error al guardar o cargar la partida"),
    /** Estado del juego inválido */
    INVALID_GAME_STATE("El estado actual del juego no permite esta operación"),
    /** Error de colisión */
    COLLISION_ERROR("Error de colisión detectado"),
    /** Jugador nulo */
    NULL_PLAYER("El jugador no puede ser nulo"),
    /** No hay jugadores */
    NO_PLAYERS("El juego requiere al menos un jugador para iniciar");

    private final String message;

    /**
     * Constructor de ErrorCode.
     * 
     * @param message mensaje descriptivo del error
     */
    ErrorCode(String message) {
        this.message = message;
    }

    /**
     * Obtiene el mensaje del error.
     * 
     * @return el mensaje descriptivo
     */
    public String getMessage() {
        return message;
    }
}
