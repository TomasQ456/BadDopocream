package main.domain.event;

import main.domain.enums.EventType;

/**
 * Representa un evento del juego.
 * Los eventos son inmutables una vez creados.
 * 
 * @author Bad Dopo-Cream Team
 * @version 1.0
 */
public class GameEvent {

    private final EventType type;
    private final Object data;
    private final long timestamp;

    /**
     * Constructor de GameEvent.
     * 
     * @param type el tipo de evento
     * @param data datos asociados al evento (puede ser null)
     */
    public GameEvent(EventType type, Object data) {
        this.type = type;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Constructor de GameEvent sin datos.
     * 
     * @param type el tipo de evento
     */
    public GameEvent(EventType type) {
        this(type, null);
    }

    /**
     * Obtiene el tipo de evento.
     * 
     * @return el tipo de evento
     */
    public EventType getType() {
        return type;
    }

    /**
     * Obtiene los datos del evento.
     * 
     * @return los datos del evento, o null si no hay
     */
    public Object getData() {
        return data;
    }

    /**
     * Obtiene el timestamp del evento.
     * 
     * @return el timestamp en milisegundos
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Obtiene los datos del evento como un tipo específico.
     * 
     * @param <T> el tipo esperado
     * @param clazz la clase del tipo esperado
     * @return los datos casteados al tipo, o null
     */
    @SuppressWarnings("unchecked")
    public <T> T getDataAs(Class<T> clazz) {
        if (data != null && clazz.isInstance(data)) {
            return (T) data;
        }
        return null;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "type=" + type +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
