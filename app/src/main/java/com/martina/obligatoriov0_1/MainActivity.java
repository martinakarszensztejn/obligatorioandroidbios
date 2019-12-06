package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.martina.obligatoriov0_1.constantes.Constantes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = MainActivity.this.getSharedPreferences( Constantes.SHARED_PREFERENCES_NAME, MainActivity.this.MODE_PRIVATE);
        String email_Usuario=preferences.getString(Constantes.EMAIL_SESION_INICIADA,null);

        if(email_Usuario!=null){
            Intent intent= new Intent(this, HubActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constantes.EMAIL_EXTRA_INTENT,email_Usuario);
            startActivity(intent);


        }else {


            Button registrarse = findViewById(R.id.registrarse);
            Button iniciarSesion = findViewById(R.id.iniciarSesion);
            TextView tv = findViewById(R.id.lblEmail);

            registrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent irARegistroIntent = new Intent(MainActivity.this, RegistroActivity.class);
                    irARegistroIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(irARegistroIntent);
                }
            });
            iniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent irAIS = new Intent(MainActivity.this, LoginActivity.class);
                    irAIS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(irAIS);
                }
            });

        }

    }




    }

