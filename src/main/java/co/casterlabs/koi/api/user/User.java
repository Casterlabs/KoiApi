package co.casterlabs.koi.api.user;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {
    @SerializedName("image_link")
    private String imageLink;
    @SerializedName("follower_count")
    private long followerCount;
    @SerializedName("following_count")
    private long followingCount;
    private String UUID;
    private String displayname;
    private String username;
    private UserPlatform platform;

}
