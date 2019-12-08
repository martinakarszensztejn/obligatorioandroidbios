package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.martina.obligatoriov0_1.DetalleActivity;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.objetos.Transportation;


import java.io.Serializable;
import java.util.List;

public class DetalleBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constantes.INFORMACION,"Se escuchó el DetalleBroadcast!");
        int id = intent.getIntExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,0);
        String recepcion_nombre_receptor = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RNAME);
        String fecha = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE);
        String destino_direccion = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD);
        double destino_lat = intent.getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT,0);
        double destino_long = intent.getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG,0);
        String origen_direccion = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD);
        double origen_lat = intent.getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT,0);
        double origen_long = intent.getDoubleExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG,0);
        String recepcion_fecha = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RDATE);
        String recepcion_lat = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLAT);
        String recepcion_long = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLONG);
        String recepcion_observaciones = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ROBS);
        String estado = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS);
        String vehiculo_chofer = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VC);
        String vehiculo_marca = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAR);
        String vehiculo_matricula = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAT);
        String vehiculo_modelo = intent.getStringExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMODEL);




        Intent intent1 = new Intent(context, DetalleActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID,id);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMODEL,vehiculo_modelo);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAT,vehiculo_matricula);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAR,vehiculo_marca);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VC,vehiculo_chofer);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS,estado);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ROBS,recepcion_observaciones);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLONG,recepcion_long);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLAT,recepcion_lat);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RDATE,recepcion_fecha);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG,origen_long);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT,origen_lat);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD,origen_direccion);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD,destino_direccion);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG,destino_long);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT,destino_lat);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE,fecha);
        intent1.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RNAME,recepcion_nombre_receptor);


        context.startActivity(intent1);
    }
}
