package br.com.gertec.server.jobs;

import android.os.Handler;
import org.java_websocket.WebSocket;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import br.com.gertec.server.Utils;
import br.com.gertec.server.socket.WebSocketInfoServer;

public class SecureServerThread extends Thread {

    public static WebSocketInfoServer s = null;
    private static WebSocketInfoServer.PublishFragment fragment;
    private static final Handler mHandler = new Handler();


    SecureServerThread(WebSocketInfoServer.PublishFragment fragment) {
        SecureServerThread.fragment = fragment;
    }

    @Override
    public void run() {
        s = new WebSocketInfoServer(Utils.SOCKETSERVER_ANDRESS, Utils.SOCKETSERVER_PORT, fragment);
        s.setReuseAddr(true);
        fragment.publish("Server iniciado na porta " + Utils.SOCKETSERVER_ANDRESS + ":" + Utils.SOCKETSERVER_PORT);
        s.run();
    }

    public static void stopThread() {
        try {
            s.stop();
            currentThread().interrupt();
            fragment.publish("Server finalizado: " + Utils.SOCKETSERVER_ANDRESS + ":" + Utils.SOCKETSERVER_PORT);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageClient(InetSocketAddress infoUser, String send) {
        Collection<WebSocket> users = s.getConnections();
        for (WebSocket c : users) {
            mHandler.post(() -> {
                if (c.getRemoteSocketAddress().equals(infoUser))
                    c.send(send);
            });
        }
    }
}
