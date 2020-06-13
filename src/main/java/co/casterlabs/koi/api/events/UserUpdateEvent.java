package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateEvent extends Event {
    private User streamer;

    @Override
    public EventType getType() {
        return EventType.USER_UPDATE;
    }

}
