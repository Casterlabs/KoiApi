package co.casterlabs.koi.api.events;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DonationEvent extends ChatEvent {
    private List<Donation> donations;

    @Data
    @AllArgsConstructor
    public static class Donation {
        @SerializedName("animated_image")
        private String animatedImage;
        private String currency;
        private double amount;
        private String image;

    }

}
