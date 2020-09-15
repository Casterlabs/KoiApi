package co.casterlabs.koi.api.events;

import com.google.gson.JsonObject;

import co.casterlabs.koi.api.Koi;
import lombok.Getter;

@Getter
public enum EventType {
    INFO(InfoEvent.class),
    FOLLOW(FollowEvent.class, "follower"),
    CHAT(ChatEvent.class),
    DONATION(DonationEvent.class, "sender"),
    SUBSCRIPTION(SubscriptionEvent.class),
    USER_UPDATE(UserUpdateEvent.class),
    STREAM_STATUS(StreamStatusEvent.class),
    UPVOTE(UpvoteEvent.class);

    // For events that may get stored, so Koi and clients can only store the UUID and platform and then retrive up-to-date user information.
    private String otherUser = null;
    private boolean data = false;
    private Class<?> clazz;

    private EventType(Class<?> clazz) {
        this.clazz = clazz;
    }

    private EventType(Class<?> clazz, String otherUser) {
        this(clazz);

        this.data = true;
        this.otherUser = otherUser;
    }

    public static Event get(JsonObject json) {
        EventType type = EventType.valueOf(json.get("event_type").getAsString());

        return (Event) Koi.getGson().fromJson(json, type.clazz);
    }

}
