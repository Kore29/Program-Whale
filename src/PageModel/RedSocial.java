package PageModel;

import java.util.List;

public class RedSocial {
    private final List<Usuario> usuarios;
    private final List<Comentario> comentarios;
    private final List<Publicacion> publicaciones;

    public RedSocial(List<Usuario> usuarios, List<Comentario> comentarios, List<Publicacion> publicaciones) {
        this.usuarios = usuarios;
        this.comentarios = comentarios;
        this.publicaciones = publicaciones;
    }

    // Usuarios
    public void addUsuario(Usuario usuario) {usuarios.add(usuario);}
    public void addRoot(Usuario usuario) {usuarios.addFirst(usuario);}

    // Publicaciones
    public void addPublicacion(Publicacion publicacion) {publicaciones.add(publicacion);}
    public void removePublicacion(Publicacion publicacion) {publicaciones.remove(publicacion);}

    // Comentarios Start
    public void addComentario(Comentario comentario) {comentarios.add(comentario);}
    public void removeComentario(Comentario comentario) {comentarios.remove(comentario);}
}
