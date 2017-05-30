package es.codigoandroid.geoturse;

import android.*;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Usuario;

public class Inicio_Sesion extends AppCompatActivity {
    private static final String TAG = "Inicio_Sesion";
    private static final int REQUEST_SIGNUP = 0;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    CouchbaseManager<String, Usuario> dbaUsuario;
    private LocationManager locManager;
    AlertDialog alert = null;

    private Database database;

    @Bind(R.id.input_email)
    EditText emailText;
    @Bind(R.id.input_password)
    EditText passwordText;
    @Bind(R.id.btn_login)
    Button loginButton;
    @Bind(R.id.link_signup)
    TextView signupLink;
    @Bind(R.id.link_cambio)
    TextView cambioLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio__sesion);
        ButterKnife.bind(this);

        if(checkAndRequestPermissions()) {
            dbaUsuario = new CouchbaseManager<String, Usuario>(this, Usuario.class);
            database = dbaUsuario.getDbCouchbase();
            try {
                // replace with the IP to use
                URL url = new URL("http://186.3.81.93:4984/db");

                Replication push = database.createPushReplication(url);
                push.setContinuous(true);
                push.start();

                Replication pull = database.createPullReplication(url);
                pull.setContinuous(true);
                pull.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                AlertNoGps();
            }
        }


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        cambioLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Inicio_Sesion.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autentificando...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 5000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        //finish();
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("_usuario", emailText.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Inicio de session fallido", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("ingrese un correo valido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("minimo 4 maximo 10 caracteres alfanumericos");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        Usuario usuarioIngresado = new Usuario();
        usuarioIngresado.setEmail(email);
        usuarioIngresado.setContraseniaHash(password);
        Usuario usuarioAlmacenado = dbaUsuario.get(usuarioIngresado.getEmail());

        if(usuarioAlmacenado!=null)
        {
            if (usuarioAlmacenado.verificarContrasenia(usuarioIngresado.getContraseniaHash())) {
                emailText.setError(null);
                passwordText.setError(null);
            } else {
                passwordText.setError("Password Incorrecto");
                valid = false;
                Log.v("Auth Error", "Password Incorrecto");
            }
        }else{
            emailText.setError("Usuario No Registrado");
            valid = false;
            Log.v("Auth Error", "Usuario No Registrado");
        }

        return valid;
    }

    private void AlertNoGps(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta apagado, para el uso correcto de la aplicacion debe estar encendido. DESEA HABILITARLO?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();
        alert.show();
    }

    private  boolean checkAndRequestPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
