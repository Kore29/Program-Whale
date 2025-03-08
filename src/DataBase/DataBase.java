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
    private static RedSocial whale;

    static {
        // Crear usuarios
        Usuario juan = new Usuario("Juan", "root", "juan@example.com", "2025-01-01", null, null);
        Usuario maria = new Usuario("María", "5678", "maria@example.com", "2024-12-15", null, null);
        Usuario carlos = new Usuario("Carlos", "abcd", "carlos@example.com", "2023-06-20", null, null);

        juan.setAmigos(Arrays.asList(maria, carlos));
        maria.setAmigos(List.of(juan));

        usuarios.addAll(Arrays.asList(juan, maria, carlos));

        // Crear publicaciones y asignarlas a usuarios
        publicaciones.add(new Publicacion(0, "Leones", "12-02-2005", Hashtags.hashtags.get(2), 3, null, "https://fotoleon"));
        publicaciones.add(new Publicacion(1, "Árboles", "15-07-2005", Hashtags.hashtags.get(3), 10, null, "https://arbol"));
        publicaciones.add(new Publicacion(2, "Aviones", "13-05-2005", Hashtags.hashtags.get(4), 1, null, "https://aviones"));

        juan.setContenido(List.of(publicaciones.get(0)));
        maria.setContenido(List.of(publicaciones.get(1)));
        carlos.setContenido(List.of(publicaciones.get(2)));

        // Crear comentarios
        comentarios.add(new Comentario(0, "Buena publicación", "12-02-2006", Hashtags.hashtags.get(0), juan.getNombre()));
        comentarios.add(new Comentario(1, "No creo que los aviones nos fumiguen", "13-05-2006", Hashtags.hashtags.get(1), maria.getNombre()));

        whale = new RedSocial(usuarios,comentarios,publicaciones);
    }

    public static List<Usuario> getUsuarios() {return usuarios;}

    public static List<Comentario> getComentarios() {return comentarios;}
    public static void addComentarios(Comentario comentario) {comentarios.add(comentario);}

    public static List<Publicacion> getPublicaciones() {return publicaciones;}
    public static void addPublicaciones(Publicacion publicacion) {publicaciones.add(publicacion);}

    public static RedSocial getRedSocial() {return whale;}
}