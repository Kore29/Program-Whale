package PageModelNew;

import java.util.List;

public class Publicacion extends Contenido {
    private Integer likes;
    private String hashtag;
    private List<Comentario> comentarios;

    public Publicacion(Integer id_contenido, String autor, String creacion, String multimedia, String texto, Integer likes, String hashtag, List<Comentario> comentarios) {
        super(id_contenido, autor, creacion, multimedia, texto);

        this.likes = (likes != null) ? likes : 0;
        this.hashtag = hashtag;
        this.comentarios = comentarios;

    }

    /** Devuelve el hashtag asociado a la publicación (ej: "#viajes"). */
    public String getHashtag() { return hashtag; }

    /** Devuelve la cantidad de likes que tiene la publicación. */
    public int getLikes() { return likes; }

    /** Devuelve la lista de comentarios de la publicación. */
    public List<Comentario> getComentarios() { return comentarios; }

    /** Reemplaza la lista de comentarios de la publicación. */
    public void addComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

}
