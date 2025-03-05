import PageModel.*;
import Utils.*;

import java.util.ArrayList;
import java.util.List;

import static Utils.UtilsRedCreate.*;

public class main {
    public static void main(String[] args) {
        List<Comentario> comentarios = new ArrayList<>();
        List<Publicacion> publicaciones = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();

        RedSocial whale = new RedSocial(usuarios, comentarios, publicaciones);

        whale.addPublicacion(UtilsExample.example1);

        System.out.println("WHALE");
        System.out.println("Tienes una sesión creada? y/n");

        String sec = sc.nextLine();
        if (sec.equals("y")) System.out.println("Cargando");
        else if (sec.equals("n")) createUsuario();

        UtilsRedShow.showPublicaciones(publicaciones);
        UtilsNavBar.NavBar();
    }
}
