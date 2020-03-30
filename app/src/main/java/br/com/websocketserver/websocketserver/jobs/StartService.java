package br.com.websocketserver.websocketserver.jobs;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import br.com.websocketserver.websocketserver.WebSocketInfoServer;

public class StartService extends IntentService implements WebSocketInfoServer.PublishFragment {
    // TODO: Rename actions, choose action names that describe tasks that this
    private static final String TAG = "WebSocketServerTRIAD";

    public StartService() {
        super("WebSocketServerTRIAD");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            SecureServerThread serverThread = new SecureServerThread(this);
            serverThread.start();
            publish("Service Started");
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String NOTIFICATION_CHANNEL_ID = "br.com.websocketserver";
            String channelName = "Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    //   .setSmallIcon(AppSpecific.SMALL_ICON)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
        }
        SecureServerThread serverThread = new SecureServerThread(this);
        serverThread.start();
        publish("Service Started");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publish("Service Destroyed");
    }

    @Override
    public void publish(String var) {
        Log.d(TAG, var);
    }
}
