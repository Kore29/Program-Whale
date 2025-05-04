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

    /**Devuelve el identificador único de la publicación.*/
    public Integer getId() {return id_contenido;}

    /**Asigna un identificador único a la publicación.*/
    public void setId(Integer id_contenido) {this.id_contenido = id_contenido;}

    /**Devuelve el nombre del autor de la publicación.*/
    public String getAutor() {return autor;}

    /**Devuelve la fecha y hora en la que se creó la publicación.*/
    public String getCreacion() {return creacion;}


    /**Devuelve el contenido multimedia asociado a la publicación.*/
    public String getMultimedia() {return multimedia;}

    /** Devuelve el texto escrito por el autor en la publicación.*/
    public String getTexto() {return texto;}
}
