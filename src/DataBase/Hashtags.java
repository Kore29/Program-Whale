package DataBase;

import java.util.ArrayList;
import java.util.List;

public class Hashtags {
    public static List<String> hashtags = new ArrayList<>();

    static {
        // Redes Sociales y Contenido
        hashtags.add("#socialmedia");
        hashtags.add("#contentcreator");
        hashtags.add("#influencer");
        hashtags.add("#digitalmarketing");
        hashtags.add("#branding");
        hashtags.add("#viral");
        hashtags.add("#reels");
        hashtags.add("#trending");
        hashtags.add("#photography");
        hashtags.add("#videoediting");

        // Educación y Aprendizaje
        hashtags.add("#education");
        hashtags.add("#learning");
        hashtags.add("#studytips");
        hashtags.add("#selfimprovement");
        hashtags.add("#onlinecourses");
        hashtags.add("#knowledge");
        hashtags.add("#studentlife");
        hashtags.add("#mindset");
        hashtags.add("#books");

        // Negocios y Emprendimiento
        hashtags.add("#business");
        hashtags.add("#entrepreneur");
        hashtags.add("#startup");
        hashtags.add("#marketing");
        hashtags.add("#leadership");
        hashtags.add("#ecommerce");
        hashtags.add("#growthmindset");
        hashtags.add("#innovation");
        hashtags.add("#productivity");
        hashtags.add("#networking");

        // Salud y Bienestar
        hashtags.add("#health");
        hashtags.add("#wellness");
        hashtags.add("#fitness");
        hashtags.add("#mentalhealth");
        hashtags.add("#yoga");
        hashtags.add("#selfcare");
        hashtags.add("#healthylifestyle");
        hashtags.add("#mindfulness");
        hashtags.add("#meditation");
        hashtags.add("#happiness");

        // Ecología y Sustentabilidad
        hashtags.add("#sustainability");
        hashtags.add("#ecofriendly");
        hashtags.add("#gogreen");
        hashtags.add("#climatechange");
        hashtags.add("#nature");
        hashtags.add("#environment");
        hashtags.add("#recycling");
        hashtags.add("#zerowaste");
        hashtags.add("#cleanenergy");
        hashtags.add("#savetheplanet");


    }

    public static void addHashTag(String hashtag) {hashtags.add(hashtag);}
}
