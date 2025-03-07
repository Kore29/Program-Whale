package Utils;

import PageModel.Publicacion;
import PageModel.Usuario;
import jdk.jshell.execution.Util;

import java.util.List;

public class UtilsShow {

    public static void showPublicaciones(List<Publicacion> publicacion) {
        for (int i=0; i<publicacion.size(); i++) {
            Publicacion activePublicacion = publicacion.get(i);
            System.out.println("----------------------------");
            System.out.println("Fecha "+activePublicacion.getFecha());
            System.out.println("----------------------------");
            System.out.println(activePublicacion.getText());
            System.out.println("("+activePublicacion.getHashtag()+")");
            System.out.println("^_^ "+activePublicacion.getLikes());
        }
    }
    public static void showUser(String name) {
        System.out.println();
    }
}
