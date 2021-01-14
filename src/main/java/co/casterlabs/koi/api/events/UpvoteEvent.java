package co.casterlabs.koi.api.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpvoteEvent extends Event {
    private int upvotes;
    private String id;

}
