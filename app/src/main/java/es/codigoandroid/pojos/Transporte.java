package es.codigoandroid.pojos;


public class Transporte{
    private String _id;
    private TipoTransporte tipo;
    private boolean obligatorio;
    private Costo costoRecurso;
    private float duracion;
    private float distancia;
    private String descripcion;
    private Estado estado;
    private Imagen imagen;

    public Transporte() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public TipoTransporte getTransporte() {
        return tipo;
    }

    public void setTipoTransporte(TipoTransporte tipo) {
        this.tipo = tipo;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Costo getCostoRecurso() {
        return costoRecurso;
    }

    public void setCostoRecurso(Costo costoRecurso) {
        this.costoRecurso = costoRecurso;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
}
