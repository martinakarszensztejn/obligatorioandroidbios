package com.martina.obligatoriov0_1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.martina.obligatoriov0_1.broadcastReceivers.ConnectionBroadcastReceiver;
import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.database.stDatabase;
import com.martina.obligatoriov0_1.metodos.MetodosDetalle;
import com.martina.obligatoriov0_1.objetos.SimpleTransportation;

import java.util.List;

public class MapaGeneralActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ConnectionBroadcastReceiver mConnBRec = new ConnectionBroadcastReceiver();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_map_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cerrar_sesion_main_map:

                return true;
            case R.id.preferencias_main_map:
                //TODO: Implementar preferencias de mapa
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_general);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");


        registerReceiver(mConnBRec, filter);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setTrafficEnabled(false);


        if(getIntent().getDoubleArrayExtra(Constantes.ORIGEN_LAT_ARRAY_EXTRA_INTENT)!=null) {
            for (int i = 0; i < getIntent().getIntArrayExtra(Constantes.ID_ARRAY_EXTRA_INTENT).length; i++) {
                LatLng latlng = new LatLng(getIntent().getDoubleArrayExtra(Constantes.ORIGEN_LAT_ARRAY_EXTRA_INTENT)[i], getIntent().getDoubleArrayExtra(Constantes.ORIGEN_LONG_ARRAY_EXTRA_INTENT)[i]);
                final int actual_ID = getIntent().getIntArrayExtra(Constantes.ID_ARRAY_EXTRA_INTENT)[i];
                String strActual_ID = String.valueOf(actual_ID);
                mMap.addMarker(new MarkerOptions().position(latlng).title(strActual_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))).setTag("marker"+strActual_ID);

                       mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int selected_ID = Integer.parseInt(marker.getTitle());
                        MetodosDetalle.getDetailedTransportation(MapaGeneralActivity.this,selected_ID);

                        return false;
                    }
                });
            }
        }

    }
}
