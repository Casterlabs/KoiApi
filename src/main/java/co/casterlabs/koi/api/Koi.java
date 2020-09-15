package co.casterlabs.koi.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.casterlabs.koi.api.events.Event;
import co.casterlabs.koi.api.events.EventType;
import co.casterlabs.koi.api.user.UserPlatform;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class Koi {
    public static final String VERSION = "1.2.0";

    private static @Getter URI koiUri;
    private static @Getter Gson gson = new Gson();

    private EventListener listener;
    private FastLogger logger;
    private KoiSocket socket;

    static {
        try {
            koiUri = new URI("wss://api.casterlabs.co/v1/koi");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Koi(@NonNull EventListener listener) {
        this(koiUri, new FastLogger(), listener);
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
        this.add(streamer, UserPlatform.CAFFEINE.name());
    }

    public void add(String streamer, String platform) {
        JsonObject request = new JsonObject();

        request.addProperty("request", "ADD");
        request.addProperty("user", streamer);
        request.addProperty("platform", platform);

        this.socket.send(request.toString());
    }

    public void remove(String streamer) {
        this.remove(streamer, UserPlatform.CAFFEINE.name());
    }

    public void remove(String streamer, String platform) {
        JsonObject request = new JsonObject();

        request.addProperty("request", "REMOVE");
        request.addProperty("user", streamer);
        request.addProperty("platform", platform);

        this.socket.send(request.toString());
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
                JsonObject packet = gson.fromJson(raw, JsonObject.class);

                switch (packet.get("type").getAsString()) {
                    case "KEEP_ALIVE":
                        this.keepAlive();
                        return;

                    case "SERVER":
                        // System.out.println(raw);
                        return;

                    case "EVENT":
                        JsonObject eventJson = packet.getAsJsonObject("event");
                        Event event = EventType.get(eventJson);

                        Util.reflectInvoke(listener, event);

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
