package com.martina.obligatoriov0_1.servicios;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ClientLocationService extends Service {
    public static Timer timer = new Timer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {


        final int id = intent.getIntExtra(Constantes.INTENT_LOCATION_ID_TRANSPORTATION, 0);
        final Context context = getApplicationContext();

        FusedLocationProviderClient locProvider= LocationServices.getFusedLocationProviderClient(context);
        Task<Location> lastLocation = locProvider.getLastLocation();
        lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double latitude;
                double longitude;
                if (location != null) {
                     latitude = location.getLatitude();
                     longitude = location.getLongitude();
                    RequestQueue queue = Volley.newRequestQueue(context);

                    JSONObject body = new JSONObject();
                    try {
                        body.put("latitud",latitude);
                        body.put("longitud",longitude);
                    } catch (JSONException e) {
                        Log.e(Constantes.ERROR_JSON,"Error creando body de latlong json",e);
                        e.printStackTrace();
                    }


                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            Constantes.URL_LOCATION+id+"/location",
                            body,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {


                                    handleResponse(response, context);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(final VolleyError error) {
                                    String elerror=error.toString();
                                    Log.e(Constantes.ERROR_JSON,elerror);
                                    if (elerror.equals("com.android.volley.ClientError")){
                                        Log.e(Constantes.ERROR_OTHERS,"Error de cliente en mandar ubicacion");
                                    }else {
                                        Log.e(Constantes.ERROR_OTHERS,"Error de timeout en mandar ubicaccion");
                                    }
                                }
                            }
                    );

                    queue.add(request);


                }else{
                    Log.e(Constantes.ERROR_OTHERS, "Error en obtencion de ubicacion. Location nula");
                }


            }
            private void handleResponse(JSONObject response, Context contexto) {
                String estado = null;
                Log.i(Constantes.INFORMACION,"Se hizo el envio");

                try {
                    estado = response.getString("status");
                    Log.i(Constantes.INFORMACION,estado);
                } catch (JSONException e) {
                    Log.e(Constantes.ERROR_JSON,"Error obteniendo el estado de la respuesta del location api post ",e);
                }
                if(estado.equals("ok")){
                    Log.i(Constantes.INFORMACION,"Se envio la ubicacion correctamente!");
                }

            }


        });
        lastLocation.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(Constantes.ERROR_OTHERS,"Error obteniendo ubicacion. Failure listener on",e);
                e.printStackTrace();
            }
        });
                                      }
                                  },
                500, 10000);

        return super.onStartCommand(intent, flags, startId);

    }
}
