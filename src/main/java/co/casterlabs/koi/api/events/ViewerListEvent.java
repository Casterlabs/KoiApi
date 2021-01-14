package co.casterlabs.koi.api.events;

import java.util.Collection;

import co.casterlabs.koi.api.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ViewerListEvent extends Event {
    private Collection<User> viewers;

}
