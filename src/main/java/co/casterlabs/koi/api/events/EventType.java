package co.casterlabs.koi.api.events;

import com.google.gson.JsonObject;

import co.casterlabs.koi.api.Koi;

public enum EventType {
    FOLLOW,
    CHAT,
    DONATION,
    SUBSCRIPTION,
    USER_UPDATE,
    STREAM_STATUS,
    UPVOTE,
    VIEWER_JOIN,
    VIEWER_LEAVE,
    VIEWER_LIST;

    public static Event get(JsonObject eventJson) {
        Class<? extends Event> clazz = null;

        switch (Koi.getGson().fromJson(eventJson.get("event_type"), EventType.class)) {
            case CHAT:
                clazz = ChatEvent.class;
                break;

            case DONATION:
                clazz = DonationEvent.class;
                break;

            case FOLLOW:
                clazz = FollowEvent.class;
                break;

            case STREAM_STATUS:
                clazz = StreamStatusEvent.class;
                break;

            case SUBSCRIPTION:
                clazz = SubscriptionEvent.class;
                break;

            case UPVOTE:
                clazz = UpvoteEvent.class;
                break;

            case USER_UPDATE:
                clazz = UserUpdateEvent.class;
                break;

            case VIEWER_JOIN:
                clazz = ViewerJoinEvent.class;
                break;

            case VIEWER_LEAVE:
                clazz = ViewerLeaveEvent.class;
                break;

            case VIEWER_LIST:
                clazz = ViewerListEvent.class;
                break;

        }

        return Koi.getGson().fromJson(eventJson, clazz);
    }

}
