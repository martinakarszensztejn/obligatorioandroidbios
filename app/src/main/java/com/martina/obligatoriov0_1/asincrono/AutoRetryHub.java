package com.martina.obligatoriov0_1.asincrono;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.martina.obligatoriov0_1.constantes.Constantes;


public class AutoRetryHub extends AsyncTask<Integer,Void,Void> {
    private Context contexto;

    public AutoRetryHub (Context context){
        contexto = context;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        Log.i(Constantes.INFORMACION,"Se inició la asynctask");
        if(integers[0]== View.VISIBLE){
            try {

                Log.i(Constantes.INFORMACION,"Entró al if");
                Thread.sleep(5500);

            } catch (InterruptedException e) {
                Log.e(Constantes.ERROR_OTHERS, "Error en el thread sleep clase asincrona hub");

            }
            Intent intentBroadcast = new Intent(Constantes.BROADCAST_RETRY_HUB);
            intentBroadcast.setAction(Constantes.BROADCAST_RETRY_HUB);


            LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);


        }
        return null;

    }
}




