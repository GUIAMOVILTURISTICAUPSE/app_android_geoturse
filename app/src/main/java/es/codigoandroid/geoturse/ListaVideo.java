package es.codigoandroid.geoturse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import es.codigoandroid.pojos.Animacion;
import es.codigoandroid.pojos.Recursos;


import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class ListaVideo extends AppCompatActivity {
    CouchbaseLiteManager<Recursos> dbaRecurso_f2;
    private String[] listavideos, numero_video ;
    public Recursos recursoAlmacenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_video);
        dbaRecurso_f2 = new CouchbaseLiteManager<Recursos>(this, Recursos.class);

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
