import PageModel.*;
import Utils.*;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Comentario> comentarios = new ArrayList<>();
        List<Publicacion> publicaciones = new ArrayList<>();

        RedSocial rs = new RedSocial("", comentarios, publicaciones);
        rs.addPublicacion(UtilsExample.example1);

        rs.getPublicaciones();

        System.out.println(UtilsExample.example1.getPublicacion());

    }
}
