package es.codigoandroid.pojos;

/**
 * Created by Alvaro on 21/12/2016.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    //@JsonProperty(value = "_id")
    //private String docId;


    private String _rev;

    @JsonProperty(value = "_id")
    private String email;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contraseniaHash;

    public Usuario()
    {}

    public Usuario(String email) {
        this.email = email;
        this.nombre = "";
        this.direccion = "";
        this.telefono = "";
        this.contraseniaHash = "";
    }

    /*
    public String getDocId() {
        return docId;
    }*/

    //@JsonAnyGetter
    public String getEmail() {
        return email;
    }

    // @JsonAnySetter
    public void setEmail(String email) {
        this.email = email;
    }

    // @JsonAnyGetter
    public String getNombre() {
        return nombre;
    }

    //@JsonAnySetter
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //@JsonAnyGetter
    public String getDireccion() {
        return direccion;
    }

    // @JsonAnySetter
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    //@JsonAnyGetter
    public String getTelefono() {
        return telefono;
    }

    // @JsonAnySetter
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // @JsonAnyGetter
    public String getContraseniaHash() {
        return contraseniaHash;
    }

    //@JsonAnySetter
    public void setContraseniaHash(String contraseniaHash) { this.contraseniaHash = contraseniaHash;}


    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }



    public boolean verificarContrasenia(String otraContrasenia)
    {
        return this.getContraseniaHash().equals(otraContrasenia.trim());
    }


    public void hashPassword()
    {
        //TODO Crear funcion de Hashing para no almacenar la contrasena en seco
        //Mecanismo de Hash
    }


    @Override
    public String toString() {
        return "Usuario [email=" + email + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
                + ", contraseniaHash=" + contraseniaHash + "]";
    }
}
