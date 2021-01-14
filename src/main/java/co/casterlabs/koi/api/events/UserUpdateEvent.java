package co.casterlabs.koi.api.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateEvent extends Event {
    private String timestamp;

}
