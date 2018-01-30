package es.codigoandroid.pojos;



public class Animacion {
    private String _id;
    private TipoAnimacion tipo;
    private String titulo;
    private String url;
    private String descripcion;

    private String path;


    public Animacion() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public TipoAnimacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoAnimacion tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
