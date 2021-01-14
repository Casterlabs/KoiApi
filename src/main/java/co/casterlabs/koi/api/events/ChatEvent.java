package co.casterlabs.koi.api.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.casterlabs.koi.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatEvent extends Event {
    private Map<String, String> emotes = new HashMap<>();
    private List<Mention> mentions = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private User sender;
    private String message;
    private String id;

    @Getter
    @AllArgsConstructor
    public static class Mention {
        private String target;
        private User mentioned;

    }

}
