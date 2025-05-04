package PageModelNew;

import java.util.List;

public class Comentario extends Contenido {
    private Integer id_referencia;

    public Comentario(Integer id_contenido, String autor, String creacion, String multimedia, String texto, Integer id_referencia) {
        super(id_contenido, autor, creacion, multimedia, texto);

        this.id_referencia = id_referencia;
    }

    /**Devuelve el ID de la publicación a la que esta hace referencia.*/
    public int getIdReferencia() {return id_referencia;}
}
