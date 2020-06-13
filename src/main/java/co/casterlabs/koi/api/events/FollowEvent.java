package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FollowEvent extends Event {
    private User streamer;
    private User follower;

    @Override
    public EventType getType() {
        return EventType.FOLLOW;
    }

}
