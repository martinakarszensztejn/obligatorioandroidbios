package com.martina.obligatoriov0_1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.martina.obligatoriov0_1.constantes.Constantes;

public class NoConnectionDialogError extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayAlert();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void displayAlert()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.error);
        builder.setMessage(R.string.errorFatalDeConexion)
                .setCancelable(false)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(Constantes.ERROR_JSON, "Error fatal. No se pudo obtener los datos del servidor.");


                        finish();


                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
