package es.codigoandroid.geoturse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.AccesibilidadRecurso;
import es.codigoandroid.pojos.Costo;
import es.codigoandroid.pojos.Facilidad;
import es.codigoandroid.pojos.Idiomas;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.TipoAccesibilidad;
import es.codigoandroid.pojos.TipoAtractivo;

/**
 * Created by carme_000 on 14/06/2017.
 */
public class MasInformacion extends AppCompatActivity {
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    public TextView tv_tipoRecurso,tv_accesibilidad,tv_acceso,tv_Infraestructura,
            tv_parqueos,tv_facilidades,tv_seguridad,tv_idiomas;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_informacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMasInfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textviews();
        mostrarInformacion();
    }

    private void textviews() {
        tv_tipoRecurso=(TextView)findViewById(R.id.tv_tipoRecurso);
        tv_accesibilidad=(TextView)findViewById(R.id.tv_accesibilidad);
        tv_acceso=(TextView)findViewById(R.id.tv_acceso);
        tv_Infraestructura=(TextView)findViewById(R.id.tv_Infraestructura);
        tv_parqueos=(TextView)findViewById(R.id.tv_parqueos);
        tv_facilidades=(TextView)findViewById(R.id.tv_facilidades);
        tv_seguridad=(TextView)findViewById(R.id.tv_seguridad);
        tv_idiomas=(TextView)findViewById(R.id.tv_idiomas);
    }

    private void mostrarInformacion() {
        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        final String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);

        ArrayList<Facilidad> facR= recursoAlmacenado.getFacilidadRecurso();
        ArrayList<Idiomas> idioR=recursoAlmacenado.getIdiomasInformac();
        ArrayList<AccesibilidadRecurso> accR=recursoAlmacenado.getOpcionesAccesibilidad();
        ArrayList<Costo> costoR=recursoAlmacenado.getCostoRecursos();
        ArrayList<String> tiposParqueos =recursoAlmacenado.getTiposParqueo();

        tv_facilidades.setText(" ");
        tv_accesibilidad.setText(" ");
        tv_acceso.setText(" ");
        tv_Infraestructura.setText(" ");
        tv_parqueos.setText(" ");
        tv_seguridad.setText(" ");
        tv_idiomas.setText(" ");
        tv_tipoRecurso.setText(" ");


        if(recursoAlmacenado.getCategoria()!= null) {
            tv_tipoRecurso.setMovementMethod(new ScrollingMovementMethod());
            tv_tipoRecurso.setText(recursoAlmacenado.getCategoria());
        }else
            tv_tipoRecurso.setText("Sin información");


        if(recursoAlmacenado.getSeguridad()!= null) {
            tv_seguridad.setMovementMethod(new ScrollingMovementMethod());
            tv_seguridad.setText(recursoAlmacenado.getSeguridad());
        }else
            tv_tipoRecurso.setText("Sin información");

        if(facR!=null) {
            tv_facilidades.setMovementMethod(new ScrollingMovementMethod());
            for (Facilidad fa : facR) {
                if (fa != null)
                    tv_facilidades.setText(tv_facilidades.getText().toString() + "\n" + fa.getTitulo() + "\n" + fa.getDescripcion());
            }

        }else
            tv_tipoRecurso.setText("Sin información");


        if(idioR!=null){
        for(Idiomas idi: idioR) {
            tv_idiomas.setMovementMethod(new ScrollingMovementMethod());
            if (idi != null)
                tv_idiomas.setText(tv_idiomas.getText() + "\n" + idi.name());
            }
        } else
            tv_idiomas.setText("Sin información");


        if(accR != null) {
            tv_accesibilidad.setMovementMethod(new ScrollingMovementMethod());
            for (AccesibilidadRecurso ac : accR) {
                if (ac != null)
                    tv_accesibilidad.setText(tv_accesibilidad.getText() + "\n" + ac.getTitulo() + ": " + ac.getDescripcion());
            }
        }else
            tv_accesibilidad.setText("Sin información");


        if(recursoAlmacenado.getAcceso()!=null) {
            tv_acceso.setMovementMethod(new ScrollingMovementMethod());
            tv_acceso.setText(recursoAlmacenado.getAcceso());
        }else
            tv_acceso.setText("Sin información");

        if(recursoAlmacenado.getInfraestructura()!=null) {
            tv_Infraestructura.setMovementMethod(new ScrollingMovementMethod());
            tv_Infraestructura.setText(recursoAlmacenado.getInfraestructura());
        }else
            tv_Infraestructura.setText("Sin información");

        if(tiposParqueos!=null) {
            tv_parqueos.setMovementMethod(new ScrollingMovementMethod());
            for (String tipoParqueo : tiposParqueos) {
                if (tipoParqueo != null) {
                    tv_parqueos.setText(tv_parqueos.getText() + "\n" + tipoParqueo);
                }
            }
        }else
            tv_parqueos.setText("Sin información");

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
