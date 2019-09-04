package es.codigoandroid.geoturse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;

public class Lista_RA extends AppCompatActivity {
    CouchbaseManager<String, Recursos> dbaRecurso;
    private String[] listaRA, numero_RA;
    public Recursos recursoAlmacenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_video);
        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        ListView listav= (ListView) findViewById(R.id.listav);
        final String mostrarR = getIntent().getExtras().getString("recurso");

        int total = 3;
        listaRA =new String[total];
        numero_RA =new String[total];
        TextView texto= (TextView)findViewById(R.id.texto);
        texto.setText("Lista de objetos de Realidad aumentada ");
        recursoAlmacenado = dbaRecurso.get(mostrarR);
        llenaListaRA(recursoAlmacenado);
        muestraLista(listav,mostrarR);

    }

        public void muestraLista(ListView lista, final String nombreRecurso){
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, numero_RA);
            lista.setAdapter(adapter);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                    Intent vistaRA = new Intent(getApplicationContext(),RealidadAumentada.class);
                    vistaRA.putExtra("recurso",nombreRecurso);
                    vistaRA.putExtra("link",listaRA[posicion]);
                      startActivity(vistaRA);
                }
            });
        }



    private void llenaListaRA(Recursos recursoAlmacenado) {
    try {

        //cambiar el id para taer los index --   recursoAlmacenado.getId()
        String arExperience;
       if(recursoAlmacenado.getId().equals(null))
            arExperience = "Megaterio";
        else
            arExperience=recursoAlmacenado.getId();

      //  arExperience = "Megaterio";
        numero_RA[0] = "Realidad aumentada con localización";
        listaRA[0]= "file:///android_asset/"+ arExperience + "/localizacion.html";

        numero_RA[1] = "Realidad aumentada Boton Scanear";
        listaRA[1]= "file:///android_asset/"+ arExperience + "/botonScaner.html";

        numero_RA[2] = "Realidad aumentada Scaneo Continuo";
        listaRA[2]= "file:///android_asset/"+ arExperience + "/scaneoContinuo.html";

        }catch (Exception e) {
            Toast.makeText(this, "Error en la lista de recursos RA"+e.getMessage(),Toast.LENGTH_LONG).show();
         }

    }





}
