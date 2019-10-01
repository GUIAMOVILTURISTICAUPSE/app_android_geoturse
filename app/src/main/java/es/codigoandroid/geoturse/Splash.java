package es.codigoandroid.geoturse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import es.codigoandroid.pojos.Recursos;

import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class Splash extends AppCompatActivity {
    private static final String TAG = "Splash";
    CouchbaseLiteManager<Recursos> dbaRecurso;


    private Bitmap mImageToBeAttached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


       /* dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        Recursos recursoIngresado = new Recursos();
        Senderos senderoIngresado = new Senderos();
        recursoIngresado.setNombre("La Chocolatera");
        recursoIngresado.setDescripcion("Está ubicado dentro de la base naval de Salinas, en la Punta más saliente de la Península de Santa Elena, y que constituye el segundo punto más saliente de la costa sudamericana.");
        recursoIngresado.setInformacionGeneral("La Chocolatera, gran acantilado que es el punto más saliente de la Península de Santa Elena y uno de los lugares más visitados por los turistas. En sus playas se realizan campeonatos de surf, por su fabuloso oleaje alto y tubular");
        recursoIngresado.setDireccion("Ubicada en el extremo Oeste de la Punta del Canton Salinas");
        recursoIngresado.setPosicion("-2.188480,-81.010379");

        ArrayList<String> puntosSedero = new ArrayList<String>();
        puntosSedero.add("-2.188480,-81.010379");
        puntosSedero.add("-2.188571,-81.010417");
        puntosSedero.add("-2.188668,-81.010454");
        puntosSedero.add("-2.188770,-81.010492");
        puntosSedero.add("-2.188877,-81.010535");
        puntosSedero.add("-2.189000,-81.010583");
        puntosSedero.add("-2.189129,-81.010610");
        puntosSedero.add("-2.189241,-81.010653");
        puntosSedero.add("-2.189327,-81.010685");
        puntosSedero.add("-2.189424,-81.010722");
        puntosSedero.add("-2.189483,-81.010739");
        puntosSedero.add("-2.189503,-81.010839");
        senderoIngresado.setRecorrido(puntosSedero);

        ArrayList<Senderos> listaSederos = new ArrayList<Senderos>();
        listaSederos.add(senderoIngresado);
        recursoIngresado.setSendero(listaSederos);

        dbaRecurso.save(recursoIngresado);

        Document docretorno = dbaRecurso.devolverDocument(recursoIngresado.getNombre());

        //La imagen la guardo como attached no como objeto Imagen
        mImageToBeAttached = BitmapFactory.decodeResource(getResources(), R.drawable.chocolatera_ver);
        attachImage(docretorno, mImageToBeAttached);
        Log.v("PruebaRegistro", "Recurso Registrado");*/


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splash.this,Inicio_Sesion.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

/*    private void attachImage(Document task, Bitmap image) {
        if (task == null || image == null) return;

        UnsavedRevision revision = task.createRevision();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        revision.setAttachment("image", "image/jpg", in);

        try {
            revision.save();
            Log.v("PruebaRegistro", "Recurso Registrado con imagen");
        } catch (CouchbaseLiteException e) {
            Log.e(this.TAG, "Cannot attach image", e);
        }
    }*/
}
