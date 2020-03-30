package br.com.websocketserver.websocketclient;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketSSLClient {

    private static WebSocketInfoClient chatclient;

    public static void createConnection() {
        try {
            chatclient = new WebSocketInfoClient(new URI("ws://localhost:2234"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatclient.connect();
    }

    public static void closeSocket() {
        try {
            chatclient.closeBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void reconnectSocket() {
        chatclient.reconnect();
    }

    public static void sendMessageSocket(String text) {
        chatclient.send(text);
    }
}
