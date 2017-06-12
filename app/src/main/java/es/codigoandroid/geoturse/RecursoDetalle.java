package es.codigoandroid.geoturse;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;

public class RecursoDetalle extends AppCompatActivity {
    private static final String TAG = "RecursoDetalle";
    CouchbaseManager<String, Recursos> dbaRecurso;
    private TextView direccion,descripcion, canton, parroquia;
    private ImageView imagen;
    private Button rutaBtnn, senderoBtnn, galeriaBtnn;
    private ImageButton rutaBtn, senderoBtn, contactoBtn, preguntasBtn, galeriaBtn;
    Location loc;

    private LocationManager locManager;

    ManejadoraGaleria manejadorGaleria;
    ViewPager mViewPager;

    int[] imagenes = {
            R.drawable.chocolatera_ver,
            R.drawable.salinas_ver,
            R.drawable.parapente_ver,
            R.drawable.escolleras_ver
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurso_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inicLocation();
        registerLocation();
        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        //imagen = (ImageView) findViewById(R.id.imagenMostrar);
        direccion = (TextView) findViewById(R.id.txt_direccionR);
        descripcion = (TextView) findViewById(R.id.txt_descripcionR);
        canton = (TextView) findViewById(R.id.txt_cantonR);
        parroquia = (TextView) findViewById(R.id.txt_parroquiaR);
        rutaBtn = (ImageButton) findViewById(R.id.btn_rutaR);
        senderoBtn = (ImageButton) findViewById(R.id.btn_senderosR);
        contactoBtn = (ImageButton) findViewById(R.id.btn_contactoR);
        preguntasBtn = (ImageButton) findViewById(R.id.btn_preguntasR);
        galeriaBtn = (ImageButton) findViewById(R.id.btn_galeriaR);



        String mostrarR = getIntent().getExtras().getString("recurso");
        toolbar.setTitle(mostrarR);

        final Recursos recursoAlmacenado = dbaRecurso.get(mostrarR);

        if(recursoAlmacenado.getSendero().size()>0){
            Log.v("Existen senderos", ""+recursoAlmacenado.getSendero().size());
            //senderoBtn.setVisibility(View.INVISIBLE);
            senderoBtn.setEnabled(true);
        }else{
            Log.v("No existen senderos", ""+recursoAlmacenado.getSendero().size());
            senderoBtn.setEnabled(false);
        }

        //imagen.setImageResource(mostrarRIm);
        direccion.setText("Dirección: "+recursoAlmacenado.getDireccion());
        descripcion.setText(recursoAlmacenado.getDescripcion());
        canton.setText("Cantón: "+recursoAlmacenado.getCanton());
        parroquia.setText("Parroquia: "+recursoAlmacenado.getParroquia());


        senderoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        });
        rutaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicLocation();
                registerLocation();
                String origen = loc.getLatitude()+","+ loc.getLongitude();
                String destino = recursoAlmacenado.getPosicion();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+origen+"&daddr="+destino));
                intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                startActivityForResult(intent,5);
                //startActivity(intent);
            }
        });
        contactoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactoActivity.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        });
        preguntasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PreguntasFrecuentes.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        });
        /*galeriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        });*/

        manejadorGaleria = new ManejadoraGaleria(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[0]));
        manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[1]));
        manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[2]));
        manejadorGaleria.agregarFragmentos(FragmentosImagenes.newInstance(imagenes[3]));

        mViewPager.setAdapter(manejadorGaleria);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==5)
            Toast.makeText(this, "GoogleMap "+resultCode , Toast.LENGTH_LONG).show();
    }

    private void inicLocation(){

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    private void registerLocation(){
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, new MyLocationListener());
    }

    private class MyLocationListener implements LocationListener{

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





    public class ManejadoraGaleria extends FragmentPagerAdapter {

        List<Fragment> fragmentos;
        public ManejadoraGaleria(FragmentManager fm) {
            super(fm);
            fragmentos = new ArrayList();
        }

        public void agregarFragmentos(Fragment xfragmento){
            fragmentos.add(xfragmento);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }
    }

    public static class FragmentosImagenes extends Fragment {

        private static final String ARG_IMAGE = "imagen";
        private int imagen;

        public static FragmentosImagenes newInstance(int imagen) {
            FragmentosImagenes fragment = new FragmentosImagenes();
            Bundle args = new Bundle();
            args.putInt(ARG_IMAGE, imagen);
            fragment.setArguments(args);
            fragment.setRetainInstance(true);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(getArguments() != null) {
                imagen = getArguments().getInt(ARG_IMAGE);
            }
        }

        public FragmentosImagenes() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_galeria, container, false);

            ImageView imagenView = (ImageView) rootView.findViewById(R.id.imageView1);
            imagenView.setImageResource(imagen);
            return rootView;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
