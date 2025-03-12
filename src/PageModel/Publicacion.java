package PageModel;

import java.util.List;

public class Publicacion extends Contenido {
    private Integer likes;
    private String hashtag;
    private String multimedia;

    public Publicacion(Integer id, String text, String fecha, String hashtag, Integer likes, List<Comentario> comentarios, String multimedia) {
        super(id, text, fecha);

        this.likes = (likes != null) ? likes : 0;
        this.hashtag = hashtag;
        this.multimedia = multimedia;

    }

    public Integer getLikes() {return likes;}
    public String getMultimedia() {return multimedia;}
    public String getHashtag() {return hashtag;}

    public void addLike() {this.likes++;}
}
