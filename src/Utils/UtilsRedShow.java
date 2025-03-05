package Utils;

import PageModel.Publicacion;
import java.util.List;

public class UtilsRedShow {

    public static void showPublicaciones(List<Publicacion> publicacion) {
        for (int i=0; i<publicacion.size(); i++) {
            Publicacion activePublicacion = publicacion.get(i);
            System.out.println(activePublicacion.getPublicacion());
        }
    }
}
