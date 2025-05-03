package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

public interface WhaleDao {
    List<Usuario> getAllUsuarios();
    List<Publicacion> getAllContenido();
    List<String> getAllAmigos();

    void insertUsuario(Usuario usuario);
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);

    void changeName(Usuario usuario, String name);

    List<String> getAmigosByUsuario(String nombre);
    void insertAmigo(String usuario, String nombre);
    void removeAmigo(String usuario, String nombre);

    void insertPublicacion(Publicacion publicacion);
    void removePublicacion(Publicacion publicacion);
    void updateLikes(int id);

    List<Publicacion> getSixPublicaciones(int page);
    List<Publicacion> getPublicacionesByHashTag(String hashtag);
    List<Publicacion> getPublicacionesByUsuario(String nombre);
    Publicacion getPublicacionById(int id);
    int getSizePublicaciones();

    void insertComentario(Comentario comentario);

    List<Comentario> getComentariosByPublicacion(int id);
}
