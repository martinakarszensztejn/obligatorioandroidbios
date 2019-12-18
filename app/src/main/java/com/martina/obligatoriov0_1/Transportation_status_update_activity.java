package com.martina.obligatoriov0_1;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import android.app.ActivityManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.martina.obligatoriov0_1.broadcastReceivers.LocationBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosDetalle;
import com.martina.obligatoriov0_1.metodos.MetodosUpdate;
import com.martina.obligatoriov0_1.servicios.ClientLocationService;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Transportation_status_update_activity extends AppCompatActivity {
    private static JSONObject myBody = new JSONObject();
    private static String miEstado = null;
    private static int miId = 0;
    private static LocationBroadcastReceiver myBRec = new LocationBroadcastReceiver();
    

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.alerta);
        builder.setMessage(R.string.wishtogoback)
                .setCancelable(true);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Transportation_status_update_activity.this,DetalleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID_NEW,getIntent().getIntExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID,0));
                startActivity(intent);




            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(Constantes.INFORMACION,"Presionó cancelar");

            }
        });



        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

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



        ImageButton backButton = findViewById(R.id.backButton);

        final Button button = findViewById(R.id.boton_lindo);
        TextView tv = findViewById(R.id.texto_lindo);
        String current_Status_1 = null;
        int id_1 = 0;
        if (getIntent().getBundleExtra(Constantes.JSON_BODY_EXTRA_EXTRA) != null) {
            Bundle bundle = getIntent().getBundleExtra(Constantes.JSON_BODY_EXTRA_EXTRA);
            boolean empty = bundle.isEmpty();
            id_1 =bundle.getInt(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID);
            current_Status_1 = bundle.getString(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS);
            tv.append(" "+current_Status_1);
            try {
                JSONObject jsonObject = new JSONObject(bundle.getString(Constantes.JSON_BODY_EXTRA));
                MetodosUpdate.updateTransportation(this,current_Status_1,id_1,jsonObject);
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "onCreate: Error en el json body",e);
            }

        }else{
            current_Status_1 = getIntent().getStringExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS);
            tv.append(" "+current_Status_1);
            id_1 = getIntent().getIntExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID,0);

        }
        final String current_Status = current_Status_1;
        final int id = id_1;



        LocalBroadcastManager.getInstance(this).registerReceiver(myBRec,new IntentFilter(Constantes.FILTRO_INTENT_LOCATION));
        JSONObject postBody=null;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME,MODE_PRIVATE);
                boolean isConnectionAvailable = sharedPreferences.getBoolean(Constantes.CONEXION, false);
                if(isConnectionAvailable){
                    MetodosDetalle.getDetailedTransportation(Transportation_status_update_activity.this,id);
                }else{
                    Toast.makeText(Transportation_status_update_activity.this,getResources().getString(R.string.nohayconexion),Toast.LENGTH_LONG).show();
                }


            }
        });

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


                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_4);
            }else if(current_Status.equals(Constantes.ESTADO_4)){
                final Context contexto = Transportation_status_update_activity.this;
                Intent intent = new Intent(contexto,ClientLocationService.class);
                intent.putExtra(Constantes.INTENT_LOCATION_ID_TRANSPORTATION,id);
                startService(intent);

                button.setText(getResources().getString(R.string.boton_update)+" "+Constantes.ESTADO_5);
            }else if(current_Status.equals(Constantes.ESTADO_5)){

                Intent intent = new Intent(this, ClientLocationService.class);
                Date time = Calendar.getInstance().getTime();
                String fechaParaMostrar = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);

                txt_recepcion_fecha.setText(getResources().getString(R.string.horaactual)+" "+fechaParaMostrar);
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
                boolean isDateCorrect=true;
                if(isMyServiceRunning(ClientLocationService.class)){
                    Intent intent = new Intent(Transportation_status_update_activity.this,ClientLocationService.class);
                    stopService(intent);

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
                        Date time = Calendar.getInstance().getTime();
                        String formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);

                        allCorrect[0]=true;



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

                            datos_extra.add(nombre_receptor);
                            datos_extra.add(observaciones);
                            datos_extra.add(formatedDate);


                        }


                    }else{
                        datos_extra=null;
                    }

                    
                       
                        
                        

                    Bundle bundle1 = getIntent().getExtras();

                    MetodosUpdate.makeBody(bundle1,current_Status,datos_extra,Transportation_status_update_activity.this);
                    miEstado=current_Status;
                    miId=id;
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


