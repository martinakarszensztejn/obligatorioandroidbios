package com.martina.obligatoriov0_1.metodos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.HubActivity;
import com.martina.obligatoriov0_1.MainActivity;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.adapters.HubAdapter;
import com.martina.obligatoriov0_1.asincrono.DatabaseSetter;
import com.martina.obligatoriov0_1.broadcastReceivers.ConnectionBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.HubBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.database.stDatabase;
import com.martina.obligatoriov0_1.objetos.AuxiliarDatabaseBundle;
import com.martina.obligatoriov0_1.objetos.SimpleTransportation;
import com.martina.obligatoriov0_1.objetos.Transportation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MetodosHub implements Serializable {


    public static void cerrarSesion(Context contexto) {
        SharedPreferences.Editor editor = contexto.getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, contexto.MODE_PRIVATE).edit();
        editor.putString(Constantes.EMAIL_SESION_INICIADA, null);
        Intent intent = new Intent(contexto, MainActivity.class);
        contexto.startActivity(intent);

        editor.apply();
    }

    public static void getSimpleTransportations(final Context contexto) {


        RequestQueue queue = Volley.newRequestQueue(contexto);
        JsonArrayRequest rq = new JsonArrayRequest(
                Request.Method.GET,
                Constantes.URL_GET_TRANSPORTATIONS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        manejarRespuesta(response, contexto);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Constantes.ERROR_JSON, "Error en el list transportations #2", error);
                    }
                }

        );


        queue.add(rq);

    }
    public static void getAllTransportations(final Context contexto){
        RequestQueue queue = Volley.newRequestQueue(contexto);
        List<JsonObjectRequest> rq = new ArrayList<>();
        List<SimpleTransportation> simpleTransportationList = HubBroadcastReceiver.simpleTransportationList;
        List<Integer> listaID = new ArrayList<>();
        if (simpleTransportationList.size()>0) {
            for (int i = 0; i < simpleTransportationList.size(); i++) {
                listaID.add(simpleTransportationList.get(i).getId());
            }
        }
        for (int i = 0; i < listaID.size(); i++) {
            String url = Constantes.URL_GET_TRANSPORTATIONS+"/"+listaID.get(i);
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
                            Log.e(Constantes.ERROR_JSON,"Error Listener on - Full transportation details "+error,error);
                        }
                    });
            rq.add(objetorq);
        }
        for (int i = 0; i < rq.size(); i++) {
            queue.add(rq.get(i));
        }

    }

    private static void manejoRespuesta(JSONObject response, Context contexto) {
        JSONObject CompleteTransportation = response;
        Transportation pedido = new Transportation();
        try {
            pedido.setDestino_direccion(CompleteTransportation.getString("destino_direccion"));
            pedido.setDestino_latitud(CompleteTransportation.getInt("destino_latitud"));
            pedido.setDestino_longitud(CompleteTransportation.getInt("destino_longitud"));
            pedido.setEstado(CompleteTransportation.getString("estado"));
            pedido.setFecha(CompleteTransportation.getString("fecha"));
            pedido.setId(CompleteTransportation.getInt("id"));
            pedido.setOrigen_direccion(CompleteTransportation.getString("origen_direccion"));
            pedido.setOrigen_latitud(CompleteTransportation.getInt("origen_latitud"));
            pedido.setOrigen_longitud(CompleteTransportation.getInt("origen_longitud"));

           if(CompleteTransportation.getString("estado").equals("iniciado")||CompleteTransportation.getString("estado").equals("cargando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("viajando")||CompleteTransportation.getString("estado").equals("descargando")||CompleteTransportation.getString("estado").equals("finalizado")) {
                pedido.setVehiculo_chofer(CompleteTransportation.getString("vehiculo_chofer"));
                pedido.setVehiculo_marca(CompleteTransportation.getString("vehiculo_marca"));
                pedido.setVehiculo_modelo(CompleteTransportation.getString("vehiculo_modelo"));
                pedido.setVehiculo_matricula(CompleteTransportation.getString("vehiculo_matricula"));

           }
           if(CompleteTransportation.getString("estado").equals("finalizado")){
               pedido.setRecepcion_fecha(CompleteTransportation.getString("recepcion_fecha"));

               pedido.setRecepcion_nombre_receptor(CompleteTransportation.getString("recepcion_nombre_receptor"));
               pedido.setRecepcion_observacion(CompleteTransportation.getString("recepcion_observacion"));
               pedido.setRecepcion_latitud(CompleteTransportation.getDouble("recepcion_latitud"));
               pedido.setRecepcion_longitud(CompleteTransportation.getDouble("recepcion_longitud"));
           }


        } catch (JSONException e) {
            Log.e(Constantes.ERROR_JSON, "Error en la lista detallada del Transportation, en el metodo manejo respuesta asignando valor a mi objeto json",e);
        }


        if(pedido!=null){
//            Intent intentBroadcast = new Intent(Constantes.FULL_TRANSPORTATION_WITH_DETAILS);
//            intentBroadcast.setAction(Constantes.FULL_TRANSPORTATION_WITH_DETAILS);
//            intentBroadcast.putExtra(Constantes.EXTRA_INTENT_FULL_TRANSPORTATION_SERIALIZED, (Serializable) pedido);
//            LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);

        } else {
            Log.i(Constantes.INFORMACION, "Pedido nulo  Metodos Hub manejoRespuesta()");
        }

    }

    private static void manejarRespuesta(JSONArray response, Context contexto) {
        List<SimpleTransportation> simpTransportationList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        List<String> estadoList = new ArrayList<>();
        List<String> origenList = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                if (response == null) {
                    Log.i(Constantes.INFORMACION, "ES NULL");
                }

                JSONObject transportation = response.getJSONObject(i);
                SimpleTransportation simpleTransportation = new SimpleTransportation();
                simpleTransportation.setId(transportation.getInt("id"));

                simpleTransportation.setEstado(transportation.getString("estado"));
                simpleTransportation.setOrigen(transportation.getString("origen_direccion"));
                simpleTransportation.setOrigen_lat(transportation.getDouble("origen_latitud"));
                simpleTransportation.setOrigen_long(transportation.getDouble("origen_longitud"));
                idList.add(transportation.getInt("id"));
                estadoList.add(transportation.getString("estado"));
                origenList.add(transportation.getString("origen_direccion"));




                simpTransportationList.add(simpleTransportation);

            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el list transportations #1", e);
            }


        }
        stDatabase db = new stDatabase(contexto);

        if(idList.size()>=1){
            db.deleteAllSt();
            AuxiliarDatabaseBundle bundle= new AuxiliarDatabaseBundle();
            bundle.setDb(db);
            bundle.setEstadoList(estadoList);
            bundle.setIdList(idList);
            bundle.setOrigenList(origenList);
            new DatabaseSetter(contexto).execute(bundle);
        }




        Intent intentBroadcast = new Intent(Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST);
        intentBroadcast.setAction(Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST);
        if (simpTransportationList.size() > 0) {
            intentBroadcast.putExtra(Constantes.EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST, (Serializable) simpTransportationList);
        } else {
            Log.i(Constantes.INFORMACION, "LISTA VACIA!!!");
        }
        intentBroadcast.setFlags(FLAG_ACTIVITY_NEW_TASK);
        LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);

    }

    public static void mostrarSimpleTransportations(List<Integer> listaID, List<String> listaEstado, List<String> listaOrigen, final Context contexto){
        if(HubBroadcastReceiver.simpleTransportationList!=null) {
            for (int i = 0; i < listaID.size(); i++) {
                listaID.remove(i);
                listaEstado.remove(i);
                listaOrigen.remove(i);

            }


            for (int i = 0; i < HubBroadcastReceiver.simpleTransportationList.size(); i++) {
                int id = HubBroadcastReceiver.simpleTransportationList.get(i).getId();

                listaID.add(id);
                String estado = HubBroadcastReceiver.simpleTransportationList.get(i).getEstado();
                listaEstado.add(estado);
                String origenn= HubBroadcastReceiver.simpleTransportationList.get(i).getOrigen();
                listaOrigen.add(origenn);


            }
            Log.i(Constantes.INFORMACION,"El size es "+String.valueOf(HubBroadcastReceiver.simpleTransportationList.size()));
            HubAdapter asd = new HubAdapter(contexto,listaID,listaOrigen,listaEstado);
            HubActivity.recyclerView.setAdapter(asd);
            HubActivity.recyclerView.setLayoutManager(new LinearLayoutManager(contexto));
            HubActivity.progressBar_hub.setVisibility(View.INVISIBLE);
            HubActivity.recyclerView.setVisibility(View.VISIBLE);

        }else {
            Log.i(Constantes.INFORMACION,"Lista nula :(");
        }








    }


    public static void noConnectionError(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.error);
        builder.setMessage(R.string.nohayconexion)
                .setCancelable(true)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(Constantes.ERROR_OTHERS, "No hay conexión, se procede a usar los datos locales.");
                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void getLocalSimpleTransportations(HubActivity contexto) {
        stDatabase database = new stDatabase(contexto);
        List<SimpleTransportation> list = database.getstList();
        int as = list.size();
        list.get(0).getId();


        Intent intentBroadcast = new Intent(Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST);
        intentBroadcast.setAction(Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST);
        if (list.size() > 0) {
            intentBroadcast.putExtra(Constantes.EXTRA_INTENT_SIMPLE_TRANSPORTATION_LIST, (Serializable) list);
        } else {
            Log.i(Constantes.INFORMACION, "LISTA DATABASE VACIA!!!");
        }
        intentBroadcast.setFlags(FLAG_ACTIVITY_NEW_TASK);
        LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);

        if (contexto.getIntent().getStringExtra(Constantes.EMAIL_EXTRA_INTENT) != null) {
        String email = contexto.getIntent().getStringExtra(Constantes.EMAIL_EXTRA_INTENT);

    }

    }

    public static void isConnectionAvailableII(Context contexto) {
        if(ConnectionBroadcastReceiver.isNetworkAvailable(contexto)){
            SharedPreferences.Editor editor = contexto.getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, contexto.MODE_PRIVATE).edit();
            editor.putBoolean(Constantes.CONEXION,true);

        }
    }
}
