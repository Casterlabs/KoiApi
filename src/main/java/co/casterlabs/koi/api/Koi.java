package co.casterlabs.koi.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.casterlabs.koi.api.events.ChatEvent;
import co.casterlabs.koi.api.events.DonationEvent;
import co.casterlabs.koi.api.events.Event;
import co.casterlabs.koi.api.events.FollowEvent;
import co.casterlabs.koi.api.events.ShareEvent;
import co.casterlabs.koi.api.events.StreamStatusEvent;
import co.casterlabs.koi.api.events.UserUpdateEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.e3ndr.FastLoggingFramework.Logging.FastLogger;

public class Koi {
    public static final String VERSION = "1.0.0";

    private static @Getter URI KOI;
    private static @Getter Gson GSON = new Gson();

    private EventListener listener;
    private FastLogger logger;
    private KoiSocket socket;

    static {
        try {
            KOI = new URI("wss://live.casterlabs.co/koi");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Koi(@NonNull EventListener listener) {
        this(KOI, new FastLogger(), listener);
    }

    @SneakyThrows
    public Koi(@NonNull URI uri, @NonNull FastLogger logger, @NonNull EventListener listener) {
        this.logger = logger;
        this.listener = listener;
        this.socket = new KoiSocket(uri);
    }

    public void close() {
        this.socket.close();
    }

    @SneakyThrows
    public Koi connectBlocking() {
        if (!this.isConnected()) {
            this.socket.connectBlocking();
        }

        return this;
    }

    public Koi connect() {
        if (!this.isConnected()) {
            this.socket.connect();
        }

        return this;
    }

    public boolean isConnected() {
        return this.socket.isOpen();
    }

    public void add(String streamer) {
        JsonObject request = new JsonObject();

        request.addProperty("request", "ADD");
        request.addProperty("user", streamer);

        this.socket.send(request.toString());
    }

    public void remove(String streamer) {
        JsonObject request = new JsonObject();

        request.addProperty("request", "REMOVE");
        request.addProperty("user", streamer);

        this.socket.send(request.toString());
    }

    private void processEvent(JsonObject json) {
        Event event = Event.fromJson(json);

        if (event != null) {
            this.listener.onEvent(event);

            switch (event.getType()) {
                case CHAT:
                    this.listener.onChat((ChatEvent) event);
                    break;

                case DONATION:
                    this.listener.onDonation((DonationEvent) event);
                    break;

                case FOLLOW:
                    this.listener.onFollow((FollowEvent) event);
                    break;

                case SHARE:
                    this.listener.onShare((ShareEvent) event);
                    break;

                case STREAM_STATUS:
                    this.listener.onStreamStatus((StreamStatusEvent) event);
                    break;

                case USER_UPDATE:
                    this.listener.onUserUpdate((UserUpdateEvent) event);
                    break;
            }
        }
    }

    private class KoiSocket extends WebSocketClient {

        public KoiSocket(URI uri) {
            super(uri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {}

        @Override
        public void onMessage(String raw) {
            try {
                JsonObject packet = GSON.fromJson(raw, JsonObject.class);

                switch (packet.get("type").getAsString()) {
                    case "KEEP_ALIVE":
                        this.keepAlive();
                        return;

                    case "SERVER":
                        // System.out.println(raw);
                        return;

                    case "EVENT":
                        JsonObject event = packet.getAsJsonObject("event");
                        processEvent(event);
                        return;
                }
            } catch (Exception e) {
                logger.exception(e);
            }
        }

        @SneakyThrows
        @Override
        public void onClose(int code, String reason, boolean remote) {
            if (remote) logger.info("Lost connection to Koi.");
        }

        private void keepAlive() {
            JsonObject request = new JsonObject();

            request.addProperty("request", "KEEP_ALIVE");

            this.send(request.toString());
        }

        @Override
        public void onError(Exception e) {
            logger.exception(e);
        }

    }

}
