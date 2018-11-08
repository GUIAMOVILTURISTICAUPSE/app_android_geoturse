package es.codigoandroid.geoturse;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        boolean valido;

        final String linkFac;
        final String linkTwi;
        final String linkInst;

        if(recursoAlmacenado.getInfContacto()!=null) {

              //// FIXME: 12/09/2017 arreglar contacto vacio

            if (recursoAlmacenado.getPersonaEncargada() != null){

                if(recursoAlmacenado.getInfContacto().getNombreResponsable()!=null)
                    nombre.setText("Nombre: " + recursoAlmacenado.getInfContacto().getNombreResponsable());

               if(recursoAlmacenado.getInfContacto().getUrlImagen()!=null){
                  Glide.with(this).load(recursoAlmacenado.getInfContacto().getUrlImagen()).into(imagen);
               }

            if (recursoAlmacenado.getInfContacto().getTelefono()!=null)
            telefono.setText("Telefono: " + recursoAlmacenado.getInfContacto().getTelefono());
            else
            telefono.setText("Telefono: No existe telefono de contacto.");

            if (recursoAlmacenado.getInfContacto().getEmail()!=null)
            email.setText("Email: " + recursoAlmacenado.getInfContacto().getEmail());
            else
            email.setText("Email: No existe email de contacto.");

            linkFac=recursoAlmacenado.getInfContacto().getFacebook();
                valido=isUrl(linkFac);
                if (valido)
                    links(facebook,linkFac);
                else
                    facebook.setVisibility(View.INVISIBLE);

            linkTwi=recursoAlmacenado.getInfContacto().getTwitter();
                valido=isUrl(linkTwi);
                if (valido)
                    links(twitter,linkTwi);
                else
                    twitter.setVisibility(View.INVISIBLE);

            linkInst=recursoAlmacenado.getInfContacto().getInstagram();
                valido=isUrl(linkInst);
                if (valido)
                    links(instagram,linkInst);
                else
                    instagram.setVisibility(View.INVISIBLE);
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



    public void links (ImageView iv, String urlLink){
        final String url_link=urlLink;
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url_link); // http://www.facebook.com/
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }


    private static boolean isUrl(String s) {
        String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";

        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();

        } catch (RuntimeException e) {
            return false;
        }
    }

}
