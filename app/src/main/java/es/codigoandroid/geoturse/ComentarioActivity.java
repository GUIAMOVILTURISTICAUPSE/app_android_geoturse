package es.codigoandroid.geoturse;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.codigoandroid.pojos.Comentario;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.Usuario;

import es.codigoandroid.datamanager.CouchbaseLiteManager;
/**
 * Created by Alvaro on 20/06/2017.
 * Modified by Ivan on 10/09/2019
 */

public class ComentarioActivity extends AppCompatActivity {
    private ViewGroup layout;
    private EditText ed;
    private HashMap<String,String> ListaCometarios = new HashMap<>();

    private ArrayList<Comentario> ListaComentario = new ArrayList<Comentario>();

    CouchbaseLiteManager<Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    public Usuario usuario;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarComentario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dbaRecurso = new CouchbaseLiteManager<Recursos>(this, Recursos.class);
        String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);


      /*  Bundle param= getIntent().getExtras();
        String d= param.getString("_usuario");
        Toast.makeText(getApplicationContext(), " dato: "+ d.toString() , Toast.LENGTH_SHORT).show();
*/
        layout = (ViewGroup) findViewById(R.id.content);

        cargar();
    }


    public void addLeft(View button)
    {
        addChild(false);
    }



    public void cargar(){

        if (recursoAlmacenado.getComentarios()!= null) {
            ListView ListaComentariosF= (ListView)findViewById(R.id.listView);
            ListaComentario = recursoAlmacenado.getComentarios();

            for(Comentario comen: ListaComentario){
                ListaCometarios.put("Nombre_usuario","Comentario de la BD");
            }

            List<HashMap<String,String>> LItems = new ArrayList<>();
            SimpleAdapter adapter=new SimpleAdapter(this, LItems,R.layout.activity_item_comentario,
                    new String[] {"Usuario","Comentario"},
                    new int[] {R.id.textView1, R.id.textview});
            Iterator it = ListaCometarios.entrySet().iterator();
            while (it.hasNext()){
                HashMap<String,String> Lista= new HashMap<>();
                Map.Entry pair = (Map.Entry)it.next();
                Lista.put("Usuario", pair.getKey().toString());
                Lista.put("Comentario",pair.getValue().toString());
                LItems.add(Lista);
            }
            ListaComentariosF.setAdapter(adapter);

       }

    }

    @SuppressLint("InlinedApi")
    private void addChild(boolean right)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        int id = R.layout.activity_item_comentario;
      /*  if (right)
        {
            id = R.layout.layout_right;
        }*/
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(id, null, false);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.textview);
        ed= (EditText)findViewById(R.id.edittext);
        textView.setText(String.valueOf(ed.getText().toString()));
        layout.addView(relativeLayout);

        ListaCometarios.put("USU",ed.getText().toString());
        ed.setText(String.valueOf(" "));
        //cargar();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
