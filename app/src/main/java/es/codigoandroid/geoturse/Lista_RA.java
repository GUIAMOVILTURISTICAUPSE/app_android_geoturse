package es.codigoandroid.geoturse;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import es.codigoandroid.pojos.Recursos;

import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class Lista_RA extends AppCompatActivity {
    CouchbaseLiteManager<Recursos> dbaRecurso;
    private String[] listaRA, numero_RA;
    public Recursos recursoAlmacenado;


    //Para los permisos
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_video);
        dbaRecurso = new CouchbaseLiteManager<Recursos>(this, Recursos.class);
        if (checkPermission()) {

            mostrarOpcionesRealidadAumentada();

        } else {
            requestPermission();
        }


    }

    private void mostrarOpcionesRealidadAumentada()
    {
        final String mostrarR = getIntent().getExtras().getString("recurso");
        ListView listav= (ListView) findViewById(R.id.listav);

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
            //if(recursoAlmacenado.getId().equals(null))
            //     arExperience = "Megaterio";
            // else
            if(recursoAlmacenado!=null ) {
                if(recursoAlmacenado.getId()==null || recursoAlmacenado.getId().equals(null) || recursoAlmacenado.getId().isEmpty() || recursoAlmacenado.getId()=="null") {
                    arExperience = "Megaterio";
                }else {
                    arExperience = recursoAlmacenado.getId();
                }
                numero_RA[0] = "Realidad aumentada con localización";
                listaRA[0]= "file:///android_asset/"+ arExperience + "/localizacion.html";

                numero_RA[1] = "Realidad aumentada Boton Scanear";
                listaRA[1]= "file:///android_asset/"+ arExperience + "/botonScaner.html";

                numero_RA[2] = "Realidad aumentada Scaneo Continuo";
                listaRA[2]= "file:///android_asset/"+ arExperience + "/scaneoContinuo.html";

            }else{
                Toast.makeText(this, "No se puede cargar Modulo de REalidad Aumentada por Recurso vacio o sin Id",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Toast.makeText(this, "Error en la lista de recursos RA"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        //  arExperience = "Megaterio";

    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    mostrarOpcionesRealidadAumentada();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }




}
