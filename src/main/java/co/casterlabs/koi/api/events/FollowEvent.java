package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FollowEvent extends Event {
    private User follower;
    private User streamer;

    @Override
    public EventType getType() {
        return EventType.FOLLOW;
    }

}
