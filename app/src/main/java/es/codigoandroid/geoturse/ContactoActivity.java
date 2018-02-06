package es.codigoandroid.geoturse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Contacto;
import es.codigoandroid.pojos.Recursos;

public class ContactoActivity extends AppCompatActivity {
    private static final String TAG = "Contacto";
    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;
    private TextView nombre, telefono, email, web;
    private ImageView imagen,facebook,twitter, instagram;

    private Contacto contBuscar = new Contacto();

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
        facebook= (ImageView)findViewById(R.id.facebook);
        twitter= (ImageView)findViewById(R.id.twitter);
        instagram= (ImageView)findViewById(R.id.instagram);


        final String linkFac;
        final String linkTwi;
        final String linkInst;

        if(recursoAlmacenado.getInfContacto()!=null) {

              //// FIXME: 12/09/2017 arreglar contacto vacio
              //Glide.with(this).load("http://www.turismo.gob.ec/wp-content/uploads/2015/02/salinas3.jpg").into(imagen);
            if (recursoAlmacenado.getPersonaEncargada() != null){
            nombre.setText("Nombre: " + recursoAlmacenado.getPersonaEncargada());
            telefono.setText("Telefono: " + recursoAlmacenado.getInfContacto().getTelefono());
            email.setText("Email: " + recursoAlmacenado.getInfContacto().getEmail());
            web.setText("Web: ");
            linkFac=recursoAlmacenado.getInfContacto().getFacebook();
            linkTwi=recursoAlmacenado.getInfContacto().getTwitter();
            linkInst=recursoAlmacenado.getInfContacto().getFacebook();

            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(linkFac); // http://www.facebook.com/
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            }
             else vacio();



        }
        else
        {
            vacio();

            }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


public void vacio(){

    nombre.setText("No existe informacion del contacto del recurso");
    telefono.setText("");
    email.setText("");
    web.setText("");
    twitter.setVisibility(View.INVISIBLE);
    facebook.setVisibility(View.INVISIBLE);
    instagram.setVisibility(View.INVISIBLE);
    imagen.setVisibility(View.INVISIBLE);
    }

}
