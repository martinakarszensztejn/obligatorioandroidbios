package com.martina.obligatoriov0_1.broadcastReceivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.martina.obligatoriov0_1.constantes.Constantes;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, context.MODE_PRIVATE).edit();


        if(isNetworkAvailable(context)){
            Log.i(Constantes.INFORMACION,"Ahora sí hay conexión");
            editor.putBoolean(Constantes.CONEXION,true);
            editor.apply();



        }else {
            Log.i(Constantes.INFORMACION,"Ahora no hay conexión");
            editor.putBoolean(Constantes.CONEXION,false);
            editor.apply();

        }


    }
    public static boolean isNetworkAvailable(Context contexto) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
