package co.casterlabs.koi.api.events;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import co.casterlabs.koi.api.Koi;

public abstract class Event {
    private static Map<EventType, Class<? extends Event>> EVENTS = new HashMap<>();

    static {
        EVENTS.put(EventType.STREAM_STATUS, StreamStatusEvent.class);
        EVENTS.put(EventType.CHAT, ChatEvent.class);
        EVENTS.put(EventType.FOLLOW, FollowEvent.class);
        EVENTS.put(EventType.DONATION, DonationEvent.class);
        EVENTS.put(EventType.SHARE, ShareEvent.class);
        EVENTS.put(EventType.USER_UPDATE, UserUpdateEvent.class);

    }

    public abstract EventType getType();

    public static Event fromJson(JsonObject event) {
        EventType type = EventType.get(event.get("event_type").getAsString());
        Class<? extends Event> clazz = EVENTS.get(type);

        if (clazz != null) {
            return Koi.getGson().fromJson(event, clazz);
        } else {
            return null;
        }
    }

}
