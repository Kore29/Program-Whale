package Utils;

import DataBase.DataBase;
import PageModel.Usuario;

import javax.xml.crypto.Data;
import java.util.List;

import static Utils.UtilsCreate.sc;

public class UtilsApp {
    public static Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        for (int i=0; i<usuarios.size(); i++) {
            if(usuarios.get(i).getNombre().equals(nombre)) {
                return usuarios.get(i);
            }
        }
        return usuarios.getFirst();
    }

    public static void startRoot() {
        System.out.print("Introduce la contraseña del usuario root: ");
        while (true) {
            String tempCont = sc.nextLine();
            if (!tempCont.equals(DataBase.getUsuarios().get(0).getContrasena())) {
                System.out.println("Contraseña incorrecta \nPrueba otra vez");
            } else {
                System.out.println("Contraseña aceptada \nCargando..."); break;
            }
        }
    }

    public static void cambiarNombre(Usuario root) {
        System.out.print("Introduce el nombre por el que deseas cambiar: ");
        String tempNomb = sc.nextLine();
        root.setNombre(tempNomb);
    }

    public static void eliminarAmigo(Usuario root) {
        System.out.print("Introduce el nombre del amigo que quieras eliminar: ");
        String tempAmig = sc.nextLine();
        root.removeAmigo(buscarUsuario(DataBase.getUsuarios(),tempAmig));
    }

    public static void anadirAmigo(Usuario root) {
        System.out.print("Introduce el nombre de la persona que quieres agregar: ");
        String tempAmig = sc.nextLine();
        root.addAmigo(buscarUsuario(DataBase.getUsuarios(),tempAmig));
    }
}
