package Utils;

import java.util.List;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;

public class UtilsShow {

    /**
     * Muestra por consola una lista de publicaciones con sus respectivos comentarios.
     * Incluye información como ID, fecha de creación, texto, hashtag, número de likes y comentarios.
     *
     * @param activePublicaciones la lista de publicaciones a mostrar.
     */
    public static void showPublicaciones(List<Publicacion> activePublicaciones) {
        for (Publicacion activePublicacion : activePublicaciones) {
            List<Comentario> activeComentarios = activePublicacion.getComentarios();

            System.out.println("\u001B[33mID:" + activePublicacion.getId() + " | " + activePublicacion.getCreacion() + "\u001B[0m");
            System.out.println(activePublicacion.getTexto() + "\n \u001B[32m" + activePublicacion.getHashtag() +"\u001B[0m / \u001B[31m" + activePublicacion.getLikes() + " <3\u001B[0m");

            if (activeComentarios != null) {
                for (Comentario comentario : activeComentarios) {
                    System.out.println("- " + comentario.getAutor() + ": " + comentario.getTexto());
                }
            } else {
                System.out.println("- No hay comentarios");
            }

            System.out.println("\u001B[35m+-----------------------------------------------------------------------------------------------------+\u001B[0m");
        }
    }

    /**
     * Muestra por consola una única publicación con todos sus comentarios.
     * Presenta los mismos datos que el método anterior, pero enfocado a una sola publicación.
     *
     * @param activePublicacion la publicación individual que se desea mostrar.
     */
    public static void showPublicacion(Publicacion activePublicacion) {

            System.out.println("\u001B[33mID:" + activePublicacion.getId() + " | " + activePublicacion.getCreacion() + "\u001B[0m");
            System.out.println(activePublicacion.getTexto() + "\n \u001B[32m" + activePublicacion.getHashtag() +"\u001B[0m / \u001B[31m" + activePublicacion.getLikes() + " <3\u001B[0m");

            for (Comentario comentario : activePublicacion.getComentarios()) {
                System.out.println("- " + comentario.getAutor() + ": " + comentario.getTexto());
            }
            System.out.println("\u001B[35m+-----------------------------------------------------------------------------------------------------+\u001B[0m");
    }
}
