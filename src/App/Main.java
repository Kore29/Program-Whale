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
        List<Comentario> comentarios = new ArrayList<>();
        List<Publicacion> publicaciones = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        RedSocial whale = new RedSocial(usuarios, comentarios, publicaciones);

        System.out.println("Bienvenidos a Whale");
        System.out.println("Tienes una sesión creada? y/n");

        String sec = sc.nextLine();
        if (sec.equals("y")) System.out.println("Cargando...");
        else if (sec.equals("n")) UtilsCreate.createUsuario();

        System.out.println("--------------------");
        System.out.println("PUBLICACIONES");
        UtilsShow.showPublicaciones(publicaciones);

        NavBar.NavBar();

        UtilsApp.clearConsole();
    }
}
