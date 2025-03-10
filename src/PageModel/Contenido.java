package PageModel;


public class Contenido {
    protected Integer id;
    protected String text;
    protected String fecha;
    protected String hashtag;

    public Contenido(Integer id, String text, String fecha, String hashtag) {
        this.id = id;
        this.text = text;
        this.fecha = fecha;
        this.hashtag = hashtag;
    }

    public Integer getId() {return id;}
    public String getText() {return text;}
    public String getFecha() {return fecha;}
    public String getHashtag() {return hashtag;}
}
