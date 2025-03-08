package Utils;

import DataBase.DataBase;
import PageModel.Publicacion;
import PageModel.Usuario;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UtilsApp {
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

        if (sc.hasNextLine()) sc.nextLine(); String tempAmig = sc.nextLine();
        root.removeAmigo(buscarUsuario(DataBase.getUsuarios(),tempAmig));
    }

    public static void anadirAmigo(Usuario root, Scanner sc) {
        StringBuilder nfr = new StringBuilder("Gente que quizás conoces: ");

        for (int i=0; i<DataBase.getUsuarios().size(); i++) {
            if (!root.esAmigo(root)) {
                nfr.append(DataBase.getUsuarios().get(i).getNombre()).append(", ");
            }
        } nfr.toString().trim();
        System.out.println(nfr.substring(0,nfr.length()-2));

        System.out.print("Introduce el nombre de la persona que quieres agregar: ");
        if (sc.hasNextLine()) sc.nextLine(); String tempAmig = sc.nextLine();
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
}
