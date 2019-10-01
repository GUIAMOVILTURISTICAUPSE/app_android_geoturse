package es.codigoandroid.geoturse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.codigoandroid.pojos.Usuario;


import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class Registro extends AppCompatActivity {
    private static final String TAG = "Registro";
    CouchbaseLiteManager<Usuario> dbaUsuario;

    @BindView(R.id.input_name)
    EditText nameText;

    /*@BindView(R.id.input_address)
    EditText addressText;*/
   @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_mobile)
    EditText mobileText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        dbaUsuario = new CouchbaseLiteManager<Usuario>(this, Usuario.class);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),Inicio_Sesion.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        String name = nameText.getText().toString();
       // String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        Usuario usuarioIngresado = new Usuario();
        usuarioIngresado.setEmail(email);
        usuarioIngresado.setNombre(name);
       // usuarioIngresado.setDireccion(address);
        usuarioIngresado.setTelefono(mobile);
        usuarioIngresado.setContraseniaHash(password);
        dbaUsuario.save(usuarioIngresado);
        emailText.setError(null);

        final ProgressDialog progressDialog = new ProgressDialog(Registro.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();



        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Creacion exitosa", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),Inicio_Sesion.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Creacion fallida", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
      //  String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("al menos 3 caracteres");
            valid = false;
        } else {
            nameText.setError(null);
        }

      /*  if (address.isEmpty()) {
            addressText.setError("ingrese una direccion");
            valid = false;
        } else {
            addressText.setError(null);
        }*/

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("ingrese un correo valido");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            mobileText.setError("ingrese un numero valido");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("minimo 4 maximo 10 caracteres alfanumericos");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("contraseñas diferente");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }


        if(dbaUsuario.get(email)==null)
        {
            /*Usuario usuarioIngresado = new Usuario();
            usuarioIngresado.setEmail(email);
            usuarioIngresado.setNombre(name);
            usuarioIngresado.setDireccion(address);
            usuarioIngresado.setTelefono(mobile);
            usuarioIngresado.setContraseniaHash(password);
            dbaUsuario.save(usuarioIngresado);*/
            emailText.setError(null);
        }else{
            //Toast.makeText(this,"El usuario ya existe", Toast.LENGTH_LONG).show();
            emailText.setError("El usuario ya existe");
            valid = false;
            Log.v("Auth Error", "El usuario ya existe");
        }

        return valid;
    }
}