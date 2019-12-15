package com.martina.obligatoriov0_1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationProvider;
import android.os.Bundle;


import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosUpdate;
import com.martina.obligatoriov0_1.servicios.ClientLocationService;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Transportation_status_update_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_status_update_activity);
        TextInputLayout layout_vehiculo_marca = findViewById(R.id.layout_vehiculo_marca);
        TextInputLayout layout_vehiculo_modelo = findViewById(R.id.layout_vehiculo_modelo);
        TextInputLayout layout_vehiculo_chofer = findViewById(R.id.layout_vehiculo_chofer);
        TextInputLayout layout_vehiculo_matricula = findViewById(R.id.layout_vehiculo_matricula);
        final TextInputEditText txt_vehiculo_marca = findViewById(R.id.txt_vehiculo_marca);
        final TextInputEditText txt_vehiculo_modelo = findViewById(R.id.txt_vehiculo_modelo);
        final TextInputEditText txt_vehiculo_chofer = findViewById(R.id.txt_vehiculo_chofer);
        final TextInputEditText txt_vehiculo_matricula = findViewById(R.id.txt_vehiculo_matricula);
        TextInputLayout layout_recepcion_nombre_receptor = findViewById(R.id.layout_recepcion_nombre_receptor);
        TextInputLayout layout_recepcion_observaciones = findViewById(R.id.layout_recepcion_observacion);
        TextInputLayout layout_recepcion_fecha = findViewById(R.id.layout_recepcion_fecha);
        final TextInputEditText txt_recepcion_nombre_receptor = findViewById(R.id.txt_recepcion_nombre_receptor);
        final TextInputEditText txt_recepcion_observacion = findViewById(R.id.txt_recepcion_observacion);
        final TextInputEditText txt_recepcion_fecha = findViewById(R.id.txt_recepcion_fecha);

        final Button button = findViewById(R.id.boton_lindo);
        TextView tv = findViewById(R.id.texto_lindo);
        final String current_Status = getIntent().getStringExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS);
        tv.append(" "+current_Status);
        final int id = getIntent().getIntExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID,0);
        JSONObject postBody=null;

        if (current_Status != null) {
            if(current_Status.equals(Constantes.ESTADO_1)){


                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_2);
                layout_vehiculo_marca.setVisibility(View.VISIBLE);
                layout_vehiculo_modelo.setVisibility(View.VISIBLE);
                layout_vehiculo_chofer.setVisibility(View.VISIBLE);
                layout_vehiculo_matricula.setVisibility(View.VISIBLE);
                txt_vehiculo_chofer.setVisibility(View.VISIBLE);
                txt_vehiculo_modelo.setVisibility(View.VISIBLE);
                txt_vehiculo_marca.setVisibility(View.VISIBLE);
                txt_vehiculo_matricula.setVisibility(View.VISIBLE);


            }else if(current_Status.equals(Constantes.ESTADO_2)){

                final Context contexto = Transportation_status_update_activity.this;



                Intent intent = new Intent(contexto,ClientLocationService.class);
                intent.putExtra(Constantes.INTENT_LOCATION_ID_TRANSPORTATION,id);
                startService(intent);




                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_3);


            }else if(current_Status.equals(Constantes.ESTADO_3)){
                Intent intent = new Intent(this, ClientLocationService.class);
                stopService(intent);
                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_4);
            }else if(current_Status.equals(Constantes.ESTADO_4)){
                final Context contexto = Transportation_status_update_activity.this;
                Intent intent = new Intent(contexto,ClientLocationService.class);
                intent.putExtra(Constantes.INTENT_LOCATION_ID_TRANSPORTATION,id);
                startService(intent);
                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_5);
            }else if(current_Status.equals(Constantes.ESTADO_5)){
                Intent intent = new Intent(this, ClientLocationService.class);

                layout_recepcion_fecha.setVisibility(View.VISIBLE);
                layout_recepcion_nombre_receptor.setVisibility(View.VISIBLE);
                layout_recepcion_observaciones.setVisibility(View.VISIBLE);

                txt_recepcion_fecha.setVisibility(View.VISIBLE);
                txt_recepcion_nombre_receptor.setVisibility(View.VISIBLE);
                txt_recepcion_observacion.setVisibility(View.VISIBLE);


                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_6);
            }else if(current_Status.equals(Constantes.ESTADO_6)){
                button.setText(getResources().getString(R.string.pedido_finalizado));
                button.setClickable(false);
                button.setEnabled(false);
            }


        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMyServiceRunning(ClientLocationService.class)){
                    Intent intent = new Intent(Transportation_status_update_activity.this,ClientLocationService.class);
                    stopService(intent);
                    ClientLocationService.timer.cancel();
                }
                List<String> datos_extra = new ArrayList<>();
                if (current_Status != null) {
                    if(current_Status.equals(Constantes.ESTADO_1)){
                        boolean[] allCorrect = new boolean[4];
                        for (int i = 0; i < datos_extra.size(); i++) {
                            datos_extra.remove(i);
                        }
                        if(txt_vehiculo_marca.getText().toString().equals("")){
                            txt_vehiculo_marca.setError(getResources().getString(R.string.campovacio));
                            allCorrect[0]=false;
                        }else{
                            txt_vehiculo_marca.setError(null);
                            allCorrect[0]=true;
                        }
                        if(txt_vehiculo_matricula.getText().toString().equals("")){
                            txt_vehiculo_matricula.setError(getResources().getString(R.string.campovacio));
                            allCorrect[1]=false;
                        }else{
                            txt_vehiculo_matricula.setError(null);
                            allCorrect[1]=true;
                        }
                        if(txt_vehiculo_modelo.getText().toString().equals("")){
                            txt_vehiculo_modelo.setError(getResources().getString(R.string.campovacio));
                            allCorrect[2]=false;
                        }else{
                            txt_vehiculo_modelo.setError(null);
                            allCorrect[2]=true;
                        }
                        if(txt_vehiculo_chofer.getText().toString().equals("")){
                            txt_vehiculo_chofer.setError(getResources().getString(R.string.campovacio));
                            allCorrect[3]=false;
                        }else{
                            txt_vehiculo_chofer.setError(null);
                            allCorrect[3]=true;
                        }

                        if(allCorrect[0]&&allCorrect[1]&&allCorrect[2]&&allCorrect[3]){
                            String marca = txt_vehiculo_marca.getText().toString();
                            String modelo = txt_vehiculo_modelo.getText().toString();
                            String matricula = txt_vehiculo_matricula.getText().toString();
                            String chofer = txt_vehiculo_chofer.getText().toString();
                            datos_extra.add(marca);
                            datos_extra.add(modelo);
                            datos_extra.add(matricula);
                            datos_extra.add(chofer);

                        }

                    }else if(current_Status.equals(Constantes.ESTADO_5)){
                        boolean[] allCorrect = new boolean[3];
                        for (int i = 0; i < datos_extra.size(); i++) {
                            datos_extra.remove(i);
                        }
                        if(txt_recepcion_fecha.getText().toString().equals("")){
                            txt_recepcion_fecha.setError(getResources().getString(R.string.campovacio));
                            allCorrect[0]=false;
                        }else{
                            txt_recepcion_fecha.setError("");
                            allCorrect[0]=true;
                            //TODO: TRABAJAR CON OBJETO DATE Y FORMATEAR LA FECHA CORRECTAMENTE.
                        }
                        if(txt_recepcion_nombre_receptor.getText().toString().equals("")){
                            txt_recepcion_nombre_receptor.setError(getResources().getString(R.string.campovacio));
                            allCorrect[1]=false;
                        }else{
                            txt_recepcion_nombre_receptor.setError(null);
                            allCorrect[1]=true;
                        }
                        if(txt_recepcion_observacion.getText().toString().equals("")){
                            txt_recepcion_observacion.setError(getResources().getString(R.string.campovacio));
                            allCorrect[2]=false;
                        }else{
                            txt_recepcion_observacion.setError(null);
                            allCorrect[2]=true;
                        }
                        if(allCorrect[0]&&allCorrect[1]&&allCorrect[2]){
                            String nombre_receptor = txt_recepcion_nombre_receptor.getText().toString();
                            String observaciones = txt_recepcion_observacion.getText().toString();
                            String fecha = txt_recepcion_fecha.getText().toString();
                            datos_extra.add(nombre_receptor);
                            datos_extra.add(observaciones);
                            datos_extra.add(fecha);


                        }


                    }else{
                        datos_extra=null;
                    }


                    JSONObject myBody = MetodosUpdate.makeBody(current_Status,datos_extra);

                    MetodosUpdate.updateTransportation(Transportation_status_update_activity.this,current_Status,id,myBody);

                }


            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

