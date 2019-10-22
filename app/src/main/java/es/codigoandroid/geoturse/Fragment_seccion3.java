package es.codigoandroid.geoturse;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.couchbase.lite.Database;
import java.net.MalformedURLException;
import java.net.URL;

import es.codigoandroid.pojos.Usuario;


import es.codigoandroid.datamanager.CouchbaseLiteManager;


public class Fragment_seccion3 extends Fragment {
    CouchbaseLiteManager<Usuario> dbaUsuario;

    private Database database;

    private EditText input_name_edit, input_address_edit, input_email_edit,
            input_mobile_edit, input_password_edit, input_reEnterPassword_edit;
    private Button btn_edit_usuario;
    private Button cerrarSesion;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View vista = inflater.inflate(R.layout.fragment_seccion3, container, false);
        input_name_edit = (EditText) vista.findViewById(R.id.input_name_edit);
        //input_address_edit = (EditText) vista.findViewById(R.id.input_address_edit);
        input_email_edit = (EditText) vista.findViewById(R.id.input_email_edit);
        input_mobile_edit = (EditText) vista.findViewById(R.id.input_mobile_edit);
        input_password_edit = (EditText) vista.findViewById(R.id.input_password_edit);
        input_reEnterPassword_edit = (EditText) vista.findViewById(R.id.input_reEnterPassword_edit);
        btn_edit_usuario = (Button) vista.findViewById(R.id.btn_edit_usuario);





        input_name_edit.setEnabled(false);
     //   input_address_edit.setEnabled(false);
        input_email_edit.setEnabled(false);
        input_mobile_edit.setEnabled(false);
        input_password_edit.setEnabled(false);
        input_reEnterPassword_edit.setEnabled(false);


        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundle = getActivity().getIntent().getExtras();
            if (bundle.getString("_usuario") != null && !bundle.getString("_usuario").equals("")) {
                //input_email_edit.setText(bundle.getString("_usuario"));
                dbaUsuario = new CouchbaseLiteManager<Usuario>(getActivity(), Usuario.class);
                Usuario usuarioIngresado = new Usuario();
                usuarioIngresado.setEmail(bundle.getString("_usuario"));
                Usuario usuarioAlmacenado = dbaUsuario.get(usuarioIngresado.getEmail());
                if(usuarioAlmacenado!=null)
                {
                    input_name_edit.setText(usuarioAlmacenado.getNombre());
                 //   input_address_edit.setText(usuarioAlmacenado.getDireccion());
                    input_email_edit.setText(usuarioAlmacenado.getEmail());
                    input_mobile_edit.setText(usuarioAlmacenado.getTelefono());
                    input_password_edit.setText(usuarioAlmacenado.getContraseniaHash());
                    input_reEnterPassword_edit.setText(usuarioAlmacenado.getContraseniaHash());
                }else{
                    Log.v("Auth Error", "Usuario No Registrado");
                }

            }
        }


        btn_edit_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_edit_usuario.getText().equals("Editar")){
                    AlertDialog alert = null;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("El correo no es permitido editar.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alert = builder.create();
                    alert.show();

                    btn_edit_usuario.setText("Guardar");
                    input_name_edit.setEnabled(true);
                  //  input_address_edit.setEnabled(true);
                    input_email_edit.setEnabled(false);
                    input_mobile_edit.setEnabled(true);
                    input_password_edit.setEnabled(true);
                    input_reEnterPassword_edit.setEnabled(true);

                }else{
                    if (!validate()) {
                        onSignupFailed();
                    }else{
                        Usuario usuarioEditado = new Usuario();
                        usuarioEditado.setEmail(input_email_edit.getText().toString());
                        usuarioEditado.setNombre(input_name_edit.getText().toString());
                     //   usuarioEditado.setDireccion(input_address_edit.getText().toString());
                        usuarioEditado.setTelefono(input_mobile_edit.getText().toString());
                        usuarioEditado.setContraseniaHash(input_password_edit.getText().toString());
                        dbaUsuario.save(usuarioEditado);


                        database = dbaUsuario.getDbCouchbase();
                        dbaUsuario.replicate();

                        Toast.makeText(getActivity(), "Edicion exitosa", Toast.LENGTH_LONG).show();
                        btn_edit_usuario.setText("Editar");
                        input_name_edit.setEnabled(false);
                       // input_address_edit.setEnabled(false);
                        input_email_edit.setEnabled(false);
                        input_mobile_edit.setEnabled(false);
                        input_password_edit.setEnabled(false);
                        input_reEnterPassword_edit.setEnabled(false);
                    }
                }


            }
        });
        //  "Inflamos" el archivo XML correspondiente a esta sección.
        return vista;
    }

    public boolean validate() {
        boolean valid = true;

        String name = input_name_edit.getText().toString();
   //     String address = input_address_edit.getText().toString();
        String email = input_email_edit.getText().toString();
        String mobile = input_mobile_edit.getText().toString();
        String password = input_password_edit.getText().toString();
        String reEnterPassword = input_reEnterPassword_edit.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            input_name_edit.setError("al menos 3 caracteres");
            valid = false;
        } else {
            input_name_edit.setError(null);
        }

    /*    if (address.isEmpty()) {
            input_address_edit.setError("ingrese una direccion");
            valid = false;
        } else {
            input_address_edit.setError(null);
        }*/

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email_edit.setError("ingrese un correo valido");
            valid = false;
        } else {
            input_email_edit.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            input_mobile_edit.setError("ingrese un numero valido");
            valid = false;
        } else {
            input_mobile_edit.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_password_edit.setError("minimo 4 maximo 10 caracteres alfanumericos");
            valid = false;
        } else {
            input_password_edit.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            input_reEnterPassword_edit.setError("contraseñas diferente");
            valid = false;
        } else {
            input_reEnterPassword_edit.setError(null);
        }
        return valid;
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Edicion fallida", Toast.LENGTH_LONG).show();
    }

}
