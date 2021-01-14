package co.casterlabs.koi.api.user;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class User {
    private final UserPlatform platform;
    private List<String> badges;
    private String color;
    private String username;
    private String UUID;
    private String link;

    @SerializedName("image_link")
    private String imageLink;

    @SerializedName("followers_count")
    private long followersCount = -1;

}
