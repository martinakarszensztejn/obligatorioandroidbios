package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.NoConnectionDialogError;
import com.martina.obligatoriov0_1.asincrono.AutoRetryHub;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosHub;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RetryHubBroadcastReceiver extends BroadcastReceiver {
    private int i=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        i++;
        if(i<5) {
            MetodosHub.getSimpleTransportations(context);
            MetodosHub.mostrarSimpleTransportations(HubActivity.listaID, HubActivity.listaEstado, HubActivity.listaOrigen, context);
            AutoRetryHub task = new AutoRetryHub(context);
            task.execute(HubActivity.progressBar_hub.getVisibility(), null, null);
            Log.i(Constantes.INFORMACION, "Reintentando conectarnos");
        }else{
            Intent intent1 = new Intent(context, NoConnectionDialogError.class);
            intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }

    }

}
