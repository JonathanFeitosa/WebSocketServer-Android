package br.com.websocketserver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.Result;
import br.com.websocketserver.websocketclient.WebSocketSSLClient;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ActivityQRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Button serverOff;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        serverOff = findViewById(R.id.serverOff);
        mScannerView = findViewById(R.id.QrCodeID);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();        // Start camera
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera(); // verificar se é nulo
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.e("WebSocketITRIAD-QRCode", rawResult.getText()); // Prints scan results
        Log.e("WebSocketITRIAD-QRCode", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        mScannerView.stopCamera();

        WebSocketSSLClient.sendMessageSocket("InfoCode: " + rawResult.getText() + " (" + rawResult.getBarcodeFormat().toString() + ")");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
