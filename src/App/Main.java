package App;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;

import PageModelNew.*;
import Utils.*;

import static Utils.UtilsColors.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static WhaleDao whaleDao = new WhaleDaoMySql();

    public static void main(String[] args) {

        System.out.println("╔══════════════════════╗\n║ \u001B[34mBIENVENIDOS A WHALE!\u001B[0m ║\n╚══════════════════════╝");
        System.out.println();

        Usuario mainUsuario = startUsuario();
        showMainMenu(mainUsuario);
    }

    public static Usuario startUsuario() {
        Usuario mainUsuario = null;

        while (mainUsuario == null) {
            System.out.println("Tienes una sesión creada? "+c[2]+"y"+r+"/"+c[1]+"n"+r);
            String opcion = sc.nextLine().trim().toLowerCase();

            switch (opcion) {
                case "y":
                    mainUsuario = loginUsuario();
                    break;
                case "n":
                    mainUsuario = createUsuario();
                    break;
                default:
                    System.out.println(c[1]+"Error: Escribe una opción válida (y/n)"+r);
            }
        }

        return mainUsuario;
    }

    public static Usuario loginUsuario() {
        while (true) {
            Usuario usuario = null;

            System.out.print(c[6]+"Introduce el nombre o email de tu usuario: "+r);
            String input = sc.nextLine().trim();

            if (!UtilsCheck.checkEmail(input).isEmpty()) usuario = whaleDao.getUsuarioByEmail(input);
            else if (!UtilsCheck.checkNombre(input).isEmpty()) usuario = whaleDao.getUsuarioByName(input);
            else {System.out.println(c[1]+"Formato inválido. Introduce un nombre o email válido." + r); continue;}

            if (usuario == null) {
                System.out.println(c[1]+"No se encontró ningún usuario con ese nombre o email. Intenta de nuevo." + r);
            } else {
                System.out.println("Introduce la contraseña del Usuario "+usuario.getNombre());

                String tempCont = "";
                while (tempCont.isEmpty()) {
                    tempCont = sc.nextLine();
                    tempCont = UtilsCheck.inspectContrasena(usuario, tempCont);
                }

                return usuario;
            }
        }
    }

    public static Usuario createUsuario() {
        Usuario usuario;

        System.out.print(c[3]+"¿Cómo deseas llamarte? "+r);
        String tempNomb = sc.nextLine();

        while (UtilsCheck.checkNombre(tempNomb).isEmpty()) {
            System.out.print(c[3]+"Escribe un nombre valido: "+r);
            tempNomb = sc.nextLine();
        }

        System.out.print(c[6]+"Escribe una contraseña para tu nuevo usuario: "+r);
        String tempCont = sc.nextLine();

        while (UtilsCheck.checkContrasena(tempCont).isEmpty()) {
            System.out.print(c[3]+"Escribe una contraseña valida: "+r);
            tempCont = sc.nextLine();
        }
        System.out.println(UtilsCheck.checkContrasena(tempCont));

        System.out.print(c[6]+"Escribe un email para asociarlo a tu cuenta: "+r);
        String tempEmail = sc.nextLine();

        while (UtilsCheck.checkEmail(tempEmail).isEmpty()) {
            System.out.print(c[3]+"Escribe un email valido: "+r);
            tempEmail = sc.nextLine();
        }

        String tempCrea = String.valueOf(LocalDate.now());

        usuario = new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
        return usuario;
    }

    public static void showMainMenu(Usuario mainUsuario) {
        while (true) {
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);
            System.out.println(c[5] + "|                          PUBLICACIONES                        |" + r);
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);

            int page = 1;
            UtilsShow.showPublicaciones(whaleDao.getSixPublicaciones(1));

            navBar();
        }
    }

    public static void navBar() {
        System.out.println("\u001B[34mSelecciona una de las siguientes opciones\n1.Perfil  2.Seleccionar Contenido  3.Crear Publicación  4.Filtrar Contenido  5.Salir de Whale\u001B[0m");
        System.out.println("6.Siguiente página  7.Anterior Página");
        while (true) {
            int option;

            while (true) {
                String opt = sc.nextLine();
                if (UtilsCheck.checkInt(opt).isEmpty()) {
                    option = Integer.parseInt(opt); break;
                } else {
                    System.out.println(UtilsCheck.checkInt(opt));
                }
            }

//            if (option==1) {perfil(); break;}
//            else if (option==2) {selectContenido(); break;}
//            else if (option==3) {Publicacion p = createPublicacion(); DataBase.addPublicaciones(p); DataBase.getUsuarios().getFirst().addPublicacion(p); break;}
//            else if (option==4) {filterContenido(); break;}
//            else if (option==5) {System.exit(0);;}
//            else {System.out.println("Porfavor, intenta escribir una parametro adecuado");}
        }

    }
}