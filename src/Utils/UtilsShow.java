package Utils;

import DataBase.DataBase;
import PageModel.*;

import java.util.List;
import java.util.Objects;

public class UtilsShow {

    public static void showPublicaciones(List<Publicacion> publicacion) {
        for (Publicacion activePublicacion : publicacion) {

            System.out.println("ID:" + activePublicacion.getId() + " | " + activePublicacion.getFecha());
            System.out.println(activePublicacion.getText() + " " + activePublicacion.getHashtag() + " / " + activePublicacion.getLikes() + " <3");


            List<Comentario> comentarios = DataBase.getComentarios();
            for (Comentario comentario : comentarios) {
                if (Objects.equals(comentario.getId(), activePublicacion.getId())) {
                    System.out.println("- " + comentario.getAutor() + ": " + comentario.getText() + " " + comentario.getHashtag());
                }
            }

            System.out.println("------------------------------------------------------");
        }
    }

    public static void showPublicacionesById(List<Publicacion> publicacion, int id) {
        for (Publicacion activePublicacion : publicacion) {
            if (activePublicacion.getId() == id) {
                System.out.println("ID:" + activePublicacion.getId() + " | " + activePublicacion.getFecha());
                System.out.println(activePublicacion.getText() + " " + activePublicacion.getHashtag() + " / " + activePublicacion.getLikes() + " <3");


                List<Comentario> comentarios = DataBase.getComentarios();
                for (Comentario comentario : comentarios) {
                    if (Objects.equals(comentario.getId(), activePublicacion.getId())) {
                        System.out.println("- " + comentario.getAutor() + ": " + comentario.getText() + " " + comentario.getHashtag());
                    }
                }
                System.out.println("------------------------------------------------------");
            }
        }
    }
}
