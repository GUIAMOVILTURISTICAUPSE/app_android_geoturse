package es.codigoandroid.geoturse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private LocationManager locManager;
    AlertDialog alert = null;
    AlertDialog alert2 = null;

    //Log
    private final String TAG = getClass().getSimpleName();
    private  static  final String MyPreferencia = "Configuracion";

    private Button cerrarSesion;

    private Adaptador_ViewPagerPrincipal Adaptador_ViewPagerPrincipal;
    //private ViewPager ViewPager;
   // private Button cerrarSesion;
    @BindView(R.id.ToolbarPrincipal)
    Toolbar toolbar;
    @BindView(R.id.AppbarPrincipal)
    AppBarLayout appbar;
    @BindView(R.id.TabLayoutPrincipal)
    TabLayout tabLayout;
    @BindView(R.id.ViewPagerPrincipal)
    ViewPager ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Iniciamos la barra de herramientas.
        //Toolbar toolbar = (Toolbar) findViewById(R.id.ToolbarPrincipal);
        setSupportActionBar(toolbar);

        //final AppBarLayout appbar = (AppBarLayout) findViewById(R.id.AppbarPrincipal);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");


        // Iniciamos la barra de tabs
       // final TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayoutPrincipal);

        // Añadimos las 3 tabs de las secciones.
        // Le damos modo "fixed" para que todas las tabs tengan el mismo tamaño. Tambien le asignamos una gravedad centrada.

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());


        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
        }

        Bundle b = getIntent().getExtras();
        String _UsuarioMostrar = b.getString("_usuario");

        // Iniciamos el viewPager.
        //ViewPager = (ViewPager) findViewById(R.id.ViewPagerPrincipal);
        // Creamos el adaptador, al cual le pasamos por parámetro el gestor de Fragmentos y muy importante, el nº de tabs o secciones que hemos creado.
        System.out.println(""+tabLayout.getTabCount());
        Adaptador_ViewPagerPrincipal = new Adaptador_ViewPagerPrincipal(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        // Y los vinculamos.
        ViewPager.setAdapter(Adaptador_ViewPagerPrincipal);

        // Y por último, vinculamos el viewpager con el control de tabs para sincronizar ambos.
        tabLayout.setupWithViewPager(ViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.maps);
        tabLayout.getTabAt(1).setIcon(R.drawable.list);
        tabLayout.getTabAt(2).setIcon(R.drawable.account);

        cerrarSesion =(Button) findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inicio_Sesion.changeEstadoCambiar(MainActivity.this, false);
                Intent intent = new Intent(MainActivity.this, Inicio_Sesion.class);
                startActivity(intent);
                SharedPreferences miPreferencia = getSharedPreferences(MainActivity.MyPreferencia, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = miPreferencia.edit();

                finish();
                editor.clear();
                editor.commit();



            }
        });

    }



    private void AlertNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta apagado, para el uso correcto de la aplicacion debe estar encendido. DESEA HABILITARLO?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy()
    {
        if(alert != null)
        {
            alert.dismiss ();
        }

        if(alert2 != null)
        {
            alert2.dismiss ();
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            //aquí vendría las acciones que tengo que realizar
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Desea salir de GEOTURSE?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alert2 = builder.create();
            alert2.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        Log.w(TAG, "App stopped");

        super.onStop();
    }




}
