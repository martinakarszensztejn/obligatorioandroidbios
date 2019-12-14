package com.martina.obligatoriov0_1.metodos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.Transportation_status_update_activity;
import com.martina.obligatoriov0_1.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MetodosUpdate {


    public static String updateTransportation(final Context contexto, String current_Status, final int id,JSONObject body) {
        RequestQueue queue = Volley.newRequestQueue(contexto);



        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.URL_UPDATE+String.valueOf(id),
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        handleResponse(response, contexto,id);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        String elerror=error.toString();
                        Log.e(Constantes.ERROR_JSON,elerror);
                        if (elerror.equals("com.android.volley.ClientError")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

                            builder.setTitle(R.string.error);
                            builder.setMessage(R.string.errorDeClienteEnUpdate)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.e(Constantes.ERROR_JSON, "Error en el update." + error);
                                            error.printStackTrace();
                                        }
                                    });


                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

                            builder.setTitle(R.string.error);
                            builder.setMessage(R.string.errorDeTimeoutEnLogin)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.e(Constantes.ERROR_JSON, "Error en el update.");


                                        }
                                    });


                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                }
        );

        queue.add(request);

    return current_Status;
    }
    private static void handleResponse(JSONObject response, Context contexto, int id) {


        try {
            JSONObject json = response.getJSONObject("data");
            String estado_nuevo = json.getString("estado");
            String status = response.getString("status");
            if(status.equals("ok")){
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

                builder.setTitle(R.string.exitos);
                builder.setMessage(R.string.exitocambiandodeestado);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    Log.i(Constantes.INFORMACION, "Todo ok en el cambio de estado de transportation");
                    }
                });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                Intent intent = new Intent(contexto,Transportation_status_update_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_ID,id);
                intent.putExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS,estado_nuevo);
                contexto.startActivity(intent);


            }
            
        } catch (JSONException e) {

            Log.e(Constantes.ERROR_JSON, "Error obteniendo el estado en la response del update de transportation",e);
        }
        

    }

    public static JSONObject makeBody(String current_Status, List<String> datos_extra) {
        JSONObject body = new JSONObject();
        String matricula=null;
        String marca=null;
        String modelo=null;
        String chofer=null;
        String nombre_receptor=null;
        String observaciones=null;
        String fecha=null;
        if (datos_extra != null) {
            // 4 representa cantidad de campos adicionales que requiere pasaje de PENDIENTE a INICIADO
            // 3 representa cantidad de campos adicionales que requiere pasaje de DESCARGANDO a FINALIZADO
            if(datos_extra.size()==4){
                marca=datos_extra.get(0);
                modelo=datos_extra.get(1);
                matricula=datos_extra.get(2);
                chofer=datos_extra.get(3);
            }else if(datos_extra.size()==3){
                nombre_receptor=datos_extra.get(0);
                observaciones=datos_extra.get(1);
                fecha=datos_extra.get(2);
            }

        }
        if(current_Status.equals(Constantes.ESTADO_1)){
            try {
                body.put("estado",Constantes.ESTADO_2);
                body.put("vehiculo_matricula",matricula);
                body.put("vehiculo_marca",marca);
                body.put("vehiculo_modelo",modelo);
                body.put("vehiculo_chofer",chofer);
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el creado del body json de tp update. Current status == 1",e);
            } catch (NullPointerException e1){
                Log.e(Constantes.ERROR_OTHERS,"Error en los extras de pasaje de estado 1 a 2",e1);
            }

        }else if(current_Status.equals(Constantes.ESTADO_2)){
            try {
                body.put("estado",Constantes.ESTADO_3);
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el creado del body json de tp update. Cur status == 2",e);
            }

        }else if(current_Status.equals(Constantes.ESTADO_3)){
            try {
                body.put("estado",Constantes.ESTADO_4);
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el creado del body json de tp update. Cur status == 3",e);
            }

        }else if(current_Status.equals(Constantes.ESTADO_4)){
            try {
                body.put("estado",Constantes.ESTADO_5);
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el creado del body json de tp update. Cur status == 4",e);
            }

        }else if(current_Status.equals(Constantes.ESTADO_5)){
            try {
                body.put("estado",Constantes.ESTADO_6);
                body.put("recepcion_nombre_receptor",nombre_receptor);
                body.put("recepcion_observacion",observaciones);
                body.put("recepcion_fecha",fecha);
                body.put("recepcion_latitud","79.421");
                body.put("recepcion_longitud","-79.419");
            } catch (JSONException e) {
                Log.e(Constantes.ERROR_JSON, "Error en el creado del body json de tp update. Current status == 5",e);
            } catch (NullPointerException e1){
                Log.e(Constantes.ERROR_OTHERS,"Error en los extras de pasaje de estado 5 a 6",e1);
            }

        }

        return body;
    }
}
