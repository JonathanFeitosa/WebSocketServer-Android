package br.com.gertec.server;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import br.com.gertec.server.jobs.StartService;

public class MainActivity extends AppCompatActivity {

    Button serverOn, serverOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverOn = findViewById(R.id.serverOn);
        serverOff = findViewById(R.id.serverOff);

        Intent startServiceIntent = new Intent(MainActivity.this, StartService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(startServiceIntent);
        } else {
            this.startService(startServiceIntent);
        }

/*        serverOn.setOnClickListener(v -> {
            startServiceIntent = new Intent(MainActivity.this, StartService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(startServiceIntent);
            } else {
                this.startService(startServiceIntent);
            }
        });

        serverOff.setOnClickListener(v -> SecureServerThread.stopThread()); */

        finish(); // retirar caso queira utilizar layout

    }
}
