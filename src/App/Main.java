package App;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;
import PageModelNew.*;


import Utils.*;
import static Utils.UtilsColors.c;
import static Utils.UtilsColors.r;



public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static WhaleDao whaleDao = new WhaleDaoMySql();

    public static Usuario mainUsuario;
    public static List<Publicacion> pagePublicaciones;

    public static void main(String[] args) {

        System.out.println("╔══════════════════════╗\n║ \u001B[34mBIENVENIDOS A WHALE!\u001B[0m ║\n╚══════════════════════╝");
        System.out.println();

        mainUsuario = startUsuario();
        showMainMenu(1);
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
                System.out.print("Introduce la contraseña del usuario "+usuario.getNombre()+": ");

                String tempCont = "";
                while (tempCont.isEmpty()) {
                    tempCont = UtilsCheck.inspectContrasena(usuario, sc.nextLine());
                    if (!tempCont.isEmpty()) continue;
                    System.out.println("Contraseña incorrecta");
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

    public static void showMainMenu(int page) {
        while (true) {
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);
            System.out.println(c[5] + "|                          PUBLICACIONES                        |" + r);
            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);

            int totalPages = whaleDao.sizePublicaciones()/6;
            pagePublicaciones = whaleDao.getSixPublicaciones(page);
            UtilsShow.showPublicaciones(pagePublicaciones);

            System.out.print("Página "+page+"/"+totalPages+" | ");
            int option = navBar();

            switch (option) {
                case 1:
                    // perfil(); // pendiente
                    break;
                case 2:
                    selectContenido();
                    break;
                case 3:
                    whaleDao.insertPublicacion(createPublicacion());
                    totalPages = 1; // Para volver al principio si creas una publicación
                    break;
                case 4:
                    // filterContenido(); // pendiente
                    break;
                case 5:
                    System.out.println("Hasta pronto :)");
                    System.exit(0);
                    break;
                case 6:
                    if (page < totalPages) page++;
                    else {System.out.println("Límite de páginas");}
                    break;
                case 7:
                    if (page > 1) page--;
                    else System.out.println("Límite de páginas");
                    break;
                default:
                    System.out.println(c[1] + "Opción inválida, intenta de nuevo." + r);
            }
        }
    }

    public static int navBar() {
        System.out.println("6.Siguiente página  7.Anterior Página");
        System.out.println("\u001B[34mSelecciona una de las siguientes opciones\n1.Perfil  2.Seleccionar Contenido  3.Crear Publicación  4.Filtrar Contenido  5.Salir de Whale\u001B[0m");
        while (true) {
            String opt = sc.nextLine();
            if (UtilsCheck.checkInt(opt).isEmpty()) {
                return Integer.parseInt(opt);
            } else {
                System.out.println(UtilsCheck.checkInt(opt));
            }
        }
    }

    public static Publicacion createPublicacion() {
        String tempText;

        while (true) {
            System.out.println("Escribe el publicación (Máximo un HashTag y 200 caracteres): ");
            tempText = sc.nextLine();

            if (UtilsCheck.check200Caracteres(tempText).isEmpty()) {
                break;
            } else {
                System.out.println(UtilsCheck.check200Caracteres(tempText));
            }
        }

        String tempHashTag = UtilsCheck.checkHashtagText(tempText);
        tempText = UtilsApp.removeHashTag(tempText);

        if (tempHashTag.isEmpty()) tempHashTag = "#whale";

        System.out.println("Enlace de contenido: (opcional)");
        String tempMult = UtilsCheck.checkLink(sc.nextLine());

        String tempFech = String.valueOf(LocalDate.now());
        if (tempMult.isEmpty()) tempMult = null;

        return new Publicacion(0, mainUsuario.getNombre(), tempFech, tempMult, tempText, 0, tempHashTag, null);
    }

    public static void selectContenido() {
        int id; Publicacion selectPublicacion;

        while (true) {
            System.out.print("Seleciona una de las posibles Publicaciones por el Id: ");

            String id_s = sc.nextLine();
            if (UtilsCheck.checkInt(id_s).isEmpty()) {
                id = Integer.parseInt(id_s);
            } else {
                System.out.println(UtilsCheck.checkInt(id_s));
                continue;
            }

            int finalId = id;
            boolean valid = pagePublicaciones.stream()
                    .anyMatch(p -> p.getId() == finalId);

            if (valid) {
                break;
            } else {
                System.out.println(c[1]+"Error, escribe un Id valido: "+r);
            }
        }

        System.out.println("1.Añadir Comentario  2.Dar Like  3.Salir al menú principal.");

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

            if (option == 1) {whaleDao.insertComentario(includeComentario(id)); break;}
            if (option == 2) {whaleDao.updateLikes(id); System.out.println("Like recibido..."); break;}
            if (option == 3) {break;}
            else System.out.println(c[1]+"Error: Escribe una opción valida"+r);
        }

    }

    public static Comentario includeComentario(int id) {
        System.out.println("Escribe 'exit' para salir.");
        System.out.print("Añade un comentario: "); String tempCome = sc.nextLine();

        if (tempCome.toLowerCase().equals("exit")) return null;

        String tempHashTag = UtilsCheck.checkHashtagText(tempCome);
        tempCome = UtilsApp.removeHashTag(tempCome);

        String tempFech = String.valueOf(LocalDate.now());

        if(tempCome != null && !tempCome.trim().isEmpty()) {
            return new Comentario(0,mainUsuario.getNombre(),tempFech,null,tempCome,id);
        }
        return null;
    }

}