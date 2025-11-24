package domain.events;

import domain.enums.GameEventType;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable envelope used by the {@link EventDispatcher} to communicate changes across layers.
 */
public final class GameEvent {
    private final GameEventType type;
    private final Object source;
    private final Map<String, Object> payload;
    private final long timestamp;

    /**
     * Builds a new immutable event envelope.
     *
     * @param type    type indicator.
     * @param source  optional publisher reference.
     * @param payload contextual metadata; copied defensively.
     */
    public GameEvent(GameEventType type, Object source, Map<String, Object> payload) {
        this.type = Objects.requireNonNull(type, "type");
        this.source = source;
        this.payload = payload == null ? Collections.emptyMap() : Collections.unmodifiableMap(payload);
        this.timestamp = Instant.now().toEpochMilli();
    }

    /**
     * @return event type identifier.
     */
    public GameEventType getType() {
        return type;
    }

    /**
     * @return originator of the event (may be {@code null}).
     */
    public Object getSource() {
        return source;
    }

    /**
     * @return immutable payload map, never {@code null}.
     */
    public Map<String, Object> getPayload() {
        return payload;
    }

    /**
     * @return UTC timestamp representing when the event was created.
     */
    public long getTimestamp() {
        return timestamp;
    }
}
