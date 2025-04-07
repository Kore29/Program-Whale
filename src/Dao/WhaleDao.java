package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

public interface WhaleDao {
    //void insertUsuario();
    Usuario getUsuarioByEmail(String email);
    Usuario getUsuarioByName(String name);

    //void insertPublicacion();
    void getAllPublicaciones();
    Publicacion getPublicacionById(int id);

    //void insertComentario(int id);
    List<Comentario> getComentariosById(int idRef);
}
