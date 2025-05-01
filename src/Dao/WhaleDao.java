package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;
import java.util.Optional;

public interface WhaleDao {
    List<Usuario> getAllUsuarios();
    List<Publicacion> getAllContenido();
    List<String> getAllAmigos(String nombre);

    void insertUsuario(Usuario usuario);
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);

    void changeName(Usuario usuario, String name);

    void insertAmigo(String usuario, String nombre);
    void removeAmigo(String usuario, String nombre);

    void insertPublicacion(Publicacion publicacion);
    void updateLikes(int id);

    List<Publicacion> getSixPublicaciones(int page);
    List<Publicacion> getPublicacionesByHashTag(String hashtag);
    List<Publicacion> getPublicacionesByUsuario(String nombre);
    Publicacion getPublicacionById(int id);
    int getSizePublicaciones();

    void insertComentario(Comentario comentario);

    List<Comentario> getComentariosById(int id);
    // List<Comentario> getComentariosByPublicaciones(Publicacion publicacion);
}
