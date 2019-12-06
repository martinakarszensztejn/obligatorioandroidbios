package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosHub;
import com.martina.obligatoriov0_1.objetos.SimpleTransportation;

import java.util.List;


public class HubBroadcastReceiver extends BroadcastReceiver {
public static List<SimpleTransportation> simpleTransportationList;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(Constantes.INFORMACION, "Broadcast de envio de lista simple de transportations exitoso!");
        simpleTransportationList  = (List<SimpleTransportation>)intent.getSerializableExtra(Constantes.EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST);

        Log.i(Constantes.INFORMACION, "El estado del primero es: " + simpleTransportationList.get(0).getEstado());
        MetodosHub.mostrarSimpleTransportations(HubActivity.listaID,HubActivity.listaEstado, HubActivity.listaOrigen, context);
//        MetodosHub.getAllTransportations(context);

    }
}