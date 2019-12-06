package com.martina.obligatoriov0_1;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.martina.obligatoriov0_1.asincrono.AutoRetryHub;
import com.martina.obligatoriov0_1.broadcastReceivers.FullTransportationListBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.HubBroadcastReceiver;
import com.martina.obligatoriov0_1.broadcastReceivers.RetryHubBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.metodos.MetodosHub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

public class HubActivity extends AppCompatActivity implements Serializable {
    private static final float MIN_DISTANCE = 150;
    public static TextView lbl_RV_id_lbl;
    public static TextView lbl_RV_estado_lbl;



    public static List<Integer> listaID = new ArrayList<Integer>();
    public static List<String> listaEstado = new ArrayList<String>();
    public static List<String> listaOrigen = new ArrayList<String>();

    public static RecyclerView recyclerView;
    private HubBroadcastReceiver MBRec = new HubBroadcastReceiver();
    private RetryHubBroadcastReceiver MBreccc = new RetryHubBroadcastReceiver();
    private FullTransportationListBroadcastReceiver MBRecc = new FullTransportationListBroadcastReceiver();
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

        MetodosHub.getSimpleTransportations(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(MBRec, new IntentFilter(
                Constantes.FILTRO_INTENT_SIMPLE_TRANSPORTATION_LIST_BROADCAST));
        LocalBroadcastManager.getInstance(this).registerReceiver(MBreccc, new IntentFilter(Constantes.BROADCAST_RETRY_HUB));
        LocalBroadcastManager.getInstance(this).registerReceiver(MBRecc, new IntentFilter(
                Constantes.FULL_TRANSPORTATION_WITH_DETAILS));
        AutoRetryHub task = new AutoRetryHub(this);
        task.execute(progressBar_hub.getVisibility(),null,null);
        Log.i(Constantes.INFORMACION,"ESTADO DE LA PB"+String.valueOf(progressBar_hub.getVisibility()));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if (getIntent().getStringExtra(Constantes.EMAIL_EXTRA_INTENT) != null) {
            String email = getIntent().getStringExtra(Constantes.EMAIL_EXTRA_INTENT);

        }
        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        String email_user_logeado = sharedPreferences.getString(Constantes.EMAIL_SESION_INICIADA, null);






    }


}