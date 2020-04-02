package br.com.websocketserver.websocketclient;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;

class WebSocketInfoClient extends WebSocketClient {

    WebSocketInfoClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("WebSocketClientTRIAD:", "Connected");
    }

    @Override
    public void onMessage(String message) {
        Log.i("WebSocketClientTRIAD:", "Servidor: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("WebSocketClientTRIAD:", "Disconnected: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        Log.i("WebSocketClientTRIAD:", "Error: " + ex.getMessage());
    }
}