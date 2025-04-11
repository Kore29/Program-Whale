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

    public String getHashtag() {return hashtag;}

    public int getLikes() {return likes;}
    public void addLike() {this.likes++;}

    public List<Comentario> getComentarios() {return comentarios;}
    public void removeComentarios(int id) {comentarios.remove(id);}
    public void addComentario(Comentario comentario) {comentarios.add(comentario);}
    public void addComentarios(List<Comentario> comentarios) {this.comentarios =  comentarios;}
}
