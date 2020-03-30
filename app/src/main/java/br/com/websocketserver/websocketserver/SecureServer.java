package br.com.websocketserver.websocketserver;

import android.content.Context;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * A secure TLS WEBSocketServer implementation.
 */
public class SecureServer extends WebSocketServer {
  //  private static Context context;
    private final PublishFragment fragment;

    public SecureServer(String host, int port, PublishFragment fragment) {
        super(new InetSocketAddress(host, port)); // Create WildcardAddress:Port
        this.fragment = fragment;
        init(fragment.getContext());
    }

    /**
     * Permitir apenas criptografias específicas
     */
//    public static SSLContext getSSLContextFromAndroidKeystore() {
//        // load up the key store
//        String storePassword = "gertec2020";
//        String keyPassword = "gertec2020";
//        String keyStore  =  " keystore.jks" ;
//        String STORETYPE = "BKS";
//
//        KeyStore ks;
//        SSLContext sslContext;
//        try {
//            ks = KeyStore.getInstance(STORETYPE);
//            File kf = new File(keyStore);
//
//            // CertificateException | IOException
//            ks.load(new FileInputStream(kf), storePassword.toCharArray());
//
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
//            keyManagerFactory.init(ks, keyPassword.toCharArray());
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//            tmf.init(ks);
//
//            sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(keyManagerFactory.getKeyManagers(), tmf.getTrustManagers(), null);
//
//        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | KeyManagementException | UnrecoverableKeyException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException();
//        }
//        return sslContext;
//    }

    private void init(final Context context) {
////        GEDI.init(context);
////        pinpad = new GertecPinpad(context);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//             //   iGedi = GEDI.getInstance(context);
//            }
//        }).start();
     //   SecureServer.context = context;
//        try {
//            Thread.sleep(250);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
            fragment.publish("an error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
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
        fragment.publish(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        fragment.publish(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        //fragment.publish(conn + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        //fragment.publish(conn + ": " + message);
        fragment.publish("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
        conn.send(message);
 //       GertecWrapper wrapper = GertecWrapper.getInstance(context);
//        try {
//            command = new JSONObject(message);
//            String stringCommand = command.getString(SERVER_MESSAGE_STRUCT.COMMAND);
//            switch (stringCommand) {
//                case RESETPINPAD:
//                    resposta = wrapper.commandResetPin(command);
//                    break;
//                case GETKEY:
//                    resposta = wrapper.commandGetKey(command);
//                    break;
//                case INITEMV:
//                    resposta = wrapper.commandInitEMV(command);
//                    break;
//                case CHECKEMV:
//                    resposta = wrapper.commandCheckEMV(command);
//                    break;
//                case REMOVEEMV:
//                    resposta = wrapper.commandRemoveEMV(command);
//                    break;
//                case GETSERIALPINPAD:
//                    resposta = wrapper.commandGetSerialPinpad(command);
//                    break;
//                case SHOWMESSAGE:
//                    resposta = wrapper.commandShowMessage(command);
//                    break;
//                case PRINTLINES:
//                    resposta = wrapper.commandPrintLines(command);
//                    break;
//                case OPENPIN:
//                    resposta = wrapper.commandOpenPin(command);
//                    break;
//                case CLOSEMESSAGE:
//                    resposta = wrapper.commandCloseMessage(command);
//                    break;
//                case READCARD:
//                    resposta = wrapper.commandReadCard(command);
//                    break;
//                case GETPIN:
//                    resposta = wrapper.commandGetPin(command);
//                    break;
//                case GETPINDUKPT:
//                    resposta = wrapper.commandGetPinDUKPT(command);
//                    break;
//                case GETPINIDENTPOS:
//                    resposta = wrapper.commandGetPinIndentPos(command);
//                    break;
//                case GETPINIDENTPOSDUKPT:
//                    resposta = wrapper.commandGetPinIndentPosDUKPT(command);
//                    break;
//                default:
//                    resposta = wrapper.commandNotFound(command);
//                    break;
//            }
//        } catch (JSONException e) {
//            fragment.publish(e.getMessage());
//            resposta = wrapper.sintaxErrorAnswer(command);
//        } catch (GediException e) {
//            fragment.publish(e.getMessage());
//            resposta = wrapper.internalLibErrorAnswer(command);
//        } catch (Exception e) {
//            fragment.publish(e.getMessage());
//            resposta = wrapper.internalLibErrorAnswer(command);
//        }
    }

    public interface PublishFragment {
        void publish(String var);
        Context getContext();
    }
}
