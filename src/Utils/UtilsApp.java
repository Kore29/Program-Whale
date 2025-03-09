package Utils;

import DataBase.DataBase;
import PageModel.Publicacion;
import PageModel.Usuario;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UtilsApp {

    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println(); // Imprime líneas en blanco
        }
    }

    public static Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        for (int i=0; i<usuarios.size(); i++) {
            if(usuarios.get(i).getNombre().equals(nombre)) {
                return usuarios.get(i);
            }
        }
        return usuarios.getFirst();
    }

    public static void cambiarNombre(Usuario root, Scanner sc) {
        System.out.println("Introduce el nombre por el que deseas cambiar: ");
        String tempNomb = sc.nextLine();
        root.setNombre(tempNomb);
        System.out.println("Nombre cambiado: " + root.getNombre());
    }

    public static void eliminarAmigo(Usuario root, Scanner sc) {
        StringBuilder fr = new StringBuilder("Amigos: ");

        for (int i=0; i<root.getAmigos().size(); i++) {
            fr.append(root.getAmigos().get(i).getNombre()).append(", ");
        } fr.toString().trim();
        System.out.println(fr.substring(0,fr.length()-2));

        System.out.print("Introduce el nombre del amigo que quieras eliminar: ");

        String tempAmig = sc.nextLine();
        root.removeAmigo(buscarUsuario(DataBase.getUsuarios(),tempAmig));
    }

    public static void anadirAmigo(Usuario root, Scanner sc) {
        StringBuilder nfr = new StringBuilder("Gente que quizás conoces: ");

        for (int i=0; i<DataBase.getUsuarios().size(); i++) {
            if (!root.esAmigo(root) && DataBase.getUsuarios().get(i)!=root) {
                nfr.append(DataBase.getUsuarios().get(i).getNombre()).append(", ");
            }
        } nfr.toString().trim();
        System.out.println(nfr.substring(0,nfr.length()-2));

        System.out.print("Introduce el nombre de la persona que quieres agregar: ");
        String tempAmig = sc.nextLine();
        root.addAmigo(buscarUsuario(DataBase.getUsuarios(),tempAmig));
    }

    public static int compararID(List<Publicacion> publicaciones) {
        int maxId = 0;
        for (int i=0; i<publicaciones.size(); i++) {
            if (publicaciones.get(i).getId() > maxId) {
                maxId = publicaciones.get(i).getId();
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