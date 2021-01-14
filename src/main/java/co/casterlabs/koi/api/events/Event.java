package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;

@Getter
public abstract class Event {
    private boolean upvotable = false;
    private User streamer;
    private EventType type;

}
