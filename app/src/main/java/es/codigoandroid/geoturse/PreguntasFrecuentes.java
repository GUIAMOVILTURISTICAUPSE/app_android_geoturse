package es.codigoandroid.geoturse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PreguntasFrecuentes extends AppCompatActivity {
    private String [] pregunta={"a","b","c","d"};
    private String [] respuesta= {"aa","bb","cc","dd"};
private ArrayAdapter<String> ListaPreguntas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_frecuentes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPreguntas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView ListaPreguntasRespuestas= (ListView)findViewById(R.id.listView);


        HashMap<String,String>  ListaPF = new HashMap<>();
        ListaPF.put("a","aa");
        ListaPF.put("b","bb");
        ListaPF.put("c","cc");
        ListaPF.put("d","dd");
        ListaPF.put("e","ee");

        List<HashMap<String,String>>  LItems = new ArrayList<>();
        SimpleAdapter adapter=new SimpleAdapter(this, LItems,R.layout.activity_items,
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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
