package br.com.gertec.server.socket;

import android.content.Context;
import android.content.Intent;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import br.com.gertec.server.barcode.ActivityQRCode;

public class WebSocketInfoServer extends WebSocketServer {

    private final PublishFragment fragment;

    public WebSocketInfoServer(String host, int port, PublishFragment fragment) {
        super(new InetSocketAddress(host, port));
        this.fragment = fragment;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            fragment.publish("Ocorreu um erro de comunicação com " + conn.getRemoteSocketAddress() + ":" + ex);
        }
    }

    @Override
    public void onStart() {
        fragment.publish("Server started!");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(300);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        fragment.publish(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " Entrou no canal!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        fragment.publish(conn.getLocalSocketAddress() + " saiu do canal!");
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        fragment.publish(conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        fragment.publish("Uma mensagem foi recebida de " + conn.getRemoteSocketAddress() + ": " + message);
        conn.send("Sua mensagem " + message + " foi recebida com sucesso!");
        messageReceived(conn.getRemoteSocketAddress(), WebSocketMessages.setValue(message));
    }

    public interface PublishFragment {
        void publish(String var);

        Context getContext();
    }

    private void messageReceived(InetSocketAddress conn, WebSocketMessages msg) {

        switch (msg) {
            case BARCODE: {
                fragment.getContext().startActivity(
                        new Intent(fragment.getContext(), ActivityQRCode.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("infoUser", conn));
            }
            case PRINTER: {

            }
            case UNDEFINED: {

            }
        }
    }
}
