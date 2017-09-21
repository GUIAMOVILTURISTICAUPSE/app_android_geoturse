package es.codigoandroid.geoturse;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alvaro on 20/06/2017.
 */

public class ComentarioActivity extends AppCompatActivity {
    private ViewGroup layout;
    private EditText ed;
    private HashMap<String,String> ListaCometarios = new HashMap<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarComentario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = (ViewGroup) findViewById(R.id.content);
        cargar();
    }


    public void addLeft(View button)
    {
        addChild(false);
    }

    public void cargar(){

        ListView ListaComentariosF= (ListView)findViewById(R.id.listView);

        ListaCometarios.put("a","aa");
        ListaCometarios.put("b","bb");

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
        cargar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
