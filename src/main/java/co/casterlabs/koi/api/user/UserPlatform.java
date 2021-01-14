package co.casterlabs.koi.api.user;

import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
public enum UserPlatform {
    //@formatter:off
    
    CAFFEINE(
        PlatformFeatures.CHAT_UPVOTES
    ),
    
    TWITCH();
    
    //@formatter:on

    private PlatformFeatures[] features;

    private UserPlatform(PlatformFeatures... features) {
        this.features = features;
    }

    public static enum PlatformFeatures {
        CHAT_UPVOTES,

    }

}
