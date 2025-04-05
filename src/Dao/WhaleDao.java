package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;

import java.util.List;

public interface WhaleDao {
    void getAllPublicaciones();
    Publicacion getPublicacionById(int id);
    List<Comentario> getComentariosByReferencia(int idRef);
}
