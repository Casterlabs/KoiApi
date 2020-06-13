package co.casterlabs.koi.api.events;

import lombok.NonNull;

public enum EventType {
    STREAM_STATUS,
    USER_UPDATE,
    CHAT,
    DONATION,
    SHARE,
    FOLLOW;

    public static EventType get(@NonNull String value) {
        for (EventType e : EventType.values()) {
            if (e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }

        return null;
    }

}
