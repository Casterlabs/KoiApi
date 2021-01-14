package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ViewerLeaveEvent extends Event {
    private User viewer;

}
