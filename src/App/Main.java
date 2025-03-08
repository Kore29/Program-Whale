package App;

import PageModel.*;
import Utils.UtilsApp;
import Utils.UtilsShow;
import DataBase.DataBase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.List;



public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {MenuMain(DataBase.getRedSocial());}

    public static void MenuMain(RedSocial whale) {

        // Cargar Pagina
        System.out.println("BIENVENIDOS A WHALE");
        System.out.println("Tienes una sesión creada? y/n");

        String sec = sc.nextLine();
        if (sec.equals("y")) startRoot();
        else if (sec.equals("n")) whale.addRoot(createRoot());

        // Página Inicial
        while (true) {
            System.out.println("------------------------------");
            System.out.println("PUBLICACIONES");
            System.out.println("------------------------------");
            UtilsShow.showPublicaciones(DataBase.getPublicaciones());

            NavBar();
        }
    }

    public static Usuario createRoot() {

        // Crear el usuario Root con el ID 0
        System.out.print("Como deseas llamarte? "); String tempNomb = sc.nextLine(); System.out.println();
        System.out.println("Escribe una contraseña para tu nuevo usuario: "); String tempCont = sc.nextLine(); System.out.println();
        System.out.println("Finalmente, escribe un email para asociarlo a tu cuenta: "); String tempEmail = sc.nextLine(); System.out.println();
        String tempCrea = String.valueOf(LocalTime.now());

        return new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
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

    public static void NavBar() {

        System.out.println("Selecciona una de las siguientes opciones\n1.Perfil,  2.Buscar Contenido");
        int option = sc.nextInt(); if (option==1) {;} else if (option==2) {;} else {}

        while (true) {
            if (option==1) {perfil(); break;}
            else if (option==2) {System.out.println("Introduce los HashTags del contenido que quieras buscar: "); String hashtagsImp = sc.nextLine(); buscarContenido(hashtagsImp); break;}
            else {System.out.println("Porfavor, intenta escribir una parametro adecuado");}
        }

    }

    public static void perfil() {
        Usuario root = DataBase.getUsuarios().get(0);
        String nombre = root.getNombre();
        List<Usuario> amigos = root.getAmigos();

        System.out.println("BIENVENIDO "+ nombre.toUpperCase());
        System.out.println("Tienes "+amigos.size()+" amigos.");;
        System.out.println();

        System.out.println("TUS PUBLICACIONES");
        if (root.getContenido().isEmpty()) {
            System.out.println("Aun no tienes publicaciones");
        } else {
            for (int i=0; i<root.getContenido().size(); i++) {
                System.out.println("- "+root.getContenido().get(i).getText());
            }
        }

        System.out.println("CONFIGURACIÓN DE USUARIO");
        System.out.println("1.Cambiar tu nombre,  2.Eliminar amigos,  3.Añadir un nuevo amigo,  4.Salir al menu principal");
        while (true) {
            int option = sc.nextInt();
            if (option==1) {UtilsApp.cambiarNombre(root, sc);}
            else if (option==2) {UtilsApp.eliminarAmigo(root, sc);}
            else if (option==3) {UtilsApp.anadirAmigo(root, sc);}
            else if (option==4) {break;}
            else {System.out.println("Escribe un parametro valido");}
        }
    }

    public static Publicacion createPublicacion() {
        int newId = UtilsApp.compararID(DataBase.getPublicaciones())+1;

        System.out.println("Contenido: ");
        String tempText = sc.nextLine();
        System.out.println("Enlace de contenido: (opcional)");
        String tempMult = sc.nextLine();

        String tempFech = String.valueOf(LocalDate.now());
        if (tempMult.isEmpty()) tempMult = null;

        return new Publicacion(newId,tempText,tempFech,null,0,null,tempMult);
    }

    public static void buscarContenido(String hashtags) {
        System.out.println("Publicaciones con el hashtag a buscar...");
        for (int i=0; i<DataBase.getPublicaciones().size(); i++) {
            if (DataBase.getPublicaciones().get(i).getHashtag().equals(hashtags)) {
                System.out.println("- "+DataBase.getPublicaciones().get(i).getText());
            }
        }
        System.out.println("Comentarios con el hashtag a buscar...");
        for (int i=0; i<DataBase.getComentarios().size(); i++) {
            if (DataBase.getComentarios().get(i).getHashtag().equals(hashtags)) {
                System.out.println("- "+DataBase.getComentarios().get(i).getText());
            }
        }
    }
}
