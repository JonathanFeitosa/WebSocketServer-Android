package br.com.websocketserver.websocketclient;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketSSLClient {

    private static WebSocketInfoClient chatclient;
    private static Boolean clientConnect;

    public static void createConnection() {
        try {
            chatclient = new WebSocketInfoClient(new URI("ws://localhost:2234"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatclient.connect();
        clientConnect = true;
    }

    public static void closeSocket() {
        try {
            chatclient.closeBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientConnect = false;

    }

    public static void reconnectSocket() {
        chatclient.reconnect();
        clientConnect = true;
    }

    public static void sendMessageSocket(String text) { if(clientConnect()) chatclient.send(text); }

    public static boolean clientConnect(){ return clientConnect; }
}
