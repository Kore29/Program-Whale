package Utils;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;
import DataBase.DataBase;
import PageModelNew.*;

import java.util.List;
import java.util.Objects;

public class UtilsShow {

//    public static void showPublicaciones(List<Publicacion> publicacion) {
//        for (Publicacion activePublicacion : publicacion) {
//
//            System.out.println("\u001B[33mID:" + activePublicacion.getId() + " | " + activePublicacion.getFecha() + "\u001B[0m");
//            System.out.println(activePublicacion.getText() + "\n \u001B[32m" + activePublicacion.getHashtag() +"\u001B[0m / \u001B[31m" + activePublicacion.getLikes() + " <3\u001B[0m");
//
//            List<Comentario> comentarios = DataBase.getComentarios();
//            for (Comentario comentario : comentarios) {
//                if (Objects.equals(comentario.getId(), activePublicacion.getId())) {
//                    System.out.println("- " + comentario.getAutor() + ": " + comentario.getText());
//                }
//            }
//
//            System.out.println("\u001B[35m+---------------------------------------------------------------+\u001B[0m");
//        }
//    }

    public static void showPublicacion(Publicacion activePublicacion, List<Comentario> activeComentarios) {

        System.out.println("\u001B[33mID:" + activePublicacion.getId() + " | " + activePublicacion.getCreacion() + "\u001B[0m");
        System.out.println(activePublicacion.getTexto() + "\n \u001B[32m" + activePublicacion.getHashtag() +"\u001B[0m / \u001B[31m" + activePublicacion.getLikes() + " <3\u001B[0m");


        for (Comentario comentario : activeComentarios) {
            if (Objects.equals(comentario.getId(), activePublicacion.getId())) {
                System.out.println("- " + comentario.getAutor() + ": " + comentario.getTexto());
            }
        }
        System.out.println("\u001B[35m+---------------------------------------------------------------+\u001B[0m");
    }
}
