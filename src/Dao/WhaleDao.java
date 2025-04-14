package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

public interface WhaleDao {
    //void insertUsuario();
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);
    List<String> getAmigos(String nombre);

    void insertPublicacion(Publicacion publicacion);
    void updateLikes(int id);

    List<Publicacion> getSixPublicaciones(int page);
    Publicacion getPublicacionById(int id);
    int sizePublicaciones();

    void insertComentario(Comentario comentario);
    List<Comentario> getComentariosById(int id);


}
