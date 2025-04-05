package PageModelNew;


public class Contenido {
    protected Integer id_contenido;
    protected String autor;
    protected String creacion;
    protected String multimedia;
    protected String texto;

    public Contenido(Integer id_contenido, String autor, String creacion, String multimedia, String texto) {
        this.id_contenido = id_contenido;
        this.autor = autor;
        this.creacion = creacion;
        this.multimedia = multimedia;
        this.texto = texto;
    }

    public Integer getId() {
        return id_contenido;
    }

    public void setId(Integer id_contenido) {
        this.id_contenido = id_contenido;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
