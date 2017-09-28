package es.codigoandroid.geoturse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.PreguntaFrecuente;

public class PreguntasFrecuentes extends AppCompatActivity {
    private String [] preguntas;
    private String [] respuestas;
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;

    private ArrayAdapter<String> ListaPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_frecuentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPreguntas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView ListaPreguntasRespuestas= (ListView)findViewById(R.id.listView);
        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);

        llenarArreglo(ListaPreguntasRespuestas);


    }



    public void llenarArreglo(ListView ListaPreguntasRespuestas){
        if(recursoAlmacenado.getPreguntasF().size()!=0){
        String p,r;

        ArrayList<PreguntaFrecuente> pregunta = recursoAlmacenado.getPreguntasF();

            int tamanio=0;
            preguntas = new String[recursoAlmacenado.getPreguntasF().size()];
            respuestas = new String[recursoAlmacenado.getPreguntasF().size()];

            for(PreguntaFrecuente pr :pregunta ){
              preguntas[tamanio]= (String)pr.getPreguntas();
              respuestas[tamanio]= (String)pr.getRespPreguntas();
                tamanio++;
             }

            HashMap<String,String>  ListaPF = new HashMap<>();

            do{
                tamanio--;
                ListaPF.put(preguntas[tamanio]+tamanio,respuestas[tamanio]+tamanio);

            }while(tamanio>0);

            List<HashMap<String,String>>  LItems = new ArrayList<>();
            SimpleAdapter adapter=new SimpleAdapter(this, LItems,R.layout.activity_item_pregunta,
                    new String[] {"Pregunta","Respuesta"},
                    new int[] {R.id.tv_Pregunta, R.id.tv_respuestas});

            Iterator it = ListaPF.entrySet().iterator();

            while (it.hasNext()){
                HashMap<String,String> Lista= new HashMap<>();
                Map.Entry pair = (Map.Entry)it.next();
                Lista.put("Pregunta", pair.getKey().toString());
                Lista.put("Respuesta",pair.getValue().toString());
                LItems.add(Lista);
            }
            ListaPreguntasRespuestas.setAdapter(adapter);


        }else{
            Toast.makeText(getApplicationContext(), " No existen preguntas sobre este recurso" , Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}
