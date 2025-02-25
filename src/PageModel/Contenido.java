package PageModel;

abstract class Contenido {
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

class Comentario extends Contenido {
    private String autor;

    public Comentario(Integer id, String text, String fecha, String hashtag, String autor) {
        super(id, text, fecha, hashtag);

        this.autor = autor;
    }

    public String getAutor() {return autor;}
}

class Publicacion extends Contenido {
    private Integer likes;
    private String[] comentarios;
    private String multimedia;

    public Publicacion(Integer id, String text, String fecha, String hashtag, Integer likes, String[] comentarios, String multimedia) {
        super(id, text, fecha, hashtag);

        this.likes = likes;
        this.comentarios = comentarios;
        this.multimedia = multimedia;
    }

    public Integer getLikes() {return likes;}
    public String[] getComentarios {return comentarios;}
    public String getAutor() {return autor.toString();}
}
