package Utils;

import PageModelNew.*;

import static App.Main.sc;
import static App.Main.mainUsuario;

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
//
//    public static void includeAmigo(String amigo) {
//        StringBuilder nfr = new StringBuilder("Gente que quizás conoces: ");
//        boolean haySugerencias = false;
//
//        for (Usuario usuario : DataBase.getUsuarios()) {
//            if (!usuario.isAmigo(usuario) && usuario != usuario) {
//                nfr.append(usuario.getNombre()).append(", ");
//                haySugerencias = true;
//            }
//        }
//
//        if (haySugerencias) {
//            System.out.println(nfr.substring(0, Math.max(0, nfr.length() - 2)));
//        } else {
//            System.out.println("No hay nuevas personas que puedas agregar.");
//            return;
//        }
//
//        while (true) {
//            System.out.print("Introduce el nombre de la persona que quieres agregar: ");
//            String tempAmig = sc.nextLine().trim();
//
//            if (tempAmig.isEmpty()) {
//                System.out.println("\u001B[31mError: Nombre vacío\u001B[0m");
//                continue;
//            }
//
//            tempAmig = tempAmig.substring(0, 1).toUpperCase() + tempAmig.substring(1).toLowerCase();
//
//            Usuario amigo = UtilsApp.getUsuarioByName(DataBase.getUsuarios(), tempAmig);
//
//            if (amigo == null) {
//                System.out.println("\u001B[31mError: Usuario no encontrado\u001B[0m");
//            } else if (usuario.isAmigo(amigo)) {
//                System.out.println("\u001B[31mError: Ya es tu amigo\u001B[0m");
//            } else {
//                usuario.addAmigo(amigo);
//                System.out.println(tempAmig + " ha sido agregado a tu lista de amigos.");
//                break;
//            }
//        }
//    }
//
//
//    public static int compararId(List<Publicacion> publicaciones) {
//        int maxId = 0;
//        for (Publicacion publicacione : publicaciones) {
//            if (publicacione.getId() > maxId) {
//                maxId = publicacione.getId();
//            }
//        }
//        return maxId;
//    }
//
//    public static Publicacion getPublicacionById(int id) {
//        for (int i=0; i<DataBase.getPublicaciones().size(); i++) {
//            Publicacion tempPubl = DataBase.getPublicaciones().get(i);
//            if (tempPubl.getId()==id) {return tempPubl;}
//        }
//        return null;
//    }*
//
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