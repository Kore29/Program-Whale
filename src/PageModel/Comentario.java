package PageModel;

public class Comentario extends Contenido {
    private String autor;

    public Comentario(Integer id, String text, String fecha, String autor) {
        super(id, text, fecha);

        this.autor = autor;
    }

    public String getAutor() {return autor;}

}
