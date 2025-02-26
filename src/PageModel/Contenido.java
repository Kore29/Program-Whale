package PageModel;
import java.util.List;

abstract class Contenido {
    protected Integer id;
    protected String text;
    protected String fecha;
    protected List<String> hashtag;

    public Contenido(Integer id, String text, String fecha, List<String> hashtag) {
        this.id = id;
        this.text = text;
        this.fecha = fecha;
        this.hashtag = hashtag;
    }

    public Integer getId() {return id;}
    public String getText() {return text;}
    public String getFecha() {return fecha;}
    public List<String> getHashtag() {return hashtag;}
}
