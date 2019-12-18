package com.martina.obligatoriov0_1.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.martina.obligatoriov0_1.Transportation_status_update_activity;
import com.martina.obligatoriov0_1.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.Serializable;

public class LocationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String extra_str = intent.getStringExtra(Constantes.JSON_BODY);
        final JSONObject extra;
        try {
            extra = new JSONObject(extra_str);


            FusedLocationProviderClient locProvider= LocationServices.getFusedLocationProviderClient(context);
            Task<Location> lastLocation = locProvider.getLastLocation();
            lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    JSONObject body = extra;
                    try {
                        body.put("recepcion_latitud",location.getLatitude());
                        body.put("recepcion_longitud",location.getLongitude());
                        Intent intent1 = new Intent(context,Transportation_status_update_activity.class);

                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        Bundle bundleExtra = intent.getBundleExtra(Constantes.BUNDLE_EXTRA_LOCATION_INTENT);
                        bundleExtra.putString(Constantes.JSON_BODY_EXTRA,body.toString());
                        intent1.putExtra(Constantes.JSON_BODY_EXTRA_EXTRA,bundleExtra);

                        context.startActivity(intent1);
                    } catch (JSONException e) {
                        Log.e(Constantes.ERROR_JSON, "ERROR JSON EN PONER LA UBICACION AL PASAR A FINALIZADO ",e);
                    }

                }
            });
            lastLocation.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(Constantes.ERROR_OTHERS, "onFailure: Error de obtención de ubicacion :(",e);
                }
            });
        } catch (JSONException e1) {
            Log.e(Constantes.ERROR_JSON, "ERROR JSON METIENDO EL EXTRA",e1);
        }
    }
}
