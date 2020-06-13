package co.casterlabs.koi.api;

import co.casterlabs.koi.api.events.ChatEvent;
import co.casterlabs.koi.api.events.DonationEvent;
import co.casterlabs.koi.api.events.Event;
import co.casterlabs.koi.api.events.FollowEvent;
import co.casterlabs.koi.api.events.ShareEvent;
import co.casterlabs.koi.api.events.StreamStatusEvent;
import co.casterlabs.koi.api.events.UserUpdateEvent;

public interface EventListener {

    default void onDonation(DonationEvent event) {}

    default void onChat(ChatEvent event) {}

    default void onStreamStatus(StreamStatusEvent event) {}

    default void onFollow(FollowEvent event) {}

    default void onUserUpdate(UserUpdateEvent event) {}

    default void onShare(ShareEvent event) {}

    default void onEvent(Event event) {}

}
