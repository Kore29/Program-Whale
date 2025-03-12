package PageModel;


public class Contenido {
    protected Integer id;
    protected String text;
    protected String fecha;
    protected String hashtag;

    public Contenido(Integer id, String text, String fecha) {
        this.id = id;
        this.text = text;
        this.fecha = fecha;
    }

    public Integer getId() {return id;}
    public String getText() {return text;}
    public String getFecha() {return fecha;}
}
