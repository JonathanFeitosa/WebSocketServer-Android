package br.com.websocketserver.websocketserver.jobs;

import br.com.websocketserver.websocketserver.SecureServer;

public class SecureServerThread extends Thread {
    private static final int SOCKETSERVER_PORT = 2234;
    private static final String SOCKETSERVER_ANDRESS = "localhost";


    SecureServer.PublishFragment fragment;
    public SecureServerThread(SecureServer.PublishFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void run() {
        SecureServer s = new SecureServer(SOCKETSERVER_ANDRESS, SOCKETSERVER_PORT, fragment);
       // SSLContext sslContext = SecureServer.getSSLContextFromAndroidKeystore();
     //   s.setWebSocketFactory(new DefaultSSLWebSocketServerFactory( sslContext ));
        fragment.publish("Server initiated at port " + SOCKETSERVER_PORT);
        s.run();
    }


}
