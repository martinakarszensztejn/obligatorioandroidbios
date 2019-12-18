package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.MapaGeneralActivity;
import com.martina.obligatoriov0_1.asincrono.DatabaseSetter;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.database.stDatabase;
import com.martina.obligatoriov0_1.metodos.MetodosHub;
import com.martina.obligatoriov0_1.objetos.AuxiliarDatabaseBundle;
import com.martina.obligatoriov0_1.objetos.SimpleTransportation;

import java.util.ArrayList;
import java.util.List;


public class HubBroadcastReceiver extends BroadcastReceiver {

    public static List<SimpleTransportation> simpleTransportationList;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(Constantes.INFORMACION, "Broadcast de envio de lista simple de transportations exitoso!");
        simpleTransportationList  = (List<SimpleTransportation>)intent.getSerializableExtra(Constantes.EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST);

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(Constantes.CONEXION,true)){
            List<Integer> idList = new ArrayList<>();
            List<String> estadoList = new ArrayList<>();
            List<String> origenList = new ArrayList<>();
            for (int i = 0; i < simpleTransportationList.size(); i++) {
                idList.add(simpleTransportationList.get(i).getId());
                estadoList.add(simpleTransportationList.get(i).getEstado());
                origenList.add(simpleTransportationList.get(i).getOrigen());

            }
            int size = idList.size();
            Log.i(Constantes.INFORMACION,"El size de la lista de IDS es "+String.valueOf(size));


        }

        Log.i(Constantes.INFORMACION, "El estado del primero es: " + simpleTransportationList.get(0).getEstado());
        MetodosHub.mostrarSimpleTransportations(HubActivity.listaID,HubActivity.listaEstado, HubActivity.listaOrigen, context);





//        MetodosHub.getAllTransportations(context);

    }
}