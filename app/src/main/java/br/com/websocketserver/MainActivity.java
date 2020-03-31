package br.com.websocketserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.websocketserver.websocketserver.jobs.SecureServerThread;
import br.com.websocketserver.websocketserver.jobs.StartService;
import br.com.websocketserver.websocketclient.WebSocketSSLClient;

public class MainActivity extends AppCompatActivity {

    Button serverOn, serverOff, clientOn, clientReconnect, clientClose, clientSend, clientFoto;
    private final int ZXING_CAMERA_PERMISSION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverOn = findViewById(R.id.serverOn);
        serverOff = findViewById(R.id.serverOff);

        clientOn = findViewById(R.id.clientConnect);
        clientReconnect = findViewById(R.id.clientReconnect);
        clientClose = findViewById(R.id.clientClose);
        clientSend = findViewById(R.id.clientSend);

        clientFoto = findViewById(R.id.clienttirarFoto);


        serverOn.setOnClickListener(v -> {
            Intent startServiceIntent = new Intent(MainActivity.this, StartService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(startServiceIntent);
            } else {
                this.startService(startServiceIntent);
            }
        });

        serverOff.setOnClickListener(v -> SecureServerThread.stopThread());

        clientOn.setOnClickListener(v -> WebSocketSSLClient.createConnection());

        clientReconnect.setOnClickListener(v -> WebSocketSSLClient.reconnectSocket());

        clientClose.setOnClickListener(v -> WebSocketSSLClient.closeSocket());

        clientSend.setOnClickListener(v -> WebSocketSSLClient.sendMessageSocket("Teste"));

        clientFoto.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
            } else {
                WebSocketSSLClient.sendMessageSocket("tirarFoto");
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ZXING_CAMERA_PERMISSION)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) WebSocketSSLClient.sendMessageSocket("tirarFoto");
    }
}
