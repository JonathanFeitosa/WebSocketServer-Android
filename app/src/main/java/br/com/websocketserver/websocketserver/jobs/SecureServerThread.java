package br.com.websocketserver.websocketserver.jobs;
import java.io.IOException;

import br.com.websocketserver.websocketserver.WebSocketInfoServer;

public class SecureServerThread extends Thread {
    private static final int SOCKETSERVER_PORT = 2234;
    private static final String SOCKETSERVER_ANDRESS = "localhost";
    private static WebSocketInfoServer s = null;

    private static WebSocketInfoServer.PublishFragment fragment;

    SecureServerThread(WebSocketInfoServer.PublishFragment fragment) {
        SecureServerThread.fragment = fragment;
    }

    @Override
    public void run() {
        s = new WebSocketInfoServer(SOCKETSERVER_ANDRESS, SOCKETSERVER_PORT, fragment);
        fragment.publish("Server iniciado na porta " + SOCKETSERVER_ANDRESS + ":"  +SOCKETSERVER_PORT);
        s.run();
    }

    public static void stopThread() {
        try {
            s.stop();
            currentThread().interrupt();
            fragment.publish("Server finalizado: " + SOCKETSERVER_ANDRESS + ":"  +SOCKETSERVER_PORT);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
