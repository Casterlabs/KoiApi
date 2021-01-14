package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ViewerJoinEvent extends Event {
    private User viewer;

}
