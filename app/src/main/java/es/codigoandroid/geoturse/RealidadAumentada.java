package es.codigoandroid.geoturse;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;

public class RealidadAumentada extends AppCompatActivity {
    //private static final String sampleDefinitionsPath = "assets/samples.json";
    //public static final String INTENT_EXTRAS_KEY_SAMPLE = "sampleData";
    private String arExperience;
    String mostrarR;

    File file;
    private ArchitectView architectView;
    double latitude = 0;
    double longitude = 0;

    LocationManager locationManager;

    CouchbaseManager<String, Recursos> dbaRecurso_f2;
    public Recursos recursoAlmacenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realidad_aumentada);

        mostrarR = getIntent().getExtras().getString("recurso");
        dbaRecurso_f2 = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        recursoAlmacenado = dbaRecurso_f2.get(mostrarR);
        arExperience = getIntent().getExtras().getString("link");
       // file = new File(Activity.DOWNLOAD_SERVICE);
       // Toast.makeText(this, "Direccion del path donde se guarda lo descargado..."+ file.getAbsolutePath().toString(),Toast.LENGTH_LONG ).show();
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey("LVlYSNaIFt6lnrd99ajNsZYPAJ38iA9vLJleez11RP7UBg3oWeu7fG8bd0miwS5/7WxzWDIaZ5M4kzFhjmr12rDltwq17At+BcNqrm3R0sC9bEvL6EUNYqMzFrYIn/IMD7tJXRKEhs0tUupUqeaIJ5U/Ji2PlC17mYdmX+/z8lNTYWx0ZWRfX9jFp5qaylXJsW0QEbAGpR5lcxJFw9G76nNq1NYzeDeRbZN+jc7JxBqCCXlGk1HTgKJjgJXHCnLtxWK2a76KLPxPI/K9bWarxveMYX/YGxC4L5HEtIH+KmVMZd1uZm2O8cJtD1NsbcO4xqs0smPR6bHSB4QpHHObgZXvpB+azGytDuhQOgkCa5xaeNkWd++Dbw7SN7a1ycGmPWsmvGNB0jpORMoBMhaSQuVJ6qX75zem0gzl8+A3qYrMnSMngUc8i4lvFAlW+3GBIetShjkHICabz5c4G+XUCBA/4Yg2jX+YLS44HzXyd4P5HefZkyYaII6n29bAUeD9rReDzPcJe+OycJTV/g9i2+wuKpmK6rQt+iRmN70QbXpIphuxd3Q4wfuVPMhxEGc6YEny+sLxYvohiVyKZG45YyrAAy4Y2LhSbQ2W9tfRX/3YYyhdEyaiSaPNVmHFC9TPFHVOQ4Z9L5WPjcGWaFjGBqTkf6i6ikvGk8dZWR+UYz4mDDqkIwP1E/mWUtrCnA5n39fThPeozh0qbH9yOc6jyA==" );
        this.architectView.onCreate( config );

        getLocalization( recursoAlmacenado );
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState ){
        super.onPostCreate(savedInstanceState);
        architectView.onPostCreate();

        try {

         // arExperience= "AlbarradaVelascoIbarra1";
        //  String p= Environment.DIRECTORY_DOWNLOADS+File.separator + "datos";
         // file= new File(p);

         //  BackgroundTask task = new BackgroundTask();
        //   BackgroundImg task = new BackgroundImg();
        //  Toast.makeText (this,"  directorio....." +file.toString() ,Toast.LENGTH_LONG).show();
       //  task.execute("assets/amantes.jpg",file.toString());
          //  task.downloadFile("https://storage.cloud.google.com/guiamovilse_recursos_storage/wikitude_3d_museo/3dObjects/assets/amantes.jpg?authuser=0",Environment.getDownloadCacheDirectory());
      //   Toast.makeText(this, " A new file is downloaded successfully  " , Toast.LENGTH_LONG).show();



            this.architectView.setLocation(-2.23351,-80.8675,100);
            this.architectView.load(arExperience);
           //this.architectView.load("https://storage.cloud.google.com/guiamovilse_recursos_storage/wikitude_3d_museo/3dObjects/index.html?authuser=0");
            // this.architectView.load("file:///android_asset/"+ arExperience + "/index.html");
            //this.architectView.load("file:///android_asset/AlbarradaVelascoIbarra1/index.html");
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
            /*    Toast.makeText(
                        activity,
                        "nueva posicion obtenida " + latitude + " " + longitude + " integrando Realidad aumentada "
                                + randomNum, Toast.LENGTH_LONG).show();
                architectView.setLocation(latitude, longitude, 1f);*/
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
          /*  Toast.makeText(
                    activity,
                    "nueva posicion seteada .... obtenida " + latitude + " " + longitude + " "
                    , Toast.LENGTH_LONG).show(); */
            //architectView.setLocation(latitude, longitude, 1f);
            // actualizo la posicion del usuario cada 5 min = 80000 ms
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 80000, 0, locationListener);

        } else if (locationManager // Puntos Wifi o senal telefonica
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // obtengo la ultima localizacion del usuario, si no la hay, es null
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = recursoAlmacenado.latitud();
                longitude = recursoAlmacenado.longuitd();
            }

            // actualizo la posicion del usuario cada 5 min = 80000 ms
            //    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 80000, 0, locationListener);

        } else {
            // servicio desactivado
             /* Toast.makeText(
                    this,
                    "Por favor active el \"Acceso a su ubicación\" desde las configuraciones.",
                    Toast.LENGTH_LONG).show(); */
        }
    }








    public class BackgroundImg extends AsyncTask<String, Void, Bitmap> {

        private Bitmap descargarImagen(String imageHttpAddress) {
            URL imageUrl = null;
            Bitmap imagen = null;
            try {
                imageUrl = new URL(imageHttpAddress);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return imagen;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Log.i("doInBackground" , "Entra en doInBackground");
            String url = strings[0];
            File file = new File(strings[1]);
           // Bitmap imagen = descargarImagen(url);
            downloadFile(url,file);
            return null;
        }

        private void downloadFile(String url,File outputFile) {
            try {
                URL u = new URL(url);
                URLConnection conn = u.openConnection();
                int contentLength = conn.getContentLength();

                DataInputStream stream = new DataInputStream(u.openStream());

                byte[] buffer = new byte[contentLength];
                stream.readFully(buffer);
                stream.close();

                DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
                fos.write(buffer);
                fos.flush();
                fos.close();
            } catch(FileNotFoundException e) {
                return; // swallow a 404
            } catch (IOException e) {
                return; // swallow a 404
            }
        }
    }




}
