package co.casterlabs.koi.api.events;

import com.google.gson.annotations.SerializedName;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StreamStatusEvent extends Event {
    @SerializedName("is_live")
    private boolean live;
    private User streamer;

    @Override
    public EventType getType() {
        return EventType.STREAM_STATUS;
    }

}
