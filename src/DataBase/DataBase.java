package DataBase;

import PageModel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataBase {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Comentario> comentarios = new ArrayList<>();
    private static List<Publicacion> publicaciones = new ArrayList<>();
    private static RedSocial whale;

    static {
        // Crear usuarios
        Usuario juan = new Usuario("Juan", "root", "juan@example.com", "2025-01-01", null, null);
        Usuario maria = new Usuario("María", "5678", "maria@example.com", "2024-12-15", null, null);
        Usuario carlos = new Usuario("Carlos", "abcd", "carlos@example.com", "2023-06-20", null, null);
        Usuario ana = new Usuario("Ana", "qwerty", "ana@example.com", "2022-03-15", null, null);
        Usuario pedro = new Usuario("Pedro", "zxcvbn", "pedro@example.com", "2021-11-10", null, null);
        Usuario sofia = new Usuario("Sofía", "12345", "sofia@example.com", "2020-08-05", null, null);

        juan.setAmigos(Arrays.asList(maria, carlos));
        maria.setAmigos(List.of(juan, ana));
        carlos.setAmigos(List.of(sofia));
        ana.setAmigos(List.of(pedro));
        pedro.setAmigos(List.of(juan));
        sofia.setAmigos(List.of(carlos, maria));

        usuarios.addAll(Arrays.asList(juan, maria, carlos, ana, pedro, sofia));

        // Crear publicaciones y asignarlas a usuarios
        publicaciones.add(new Publicacion(0, "Leones - Un majestuoso león descansa bajo el sol de la sabana africana. Esta imagen captura su grandeza y tranquilidad, recordándonos la importancia de conservar la fauna salvaje.", "12-02-2005", Hashtags.hashtags.get(2), 3, null, "https://fotoleon"));
        publicaciones.add(new Publicacion(1, "Árboles - Un antiguo roble se alza imponente en medio de un bosque sereno. Sus ramas cuentan historias de siglos pasados, ofreciendo sombra y refugio a innumerables criaturas.", "15-07-2005", Hashtags.hashtags.get(3), 10, null, "https://arbol"));
        publicaciones.add(new Publicacion(2, "Aviones - Un avión surca los cielos al amanecer, dejando una estela de esperanza. La aviación conecta culturas y acorta distancias, haciéndonos sentir que el mundo es más pequeño.", "13-05-2005", Hashtags.hashtags.get(4), 1, null, "https://aviones"));
        publicaciones.add(new Publicacion(3, "Montañas - Las montañas nevadas se elevan majestuosas hacia el cielo, desafiando los límites de la naturaleza. Este paisaje invita a la contemplación y la aventura.", "20-08-2006", Hashtags.hashtags.get(5), 7, null, "https://montanas"));
        publicaciones.add(new Publicacion(4, "Océano - El vasto océano se extiende hasta el horizonte, su marea constante susurra secretos antiguos. Cada ola es una obra de arte en movimiento.", "10-10-2007", Hashtags.hashtags.get(6), 15, null, "https://oceano"));
        publicaciones.add(new Publicacion(5, "Ciudades - La vibrante vida urbana de una ciudad moderna cobra vida bajo las luces nocturnas. Un lugar donde cada esquina cuenta una historia diferente.", "22-03-2008", Hashtags.hashtags.get(7), 5, null, "https://ciudades"));

        juan.setContenido(List.of(publicaciones.get(0), publicaciones.get(4)));
        maria.setContenido(List.of(publicaciones.get(1), publicaciones.get(3)));
        carlos.setContenido(List.of(publicaciones.get(2)));
        ana.setContenido(List.of(publicaciones.get(5)));
        pedro.setContenido(List.of(publicaciones.get(3), publicaciones.get(4)));
        sofia.setContenido(List.of(publicaciones.get(0), publicaciones.get(5)));

        // Crear comentarios
        comentarios.add(new Comentario(0, "Buena publicación", "12-02-2006", Hashtags.hashtags.get(0), juan.getNombre()));
        comentarios.add(new Comentario(1, "No creo que los aviones nos fumiguen", "13-05-2006", Hashtags.hashtags.get(1), maria.getNombre()));
        comentarios.add(new Comentario(2, "Hermoso paisaje de montañas", "20-08-2007", Hashtags.hashtags.get(3), ana.getNombre()));
        comentarios.add(new Comentario(3, "El océano es mi lugar favorito", "10-10-2008", Hashtags.hashtags.get(5), pedro.getNombre()));
        comentarios.add(new Comentario(4, "Las ciudades tienen un encanto especial", "22-03-2009", Hashtags.hashtags.get(7), sofia.getNombre()));
        comentarios.add(new Comentario(5, "Los leones son majestuosos", "12-02-2006", Hashtags.hashtags.get(2), carlos.getNombre()));

        whale = new RedSocial(usuarios, comentarios, publicaciones);
    }

    public static List<Usuario> getUsuarios() {return usuarios;}

    public static List<Comentario> getComentarios() {return comentarios;}
    public static void addComentarios(Comentario comentario) {comentarios.add(comentario);}

    public static List<Publicacion> getPublicaciones() {return publicaciones;}

    public static void addPublicaciones(Publicacion publicacion) {publicaciones.add(publicacion);}

    public static RedSocial getRedSocial() {return whale;}
}