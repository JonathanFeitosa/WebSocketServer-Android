package br.com.websocketserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import br.com.websocketserver.websocketserver.jobs.SecureServerThread;
import br.com.websocketserver.websocketserver.jobs.StartService;
import br.com.websocketserver.websocketclient.WebSocketSSLClient;

public class MainActivity extends AppCompatActivity {

    Button serverOn, serverOff, clientOn, clientReconnect, clientClose, clientSend;

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
    }
}
