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

        while (true) {
            String sec = sc.nextLine();
            if (sec.equals("y")) {startRoot(); break;}
            else if (sec.equals("n")) {whale.addRoot(createRoot()); break;}
            else {System.out.println(c[1]+"Error: Escribe una opción valida"+r);}
        }

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
        while (UtilsCheck.checkNombre(tempNomb).isEmpty()) {
            System.out.print(c[3]+"Escribe un nombre valido: "+r);
            tempNomb = sc.nextLine();
        }

        System.out.println("Escribe una contraseña para tu nuevo usuario: ");
        String tempCont = sc.nextLine();
        while (UtilsCheck.checkContrasena(tempCont).isEmpty()) {
            System.out.print(c[3]+"Escribe una contraseña valida: "+r);
            tempCont = sc.nextLine();
        }
        System.out.println(UtilsCheck.checkContrasena(tempCont));

        System.out.println("Escribe un email para asociarlo a tu cuenta: ");
        String tempEmail = sc.nextLine();
        while (UtilsCheck.checkEmail(tempEmail).isEmpty()) {
            System.out.print(c[3]+"Escribe un email valido: "+r);
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
            int option;

            while (true) {
                String opt = sc.nextLine();
                if (UtilsCheck.checkInt(opt).isEmpty()) {
                    option = Integer.parseInt(opt); break;
                } else {
                    System.out.println(UtilsCheck.checkInt(opt));
                }
            }

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
            int option;

            while (true) {
                String opt = sc.nextLine();
                if (UtilsCheck.checkInt(opt).isEmpty()) {
                    option = Integer.parseInt(opt); break;
                } else {
                    System.out.println(UtilsCheck.checkInt(opt));
                }
            }

            if (option==1) {UtilsApp.changeNombre(root, sc);}
            else if (option==2) {UtilsApp.deleteAmigo(root, sc);}
            else if (option==3) {UtilsApp.includeAmigo(root, sc);}
            else if (option==4) {UtilsApp.clearConsole(); break;}
            else {System.out.println("Escribe un parametro valido");}
        }
    }

    public static Publicacion createPublicacion() {
        int newId = UtilsApp.compararId(DataBase.getPublicaciones())+1;
        String tempText;

        while (true) {
            System.out.println("Escribe el contenido (Máximo un HashTag y 200 caracteres): ");
            tempText = sc.nextLine();

            if (UtilsCheck.check200Caracteres(tempText).isEmpty()) {
                break;
            } else {
                System.out.println(UtilsCheck.check200Caracteres(tempText));
            }
        }

        String tempHashTag = UtilsCheck.checkHashtagText(tempText);
        tempText = UtilsApp.removeHashTag(tempText);

        System.out.println("Enlace de contenido: (opcional)");
        String tempMult = UtilsCheck.checkLink(sc.nextLine());

        String tempFech = String.valueOf(LocalDate.now());
        if (tempMult.isEmpty()) tempMult = null;

        return new Publicacion(newId,tempText,tempFech,tempHashTag,0,new ArrayList<>(),tempMult);
    }

    public static void selectContenido() {
        int id; Publicacion selectPubl;

        while (true) {
            System.out.print("Seleciona una de las posibles Publicaciones por el Id: ");
            id = Integer.parseInt(sc.nextLine());
            if (DataBase.getPublicaciones().contains(UtilsApp.getPublicacionById(id))) {
                selectPubl = UtilsApp.getPublicacionById(id); break;
            } else {
                System.out.println(c[1]+"Error, escribe un Id valido: "+r);
            }
        }

        System.out.println("1.Añadir Comentario  2.Dar Like");

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

            if (option == 1) {includeComentario(selectPubl); break;}
            if (option == 2) {selectPubl.addLike(); System.out.println("Like recibido..."); break;}
            if (option == 3) System.out.println("Error: Escribe una opcion valida");
        }

    }

    public static void includeComentario(Publicacion publicacion) {
        System.out.print("Añade un comentario: "); String comentario = sc.nextLine();
        if(comentario != null && !comentario.trim().isEmpty()) {
            DataBase.addComentarios(new Comentario(publicacion.getId(),comentario,"12-02-2004",null, DataBase.getUsuarios().getFirst().getNombre()));
        }
    }

    public static void filterContenido() {
        while (true) {
            System.out.println("Escribe 'exit' para salir.");
            System.out.print("Introduce el HashTag del contenido que quieras buscar:");
            String hashtags = sc.nextLine();

            if (hashtags.equalsIgnoreCase("exit")) break;
            hashtags = UtilsCheck.checkIsHashTag(hashtags);


            System.out.println("Publicaciones con el hashtag a buscar...");
            StringBuilder pubWithHT = new StringBuilder();
            for (int i=0; i<DataBase.getPublicaciones().size(); i++) {
                if (DataBase.getPublicaciones().get(i).getHashtag().equals(hashtags)) {
                    pubWithHT.append("- ").append(DataBase.getPublicaciones().get(i).getText()).append("\n");
                }
            }

            if (pubWithHT.isEmpty()) System.out.println("No hay Publicaciones con ese HashTag."); else {
                System.out.println(pubWithHT.toString());
            }

            System.out.println("Comentarios con el hashtag a buscar...");
            StringBuilder comWithHT = new StringBuilder();
            for (int i=0; i<DataBase.getComentarios().size(); i++) {
                if (DataBase.getComentarios().get(i).getHashtag().equals(hashtags)) {
                    comWithHT.append("- ").append(DataBase.getComentarios().get(i).getText()).append("\n");
                }
            }

            if (comWithHT.isEmpty()) System.out.println("No hay Comentarios con ese HashTag."); else {
                System.out.println(pubWithHT.toString());
            }
        }

    }
}
