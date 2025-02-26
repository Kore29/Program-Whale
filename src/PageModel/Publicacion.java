package PageModel;
import Utils.UtilsExample;

import java.util.List;

public class Publicacion extends Contenido {
    private Integer likes;
    private List<String> comentarios;
    private String multimedia;

    public Publicacion(Integer id, String text, String fecha, List<String> hashtag, Integer likes, List<String> comentarios, String multimedia) {
        super(id, text, fecha, hashtag);

        this.likes = likes;
        this.comentarios = comentarios;
        this.multimedia = multimedia;
    }

    public Integer getLikes() {return likes;}
    public List<String> getComentarios() {return comentarios;}
    public String getMultimedia() {return multimedia;}

    public String getPublicacion() {
        return ("Id: "+id+", Likes: "+likes+" Texto: "+text+" Fecha: "+fecha+", Comentario: "+getComentarios()+", HashTag: "+hashtag);
    }
}
