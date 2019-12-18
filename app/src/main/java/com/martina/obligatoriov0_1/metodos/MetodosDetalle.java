package com.martina.obligatoriov0_1.metodos;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.NoConnectionDialogError;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.broadcastReceivers.NotificationBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.objetos.Transportation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MetodosDetalle {

    public static void getDetailedTransportation(final Context contexto, int id){
        Log.i(Constantes.INFORMACION,"Se entró a getDetaildedTransportation method");
        RequestQueue queue = Volley.newRequestQueue(contexto);



        String url = Constantes.URL_GET_TRANSPORTATIONS+"/"+id;
        JsonObjectRequest objetorq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        manejoRespuesta(response, contexto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.toString().equals("com.android.volley.TimeoutError")) {
                            Log.e(Constantes.ERROR_OTHERS,"Timeout obteniendo detalles. :(",error);
                            error.printStackTrace();
                            Intent intent = new Intent(contexto,NoConnectionDialogError.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexto.startActivity(intent);



                        }else{
                            Log.e(Constantes.ERROR_JSON,"Error en el detalle. Error no de timeout",error);
                            error.printStackTrace();
                        }
                    }
                });
        queue.add(objetorq);





    }
    private static void manejoRespuesta(JSONObject response, Context contexto) {
        Log.i(Constantes.INFORMACION,"Se entró al manejoRespuesta del metodosdetalle");
        JSONObject CompleteTransportation = response;
        Transportation pedido = new Transportation();
        try {
            pedido.setDestino_direccion(CompleteTransportation.getString("destino_direccion"));
            pedido.setDestino_latitud(CompleteTransportation.getDouble("destino_latitud"));
            pedido.setDestino_longitud(CompleteTransportation.getDouble("destino_longitud"));
            pedido.setEstado(CompleteTransportation.getString("estado"));
            if(CompleteTransportation.getString("estado").equals("iniciado")||CompleteTransportation.getString("estado").equals("cargando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("descargando")||CompleteTransportation.getString("estado").equals("finalizado")){
                pedido.setVehiculo_chofer(CompleteTransportation.getString("vehiculo_chofer"));
                pedido.setVehiculo_marca(CompleteTransportation.getString("vehiculo_marca"));
                pedido.setVehiculo_modelo(CompleteTransportation.getString("vehiculo_modelo"));
                pedido.setVehiculo_matricula(CompleteTransportation.getString("vehiculo_matricula"));
            }
            pedido.setFecha(CompleteTransportation.getString("fecha"));
            String fecha = CompleteTransportation.getString("fecha");
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",Locale.ENGLISH).parse(fecha);
            int id_pedido=CompleteTransportation.getInt("id");
            MetodosDetalle.crearNotificacion(contexto,date,id_pedido);
            pedido.setId(id_pedido);
            pedido.setOrigen_direccion(CompleteTransportation.getString("origen_direccion"));
            pedido.setOrigen_latitud(CompleteTransportation.getDouble("origen_latitud"));
            pedido.setOrigen_longitud(CompleteTransportation.getDouble("origen_longitud"));
            if(CompleteTransportation.getString("estado").equals("finalizado")){
                pedido.setRecepcion_fecha(CompleteTransportation.getString("recepcion_fecha"));
                pedido.setRecepcion_nombre_receptor(CompleteTransportation.getString("recepcion_nombre_receptor"));
                pedido.setRecepcion_observacion(CompleteTransportation.getString("recepcion_observacion"));
                pedido.setRecepcion_latitud(CompleteTransportation.getDouble("recepcion_latitud"));
                pedido.setRecepcion_longitud(CompleteTransportation.getDouble("recepcion_longitud"));

            }






        Intent intentBroadcast = new Intent(Constantes.FILTRO_INTENT_TRANSPORTATION_DETALLADA);
        intentBroadcast.setAction(Constantes.FILTRO_INTENT_TRANSPORTATION_DETALLADA);
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DD, pedido.getDestino_direccion());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ID, pedido.getId());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLAT, pedido.getDestino_latitud());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DLONG, pedido.getDestino_longitud());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_STATUS, pedido.getEstado());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_DATE, pedido.getFecha());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OD, pedido.getOrigen_direccion());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLAT, pedido.getOrigen_latitud());
        intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_OLONG, pedido.getOrigen_longitud());
        if(CompleteTransportation.getString("estado").equals("iniciado")||CompleteTransportation.getString("estado").equals("cargando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("descargando")||CompleteTransportation.getString("estado").equals("finalizado")){
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VC, pedido.getVehiculo_chofer());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAR, pedido.getVehiculo_marca());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMAT, pedido.getVehiculo_matricula());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_VMODEL, pedido.getVehiculo_modelo());

        }
        if(CompleteTransportation.getString("estado").equals("finalizado")) {
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RDATE, pedido.getRecepcion_fecha());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLAT, pedido.getRecepcion_latitud());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RLONG, pedido.getRecepcion_longitud());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_RNAME, pedido.getRecepcion_nombre_receptor());
            intentBroadcast.putExtra(Constantes.TRANSPORTATION_DETALLADA_EXTRA_INTENT_ROBS, pedido.getRecepcion_observacion());

        }
        LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);

    } catch (JSONException e) {
        Log.e(Constantes.ERROR_JSON, "Error en la lista detallada del Transportation, en el metodo manejo respuesta asignando valor a mi objeto json",e);
        e.printStackTrace();
    } catch (ParseException e) {
            Log.e(Constantes.ERROR_JSON, "Error en el parse de fecha",e);
            e.printStackTrace();

        }

    }

    private static void crearNotificacion(Context contexto, Date date,int id_pedido) {



        AlarmManager alarmManager = (AlarmManager)contexto.getSystemService(Context.ALARM_SERVICE);
        long time = date.getTime();

        Intent intent1 = new Intent(Constantes.INICIO_BROADCAST_ALARMA);
        intent1.putExtra(Constantes.NOTIFICATION_ID_PEDIDO,id_pedido);
        intent1.setAction(Constantes.INICIO_BROADCAST_ALARMA);
        PendingIntent pintent = PendingIntent.getBroadcast(contexto,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MINUTE,-10);
        long mTime=instance.getTimeInMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                alarmManager.setExact(AlarmManager.RTC_WAKEUP,mTime,pintent);



        }


    }



}
