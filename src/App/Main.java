package App;

import PageModel.*;
import Utils.UtilsCreate;
import Utils.UtilsApp;
import Utils.UtilsShow;
import DataBase.DataBase;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RedSocial whale = DataBase.getRedSocial();

        System.out.println("Bienvenidos a Whale");
        System.out.println("Tienes una sesión creada? y/n");

        String sec = sc.nextLine();
        if (sec.equals("y")) System.out.println("Cargando...");
        else if (sec.equals("n")) whale.addRoot(UtilsCreate.createUsuario());

        // Página Inicial
        while (true) {
            System.out.println("PUBLICACIONES");
            UtilsShow.showPublicaciones(DataBase.getPublicaciones());

            NavBar();
            break;
        }
    }

    public static void NavBar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Selecciona una de las siguientes opciones -> \n Perfil 'Aceder al perfil' \n Buscar Contenido \n Gestionar Amigo");
        String selected = sc.nextLine();

        if (selected.equals("Perfil")) {
            perfil();
        } else if (selected.equals("Buscar Contenido")) {
            System.out.println("Introduce los HashTags del contenido que quieras buscar: ");
            String hashtagsImp = sc.nextLine();
            buscarContenido(hashtagsImp);
        } else if (selected.equals("Gestionar Amigos")) {
            gestionarAmigos();
        } else {
            System.out.println("Porfavor, intenta escribir una parametro adecuado");
        }
    }

    public static void perfil() {
        Usuario root = DataBase.getUsuarios().get(0);
        String nombre = root.getNombre();
        List<Usuario> amigos = root.getAmigos();

        System.out.println("Bienvenido "+ nombre);
        System.out.println("Tienes "+amigos.size()+" amigos.");;
    }

    public static void buscarContenido(String hashtags) {

    }

    public static void gestionarAmigos() {

    }
}
