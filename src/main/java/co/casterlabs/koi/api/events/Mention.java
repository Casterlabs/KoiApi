package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Data;

@Data
public class Mention {
    private User user;
    private String text;

}
