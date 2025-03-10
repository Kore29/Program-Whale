package App;

import PageModel.*;
import Utils.*;
import DataBase.DataBase;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;


public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final String r = "\u001B[0m";
    private static final String[] c = {
            "\u001B[30m", // Negro
            "\u001B[31m", // Rojo
            "\u001B[32m", // Verde
            "\u001B[33m", // Amarillo
            "\u001B[34m", // Azul
            "\u001B[35m", // Magenta
            "\u001B[36m", // Cian
            "\u001B[37m"  // Blanco
    };

    public static void main(String[] args) {MenuMain(DataBase.getRedSocial());}

    public static void MenuMain(RedSocial whale) {

        // Cargar Pagina
        System.out.println("╔══════════════════════╗\n║ \u001B[34mBIENVENIDOS A WHALE!\u001B[0m ║\n╚══════════════════════╝");
        System.out.println();
        System.out.println("Tienes una sesión creada? "+c[2]+"y"+r+"/"+c[1]+"n"+r);

        String sec = sc.nextLine();
        if (sec.equals("y")) startRoot();
        else if (sec.equals("n")) whale.addRoot(createRoot());

        // Página Inicial
        while (true) {
            System.out.println("------------------------------");
            System.out.println("PUBLICACIONES");
            System.out.println("------------------------------");
            UtilsShow.showPublicaciones(DataBase.getPublicaciones());

            navBar();
        }
    }

    public static Usuario createRoot() {
        // Crear el usuario Root con el ID 0
        System.out.print("Como deseas llamarte? ");
        String tempNomb = sc.nextLine();
        while (UtilsApp.checkNombre(tempNomb).isEmpty()) {
            System.out.print("Escribe un nombre valido: ");
            tempNomb = sc.nextLine();
        }

        System.out.println("Escribe una contraseña para tu nuevo usuario: ");
        String tempCont = sc.nextLine();
        while (UtilsApp.checkContrasena(tempCont).isEmpty()) {
            System.out.print("Escribe una contraseña valida: ");
            tempCont = sc.nextLine();
        }
        System.out.println(UtilsApp.checkContrasena(tempCont));

        System.out.println("Finalmente, escribe un email para asociarlo a tu cuenta: ");
        String tempEmail = sc.nextLine();
        while (UtilsApp.checkEmail(tempEmail).isEmpty()) {
            System.out.print("Escribe un email valido: ");
            tempEmail = sc.nextLine();
        }

        String tempCrea = String.valueOf(LocalTime.now());

        return new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
    }

    public static void startRoot() {
        System.out.print("Introduce la contraseña del usuario root: ");
        while (true) {
            String tempCont = sc.nextLine();
            if (!tempCont.equals(DataBase.getUsuarios().getFirst().getContrasena())) {
                System.out.println("Contraseña incorrecta \nPrueba otra vez");
            } else {
                System.out.println("Contraseña aceptada \nCargando..."); break;
            }
        }
    }

    public static void navBar() {

        System.out.println("Selecciona una de las siguientes opciones\n1.Perfil  2.Seleccionar Contenido  3.Crear Contenido  4.Filtrar Contenido");
        while (true) {
            int option = sc.nextInt(); sc.nextLine();

            if (option==1) {UtilsApp.clearConsole(); perfil(); break;}
            if (option==2) {UtilsApp.clearConsole(); selectContenido(); break;}
            else if (option==3) {UtilsApp.clearConsole(); DataBase.addPublicaciones(createPublicacion()); break;}
            else if (option==4) {UtilsApp.clearConsole(); filterContenido(); break;}
            else {System.out.println("Porfavor, intenta escribir una parametro adecuado");}
        }

    }

    public static void perfil() {
        while (true) {
            Usuario root = DataBase.getUsuarios().getFirst();
            String nombre = root.getNombre();
            List<Usuario> amigos = root.getAmigos();

            System.out.println("BIENVENIDO "+ nombre.toUpperCase());
            System.out.println("Tienes "+amigos.size()+" amigo/s.");
            System.out.println();

            System.out.println("TUS PUBLICACIONES");
            if (root.getContenido().isEmpty()) {
                System.out.println("Aun no tienes publicaciones");
            } else {
                UtilsShow.showPublicacionesById(DataBase.getPublicaciones(),0);
            }

            System.out.println("CONFIGURACIÓN DE USUARIO");
            System.out.println("1.Cambiar tu nombre  2.Eliminar amigos  3.Añadir un nuevo amigo  4.Salir al menu principal");
            int option = sc.nextInt(); sc.nextLine();

            if (option==1) {UtilsApp.changeNombre(root, sc);}
            else if (option==2) {UtilsApp.deleteAmigo(root, sc);}
            else if (option==3) {UtilsApp.includeAmigo(root, sc);}
            else if (option==4) {UtilsApp.clearConsole(); break;}
            else {System.out.println("Escribe un parametro valido");}
        }
    }

    public static Publicacion createPublicacion() {
        int newId = UtilsApp.compararId(DataBase.getPublicaciones())+1;

        System.out.println("Contenido: "); String tempText = sc.nextLine();
        System.out.println("Enlace de contenido: (opcional)"); String tempMult = sc.nextLine();

        String tempFech = String.valueOf(LocalDate.now());
        if (tempMult.isEmpty()) tempMult = null;

        return new Publicacion(newId,tempText,tempFech,null,0,new ArrayList<>(),tempMult);
    }

    public static void selectContenido() {
        System.out.print("Seleciona una de las posibles Publicaciones por el ID: ");
        int id = sc.nextInt();

        Publicacion selectPubl = UtilsApp.getPublicacionById(id);
        System.out.println("1.Añadir Comentario  2.Dar Like");

        while (true) {
            int option = sc.nextInt();
            if (option == 1) {sc.nextLine(); includeComentario(selectPubl); break;}
            if (option == 2) {selectPubl.addLike(); System.out.println("Like recibido..."); break;}
            if (option == 3) System.out.println("Escribe una opcion valida");
        }

    }

    public static void includeComentario(Publicacion publicacion) {
        System.out.print("Añade un comentario: "); String comentario = sc.nextLine();
        if(comentario != null && !comentario.trim().isEmpty()) {
            DataBase.addComentarios(new Comentario(publicacion.getId(),comentario,"12-02-2004",null, DataBase.getUsuarios().getFirst().getNombre()));
        }
    }

    public static void filterContenido() {
        System.out.println("Introduce los HashTags del contenido que quieras buscar: (escribe exit para salir)");
        String hashtags = sc.nextLine();

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
