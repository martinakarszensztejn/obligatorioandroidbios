package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.martina.obligatoriov0_1.broadcastReceivers.ConnectionBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.DetalleBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.NotificationBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosDetalle;
import com.martina.obligatoriov0_1.objetos.Transportation;
import com.martina.obligatoriov0_1.servicios.ClientLocationService;

import java.io.Serializable;

public class DetalleActivity extends AppCompatActivity {
    private ConnectionBroadcastReceiver mConnBRec = new ConnectionBroadcastReceiver();
    private NotificationBroadcastReceiver mNotBRec = new NotificationBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String estado_current = getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS);
        if(estado_current==null){
            finish();
        }else{


            setContentView(R.layout.activity_detalle);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            IntentFilter filtro = new IntentFilter();
            filtro.addAction(Constantes.INICIO_BROADCAST_ALARMA);

            registerReceiver(mConnBRec, filter);
            registerReceiver(mNotBRec, filtro);

            TextView lbl_detalle_id = findViewById(R.id.lbl_detalle_id);
            TextView lbl_detalle_destino_direccion = findViewById(R.id.lbl_detalle_destino_direccion);
            TextView lbl_detalle_destino_lat = findViewById(R.id.lbl_detalle_destino_lat);
            TextView lbl_detalle_destino_long = findViewById(R.id.lbl_detalle_destino_long);
            TextView lbl_detalle_origen_direccion = findViewById(R.id.lbl_detalle_origen_direccion);
            TextView lbl_detalle_origen_lat = findViewById(R.id.lbl_detalle_origen_lat);
            TextView lbl_detalle_origen_long = findViewById(R.id.lbl_detalle_origen_long);
            TextView lbl_detalle_estado = findViewById(R.id.lbl_detalle_estado);
            TextView lbl_detalle_fecha = findViewById(R.id.lbl_detalle_fecha);
            FloatingActionButton mapFab = findViewById(R.id.mapFab);
            TextView lbl_detalle_vehiculo_marca = findViewById(R.id.lbl_detalle_vehiculo_marca);
            TextView lbl_detalle_vehiculo_modelo = findViewById(R.id.lbl_detalle_vehiculo_modelo);
            TextView lbl_detalle_vehiculo_matricula = findViewById(R.id.lbl_detalle_vehiculo_matricula);
            TextView lbl_detalle_vehiculo_chofer = findViewById(R.id.lbl_detalle_vehiculo_chofer);
            TextView lbl_detalle_recepcion_nombre_receptor = findViewById(R.id.lbl_detalle_recepcion_nombre_receptor);
            TextView lbl_detalle_recepcion_observaciones = findViewById(R.id.lbl_detalle_recepcion_observaciones);
            TextView lbl_detalle_recepcion_fecha = findViewById(R.id.lbl_detalle_recepcion_fecha);




            lbl_detalle_id.append(String.valueOf(getIntent().getIntExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,0)));
            lbl_detalle_destino_direccion.setText(getResources().getString(R.string.destino)+" "+getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD));
            lbl_detalle_destino_lat.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT,0)));
            lbl_detalle_destino_long.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG,0)));
            lbl_detalle_estado.setText(getResources().getString(R.string.estado)+" "+estado_current);
            lbl_detalle_fecha.setText(getResources().getString(R.string.fecha)+" "+getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE));
            lbl_detalle_origen_direccion.setText(getResources().getString(R.string.origen)+" "+getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD));
            lbl_detalle_origen_lat.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT,0)));
            lbl_detalle_origen_long.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG,0)));
            lbl_detalle_vehiculo_marca.setText(getResources().getString(R.string.veh_marca)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAR)));
            lbl_detalle_vehiculo_matricula.setText(getResources().getString(R.string.veh_matricula)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAT)));
            lbl_detalle_vehiculo_chofer.setText(getResources().getString(R.string.veh_chofer)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VC)));
            lbl_detalle_vehiculo_modelo.setText(getResources().getString(R.string.veh_modelo)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMODEL)));
            lbl_detalle_recepcion_nombre_receptor.setText(getResources().getString(R.string.nombre_receptor)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RNAME)));
            lbl_detalle_recepcion_observaciones.setText(getResources().getString(R.string.observaciones_de_recepcion)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ROBS)));
            lbl_detalle_recepcion_fecha.setText(getResources().getString(R.string.fecha_del_receptor)+" "+String.valueOf(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RDATE)));


            if(estado_current.equals(Constantes.ESTADO_2)|estado_current.equals(Constantes.ESTADO_3)|estado_current.equals(Constantes.ESTADO_4)|estado_current.equals(Constantes.ESTADO_5)|estado_current.equals(Constantes.ESTADO_6)){
                lbl_detalle_vehiculo_chofer.setVisibility(View.VISIBLE);
                lbl_detalle_vehiculo_marca.setVisibility(View.VISIBLE);
                lbl_detalle_vehiculo_matricula.setVisibility(View.VISIBLE);
                lbl_detalle_vehiculo_modelo.setVisibility(View.VISIBLE);

            }else{
                lbl_detalle_vehiculo_chofer.setVisibility(View.GONE);
                lbl_detalle_vehiculo_marca.setVisibility(View.GONE);
                lbl_detalle_vehiculo_matricula.setVisibility(View.GONE);
                lbl_detalle_vehiculo_modelo.setVisibility(View.GONE);

            }

            if(estado_current.equals(Constantes.ESTADO_6)){
                lbl_detalle_recepcion_fecha.setVisibility(View.VISIBLE);
                lbl_detalle_recepcion_nombre_receptor.setVisibility(View.VISIBLE);
                lbl_detalle_recepcion_observaciones.setVisibility(View.VISIBLE);
            }else{
                lbl_detalle_recepcion_fecha.setVisibility(View.GONE);
                lbl_detalle_recepcion_nombre_receptor.setVisibility(View.GONE);
                lbl_detalle_recepcion_observaciones.setVisibility(View.GONE);
            }
            FloatingActionButton fab = findViewById(R.id.detalle_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(DetalleActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(DetalleActivity.this,
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                },
                                99
                        );
                    } else {
                        Log.d(Constantes.INFORMACION, "getLocation: permissions granted");
                        Intent intent = new Intent(DetalleActivity.this,Transportation_status_update_activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS,getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS));
                        intent.putExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID,getIntent().getIntExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,0));
                        startActivity(intent);
                    }

                }
            });

            mapFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DetalleActivity.this,MapaDetalleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(Constantes.DETALLE_MAPA_OLAT,getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT,0));
                    intent.putExtra(Constantes.DETALLE_MAPA_OLONG,getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG,0));
                    intent.putExtra(Constantes.DETALLE_MAPA_DLAT,getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT,0));
                    intent.putExtra(Constantes.DETALLE_MAPA_DLONG,getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG,0));
                    intent.putExtra(Constantes.DETALLE_MAPA_ID_EXTRA,getIntent().getIntExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,0));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HubActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnBRec);
    }
}
