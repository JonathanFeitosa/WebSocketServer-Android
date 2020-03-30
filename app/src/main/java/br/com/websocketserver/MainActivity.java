package br.com.websocketserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import br.com.websocketserver.websocketserver.jobs.StartService;
import br.com.websocketserver.websocketclient.SSLClientExample;

public class MainActivity extends AppCompatActivity {

    Button serverOn, clientOn, clientReconnect, clientClose, clientSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverOn = (Button) findViewById(R.id.serverOn);
        clientOn = (Button) findViewById(R.id.clientConnect);
        clientReconnect = (Button) findViewById(R.id.clientReconnect);
        clientClose = (Button) findViewById(R.id.clientClose);
        clientSend = (Button) findViewById(R.id.clientSend);

        serverOn.setOnClickListener(v -> {
            Intent startServiceIntent = new Intent(MainActivity.this, StartService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(startServiceIntent);
            } else {
                this.startService(startServiceIntent);
            }
        });

        clientOn.setOnClickListener(v -> {
            SSLClientExample.createConnection();;

        });

        clientReconnect.setOnClickListener(v -> {
            SSLClientExample.reconnectSocket();;

        });

        clientClose.setOnClickListener(v -> {
            SSLClientExample.closeSocket();;

        });

        clientSend.setOnClickListener(v -> {
            SSLClientExample.sendMessageSocket("Teste");;
        });
    }
}
