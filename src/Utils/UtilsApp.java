package Utils;

import PageModel.*;
import DataBase.DataBase;

import java.util.List;
import java.util.Scanner;

public class UtilsApp {

    // GENERAL
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Usuario getUsuarioByName(List<Usuario> usuarios, String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return usuarios.getFirst();
    }

    // USUARIO ROOT
    public static void changeNombre(Usuario root, Scanner sc) {
        System.out.println("Introduce el nombre por el que deseas cambiar: ");
        String tempNomb = sc.nextLine();
        root.setNombre(tempNomb);
        System.out.println("Nombre cambiado: " + root.getNombre());
    }

    public static void deleteAmigo(Usuario root, Scanner sc) {
        if (root.getAmigos().isEmpty()) {
            System.out.println("No tienes amigos en tu lista.");
            return;
        }

        StringBuilder fr = new StringBuilder("Amigos: ");
        for (Usuario amigo : root.getAmigos()) {
            fr.append(amigo.getNombre()).append(", ");
        }
        System.out.println(fr.substring(0, Math.max(0, fr.length() - 2)));

        while (true) {
            System.out.print("Introduce el nombre del amigo que quieras eliminar: ");
            String tempAmig = sc.nextLine().trim();

            if (tempAmig.isEmpty()) {
                System.out.println("\u001B[31mError: Nombre vacío\u001B[0m");
                continue;
            }
            tempAmig = tempAmig.substring(0, 1).toUpperCase() + tempAmig.substring(1).toLowerCase();

            Usuario amigo = UtilsApp.getUsuarioByName(DataBase.getUsuarios(), tempAmig);

            if (amigo == null || !root.isAmigo(amigo)) {
                System.out.println("\u001B[31mError: Introduce un nombre válido\u001B[0m");
            } else {
                root.removeAmigo(amigo);
                System.out.println(tempAmig + " ha sido eliminado de tu lista de amigos.");
                break;
            }
        }
    }


    public static void includeAmigo(Usuario root, Scanner sc) {
        StringBuilder nfr = new StringBuilder("Gente que quizás conoces: ");
        boolean haySugerencias = false;

        for (Usuario usuario : DataBase.getUsuarios()) {
            if (!root.isAmigo(usuario) && usuario != root) {
                nfr.append(usuario.getNombre()).append(", ");
                haySugerencias = true;
            }
        }

        if (haySugerencias) {
            System.out.println(nfr.substring(0, Math.max(0, nfr.length() - 2)));
        } else {
            System.out.println("No hay nuevas personas que puedas agregar.");
            return;
        }

        while (true) {
            System.out.print("Introduce el nombre de la persona que quieres agregar: ");
            String tempAmig = sc.nextLine().trim();

            if (tempAmig.isEmpty()) {
                System.out.println("\u001B[31mError: Nombre vacío\u001B[0m");
                continue;
            }

            // Corregimos la capitalización
            tempAmig = tempAmig.substring(0, 1).toUpperCase() + tempAmig.substring(1).toLowerCase();

            // Buscar usuario
            Usuario amigo = UtilsApp.getUsuarioByName(DataBase.getUsuarios(), tempAmig);

            if (amigo == null) {
                System.out.println("\u001B[31mError: Usuario no encontrado\u001B[0m");
            } else if (root.isAmigo(amigo)) {
                System.out.println("\u001B[31mError: Ya es tu amigo\u001B[0m");
            } else {
                root.addAmigo(amigo);
                System.out.println(tempAmig + " ha sido agregado a tu lista de amigos.");
                break;
            }
        }
    }


    // DATABASE
    public static int compararId(List<Publicacion> publicaciones) {
        int maxId = 0;
        for (Publicacion publicacione : publicaciones) {
            if (publicacione.getId() > maxId) {
                maxId = publicacione.getId();
            }
        }
        return maxId;
    }

    public static Publicacion getPublicacionById(int id) {
        for (int i=0; i<DataBase.getPublicaciones().size(); i++) {
            Publicacion tempPubl = DataBase.getPublicaciones().get(i);
            if (tempPubl.getId()==id) {return tempPubl;}
        }
        return null;
    }

    // CHECK
    public static String checkEmail(String email) {
        if (email.length() > 50) {
            return "";
        }

        String[] dominiosPermitidos = {"@gmail.com", "@hotmail.com", "@yahoo.com", "@outlook.com", "@protonmail.com", "@icloud.com"};

        boolean esValido = false;
        for (String dominio : dominiosPermitidos) {
            if (email.endsWith(dominio)) {esValido = true; break;}
        }

        return esValido ? email : "";
    }

    public static String checkNombre(String nombre) {

        if (nombre.length() > 30) {
            return "";
        }

        if (!nombre.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            return "";
        }

        StringBuilder nombreFormateado = new StringBuilder();
        for (String palabra : nombre.split(" ")) {
            if (!palabra.isEmpty()) {
                nombreFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return nombreFormateado.toString().trim();
    }

    public static String checkContrasena(String contrasena) {

        if (contrasena.length() > 18) {
            return "";
        }
        if (contrasena.contains(" ")) {
            return "";
        }

        boolean letr = contrasena.matches(".*[a-zA-Z].*");
        boolean nume = contrasena.matches(".*\\d.*");
        boolean tn8Cara = contrasena.length() > 8;

        if (letr && nume && tn8Cara) {
            return "La contraseña es muy segura.";
        } else if (nume) {
            return "La contraseña es medianamente segura.";
        } else if (letr) {
            return "La contraseña es poco segura.";
        } else {
            return "La contraseña no cumple con los requisitos minimos.";
        }
    }
}