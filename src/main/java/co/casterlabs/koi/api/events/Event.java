package co.casterlabs.koi.api.events;

import co.casterlabs.koi.api.user.User;

public abstract class Event {

    public abstract User getStreamer();

    public abstract EventType getType();

}
