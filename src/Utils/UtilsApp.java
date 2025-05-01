package Utils;

import PageModelNew.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static App.Main.*;

public class UtilsApp {

    public static String changeNombre(Usuario usuario) {
        System.out.println("Introduce el nombre por el que deseas cambiar: ");
        String tempNomb = sc.nextLine();
        System.out.println("Nombre cambiado: " + tempNomb);
        return tempNomb;
    }

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

    public static String includeAmigo(Usuario usuario) {
        List<String> amigosActuales = whaleDao.getAllAmigos(usuario.getNombre());
        usuario.setAmigos(amigosActuales);

        List<Usuario> todosUsuarios = whaleDao.getAllUsuarios().stream()
                .filter(u -> !u.getNombre().equalsIgnoreCase(usuario.getNombre()))   // no tú mismo
                .filter(u -> !amigosActuales.contains(u.getNombre()))                // ni quienes ya son tu amigo
                .collect(Collectors.toList());

        if (todosUsuarios.isEmpty()) {
            System.out.println(UtilsColors.c[1] + "No hay usuarios disponibles para agregar como amigos." + UtilsColors.r);
            return "";
        }

        Collections.shuffle(todosUsuarios);
        List<Usuario> recomendados = todosUsuarios.subList(0, Math.min(5, todosUsuarios.size()));

        System.out.println(" USUARIOS QUE TE PUEDEN INTERESAR ");

        for (int i = 0; i < recomendados.size(); i++) {
            Usuario recomendado = recomendados.get(i);
            System.out.printf("%s%d%s. %s%s%s - Miembro desde: %s\n",
                    UtilsColors.c[4], (i + 1), UtilsColors.r,
                    UtilsColors.c[6], recomendado.getNombre(), UtilsColors.r,
                    recomendado.getCreacion());
        }

        while (true) {
            System.out.print("\nIntroduce el nombre del usuario a agregar ('salir' para cancelar): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("salir")) {
                System.out.println("Operación cancelada.");
                return "";
            }

            if (input.isEmpty() || UtilsCheck.checkNombre(input).isEmpty()) {
                System.out.println(UtilsColors.c[1] + "Error: Nombre inválido" + UtilsColors.r);
                continue;
            }

            Optional<Usuario> seleccionado = recomendados.stream()
                    .filter(u -> u.getNombre().equalsIgnoreCase(input))
                    .findFirst();

            if (seleccionado.isPresent()) {
                Usuario amigo = seleccionado.get();
                System.out.printf("%s¿Agregar a %s como amigo? (s/n): %s",
                        UtilsColors.c[4], amigo.getNombre(), UtilsColors.r);

                String confirm = sc.nextLine().trim();
                if (confirm.equalsIgnoreCase("s")) {
                    System.out.println(UtilsColors.c[2] + "¡Amigo agregado con éxito!" + UtilsColors.r);
                    return amigo.getNombre();
                } else {
                    System.out.println("Operación cancelada.");
                }
                break;
            } else {
                System.out.println(UtilsColors.c[1] + "Error: El nombre no coincide con las opciones mostradas" + UtilsColors.r);
            }
        }
        return null;
    }
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