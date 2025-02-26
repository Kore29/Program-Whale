import PageModel.*;

import java.util.List;

public class RedSocial {
    private String usuarios;
    private List<Comentario> comentarios;
    private List<Publicacion> publicaciones;

    public RedSocial(String usuarios, List<Comentario> comentarios, List<Publicacion> publicaciones) {
        this.usuarios = usuarios;
        this.comentarios = comentarios;
        this.publicaciones = publicaciones;
    }

    public void getPublicaciones() {
        for (int i=0; i<publicaciones.size(); i++) {
            System.out.println(publicaciones.get(0));;
        }
   }

    public void addPublicacion(Publicacion pub) {
        publicaciones.add(pub);
    }

    public List<String> getComentarios(Publicacion pub) {
        return pub.getComentarios();
    }

}
