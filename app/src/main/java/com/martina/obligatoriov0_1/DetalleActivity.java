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
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.objetos.Transportation;
import com.martina.obligatoriov0_1.servicios.ClientLocationService;

import java.io.Serializable;

public class DetalleActivity extends AppCompatActivity {
    private ConnectionBroadcastReceiver mConnBRec = new ConnectionBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");


        registerReceiver(mConnBRec, filter);

        TextView lbl_detalle_id = findViewById(R.id.lbl_detalle_id);
        TextView lbl_detalle_destino_direccion = findViewById(R.id.lbl_detalle_destino_direccion);
        TextView lbl_detalle_destino_lat = findViewById(R.id.lbl_detalle_destino_lat);
        TextView lbl_detalle_destino_long = findViewById(R.id.lbl_detalle_destino_long);
        TextView lbl_detalle_origen_direccion = findViewById(R.id.lbl_detalle_origen_direccion);
        TextView lbl_detalle_origen_lat = findViewById(R.id.lbl_detalle_origen_lat);
        TextView lbl_detalle_origen_long = findViewById(R.id.lbl_detalle_origen_long);
        TextView lbl_detalle_estado = findViewById(R.id.lbl_detalle_estado);
        TextView lbl_detalle_fecha = findViewById(R.id.lbl_detalle_fecha);




        lbl_detalle_id.append(String.valueOf(getIntent().getIntExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,0)));
        lbl_detalle_destino_direccion.setText(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD));
        lbl_detalle_destino_lat.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT,0)));
        lbl_detalle_destino_long.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG,0)));
        lbl_detalle_estado.setText(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS));
        lbl_detalle_fecha.setText(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE));
        lbl_detalle_origen_direccion.setText(getIntent().getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD));
        lbl_detalle_origen_lat.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT,0)));
        lbl_detalle_origen_long.setText(String.valueOf(getIntent().getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG,0)));

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
    }
}
