package com.martina.obligatoriov0_1.metodos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MetodosLogin {
public static boolean checkBox_MantenerSesionIniciada=false;
    public static JSONObject getLoginBody(EditText email, EditText pass) {

        try {
            JSONObject body = new JSONObject();
            String email_st=String.valueOf(email.getText());
            String pass_st=String.valueOf(pass.getText());
            body.put("email", email_st);
            body.put("password", pass_st);


            return body;
        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void iniciarSesion(final Context contexto, EditText editText_Email, EditText editText_Contraseña, Switch aSwitch) {
        final boolean isActivatedd=aSwitch.isChecked();
        Log.i(Constantes.INFORMACION,"IS SELECTED: "+String.valueOf(isActivatedd));
        RequestQueue queue = Volley.newRequestQueue(contexto);

        JSONObject loginBody = MetodosLogin.getLoginBody(editText_Email,editText_Contraseña);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.URL_LOGIN,
                loginBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        handleResponse(response, contexto, isActivatedd);
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
                            builder.setMessage(R.string.usuarioOContraseñaIncorrecta)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Log.e(Constantes.ERROR_JSON, "Error en el login." + error);
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
                                            Log.e(Constantes.ERROR_JSON, "Error en el login.");


                                        }
                                    });


                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    }
                }
        );

        queue.add(request);


    }
    private static void handleResponse(JSONObject response, Context contexto, boolean isActivatedd) {
        SharedPreferences.Editor editor = contexto.getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, contexto.MODE_PRIVATE).edit();
        String email_Usuario_Logeado=null;
        try {
            email_Usuario_Logeado=response.getString("email");
        } catch (JSONException e) {
            Log.e(Constantes.ERROR_JSON,"Error obteniendo el mail del usuario que ingresó");
        }
        if (isActivatedd){
            Log.i(Constantes.INFORMACION,"ESTA SELECCIONADO: "+String.valueOf(isActivatedd));

            editor.putString(Constantes.EMAIL_SESION_INICIADA, email_Usuario_Logeado);
            editor.apply();
        }else {
            Log.i(Constantes.INFORMACION,"ESTA SELECCIONADO: "+String.valueOf(isActivatedd));

            editor.putString(Constantes.EMAIL_SESION_INICIADA, null);
            editor.apply();
        }

        Intent intentBroadcast = new Intent(Constantes.BROADCAST_SESION_INICIADA_CON_EXITO);
        intentBroadcast.setAction(Constantes.BROADCAST_SESION_INICIADA_CON_EXITO);
        intentBroadcast.putExtra(Constantes.EMAIL_EXTRA_INTENT, email_Usuario_Logeado);
        intentBroadcast.setFlags(FLAG_ACTIVITY_NEW_TASK);
        LocalBroadcastManager.getInstance(contexto).sendBroadcast(intentBroadcast);



    }

}
