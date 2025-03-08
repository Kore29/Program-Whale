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
    public static boolean validarCorreo(String email) {
        //Verifica la longitud del correo electronico
        if (email.length() > 20) {
            System.out.println("Error: El correo electrónico no puede tener más de 20 caracteres.");
            return false;
        }

        //Verifica si contiene alguno de los dos dominios
        if (!email.contains("@gmail.com") || (!email.contains("@hotmail.com"))) {
            System.out.println("El correo electrónico no es válido.");
            return false;
        }
        System.out.println("El correo electrónico es válido.");
        return true;
    }
    public static boolean tamanyNombre (String nombre){
        //Comprueba si no supera de los 20 caracteres
        if (nombre.length() > 20) {
            System.out.println("Error: El correo electrónico no puede tener más de 20 caracteres.");
            return false;
        }
        System.out.println("El nombre cumple con los parametros");
        return true;
    }
    public static void crearContrasenya() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce tu contraseña (máximo 18 caracteres, sin espacios): ");
        String contrasenya = sc.nextLine();

        //Valida la contraseña
        String resultado = validarContrasenya(contrasenya);
        System.out.println(resultado);
    }

    public static String validarContrasenya(String contrasenya) {
        //Restricciones
        if (contrasenya.length() > 18) {
            return "Error: La contraseña no puede tener más de 18 caracteres.";
        }
        if (contrasenya.contains(" ")) {
            return "Error: La contraseña no puede contener espacios.";
        }

        //Los niveles de seguridad
        boolean tieneLetras = contrasenya.matches(".*[a-zA-Z].*"); // Verifica si tiene letras
        boolean tieneNumeros = contrasenya.matches(".*\\d.*"); // Verifica si tiene números
        boolean tieneMasDe8Caracteres = contrasenya.length() > 8; // Verifica si tiene más de 8 caracteres

        if (tieneLetras && tieneNumeros && tieneMasDe8Caracteres) {
            return "La contraseña es muy segura.";
        } else if (tieneNumeros) {
            return "La contraseña es medianamente segura.";
        } else if (tieneLetras) {
            return "La contraseña es poco segura.";
        } else {
            return "La contraseña no cumple con los requisitos minimos.";
        }
    }
}