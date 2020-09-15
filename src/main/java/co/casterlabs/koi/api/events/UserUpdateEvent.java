package co.casterlabs.koi.api.events;

import java.time.Instant;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateEvent extends Event {
    private String timestamp;
    private User streamer;

    public Instant getTimestamp() {
        return Instant.parse(this.timestamp);
    }

    @Override
    public EventType getType() {
        return EventType.USER_UPDATE;
    }

}
