package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;

import java.util.List;

public interface WhaleDao {
    Publicacion getPublicacionById(int id);
    Comentario getComentarioByIds(int id_con, int id_ref);
}
