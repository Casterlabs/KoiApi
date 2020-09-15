package co.casterlabs.koi.api.events;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DonationEvent extends ChatEvent {
    @SerializedName("usd_equivalent")
    private double usdEquivalent;
    private String formatted;
    private String currency;
    private double amount;
    private String image;

    @Override
    public EventType getType() {
        return EventType.DONATION;
    }

}
