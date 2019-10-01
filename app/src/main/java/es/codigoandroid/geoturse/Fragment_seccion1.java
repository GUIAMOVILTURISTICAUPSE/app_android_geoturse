package es.codigoandroid.geoturse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

import es.codigoandroid.pojos.Recursos;

import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class Fragment_seccion1 extends Fragment {
    private static final String TAG = "Fragment_seccion1";
    CouchbaseLiteManager<Recursos> dbaRecurso_f1;
    private ArrayList<Recursos> recursos_f1;
    MapView mMapView;
    private GoogleMap googleMap;
    LatLng upse;
    Location loc;
    private LocationManager locManager;
    int contador = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        final View vista = inflater.inflate(R.layout.fragment_seccion1, container, false);
        dbaRecurso_f1 = new CouchbaseLiteManager<Recursos>(this.getActivity(), Recursos.class);
        inicLocation();
        registerLocation();
        //loc = new Location(String.valueOf(new LatLng(-2.229612,-80.8820533)));
        inicPuntosMarker();

        contador=contador +1;
        if(contador<2){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Ubicando...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 10000);}


        upse = new LatLng(-2.147709, -80.624193);

        mMapView = (MapView) vista.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMyLocationEnabled(true);
                //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//GoogleMap.MAP_TYPE_NORMAL - GoogleMap.MAP_TYPE_HYBRID - GoogleMap.MAP_TYPE_SATELLITE

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(upse));
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(upse).zoom(9).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent intent = new Intent(vista.getContext(), RecursoDetalle.class);
                        intent.putExtra("recurso", marker.getTitle());
                        vista.getContext().startActivity(intent);
                        return false;
                    }
                });

            }
        });


        Spinner spinner=(Spinner) vista.findViewById(R.id.spinner_prin_radio);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item=parent.getItemAtPosition(position).toString();
                if (item.equals("Mostrar todos"))
                {
                    // crea todos los markers sin execcion
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                    }


                }
                if (item.equals("5 kilometros a la redonda"))
                {
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        if(distancia_loc_contenidos(loc,puntoRecurso,5)){
                            googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        }
                    }

                }
                if (item.equals("10 kilometros a la redonda"))
                {
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        if(distancia_loc_contenidos(loc,puntoRecurso,10)){
                            googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        }
                    }

                }
                if (item.equals("15 kilometros a la redonda"))
                {
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        if(distancia_loc_contenidos(loc,puntoRecurso,15)){
                            googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        }
                    }

                }
                if (item.equals("25 kilometros a la redonda"))
                {
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        if(distancia_loc_contenidos(loc,puntoRecurso,25)){
                            googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        }
                    }

                }
                if (item.equals("40 kilometros a la redonda"))
                {
                    googleMap.clear();
                    for(Recursos r : recursos_f1) {
                        LatLng puntoRecurso;
                        puntoRecurso = new LatLng(r.latitud(), r.longuitd());
                        if(distancia_loc_contenidos(loc,puntoRecurso,40)){
                            googleMap.addMarker(new MarkerOptions().position(puntoRecurso).title(r.getNombre()).snippet(r.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        //Log.v("ubicacion resumen", ""+loc.getLongitude() + " - " + loc.getLatitude());
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        //Log.v("ubicacion pausa", ""+loc.getLongitude() + " - " + loc.getLatitude());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        //Log.v("ubicacion destruir", ""+loc.getLongitude() + " - " + loc.getLatitude());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
        //Log.v("ubicacion memoria", ""+loc.getLongitude() + " - " + loc.getLatitude());
    }

    private void inicLocation(){

        locManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    private void registerLocation(){
        locManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000,0,new Fragment_seccion1.MyLocationListener());
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onProviderEnabled(String provider) {
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        @Override
        public void onProviderDisabled(String provider) {
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    public boolean distancia_loc_contenidos(Location miUbicacion, LatLng punto, double radio){
        Location instLoc = new Location("punto");
        boolean verificar_distancia = false;
        double distance;
        LatLng point = punto;
        instLoc.setLatitude(point.latitude);
        instLoc.setLongitude(point.longitude);


        distance = miUbicacion.distanceTo(instLoc);
        Log.v("ver cantidad ", ""+distance);
        if((distance/1000) < radio){
            verificar_distancia =true;
            Log.v("ver cantidad ", ""+distance);
        }
        return verificar_distancia;
    }

    public void inicPuntosMarker(){
        recursos_f1 = dbaRecurso_f1.queryForDocumentTypeAsArrayList();
    }


}
