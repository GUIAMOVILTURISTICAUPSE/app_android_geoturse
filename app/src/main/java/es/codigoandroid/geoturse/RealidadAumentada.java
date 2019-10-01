package es.codigoandroid.geoturse;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import es.codigoandroid.advanced.ArchitectViewExtension;
import es.codigoandroid.advanced.ScreenshotSaverExtension;
import es.codigoandroid.pojos.Recursos;

import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class RealidadAumentada extends AppCompatActivity {
    //private static final String sampleDefinitionsPath = "assets/samples.json";
    //public static final String INTENT_EXTRAS_KEY_SAMPLE = "sampleData";
    private String arExperience;
    String mostrarR;
    private static final String EXTENSION_SCREENSHOT = "screenshot";

    File file;
    private ArchitectView architectView;
    double latitude = 0;
    double longitude = 0;

    LocationManager locationManager;

    CouchbaseLiteManager<Recursos> dbaRecurso_f2;
    public Recursos recursoAlmacenado;
    private final Map<String, ArchitectViewExtension> extensions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realidad_aumentada);

        mostrarR = getIntent().getExtras().getString("recurso");
        dbaRecurso_f2 = new CouchbaseLiteManager<Recursos>(this, Recursos.class);
        recursoAlmacenado = dbaRecurso_f2.get(mostrarR);
        arExperience = getIntent().getExtras().getString("link");

        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();

       config.setLicenseKey("2f4NRVBlBmqoKoQoiv7bwnbs8BDfEaKrvPP/SacDF8TtUYVhAe1+pb5TGuSFuJfG/rFie4YEYYthiDIQ5DfgR0RHqAKggUIaxvnhmXOW8YOur+ARkdgl5RYTLgmRTuU9RYb1Ry/XU+O67mRSVXNJ4HIZ6I43xcsUveBAtw/Gl41TYWx0ZWRfX/PQ/qArUWYZgJ2vnd/H8bi6K/SeLWsYKP4HR4sPiOMA/6F7aCqqSQ49PVxFhZuh97reCOF//F1MUINw/GpGJxEyRRc4U6ji76E02syJuS49vYBc6NlSzUACi6oYhsP03B4tRvUasnhDJGe9iPjC/6Y2n10uUpS2hm2fAXT5JzqPClJxntSJj+DNWcVvdt2uBid4ae4LN6/+3Y8hI7AymJAmWDtiQWOmJw02kE98tveU+ZFo4TfY027mRR56GOAXb1n/OX91zuGsMyXcShrKDib12+AqV1SKSVJ0wCGm2eRcX0EBkQ2Hp4RB3/Oc0VJO7ayqJJOIkIbWOq/uQ7q+NdA8lgdWUJhl7x4gwWwulfAXWxltNgxRJTjkblWKgjGxaX8BB8fX/2fvd3SpLp5M9LsKBAmZql/3Cixf4TqEK/svXrrYZasddmzbf2/4CYdjPkGyPqAoT9xJev1lf1uXC304oZ0WKIZL6+igj536vxG1ErgmcE4o7X5YqGT/QZJfphvWpn9eAtsToKCVNVLiT6JBPIYaTqah7MamGFTKnvBXO1dqRTYTw0+X2rURVzSnnE/dKYpZej0t" );


        extensions.put(EXTENSION_SCREENSHOT, new ScreenshotSaverExtension(this, architectView));

        for (ArchitectViewExtension extension : extensions.values()) {
            extension.onCreate();
        }

        this.architectView.onCreate( config );
        getLocalization( recursoAlmacenado );
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState ){
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();

        try {

          //  this.architectView.setLocation(-2.23351,-80.8675,100);
            this.architectView.load(arExperience);
            this.architectView.onResume();

        }catch (Exception e){
            Toast.makeText(this, "Catch de la presentacion :(", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
//        architectView.onResume();
        if (this.architectView != null) {
            this.architectView.setLocation(latitude, longitude, 1f);
            this.architectView.onResume();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //architectView.onDestroy();
        if (this.architectView != null) {
            this.architectView.onDestroy();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        //architectView.onPause();
        if (this.architectView != null) {
            this.architectView.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    private void getLocalization(final Recursos recursoAlmacenado) {

        final Activity activity = this;
        // Obtenemos una referencia al LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Nos registramos para recibir actualizaciones de la posición
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = recursoAlmacenado.latitud();
                    longitude = recursoAlmacenado.longuitd();
                }
                Random rand = new Random();
                int randomNum = rand.nextInt(50);

            }

            @Override
            public void onProviderDisabled(String provider) {
           /*     Toast.makeText(activity, "Debe de habilitar el \"Acceso a su ubicación\" ",
                        Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

           @Override
            public void onStatusChanged(String provider, int status,Bundle extras) {

                // muestro un mensaje segun el estatus erroneo de la senal
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                  /*      Toast.makeText(
                                activity,
                                "Se ha perdido la comunicación con el GPS y/o redes teléfonicas.",
                                Toast.LENGTH_LONG).show(); */
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                      /*  Toast.makeText(
                                activity,
                                "Se ha perdido la comunicación con el GPS y/o redes teléfonicas.",
                                Toast.LENGTH_LONG).show(); */
                        break;
                    case LocationProvider.AVAILABLE:
                     /*   Toast.makeText(
                                activity,
                                "Se ha establecido la conexión con el GPS y/o redes teléfonicas.",
                                Toast.LENGTH_LONG).show(); */
                        break;
                }

            }
        };

        // Si esta habilitada el GPS en el dispositivo
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // obtengo la ultima localizacion del usuario, si no la hay, es null
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = -2.2335 /*recursoAlmacenado.latitud()*/;
                longitude = -80.8675 /* recursoAlmacenado.longuitd()*/;
            }
           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 80000, 0, locationListener);

        } else if (locationManager // Puntos Wifi o senal telefonica
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // obtengo la ultima localizacion del usuario, si no la hay, es null
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = recursoAlmacenado.latitud();
                longitude = recursoAlmacenado.longuitd();
            }



        } else {
            // servicio desactivado
             /* Toast.makeText(
                    this,
                    "Por favor active el \"Acceso a su ubicación\" desde las configuraciones.",
                    Toast.LENGTH_LONG).show(); */
        }
    }





}
