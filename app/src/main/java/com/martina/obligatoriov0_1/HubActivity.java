package com.martina.obligatoriov0_1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.martina.obligatoriov0_1.asincrono.AutoRetryHub;
import com.martina.obligatoriov0_1.broadcastReceivers.ConnectionBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.DetalleBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.FullTransportationListBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.HubBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.RetryHubBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.database.stDatabase;
import com.martina.obligatoriov0_1.metodos.MetodosHub;
import com.martina.obligatoriov0_1.objetos.SimpleTransportation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class HubActivity extends AppCompatActivity implements Serializable {
    private static final float MIN_DISTANCE = 150;
    public static TextView lbl_RV_id_lbl;
    public static TextView lbl_RV_estado_lbl;



    public static List<Integer> listaID = new ArrayList<Integer>();
    public static List<String> listaEstado = new ArrayList<String>();
    public static List<String> listaOrigen = new ArrayList<String>();

    public static RecyclerView recyclerView;
    private HubBroadcastReceiver mBRec = new HubBroadcastReceiver();
    private ConnectionBroadcastReceiver mConnBRec = new ConnectionBroadcastReceiver();
    private RetryHubBroadcastReceiver mBreccc = new RetryHubBroadcastReceiver();
    private FullTransportationListBroadcastReceiver mBRecc = new FullTransportationListBroadcastReceiver();
    public static ProgressBar progressBar_hub;
    
    





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cerrar_sesion:
                final AlertDialog.Builder builder = new AlertDialog.Builder(HubActivity.this);

                builder.setTitle(R.string.salir);
                builder.setMessage(R.string.estaSeguroCerrarSesion)
                        .setCancelable(true)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MetodosHub.cerrarSesion(HubActivity.this);
                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            case R.id.action_refrescar:
                MetodosHub.getSimpleTransportations(this);
                MetodosHub.mostrarSimpleTransportations(listaID, listaEstado, listaOrigen, this);

                Log.i(Constantes.INFORMACION, "El size es " + String.valueOf(HubBroadcastReceiver.simpleTransportationList.size()));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String email_user_logeado = sharedPreferences.getString(Constantes.EMAIL_SESION_INICIADA, null);

        MetodosHub.isConnectionAvailableII(this);
        boolean isConnectionAvailable = sharedPreferences.getBoolean(Constantes.CONEXION, true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar_hub = findViewById(R.id.progressBar_hub);
        TextView reminder = findViewById(R.id.reminder);
        TextView email_usuario_ing = findViewById(R.id.reminder);
        lbl_RV_id_lbl = findViewById(R.id.lbl_Rv_Item_id);

        lbl_RV_estado_lbl = findViewById(R.id.lbl_Rv_Item_estado);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            progressBar_hub.setIndeterminate(true);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        DetalleBroadcastReceiver mybr = new DetalleBroadcastReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mybr, new IntentFilter(
                Constantes.FILTRO_INTENT_TRANSPORTATION_DETALLADA));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBRec, new IntentFilter(
                Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBreccc, new IntentFilter(Constantes.BROADCAST_RETRY_HUB));
        LocalBroadcastManager.getInstance(this).registerReceiver(mBRecc, new IntentFilter(
                Constantes.FULL_TRANSPORTATION_WITH_DETAILS));
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");


        registerReceiver(mConnBRec, filter);


        if(isConnectionAvailable){
            
        
        MetodosHub.getSimpleTransportations(this);

        AutoRetryHub task = new AutoRetryHub(this);
        task.execute(progressBar_hub.getVisibility(),null,null);
        Log.i(Constantes.INFORMACION,"ESTADO DE LA PB"+String.valueOf(progressBar_hub.getVisibility()));


       

        }else {

            MetodosHub.noConnectionError(this);

            MetodosHub.getLocalSimpleTransportations(this);


        }
    }


}