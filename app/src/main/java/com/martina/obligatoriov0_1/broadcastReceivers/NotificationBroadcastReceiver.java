package com.martina.obligatoriov0_1.broadcastReceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.constantes.Constantes;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context contexto, Intent intent) {
        int id_pedido = intent.getIntExtra(Constantes.NOTIFICATION_ID_PEDIDO, 0);
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(Constantes.CANAL,Constantes.CANAL, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = contexto.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(contexto.getApplicationContext(),Constantes.CANAL);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(contexto.getResources().getString(R.string.avisodepedidoinminente));
        String s = contexto.getResources().getString(R.string.pronto)+" "+contexto.getResources().getString(R.string.id)+String.valueOf(id_pedido);

        builder.setContentText(s);

        Intent intent_2 = new Intent(contexto, HubActivity.class);
        PendingIntent notificationPIntent =
                PendingIntent.getActivity(
                        contexto,
                        Constantes.NOTIFICATION_ID,
                        intent_2,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notificationPIntent);
        Notification build = builder.build();
        NotificationManager mNotifyManager = (NotificationManager) contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(Constantes.NOTIFICATION_ID,build);
    }
}
