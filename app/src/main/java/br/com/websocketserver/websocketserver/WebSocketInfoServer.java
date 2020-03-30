package br.com.websocketserver.websocketserver;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;


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
            fragment.publish("Ocorreu um erro de comunicação com " + conn.getRemoteSocketAddress()  + ":" + ex);
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
        //conn.send("Welcome to the server!"); //This method sends a message to the new client
        //broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        fragment.publish(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " Entrou no canal!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        fragment.publish(conn + " saiu do canal!");
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        fragment.publish(conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        fragment.publish("Uma mensagem foi recebida de "	+ conn.getRemoteSocketAddress() + ": " + message);

        conn.send(message);
    }

    public interface PublishFragment {
        void publish(String var);
    }
}
