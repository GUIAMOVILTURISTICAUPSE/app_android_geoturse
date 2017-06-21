package es.codigoandroid.geoturse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;

public class ContactoActivity extends AppCompatActivity {
    private static final String TAG = "Contacto";
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    private TextView nombre, telefono, email, web;
    private ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarContacto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);

        imagen = (ImageView) findViewById(R.id.imagenContacto);
        nombre = (TextView) findViewById(R.id.txt_nombre);
        telefono = (TextView) findViewById(R.id.txt_telefono);
        email = (TextView) findViewById(R.id.txt_email);
        web = (TextView) findViewById(R.id.txt_web);

        Glide.with(this).load("http://www.turismo.gob.ec/wp-content/uploads/2015/02/salinas3.jpg").into(imagen);
        //Glide.with(this).load(recursoAlmacenado.getImagenPrinc().getUrl()).into(imagen);
        nombre.setText("Nombre: "+recursoAlmacenado.getInfContacto().getNombreResponsable());
        telefono.setText("Telefono: "+recursoAlmacenado.getInfContacto().getTelefono());
        email.setText("Email: "+recursoAlmacenado.getInfContacto().getEmail());
        web.setText("Web: ");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
