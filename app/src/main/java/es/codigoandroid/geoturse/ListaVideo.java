package es.codigoandroid.geoturse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.ArrayList;
import java.util.Iterator;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Animacion;
import es.codigoandroid.pojos.Recursos;

public class ListaVideo extends AppCompatActivity {
    CouchbaseManager<String, Recursos> dbaRecurso_f2;
    private String[] listavideos, numero_video ;
    public Recursos recursoAlmacenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_video);
        dbaRecurso_f2 = new CouchbaseManager<String, Recursos>(this, Recursos.class);

        ListView listav= (ListView) findViewById(R.id.listav);

        final String mostrarR = getIntent().getExtras().getString("recurso");
        final int total = getIntent().getExtras().getInt("total");
        listavideos=new String[total];
        numero_video=new String[total];
        recursoAlmacenado = dbaRecurso_f2.get(mostrarR);
        llenaListaVideos(recursoAlmacenado);
        TextView texto= (TextView)findViewById(R.id.texto);
        texto.setText("Lista de videos "+ mostrarR);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, numero_video);
        listav.setAdapter(adapter);

        listav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Multimedia.class);
                intent.putExtra("nombre",mostrarR);
                intent.putExtra("link",listavideos[position]);
                startActivity(intent);
            }
        });

    }

    public int llenaListaVideos(Recursos r){
        int x=0;
        for(Animacion a: r.getAnimaciones()) {
            if (a.getTipo().name() =="VIDEO") {
                numero_video[x]= "Video " + (x+1);
                listavideos[x]=a.getUrl();
                x++;
            }
        }
        return x;
    }

}
