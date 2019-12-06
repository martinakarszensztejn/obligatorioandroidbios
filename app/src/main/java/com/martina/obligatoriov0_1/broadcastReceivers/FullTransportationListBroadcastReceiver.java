package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.martina.obligatoriov0_1.constantes.Constantes;

import com.martina.obligatoriov0_1.objetos.Transportation;

import java.util.ArrayList;
import java.util.List;

public class FullTransportationListBroadcastReceiver extends BroadcastReceiver {
    public static List<Transportation> listaTr= new ArrayList<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        listaTr.add(
                (Transportation) intent.getSerializableExtra(Constantes.EXTRA_INTENT_FULL_TRANSPORTATION_SERIALIZED)

        );
        for (int i = 0; i < listaTr.size(); i++) {
            Log.i(Constantes.INFORMACION, listaTr.get(i).getOrigen_direccion());
        }
    }

}
