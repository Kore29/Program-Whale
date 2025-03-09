package PageModel;

import java.util.ArrayList;
import java.util.List;

public class Publicacion extends Contenido {
    private Integer likes;
    private String multimedia;

    public Publicacion(Integer id, String text, String fecha, String hashtag, Integer likes, List<Comentario> comentarios, String multimedia) {
        super(id, text, fecha, hashtag);

        this.likes = (likes != null) ? likes : 0;
        this.multimedia = multimedia;
    }

    public Integer getLikes() {return likes;}
    public String getMultimedia() {return multimedia;}

    public void addLike() {this.likes++;}
}
