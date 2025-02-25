package PageModel;

public class Publicacion extends Contenido {
    private Integer likes;
    private String[] comentarios;
    private String multimedia;

    public Publicacion(Integer id, String text, String fecha, String hashtag, Integer likes, String[] comentarios, String multimedia) {
        super(id, text, fecha, hashtag);

        this.likes = likes;
        this.comentarios = comentarios;
        this.multimedia = multimedia;
    }

    public Integer getLikes() {return likes;}
    public String[] getComentarios() {return comentarios;}
    public String getAutor() {return multimedia;}
}
