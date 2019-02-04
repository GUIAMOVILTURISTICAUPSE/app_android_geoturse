package es.codigoandroid.geoturse;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Animacion;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.TipoAnimacion;

public class RecursoDetalle extends AppCompatActivity {
    private static final String TAG = "RecursoDetalle";
    CouchbaseManager<String, Recursos> dbaRecurso;
    private TextView direccion,descripcion, canton, parroquia;
    private ImageView imagen;
    private Button rutaBtnn, senderoBtnn, galeriaBtnn;
    private ImageButton rutaBtn, senderoBtn, contactoBtn, preguntasBtn, galeriaBtn, masInfoBtn, comentarioBtn,multimediaBtn,realidadAumentadaBtn;
    Location loc;

    private ArrayList<Animacion> listaAnimacion;//para pruebas
    private Animacion animacion; //para pruebas
    private TipoAnimacion tipoAnimacion; //para pruebas

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
        imagen = (ImageView) findViewById(R.id.imagen);
        direccion = (TextView) findViewById(R.id.txt_direccionR);
        descripcion = (TextView) findViewById(R.id.txt_descripcionR);
        canton = (TextView) findViewById(R.id.txt_cantonR);
        parroquia = (TextView) findViewById(R.id.txt_parroquiaR);
        rutaBtn = (ImageButton) findViewById(R.id.btn_rutaR);
        senderoBtn = (ImageButton) findViewById(R.id.btn_senderosR);
        contactoBtn = (ImageButton) findViewById(R.id.btn_contactoR);
        preguntasBtn = (ImageButton) findViewById(R.id.btn_preguntasR);
        galeriaBtn = (ImageButton) findViewById(R.id.btn_galeriaR);
        masInfoBtn = (ImageButton) findViewById(R.id.btn_masinfoR);
      //  comentarioBtn = (ImageButton) findViewById(R.id.btn_comentarioR);
        multimediaBtn =(ImageButton) findViewById(R.id.btn_multimedia);
        realidadAumentadaBtn = (ImageButton)findViewById(R.id.btn_realidadAumentada);

        String mostrarR = getIntent().getExtras().getString("recurso");
        toolbar.setTitle(mostrarR);

        final Recursos recursoAlmacenado = dbaRecurso.get(mostrarR);
        //prueba(recursoAlmacenado);

        if(recursoAlmacenado.getSendero().size()>0){
            Log.v("Existen senderos", ""+recursoAlmacenado.getSendero().size());
            //senderoBtn.setVisibility(View.INVISIBLE);
            senderoBtn.setEnabled(true);
        }else{
            Log.v("No existen senderos", ""+recursoAlmacenado.getSendero().size());
            senderoBtn.setEnabled(false);
        }

       if (recursoAlmacenado.getImagenPrincipal()!=null)
           obtenerImagen(recursoAlmacenado.getImagenPrincipal().getUrl());
       else
           Glide.with(this).load("http://www.andes.info.ec/sites/default/files/styles/large/public/field/image/salinas_1.jpg?itok=DZ7NxVqH").into(imagen);

        direccion.setText("Dirección: "+recursoAlmacenado.getDireccion());
        descripcion.setText(recursoAlmacenado.getDescripcion());
        canton.setText("Cantón: "+recursoAlmacenado.getCanton());
        parroquia.setText("Parroquia: "+recursoAlmacenado.getParroquia());

        //cantidad numerica de los objetos existentes para validación
        final int totalVideos = verTotalVideos(recursoAlmacenado);
        final int totalRealidadAumentada = verTotalRealidadAumentada(recursoAlmacenado);

        // comentarioBtn.setVisibility(View.INVISIBLE);

        senderoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recursoAlmacenado.getSendero().size()>0){
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());

                  //  Toast.makeText(getApplicationContext(), " debio entrar a el sendero del recurso" , Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), " No existen senderos en este recurso" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        rutaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!recursoAlmacenado.getPosicion().isEmpty()) {
                    inicLocation();
                    registerLocation();
                    if(loc !=null) {
                        String origen = loc.getLatitude() + "," + loc.getLongitude();
                        String destino = recursoAlmacenado.getPosicion();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?saddr=" + origen + "&daddr=" + destino));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivityForResult(intent, 5);
                        //startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), " Problemas al obtener la localizacion actual" , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), " No existen rutas en este recurso" , Toast.LENGTH_SHORT).show();
                }
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
                if (recursoAlmacenado.getPreguntasF().size()!=0)
                startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), " No existen preguntas sobre este recurso" , Toast.LENGTH_SHORT).show();

            }
        });
        galeriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!recursoAlmacenado.getGaleria().isEmpty()){
                Intent intent = new Intent(getApplicationContext(), GalleriaRecurso.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), "Oh no!, No existen galeria del recurso!" , Toast.LENGTH_SHORT).show();
            }
        });
        masInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MasInformacion.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        });
   /*     comentarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Intent intent = new Intent(getApplicationContext(), ComentarioActivity.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }
        }); */
        multimediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalVideos >0) {
                    Intent intent = new Intent(getApplicationContext(), ListaVideo.class);
                    intent.putExtra("recurso", recursoAlmacenado.getNombre());
                    intent.putExtra("total",totalVideos);
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), "Oh no!, No existen videos del recurso!" , Toast.LENGTH_SHORT).show();
            }

        });

        realidadAumentadaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* validacion si existe un index en el pojo para presentar la realidad aumentada
                if(totalRealidadAumentada >0) {
                    Intent intent = new Intent(getApplicationContext(), RealidadAumentada.class);
                    intent.putExtra("recurso", recursoAlmacenado.getNombre());
                    startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), "Oh no!, No existe realidad aumentada en el recurso!" , Toast.LENGTH_SHORT).show();
            */
                Intent intent = new Intent(getApplicationContext(), RealidadAumentada.class);
                intent.putExtra("recurso", recursoAlmacenado.getNombre());
                startActivity(intent);
            }

        });

    }


    public void prueba(Recursos r){
        listaAnimacion=new ArrayList<Animacion>();

        animacion=new Animacion();
        animacion.setTitulo("Video_Prueba1");
        animacion.setDescripcion("Alabarrada Video_Prueba1");
       // animacion.setUrl_video("https://www.youtube.com/watch?v=-LJ0xkDLeE8");
        tipoAnimacion= TipoAnimacion.VIDEO;
        animacion.setTipo(tipoAnimacion);
        listaAnimacion.add(animacion);
        r.setAnimaciones(listaAnimacion);
    }

    public int verTotalVideos(Recursos r){
        int x=0;
        for(Animacion a: r.getAnimaciones()) {
            if (a.getTipo().name() =="VIDEO") {
                x++;
            }
        }
     return x;
    }

    public int verTotalRealidadAumentada(Recursos r){
        int x=0;
        for(Animacion a: r.getAnimaciones()) {
            if (a.getTipo().name() =="REALIDAD_AUMENTADA") {
                x++;
            }
        }
        return x;
    }


    private void obtenerImagen(String myfeed) {
      try {
        BackgroundTask task =new BackgroundTask();
        task.execute(myfeed);
        Bitmap imag= task.get();
          imagen.setImageBitmap(imag);
       } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
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
