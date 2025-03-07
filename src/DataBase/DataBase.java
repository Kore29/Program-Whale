package DataBase;

import PageModel.*;
import Utils.UtilsShow;
import PageModel.Usuario.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataBase {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Comentario> comentarios = new ArrayList<>();
    private static List<Publicacion> publicaciones = new ArrayList<>();

    static {
        // All Usuarios
        usuarios.add(new Usuario("Juan", "1234", "juan@example.com", "2025-01-01", null, null));
        usuarios.add(new Usuario("María", "5678", "maria@example.com", "2024-12-15", null, null));
        usuarios.add(new Usuario("Carlos", "abcd", "carlos@example.com", "2023-06-20", null, null));
        usuarios.add(new Usuario("Ana", "efgh", "ana@example.com", "2022-11-11", null, null));
        usuarios.add(new Usuario("Luis", "ijkl", "luis@example.com", "2024-02-28", null, null));
        usuarios.add(new Usuario("Sofía", "mnop", "sofia@example.com", "2025-03-07", null, null));

        // All Comentarios
        comentarios.add(new Comentario(0,"Buena publicacion","12-02-2006",Hashtags.hashtags.get(0), usuarios.get(0).getNombre()));
        comentarios.add(new Comentario(0,"Buena publicacion","12-02-2006",Hashtags.hashtags.get(0), usuarios.get(1).getNombre()));

        // All Publicaciones

    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
}