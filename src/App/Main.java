package App;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;

import PageModelNew.*;
import Utils.*;

import static Utils.UtilsColors.*;

import java.time.LocalDate;
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
            System.out.println("¿Ya tienes un usuario? (y/n): ");
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

            if (!UtilsCheck.checkEmail(input).equals("")) usuario = whaleDao.getUsuarioByEmail(input);
            else if (!UtilsCheck.checkNombre(input).equals("")) usuario = whaleDao.getUsuarioByName(input);
            else {System.out.println(c[1]+"Formato inválido. Introduce un nombre o email válido." + r); continue;}

            if (usuario == null) {
                System.out.println(c[1]+"No se encontró ningún usuario con ese nombre o email. Intenta de nuevo." + r);
            } else {

                System.out.println(c[1]+"Introduce la contraseña del Usuario "+usuario.getNombre()+r);
                String tempCont = "";

                while (tempCont.equals("")) {
                    tempCont = sc.nextLine();
                    tempCont = UtilsCheck.inspectContrasena(usuario, tempCont);
                }

                return usuario;
            }
        }
    }

    public static Usuario createUsuario() {
        Usuario usuario = null;

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


            whaleDao.getAllPublicaciones();
            sc.nextLine();

            // navBar(); Descomenta si lo usas
        }
    }
}