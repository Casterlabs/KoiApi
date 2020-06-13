package co.casterlabs.koi.api.events;

import com.google.gson.annotations.SerializedName;

import co.casterlabs.koi.api.user.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DonationEvent extends Event {
    @SerializedName("usd_equivalent")
    private double USDEquivalent;
    private User streamer;
    private User sender;
    private String formatted;
    private String currency;
    private double amount;
    private String image;

    @Override
    public EventType getType() {
        return EventType.DONATION;
    }

}
