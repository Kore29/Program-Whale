import PageModel.Publicacion;
import Utils.UtilsRed;

import java.util.ArrayList;

public class RedSocial {
    private String usuarios;
    private ArrayList<Publicacion> publicaciones = new ArrayList<>();

    public RedSocial(String usuarios, ArrayList<Publicacion> publicaciones) {
        this.usuarios = usuarios;
        this.publicaciones = publicaciones;
    }

    public static void main(String[] args) {
        String[] comentarios = new String[2];
        Publicacion publicacion = new Publicacion(1,"hola como estás","12-02-2006","#peru",12, comentarios, "https://vueltaporelzoo.com");
    }
}
