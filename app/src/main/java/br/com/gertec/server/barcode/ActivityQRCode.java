package br.com.gertec.server.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.net.InetSocketAddress;

import br.com.gertec.server.R;
import br.com.gertec.server.jobs.SecureServerThread;

public class ActivityQRCode extends AppCompatActivity {

    private InetSocketAddress infoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        infoUser = (InetSocketAddress) getIntent().getSerializableExtra("infoUser");

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES); //passa o tipo de código que se quer na leitura
        integrator.setPrompt("Camera Scan"); //texto que aparecerá na tela do scaner
        integrator.setCameraId(0); //câmera utilizada no scan. Como é 0, a câmera é a traseira. Se fosse valor 1, seria a câmera frontal
        integrator.initiateScan();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                Log.e("WebSocketITRIAD-QRCode", "InfoCode: " + result.getContents() + " (" +  result.getFormatName() + ")"); // Prints scan results
                SecureServerThread.sendMessageClient(infoUser, "InfoCode: " + result.getContents() + " (" +  result.getFormatName() + ")");
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
