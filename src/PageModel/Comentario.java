package PageModel;

public class Comentario extends Contenido {
    private String autor;

    public Comentario(Integer id, String text, String fecha, String hashtag, String autor) {
        super(id, text, fecha, hashtag);

        this.autor = autor;
    }

    public String getAutor() {return autor;}

}
