package co.casterlabs.koi.api.events;

import java.util.ArrayList;
import java.util.List;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatEvent extends Event {
    private List<Mention> mentions = new ArrayList<>();
    private User sender;
    private String message;
    private User streamer;
    private String id;

    @Override
    public EventType getType() {
        return EventType.CHAT;
    }

}
