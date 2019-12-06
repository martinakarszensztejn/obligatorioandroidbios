package com.martina.obligatoriov0_1.metodos;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.martina.obligatoriov0_1.R;
import com.martina.obligatoriov0_1.RegistroActivity;
import com.martina.obligatoriov0_1.constantes.Constantes;

import org.json.JSONException;
import org.json.JSONObject;

public class MetodosRegistro {
    public static JSONObject getRegistroBody(EditText nombre, EditText apel, EditText email, EditText pass, Context contexto) {
        JSONObject regBody = new JSONObject();
        try {

            String nombreCompleto = nombre.getText()+" "+apel.getText();

            regBody.put("name",nombreCompleto);
            regBody.put("email",String.valueOf(email.getText()));
            regBody.put("password",String.valueOf(pass.getText()));

            return regBody;
        }catch (JSONException e){
            Log.e("ERROR JSON","ERROR JSON #1");
            Toast.makeText(contexto,"No se pudo realizar el envío. :(", Toast.LENGTH_SHORT).show();

            return null;
        }

    }

    public static void registrar(final Context contexto, EditText editText1, EditText editText2, EditText editText3, EditText editText4) {

        RequestQueue rq = Volley.newRequestQueue(contexto);

        JSONObject registroBody = MetodosRegistro.getRegistroBody(editText1, editText2, editText3, editText4, contexto);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constantes.URL_REGISTRO,registroBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject respuesta) {
                if (respuesta!= null) {
                    manejarRespuesta(respuesta, contexto);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(Constantes.ERROR_JSON,"El error es en el registro. Error: "+error+" #2");
                        error.printStackTrace();
                        Toast.makeText(contexto,R.string.errorRegistro,Toast.LENGTH_LONG).show();


                    }
                });

            rq.add(request);
        }

    private static void manejarRespuesta(JSONObject respuesta, Context contexto) {

            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

            builder.setTitle(R.string.exitos);
            builder.setMessage(R.string.mensajeRegistroExitoso)
            .setCancelable(false)
            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                Log.i(Constantes.EXITO,"Se registró un usuario con éxito.");
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            }
}
