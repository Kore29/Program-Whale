import PageModel.*;

import java.util.List;
import java.util.Scanner;

public class RedSocial {
    private List<Usuario> usuarios;
    private List<Comentario> comentarios;
    private List<Publicacion> publicaciones;

    public RedSocial(List<Usuario> usuarios, List<Comentario> comentarios, List<Publicacion> publicaciones) {
        this.usuarios = usuarios;
        this.comentarios = comentarios;
        this.publicaciones = publicaciones;
    }

    // Usuarios Start
    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    // Usuarios End

    // Publicaciones Start
    public void addPublicacion(Publicacion publicacion) {
        publicaciones.add(publicacion);
    }
    public void removePublicacion(Publicacion publicacion) {
        publicaciones.remove(publicacion);
    }
    // Publicaciones End

    // Comentarios Start
    public List<String> getComentarios(Publicacion publicacion) {
        return publicacion.getComentarios();
    }
    // Comentarios End

}
