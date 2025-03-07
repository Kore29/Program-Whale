package Utils.DataBase;
import PageModel.*;

import java.util.Arrays;
import java.util.List;

public class UtilsExample {
    public static List<String> hashtag1 = Arrays.asList(UtilsHashtags.hashtags.get(0),UtilsHashtags.hashtags.get(1),UtilsHashtags.hashtags.get(2));
    public static List<String> comentario1 = Arrays.asList("No me gusta, dislike","me parece fuera de lugar","En españa se come mejor");
    public static Publicacion example1 = new Publicacion(1,"hola como estás","12-02-2006",hashtag1,12, comentario1, "https://vueltaporelzoo.com");
}
