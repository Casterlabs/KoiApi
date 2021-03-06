package co.casterlabs.koi.api;

public interface EventListener {

    default void onClose(boolean remote) {}

    default void onServerMessage(String message) {}

    default void onException(Exception e) {
        e.printStackTrace();
    }

}
