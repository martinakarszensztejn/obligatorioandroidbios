package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.constantes.Constantes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LoginBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(Constantes.INFORMACION,"Broadcast de inicio de sesión exitoso!");
        String email_Usuario_Logeado=intent.getStringExtra(Constantes.EMAIL_EXTRA_INTENT);
        Log.i(Constantes.INFORMACION,"El email del usuario es: "+email_Usuario_Logeado);

        Intent intent_2= new Intent(context, HubActivity.class);
        intent_2.putExtra(Constantes.EMAIL_EXTRA_INTENT,email_Usuario_Logeado);
        intent_2.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent_2);
    }
}


