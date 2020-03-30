package br.com.websocketserver.websocketclient;

import java.net.URI;
import java.net.URISyntaxException;

public class SSLClientExample {

    private static WebSocketChatClient chatclient;

    /*
     * Keystore with certificate created like so (in JKS format):
     *
     *keytool -genkey -keyalg RSA -validity 3650 -keystore "keystore.jks" -storepass "storepassword" -keypass "keypassword" -alias "default" -dname "CN=127.0.0.1, OU=MyOrgUnit, O=MyOrg, L=MyCity, S=MyRegion, C=MyCountry"
     */
    public static void createConnection() {
        try {
            chatclient = new WebSocketChatClient(new URI("ws://localhost:2234"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        chatclient.connect();
//
//        // load up the key store
//        String STORETYPE = "JKS";
//        String KEYSTORE = "keystore.jks";
//        String STOREPASSWORD = "gertec2020";
//        String KEYPASSWORD = "gertec2020";
//        try {
//
//            KeyStore ks = KeyStore.getInstance(STORETYPE);
//            File kf = new File(KEYSTORE);
//            ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());
//
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//            kmf.init(ks, KEYPASSWORD.toCharArray());
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//            tmf.init(ks);
//
//            SSLContext sslContext;
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//            // sslContext.init( null, null, null ); // will use java's default key and trust store which is sufficient unless you deal with self-signed certificates
//
//            SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();
//
//            if (chatclient != null) {
//                chatclient.setSocketFactory(factory);
//                chatclient.connectBlocking();
//            }
//        } catch (KeyStoreException | CertificateException | KeyManagementException | NoSuchAlgorithmException | UnrecoverableKeyException | InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
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
