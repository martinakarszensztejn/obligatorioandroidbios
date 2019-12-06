package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.broadcastReceivers.LoginBroadcastReceiver;
import com.martina.obligatoriov0_1.metodos.MetodosLogin;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_Email;
    private EditText editText_Contraseña;

    private LoginBroadcastReceiver MBRec = new LoginBroadcastReceiver();
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LocalBroadcastManager.getInstance(this).registerReceiver(MBRec, new IntentFilter(
                Constantes.BROADCAST_SESION_INICIADA_CON_EXITO));
        Button button = findViewById(R.id.btnEnviarLogin);
        TextView textView_Titulo = findViewById(R.id.lblTituloLogin);
        TextView textView_Email = findViewById(R.id.lblEmailLogin);
        TextView textView_Contraseña = findViewById(R.id.lblContraseñaLogin);
        editText_Email = findViewById(R.id.txtEmailLogin);
        editText_Contraseña = findViewById(R.id.txtContraseñaLogin);
        aSwitch=findViewById(R.id.switch1);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MetodosLogin.iniciarSesion(LoginActivity.this, editText_Email, editText_Contraseña, aSwitch);


            }
        });


    }
}
