package App;

import java.util.Scanner;

public class NavBar {

    public static void NavBar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("------------------------------------------------------");
        System.out.println("|  Perfil  |  Buscar Contenido  |  Gestionar Amigos  |");
        System.out.println("------------------------------------------------------");

        String selected = sc.nextLine();
        if (selected.equals("Perfil")) {
            perfil();
        } else if (selected.equals("Buscar Contenido")) {
            System.out.println("Introduce los HashTags del contenido que quieras buscar: ");
            String hashtagsImp = sc.nextLine();
            buscarContenido(hashtagsImp);
        } else if (selected.equals("Gestionar Amigos")) {
            gestionarAmigos();
        }
    }

    public static void perfil() {
        System.out.println("Amigos: ");

    }

    public static void buscarContenido(String hashtags) {

    }

    public static void gestionarAmigos() {

    }
}
