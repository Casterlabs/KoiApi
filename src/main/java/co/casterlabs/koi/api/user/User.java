package co.casterlabs.koi.api.user;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    private UserPlatform platform;
    private String UUID;
    private String username;
    private String displayname;
    @SerializedName("image_link")
    private String imageLink;
    private String color;
    private String link;
    @SerializedName("follower_count")
    private long followerCount;

}
