package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatEvent extends Event {
    private User streamer;
    private User sender;
    private String message;

    @Override
    public EventType getType() {
        return EventType.CHAT;
    }

}
