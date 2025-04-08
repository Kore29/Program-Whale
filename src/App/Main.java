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

        Usuario mainUsuario = iniciarUsuario();
        mostrarMenuPrincipal();
    }

    public static Usuario iniciarUsuario() {
        Usuario usuario = null;

        while (usuario == null) {
            System.out.println("¿Ya tienes un usuario? (y/n): ");
            String opcion = sc.nextLine().trim().toLowerCase();

            switch (opcion) {
                case "y":
                    usuario = autenticarUsuario();
                    break;
                case "n":
                    crearUsuario();
                    break;
                default:
                    System.out.println(c[1] + "Error: Escribe una opción válida (y/n)" + r);
            }
        }

        return usuario;
    }

    public static Usuario autenticarUsuario() {
        while (true) {
            System.out.print("Introduce el nombre o email de tu usuario: " + c[6]);
            String input = sc.nextLine().trim();

            Usuario usuario = null;

            if (!UtilsCheck.checkEmail(input).equals("")) {
                usuario = whaleDao.getUsuarioByEmail(input);
            } else if (!UtilsCheck.checkNombre(input).equals("")) {
                usuario = whaleDao.getUsuarioByName(input);
            } else {
                System.out.println(c[1] + "Formato inválido. Introduce un nombre o email válido." + r);
                continue;
            }

            if (usuario == null) {
                System.out.println(c[1] + "No se encontró ningún usuario con ese nombre o email. Intenta de nuevo." + r);
            } else {
                System.out.println("Introduce la contraseña del Usuario "+usuario.getNombre());
                String tempCont = "";
                UtilsCheck.checkContrasena(usuario,sc.nextLine());
            }
        }
    }

    public static Usuario crearUsuario() {
        System.out.print("¿Cómo deseas llamarte? " + c[6]);
        String tempNomb = sc.nextLine();
        System.out.print(r);

        while (UtilsCheck.checkNombre(tempNomb).isEmpty()) {
            System.out.print(c[3]+"Escribe un nombre valido: "+r);
            tempNomb = sc.nextLine();
        }

        System.out.print("Escribe una contraseña para tu nuevo usuario: " + c[6]);
        String tempCont = sc.nextLine();
        System.out.print(r);

        while (UtilsCheck.checkContrasena(tempCont).isEmpty()) {
            System.out.print(c[3]+"Escribe una contraseña valida: "+r);
            tempCont = sc.nextLine();
        }
        System.out.println(UtilsCheck.checkContrasena(tempCont));

        System.out.print("Escribe un email para asociarlo a tu cuenta: " + c[6]);
        String tempEmail = sc.nextLine();
        System.out.print(r);

        while (UtilsCheck.checkEmail(tempEmail).isEmpty()) {
            System.out.print(c[3]+"Escribe un email valido: "+r);
            tempEmail = sc.nextLine();
        }

        String tempCrea = String.valueOf(LocalDate.now());

        return new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
    }

    public static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);
            System.out.println(c[5] + "|                          PUBLICACIONES                        |" + r);
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);

            sc.nextLine();

            // navBar(); // Descomenta si lo usas
        }
    }
}