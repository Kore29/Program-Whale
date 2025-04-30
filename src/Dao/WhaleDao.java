package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;
import java.util.Optional;

public interface WhaleDao {
    void insertUsuario(Usuario usuario);
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);

    void changeName(Usuario usuario, String name);

    List<Usuario> getAllUsuarios();
    List<String> getAmigos(String nombre);
    void insertAmigo(Usuario usuario, String nombre);
    void removeAmigo(Usuario usuario, String nombre);

    void insertPublicacion(Publicacion publicacion);
    void updateLikes(int id);

    List<Publicacion> getSixPublicaciones(int page);
    List<Publicacion> getFilterPublicaciones(String hashtag);
    List<Publicacion> getUsuarioPublicaciones(String nombre);
    Publicacion getPublicacionById(int id);
    int sizePublicaciones();

    void insertComentario(Comentario comentario);
    List<Comentario> getComentariosById(int id);
}
