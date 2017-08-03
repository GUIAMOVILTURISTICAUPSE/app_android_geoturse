package es.codigoandroid.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Senderos {
    @JsonProperty(value = "_id")
    private String _id;
    //recorrido
    private ArrayList<String> recorrido = new ArrayList<String>();//Utilizo
    //
    private ArrayList<Atractivo> atractivos = new ArrayList<Atractivo>();
    private Float duracion;
    private Float distancia;
    private DificultadRecorrido dificultad;
    private ArrayList<LocacionAtractivo> locacionAtractivos = new ArrayList<LocacionAtractivo>();
    private Estado estado;
    private String instrucciones;
    private ArrayList<Transporte> transporte = new ArrayList<Transporte>();
    private ArrayList<String> equipamento = new ArrayList<String>();
    private ArrayList<Imagen> galeria = new ArrayList<Imagen>();
    private Imagen imagenPrincipal;
    private Animacion animacion;
    private String nombre;
    private String descripcion;
    private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    private DisponibilidadCelular disponibilidadSenalCelular;

    //Constructor por defecto
    public Senderos() {
    }

    //getter and setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public ArrayList<String> getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(ArrayList<String> recorrido) {
        this.recorrido = recorrido;
    }

    public ArrayList<Atractivo> getAtractivos() {
        return atractivos;
    }

    public void setAtractivos(ArrayList<Atractivo> atractivos) {
        this.atractivos = atractivos;
    }

    public Float getDuracion() {
        return duracion;
    }

    public void setDuracion(Float duracion) {
        this.duracion = duracion;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }

    public DificultadRecorrido getDificultad() {
        return dificultad;
    }

    public void setDificultad(DificultadRecorrido dificultad) {
        this.dificultad = dificultad;
    }

    public ArrayList<LocacionAtractivo> getLocacionAtractivos() {
        return locacionAtractivos;
    }

    public void setLocacionAtractivos(ArrayList<LocacionAtractivo> locacionAtractivos) {
        this.locacionAtractivos = locacionAtractivos;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public ArrayList<Transporte> getTransporte() {
        return transporte;
    }

    public void setTransporte(ArrayList<Transporte> transporte) {
        this.transporte = transporte;
    }

    public ArrayList<String> getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(ArrayList<String> equipamento) {
        this.equipamento = equipamento;
    }

    public ArrayList<Imagen> getGaleria() {
        return galeria;
    }

    public void setGaleria(ArrayList<Imagen> galeria) {
        this.galeria = galeria;
    }

    public Imagen getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(Imagen imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public Animacion getAnimacion() {
        return animacion;
    }

    public void setAnimacion(Animacion animacion) {
        this.animacion = animacion;
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

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public DisponibilidadCelular getDisponibilidadSenalCelular() {
        return disponibilidadSenalCelular;
    }

    public void setDisponibilidadSenalCelular(DisponibilidadCelular disponibilidadSenalCelular) {
        this.disponibilidadSenalCelular = disponibilidadSenalCelular;
    }

    //Metodos
    public void comentar(){

    }
    public void subirImagen(Imagen imagen){

    }
    public void mostrarRuta(){

    }
    public void mostrarAtractivos(){

    }
    public void mostrarMediosTransporte(){

    }
    public void verAnimacion(Animacion animacion){

    }




}
