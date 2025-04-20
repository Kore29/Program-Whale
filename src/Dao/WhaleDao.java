package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

public interface WhaleDao {
    void insertUsuario(Usuario usuario);
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);
    List<String> getAmigos(String nombre);
    void changeName(Usuario usuario, String name);

    void insertPublicacion(Publicacion publicacion);
    void updateLikes(int id);

    List<Publicacion> getSixPublicaciones(int page);
    List<Publicacion> getFilterPublicaciones(String hashtag);
    Publicacion getPublicacionById(int id);
    int sizePublicaciones();

    void insertComentario(Comentario comentario);
    List<Comentario> getComentariosById(int id);


}
