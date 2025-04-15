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


    // ID
    public Integer getId() {
        return id_contenido;
    }
    public void setId(Integer id_contenido) {this.id_contenido = id_contenido;}

    //Autor
    public String getAutor() {
        return autor;
    }

    //Creacion
    public String getCreacion() {
        return creacion;
    }

    //Multimedia
    public String getMultimedia() {
        return multimedia;
    }

    //Texto
    public String getTexto() {return texto;}

}
