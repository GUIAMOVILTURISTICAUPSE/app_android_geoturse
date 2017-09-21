package es.codigoandroid.geoturse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.AccesibilidadRecurso;
import es.codigoandroid.pojos.Costo;
import es.codigoandroid.pojos.Facilidad;
import es.codigoandroid.pojos.Idiomas;
import es.codigoandroid.pojos.Recursos;

/**
 * Created by carme_000 on 14/06/2017.
 */
public class MasInformacion extends AppCompatActivity {
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    public TextView tv_tipoRecurso,tv_accesibilidad,tv_acceso,tv_Infraestructura,tv_parqueos,tv_facilidades,tv_seguridad,tv_idiomas;


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

        tv_facilidades.setText(" ");
        tv_accesibilidad.setText(" ");
        tv_acceso.setText(" ");
        tv_Infraestructura.setText(" ");
        tv_parqueos.setText(" ");
        tv_seguridad.setText(" ");
        tv_idiomas.setText(" ");
        tv_tipoRecurso.setText(" ");


        if(recursoAlmacenado.getCategoria()!= null)
            tv_tipoRecurso.setText(recursoAlmacenado.getCategoria());

        if(recursoAlmacenado.getSeguridad()!= null)
            tv_seguridad.setText(recursoAlmacenado.getSeguridad());

        for(Facilidad fa: facR)
            if(fa!=null)
                tv_facilidades.setText(tv_facilidades.getText().toString()+"\n"+fa.getTitulo()+"\n"+fa.getDescripcion());

        for(Idiomas idi: idioR)
            if(idi!=null)
                tv_idiomas.setText(tv_idiomas.getText()+"\n"+idi.name());

        for(AccesibilidadRecurso ac: accR)
            if(ac!=null)
                tv_accesibilidad.setText(tv_accesibilidad.getText()+"\n"+ac.getTitulo()+": "+ac.getDescripcion());



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }



}
