package es.codigoandroid.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recursos {

    @JsonProperty(value = "_id")
    private String id; //necesito usar para las carpetas
    private String nombre;//Utilizo
    private String descripcion;//Utilizo
    private String informacionGeneral;//Utilizo
    private String direccion;//Utilizo
    private String provincia;
    private String canton; //Utilizo
    private String parroquia;//Utilizo
    private String posicion;//Utilizo
    private Imagen imagenPrincipal;//Utilizo
    private String categoria;
    private String propietario;
    private ArrayList<Imagen> galeria = new ArrayList<Imagen>();//Utilizo
    private ArrayList<Senderos> sendero = new ArrayList<Senderos>();//Utilizo
    private String personaEncargada;
    private String acceso;
    private String infraestructura;

  //  private String url_video;

    private ArrayList<Animacion> animaciones =new ArrayList<Animacion>();


    //private String _id;



    private String _rev;//Utilizo

    private ArrayList<Costo> costoRecursos = new ArrayList<Costo>();
    private ArrayList<AccesibilidadRecurso> opcionesAccesibilidad = new ArrayList<AccesibilidadRecurso>();
    private ArrayList<Facilidad> facilidadRecurso = new ArrayList<Facilidad>();
    private ArrayList<Recomendacion> recomendacion = new ArrayList<Recomendacion>();
    private Contacto infContacto;
    private float ranking;
    private Estado estado;
    private ArrayList<Idiomas> idiomasInformac = new ArrayList<Idiomas>();
    //private ArrayList<String> preguntasFrecuentes = new ArrayList<String>();
    private ArrayList<PreguntaFrecuente> preguntasF = new ArrayList<PreguntaFrecuente>();

    private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    private ArrayList<TipoAtractivo> tipoAtractivo = new ArrayList<TipoAtractivo>();
    private ArrayList<String> tiposParqueo = new ArrayList<>();
    private ArrayList<TipoAccesibilidad> opcionesTipoAccesibilidad = new ArrayList<TipoAccesibilidad>();
    private String horario;
    private String seguridad;




    //constructor por defecto
    public Recursos(){

    }

    public Recursos(String nombre, String direccion, String posicion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.posicion = posicion;
    }




    public ArrayList<Animacion> getAnimaciones() {
        return animaciones;
    }

    public void setAnimaciones(ArrayList<Animacion> animaciones) {
        this.animaciones = animaciones;
    }


    //getters and setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Imagen getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(Imagen imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

      public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInformacionGeneral() {
        return informacionGeneral;
    }

    public void setInformacionGeneral(String informacionGeneral) {
        this.informacionGeneral = informacionGeneral;
    }
    public String getPersonaEncargada() {
        return personaEncargada;
    }

    public void setPersonaEncargada(String personaEncargada) {
        this.personaEncargada = personaEncargada;
    }


    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Costo> getCostoRecursos() {
        return costoRecursos;
    }

    public void setCostoRecursos(ArrayList<Costo> costoRecursos) {
        this.costoRecursos = costoRecursos;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }


    public String getInfraestructura() {       return infraestructura;    }

    public void setInfraestructura(String infraestructura) {
        this.infraestructura = infraestructura;
    }

    public ArrayList<AccesibilidadRecurso> getOpcionesAccesibilidad() {
        return opcionesAccesibilidad;
    }

    public void setOpcionesAccesibilidad(ArrayList<AccesibilidadRecurso> opcionesAccesibilidad) {
        this.opcionesAccesibilidad = opcionesAccesibilidad;
    }

    public ArrayList<Facilidad> getFacilidadRecurso() {
        return facilidadRecurso;
    }

    public void setFacilidadRecurso(ArrayList<Facilidad> facilidadRecurso) {
        this.facilidadRecurso = facilidadRecurso;
    }

    public ArrayList<Recomendacion> getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(ArrayList<Recomendacion> recomendacion) {
        this.recomendacion = recomendacion;
    }

    public Contacto getInfContacto() {
        return infContacto;
    }

    public void setInfContacto(Contacto infContacto) {
        this.infContacto = infContacto;
    }

    public float getRanking() {
        return ranking;
    }

    public void setRanking(float ranking) {
        this.ranking = ranking;
    }

    public ArrayList<Imagen> getGaleria() {
        return galeria;
    }

    public void setGaleria(ArrayList<Imagen> galeria) {
        this.galeria = galeria;
    }


    public ArrayList<Senderos> getSendero() {
        return sendero;
    }

    public void setSendero(ArrayList<Senderos> sendero) {
        this.sendero = sendero;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArrayList<Idiomas> getIdiomasInformac() {
        return idiomasInformac;
    }

    public void setIdiomasInformac(ArrayList<Idiomas> idiomasInformac) {
        this.idiomasInformac = idiomasInformac;
    }


    public ArrayList<PreguntaFrecuente> getPreguntasF() {
        return preguntasF;
    }

    public void setPreguntasF(ArrayList<PreguntaFrecuente> preguntasF) {
        this.preguntasF = preguntasF;
    }



    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    //otros metodos
    public void obtenerRuta( String posicion){

    }

    public void verReviews(){
    }

    public void llamarContacto(){
    }

    public void calcularRankingTotal(){

    }

    public void agregarImagen(){
    }

    public void reportarMalaUbicacion(){
    }

    public void agregarRecomendacion(){

    }
    public void verSenderos(){

    }
    public void verGaleria(){

    }
    public void comentar(){

    }
    public void sugerirCambio(){

    }
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSeguridad() {
        return seguridad;
    }

    public void setSeguridad(String seguridad) {
        this.seguridad = seguridad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public ArrayList<String> getTiposParqueo() {
        return tiposParqueo;
    }

    public void setTiposParqueo(ArrayList<String> tiposParqueo) {
        this.tiposParqueo = tiposParqueo;
    }

   /* public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {
        this.url_video = url_video;
    }
*/



    public double latitud(){
        double latitud;
        latitud=0.00;
        String string = posicion;
        String letras="abcdefghyjklmnñopqrstuvwxyzABCDEFGHYJKLMNÑOPQRSTUVWXYZ";


        char[] arrayChar = posicion.toCharArray();

            if(arrayChar.length>5){
                String[] parts = string.split(",");
                String part1 = parts[0];
                if(!part1.isEmpty()) {
                    for (int i = 0; i < part1.length(); i++) {
                        if (letras.indexOf(part1.charAt(i), 0) != -1) {
                            return 0.0;
                        }
                    }
                    latitud = Double.parseDouble(part1);
                    return latitud;
                }
                else{
                    return 0.0;
                }
            }
            else{
                return 0.0;
            }

    }

    public double longuitd(){
        double longuitd;
        longuitd=0.00;
        String string = posicion;
        String letras="abcdefghyjklmnñopqrstuvwxyzABCDEFGHYJKLMNÑOPQRSTUVWXYZ";

        char[] arrayChar = posicion.toCharArray();

            if(arrayChar.length>10){
                String[] parts = string.split(",");
                String part1 = parts[1];
                if(!part1.isEmpty()) {
                    for (int i = 0; i < part1.length(); i++) {
                        if (letras.indexOf(part1.charAt(i), 0) != -1) {
                            return 0.0;
                        }
                    }
                longuitd = Double.parseDouble(part1);
                return longuitd;
                }else{
                    return 0.0;
                }
                }
            else{
                return 0.0;
            }

    }



}
