package co.casterlabs.koi.api.events;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StreamStatusEvent extends Event {
    @SerializedName("is_live")
    private boolean live;
    private String title;

}
