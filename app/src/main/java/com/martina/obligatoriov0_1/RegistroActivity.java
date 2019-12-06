package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosRegistro;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button enviar = findViewById(R.id.enviar);
        TextView textView1 = findViewById(R.id.lblNombre);
        TextView textView2 = findViewById(R.id.lblApellido);
        TextView textView3 = findViewById(R.id.lblEmail);
        TextView textView4 = findViewById(R.id.lblContraseña);
        editText1 = findViewById(R.id.txtNombre);
        editText2 = findViewById(R.id.txtApellido);
        editText3 = findViewById(R.id.txtEmail);
        editText4 = findViewById(R.id.txtContraseña);

        enviar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                MetodosRegistro.registrar(RegistroActivity.this, editText1,editText2,editText3,editText4);


            }




        }
        );
    }
}