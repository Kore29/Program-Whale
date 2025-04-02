package PageModelNew;

public class Publicacion extends Contenido {
    private Integer likes;
    private String hashtag;

    public Publicacion(Integer id_contenido, String autor, String creacion, String multimedia, String texto, Integer likes, String hashtag) {
        super(id_contenido, autor, creacion, multimedia, texto);

        this.likes = (likes != null) ? likes : 0;
        this.hashtag = hashtag;

    }

    public String getHashtag() {return hashtag;}
    public int getLikes() {return likes;}
    public void addLike() {this.likes++;}
}
