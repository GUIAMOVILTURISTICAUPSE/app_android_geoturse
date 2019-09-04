package es.codigoandroid.geoturse;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Locale;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.Senderos;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static final String TAG = "RecursoDetalle";
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    public String puntoString;

    private GoogleMap mMap;

    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senderos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        LatLng markerpunto = new LatLng(recursoAlmacenado.latitud(), recursoAlmacenado.longuitd());
        mMap.addMarker(new MarkerOptions().position(markerpunto).title(recursoAlmacenado.getNombre()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(markerpunto)
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        PolylineOptions sendero = new PolylineOptions()
                .width(5)
                .color(Color.BLUE)
                .geodesic(true);

        for(Senderos s : recursoAlmacenado.getSendero()) {
            if(s.getNombre()!= null)
                for(String p : s.getRecorrido()) {
                    LatLng puntoRecurso;
//                    puntoRecurso = new LatLng(latitud(p), longuitd(p));
                    puntoRecurso = new LatLng(latlong(p,0), latlong(p,1));
                    sendero.add(puntoRecurso);
                }
        }
        Polyline polyline = mMap.addPolyline(sendero);
    }

    public double latlong(String posicion, int pos){
        double dato=0.00;
        String[] parts;
        String string = posicion;

        int x=0;

        char[] arrayChar = string.toCharArray();
        for(int i=0; i<arrayChar.length; i++){
            if(arrayChar[i] == ',')
                x++;
        }
        if(x>=1) {
            parts = posicion.split(",");
            String part1 = parts[pos];
            dato = Double.parseDouble(part1);
        }
        return dato;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
