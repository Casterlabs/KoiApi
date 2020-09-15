package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpvoteEvent extends Event {
    private ChatEvent event;
    private User streamer;
    private int upvotes;
    private String id;

    @Override
    public EventType getType() {
        return EventType.UPVOTE;
    }

}
