package Utils;

import PageModelNew.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static App.Main.*;
import static Utils.UtilsColors.c;
import static Utils.UtilsColors.r;

public class UtilsApp {

    /**
     * Solicita al usuario un nuevo nombre y lo devuelve.
     * @param usuario el usuario que desea cambiar su nombre.
     * @return el nuevo nombre introducido por el usuario.
     */
    public static String changeNombre(Usuario usuario) {
        System.out.println("Introduce el nombre por el que deseas cambiar: ");
        String tempNomb = sc.nextLine();
        System.out.println("Nombre cambiado: " + tempNomb);
        return tempNomb;
    }

    /**
     * Solicita al usuario el ID de una publicación a eliminar y la elimina si se confirma.
     * @param publicacions lista de publicaciones del usuario.
     * @return la publicación eliminada si se elimina con éxito, o null si se cancela la operación.
     */
    public static Publicacion deletePublicacion(List<Publicacion> publicacions) {
        while (true) {
            System.out.println("("+c[4]+"'salir'"+r+" para cancelar)");
            System.out.print("Introduce el ID de la publicación que deseas eliminar: ");

            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("salir")) {
                System.out.println("Operación cancelada.");
                break;
            }

            int id = 0;
            if (UtilsCheck.checkInt(input).isEmpty()) id = Integer.parseInt(input);
            else {
                System.out.println("Caracter Invalido");
                continue;
            }

            Publicacion publicacionAEliminar = null;
            for (Publicacion publicacion : publicacions) {
                if (publicacion.getId() == id) {
                    publicacionAEliminar = publicacion;
                    break;
                }
            }

            if (publicacionAEliminar == null) {
                System.out.println("No se encontró ninguna publicación con ese ID.");
                continue;
            }

            System.out.print("¿Estás seguro de que quieres eliminar la publicación con ID " + id + "? (s/n): ");
            String confirm = sc.nextLine().trim();

            if (confirm.equalsIgnoreCase("s")) {

                publicacions.remove(publicacionAEliminar);
                System.out.println("Publicación eliminada con éxito.");
                return publicacionAEliminar;
            } else {
                System.out.println("Operación cancelada.");
                break;
            }
        }

        return null;
    }

    /**
     * Muestra la lista de amigos del usuario y solicita el nombre de uno para eliminarlo.
     * @param usuario el usuario que desea eliminar un amigo.
     * @return el nombre del amigo a eliminar, o una cadena vacía si no hay amigos o hay un error.
     */
    public static String deleteAmigo(Usuario usuario) {
        if (usuario.getAmigos().isEmpty()) {
            System.out.println("No tienes amigos en tu lista.");
            return "";
        }

        StringBuilder fr = new StringBuilder("Amigos: ");
        for (String iter : usuario.getAmigos()) {
            fr.append(iter).append(", ");
        }
        System.out.println(fr.substring(0, Math.max(0, fr.length() - 2)));

        while (true) {
            System.out.print("Introduce el nombre del amigo que quieras eliminar: ");
            String tempAmig = sc.nextLine().trim();

            if (tempAmig.isEmpty()) {
                System.out.println("\u001B[31mError: Nombre vacío\u001B[0m");
                continue;
            }

            if (usuario.isAmigo(tempAmig)) {
                return tempAmig;
            }
        }
    }

    /**
     * Recomienda amigos al usuario y solicita el nombre de uno para agregar.
     * @param usuario el usuario que quiere agregar un nuevo amigo.
     * @return el nombre del nuevo amigo si se confirma la operación, o una cadena vacía si se cancela o hay un error.
     */
    public static String includeAmigo(Usuario usuario) {
        List<String> amigos = whaleDao.getAmigosByUsuario(usuario.getNombre());
        usuario.setAmigos(amigos);

        List<Usuario> candidatos = whaleDao.getAllUsuarios().stream()
                .filter(u -> !u.getNombre().equalsIgnoreCase(usuario.getNombre()))
                .filter(u -> !amigos.contains(u.getNombre()))
                .collect(Collectors.toList());

        if (candidatos.isEmpty()) {
            System.out.println("No hay usuarios disponibles para agregar como amigos.");
            return "";
        }

        Collections.shuffle(candidatos);
        List<Usuario> recomendados = candidatos.subList(0, Math.min(5, candidatos.size()));

        System.out.println("Usuarios recomendados:");
        for (Usuario u : recomendados) {
            System.out.println("- " + u.getNombre() + ", desde: " + u.getCreacion());
        }

        System.out.println("("+c[4]+"'salir'"+r+" para cancelar)");
        System.out.print("\nIntroduce el nombre del usuario a agregar: ");
        String input = sc.nextLine().trim();

        if (input.equalsIgnoreCase("salir")) {
            System.out.println("Operación cancelada.");
            return "";
        }

        for (Usuario u : recomendados) {
            if (u.getNombre().equalsIgnoreCase(input)) {
                System.out.print("¿Agregar a " + u.getNombre() + " como amigo? (s/n): ");
                String confirm = sc.nextLine().trim();
                if (confirm.equalsIgnoreCase("s")) {
                    System.out.println("¡Amigo agregado con éxito!");
                    return u.getNombre();
                } else {
                    System.out.println("Operación cancelada.");
                    return "";
                }
            }
        }

        System.out.println("Nombre no válido.");
        return "";
    }

    /**
     * Elimina todas las palabras que empiezan con '#' del texto dado.
     * @param text el texto que puede contener hashtags.
     * @return el mismo texto sin palabras que contengan hashtags (palabras que empiecen por '#').
     */
    public static String removeHashTag(String text) {
        StringBuilder newText = new StringBuilder();
        for (String e : text.split(" ")) {
            if (!e.startsWith("#")) {
                newText.append(e).append(" ");
            }
        }
        return newText.toString().trim();
    }
}