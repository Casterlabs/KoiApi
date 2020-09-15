package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SubscriptionEvent extends Event {
    private User subscriber;
    private User streamer;
    private int months;

    @Override
    public EventType getType() {
        return EventType.SUBSCRIPTION;
    }

}
