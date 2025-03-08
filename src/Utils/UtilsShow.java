package Utils;

import DataBase.DataBase;
import PageModel.Comentario;
import PageModel.Publicacion;
import java.util.List;

public class UtilsShow {

    public static void showPublicaciones(List<Publicacion> publicacion) {
        for (int i=0; i<publicacion.size(); i++) {

            Publicacion activePublicacion = publicacion.get(i);

            System.out.println("ID:"+activePublicacion.getId()+" | "+activePublicacion.getFecha());
            System.out.println(activePublicacion.getText()+" "+activePublicacion.getHashtag()+" / "+activePublicacion.getLikes()+" <3");


            List<Comentario> comentarios = DataBase.getComentarios();
            for (int j=0; j<comentarios.size(); j++) {
                if (comentarios.get(j).getId() == activePublicacion.getId()) {
                    System.out.println("- "+comentarios.get(j).getAutor()+": "+comentarios.get(j).getText()+" "+comentarios.get(j).getHashtag());
                }
            }

            System.out.println("------------------------------------------------------");
        }
    }

    public static void showPublicacionesById(List<Publicacion> publicacion, int id) {
        for (int i=0; i<publicacion.size(); i++) {
            Publicacion activePublicacion = publicacion.get(i);

            if (activePublicacion.getId() == id) {
                System.out.println("ID:" + activePublicacion.getId() + " | " + activePublicacion.getFecha());
                System.out.println(activePublicacion.getText() + " " + activePublicacion.getHashtag() + " / " + activePublicacion.getLikes() + " <3");


                List<Comentario> comentarios = DataBase.getComentarios();
                for (int j = 0; j < comentarios.size(); j++) {
                    if (comentarios.get(j).getId() == activePublicacion.getId()) {
                        System.out.println("- " + comentarios.get(j).getAutor() + ": " + comentarios.get(j).getText() + " " + comentarios.get(j).getHashtag());
                    }
                }
                System.out.println("------------------------------------------------------");
            }
        }
    }
}
