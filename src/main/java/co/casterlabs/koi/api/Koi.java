package co.casterlabs.koi.api;

import java.io.Closeable;
import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import co.casterlabs.koi.api.events.Event;
import co.casterlabs.koi.api.events.EventType;
import co.casterlabs.koi.api.user.UserPlatform;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class Koi implements Closeable {
    public static final String VERSION = "2.3.0";

    private static final @Getter String koiUrl = "wss://api.casterlabs.co/v2/koi";
    private static @Getter Gson gson = new Gson();

    private EventListener listener;
    private FastLogger logger;
    private KoiSocket socket;

    private JsonObject request;

    public Koi(@NonNull EventListener listener, String clientId) {
        this(koiUrl, new FastLogger(), listener, clientId);
    }

    @SneakyThrows
    public Koi(@NonNull String url, @NonNull FastLogger logger, @NonNull EventListener listener, String clientId) {
        this.logger = logger;
        this.listener = listener;
        this.socket = new KoiSocket(new URI(url + "?client_id=" + clientId));
    }

    @Override
    public void close() {
        this.socket.close();
    }

    public boolean isConnected() {
        return this.socket.isOpen();
    }

    public Koi hookStreamStatus(String username, UserPlatform platform) throws InterruptedException {
        if (this.isConnected()) {
            throw new IllegalStateException("Already connected.");
        } else {
            this.request = new JsonObject();

            this.request.addProperty("type", "USER_STREAM_STATUS");
            this.request.addProperty("username", username);
            this.request.addProperty("platform", platform.name());
            this.request.addProperty("nonce", "_login");

            this.socket.connectBlocking();

            return this;
        }
    }

    public Koi login(String token) throws InterruptedException {
        if (this.isConnected()) {
            throw new IllegalStateException("Already connected.");
        } else {
            this.request = new JsonObject();

            this.request.addProperty("type", "LOGIN");
            this.request.addProperty("token", token);
            this.request.addProperty("nonce", "_login");

            this.socket.connectBlocking();

            return this;
        }
    }

    private class KoiSocket extends WebSocketClient {

        public KoiSocket(URI uri) {
            super(uri);

            this.addHeader("User-Agent", "Koi-Api");
            this.setTcpNoDelay(true);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            if (request == null) {
                this.close();
            } else {
                this.send(request.toString());
                request = null;
            }
        }

        @Override
        public void send(String text) {
            logger.debug("\u2191 " + text);

            super.send(text);
        }

        private void keepAlive(JsonElement nonce) {
            JsonObject request = new JsonObject();

            request.addProperty("type", "KEEP_ALIVE");
            request.add("nonce", nonce);

            this.send(request.toString());
        }

        @Override
        public void onMessage(String raw) {
            logger.debug("\u2193 " + raw);

            try {
                JsonObject packet = gson.fromJson(raw, JsonObject.class);

                switch (packet.get("type").getAsString()) {
                    case "KEEP_ALIVE":
                        this.keepAlive(packet.get("nonce"));
                        return;

                    case "SERVER":
                        listener.onServerMessage(packet.get("server").getAsString());
                        return;

                    case "EVENT":
                        JsonObject eventJson = packet.getAsJsonObject("event");
                        Event event = EventType.get(eventJson);

                        Util.reflectInvoke(listener, event);

                        return;
                }
            } catch (Exception e) {
                listener.onException(e);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            if (listener == null) {
                if (remote) {
                    logger.info("Lost connection to Koi.");
                }
            } else {
                // So the user can immediately reconnect without
                // errors from the underlying library.
                new Thread(() -> listener.onClose(remote)).start();
            }
        }

        @Override
        public void onError(Exception e) {
            listener.onException(e);
        }

    }

}
