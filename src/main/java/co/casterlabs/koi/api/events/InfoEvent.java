package co.casterlabs.koi.api.events;

import com.google.gson.JsonObject;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InfoEvent extends Event {
    private User streamer;
    private JsonObject event;

    @Override
    public EventType getType() {
        return EventType.INFO;
    }

}
