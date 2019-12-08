package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.martina.obligatoriov0_1.broadcastReceivers.DetalleBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.objetos.Transportation;

import java.io.Serializable;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

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
    }
}
