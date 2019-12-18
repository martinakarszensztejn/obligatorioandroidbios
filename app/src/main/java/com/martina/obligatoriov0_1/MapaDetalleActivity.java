package com.martina.obligatoriov0_1;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.martina.obligatoriov0_1.constantes.Constantes;

public class MapaDetalleActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_detalle);
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
        double lat_origen = getIntent().getDoubleExtra(Constantes.DETALLE_MAPA_OLAT, 100);
        double lat_destino = getIntent().getDoubleExtra(Constantes.DETALLE_MAPA_DLAT, 100);
        double lon_origen = getIntent().getDoubleExtra(Constantes.DETALLE_MAPA_OLONG, -100);
        double lon_destino = getIntent().getDoubleExtra(Constantes.DETALLE_MAPA_DLONG, -100);
        int id = getIntent().getIntExtra(Constantes.DETALLE_MAPA_ID_EXTRA, 0);
        LatLng origen = new LatLng(lat_origen, lon_origen);
        LatLng destino = new LatLng(lat_destino,lon_destino);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(origen));
        mMap.setTrafficEnabled(false);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setAllGesturesEnabled(true);

        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle(this,R.raw.map);
        mMap.setMapStyle(mapStyleOptions);
        mMap.addMarker(new MarkerOptions().title(String.valueOf(id)+" "+getResources().getString(R.string.origen)).position(origen).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag("origen");
        mMap.addMarker(new MarkerOptions().title(String.valueOf(id)+" "+getResources().getString(R.string.destino)).position(destino).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))).setTag("destino");



    }
}
