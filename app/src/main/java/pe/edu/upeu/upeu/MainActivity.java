package pe.edu.upeu.upeu;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    Button btnEnviar, btnEnviar2;
    EditText txtNumero, txtMensaje;
    String phoneNo;
    String message;
    String [] datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bu=getIntent().getExtras();
        String usurio=bu.getString("valorUser");
        btnEnviar=(Button)findViewById(R.id.button2);
        btnEnviar2=(Button)findViewById(R.id.button3);

        txtNumero=(EditText)findViewById(R.id.editText3);
        txtMensaje=(EditText)findViewById(R.id.editText5);

        Log.i("Informacion",usurio);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        btnEnviar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });

    }


    protected void sendSMSMessage() {
        phoneNo = txtNumero.getText().toString();
        message = txtMensaje.getText().toString();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();

                    smsManager.sendTextMessage(phoneNo, null, message, null, null);

                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }


    public void sendSMS(){
        Intent smsInte=new Intent(Intent.ACTION_VIEW);
        smsInte.setData(Uri.parse("smsto:"));
        smsInte.setType("vnd.android-dir/mms-sms");
        smsInte.putExtra("address",new String(String.valueOf(txtNumero.getText())));
        smsInte.putExtra("sms_body", String.valueOf(txtMensaje.getText()));
        try {
            startActivity(smsInte);
            Log.i("Informacion", "Mensaje Enviado");
            //finish();
            Log.i("Informacion", "Mensaje Enviado");
        }catch (ActivityNotFoundException ex){
            Log.i("Informacion", "Mensaje Enviado "+ex.getMessage());
            Toast.makeText(MainActivity.this, "Error al enviar mensaje", Toast.LENGTH_SHORT).show();
        }

    }


}
