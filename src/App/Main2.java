//package App;
//
//import PageModel.*;
//import Utils.*;
//import DataBase.DataBase;
//
//import java.time.LocalDate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import java.util.Scanner;
//
//
//public class Main2 {
//    private static final Scanner sc = new Scanner(System.in);
//
//    private static final String r = "\u001B[0m";
//    private static final String[] c = {
//            "\u001B[30m", // Negro
//            "\u001B[31m", // Rojo
//            "\u001B[32m", // Verde
//            "\u001B[33m", // Amarillo
//            "\u001B[34m", // Azul
//            "\u001B[35m", // Magenta
//            "\u001B[36m", // Cian
//            "\u001B[37m"  // Blanco
//    };
//
//    public static void main(String[] args) {MenuMain(DataBase.getRedSocial());}
//
//    public static void MenuMain(RedSocial whale) {
//
//        // Cargar Pagina
//        System.out.println("╔══════════════════════╗\n║ \u001B[34mBIENVENIDOS A WHALE!\u001B[0m ║\n╚══════════════════════╝");
//        System.out.println();
//        System.out.println("Tienes una sesión creada? "+c[2]+"y"+r+"/"+c[1]+"n"+r);
//
//
//        while (true) {
//            String sec = sc.nextLine();
//            if (sec.equals("y")) {startRoot(); break;}
//            else if (sec.equals("n")) {whale.addRoot(createRoot()); break;}
//            else {System.out.println(c[1]+"Error: Escribe una opción valida"+r);}
//        }
//
//        // Página Inicial
//        while (true) {
//            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);
//            System.out.println(c[5] + "|                          PUBLICACIONES                        |" + r);
//            System.out.println(c[5] + "+---------------------------------------------------------------+" + r);
//            UtilsShow.showPublicaciones(DataBase.getPublicaciones());
//
//            navBar();
//        }
//    }
//
//    public static Usuario createRoot() {
//        // Crear el usuario Root con el ID 0
//        System.out.print("¿Cómo deseas llamarte? " + c[6]);
//        String tempNomb = sc.nextLine();
//        System.out.print(r);
//
//        while (UtilsCheck.checkNombre(tempNomb).isEmpty()) {
//            System.out.print(c[3]+"Escribe un nombre valido: "+r);
//            tempNomb = sc.nextLine();
//        }
//
//        System.out.print("Escribe una contraseña para tu nuevo usuario: " + c[6]);
//        String tempCont = sc.nextLine();
//        System.out.print(r);
//
//        while (UtilsCheck.checkContrasena(tempCont).isEmpty()) {
//            System.out.print(c[3]+"Escribe una contraseña valida: "+r);
//            tempCont = sc.nextLine();
//        }
//        System.out.println(UtilsCheck.checkContrasena(tempCont));
//
//        System.out.print("Escribe un email para asociarlo a tu cuenta: " + c[6]);
//        String tempEmail = sc.nextLine();
//        System.out.print(r);
//
//        while (UtilsCheck.checkEmail(tempEmail).isEmpty()) {
//            System.out.print(c[3]+"Escribe un email valido: "+r);
//            tempEmail = sc.nextLine();
//        }
//
//        String tempCrea = String.valueOf(LocalDate.now());
//
//        return new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
//    }
//
//    public static void startRoot() {
//        System.out.print("Introduce la contraseña del usuario root: " + c[6]);
//        while (true) {
//            String tempCont = sc.nextLine();
//            if (!tempCont.equals(DataBase.getUsuarios().getFirst().getContrasena())) {
//                System.out.println(c[1] + "Contraseña incorrecta \nPrueba otra vez" + r);
//            } else {
//                System.out.println(c[2] + "Contraseña aceptada \nCargando..." + r);
//                break;
//            }
//        }
//    }
//
//    public static void navBar() {
//        System.out.println("\u001B[34mSelecciona una de las siguientes opciones\n1.Perfil  2.Seleccionar Contenido  3.Crear Publicación  4.Filtrar Contenido  5.Salir de Whale\u001B[0m");
//        while (true) {
//            int option;
//
//            while (true) {
//                String opt = sc.nextLine();
//                if (UtilsCheck.checkInt(opt).isEmpty()) {
//                    option = Integer.parseInt(opt); break;
//                } else {
//                    System.out.println(UtilsCheck.checkInt(opt));
//                }
//            }
//
//            if (option==1) {UtilsApp.clearConsole(); perfil(); break;}
//            else if (option==2) {UtilsApp.clearConsole(); selectContenido(); break;}
//            else if (option==3) {UtilsApp.clearConsole(); Publicacion p = createPublicacion(); DataBase.addPublicaciones(p); DataBase.getUsuarios().getFirst().addPublicacion(p); break;}
//            else if (option==4) {UtilsApp.clearConsole(); filterContenido(); break;}
//            else if (option==5) {System.exit(0);;}
//            else {System.out.println("Porfavor, intenta escribir una parametro adecuado");}
//        }
//
//    }
//
//    public static void perfil() {
//        while (true) {
//            Usuario root = DataBase.getUsuarios().getFirst();
//            String nombre = root.getNombre();
//            List<Usuario> amigos = root.getAmigos();
//
//            System.out.println("BIENVENID@ "+ nombre.toUpperCase());
//            System.out.println(root.getEmail() + " | Llevas en Whale desde "+root.getCreacion());
//            System.out.println("Tienes "+amigos.size()+" amigo/s.");
//            System.out.println();
//
//            System.out.println("TUS PUBLICACIONES");
//            if (root.getPublicaciones().isEmpty()) {
//                System.out.println("Aun no tienes publicaciones");
//            } else {
//                for (int i=0; i<root.getPublicaciones().size(); i++) {
//                    UtilsShow.showPublicaciones(root.getPublicaciones());
//                }
//            }
//            System.out.println();
//
//            System.out.println("CONFIGURACIÓN DE USUARIO");
//            System.out.println("1.Cambiar tu nombre  2.Eliminar amigos  3.Añadir un nuevo amigo  4.Salir al menú principal");
//            int option;
//
//            while (true) {
//                String opt = sc.nextLine();
//                if (UtilsCheck.checkInt(opt).isEmpty()) {
//                    option = Integer.parseInt(opt); break;
//                } else {
//                    System.out.println(UtilsCheck.checkInt(opt));
//                }
//            }
//
//            if (option==1) {UtilsApp.changeNombre(root, sc);}
//            else if (option==2) {UtilsApp.deleteAmigo(root, sc);}
//            else if (option==3) {UtilsApp.includeAmigo(root, sc);}
//            else if (option==4) {UtilsApp.clearConsole(); break;}
//            else {System.out.println("Escribe un parametro valido");}
//        }
//    }
//
//    public static Publicacion createPublicacion() {
//        int newId = UtilsApp.compararId(DataBase.getPublicaciones())+1;
//        String tempText;
//
//        while (true) {
//            System.out.println("Escribe el publicación (Máximo un HashTag y 200 caracteres): ");
//            tempText = sc.nextLine();
//
//            if (UtilsCheck.check200Caracteres(tempText).isEmpty()) {
//                break;
//            } else {
//                System.out.println(UtilsCheck.check200Caracteres(tempText));
//            }
//        }
//
//        String tempHashTag = UtilsCheck.checkHashtagText(tempText);
//        tempText = UtilsApp.removeHashTag(tempText);
//
//        System.out.println("Enlace de contenido: (opcional)");
//        String tempMult = UtilsCheck.checkLink(sc.nextLine());
//
//        String tempFech = String.valueOf(LocalDate.now());
//        if (tempMult.isEmpty()) tempMult = null;
//
//        return new Publicacion(newId,tempText,tempFech,tempHashTag,0,new ArrayList<>(),tempMult);
//    }
//
//    public static void selectContenido() {
//        int id; Publicacion selectPubl;
//
//        while (true) {
//            System.out.print("Seleciona una de las posibles Publicaciones por el Id: ");
//
//            String id_s = sc.nextLine();
//            if (UtilsCheck.checkInt(id_s).isEmpty()) {
//                id = Integer.parseInt(id_s);
//            } else {
//                System.out.println(UtilsCheck.checkInt(id_s));
//                continue;
//            }
//
//            if (DataBase.getPublicaciones().contains(UtilsApp.getPublicacionById(id))) {
//                selectPubl = UtilsApp.getPublicacionById(id); break;
//            } else {
//                System.out.println(c[1]+"Error, escribe un Id valido: "+r);
//            }
//        }
//
//        System.out.println("1.Añadir Comentario  2.Dar Like  3.Salir al menú principal.");
//
//        while (true) {
//            int option;
//            while (true) {
//                String opt = sc.nextLine();
//                if (UtilsCheck.checkInt(opt).isEmpty()) {
//                    option = Integer.parseInt(opt); break;
//                } else {
//                    System.out.println(UtilsCheck.checkInt(opt));
//                }
//            }
//
//            if (option == 1) {includeComentario(selectPubl); break;}
//            if (option == 2) {selectPubl.addLike(); System.out.println("Like recibido..."); break;}
//            if (option == 3) {break;}
//            else System.out.println(c[1]+"Error: Escribe una opción valida"+r);
//        }
//
//    }
//
//    public static void includeComentario(Publicacion publicacion) {
//        System.out.println("Escribe 'exit' para salir.");
//        System.out.print("Añade un comentario: "); String tempCome = sc.nextLine();
//
//        if (tempCome.toLowerCase().equals("exit")) return;
//
//        String tempHashTag = UtilsCheck.checkHashtagText(tempCome);
//        tempCome = UtilsApp.removeHashTag(tempCome);
//
//        if(tempCome != null && !tempCome.trim().isEmpty()) {
//            DataBase.addComentarios(new Comentario(publicacion.getId(),tempCome,LocalDate.now().toString(), DataBase.getUsuarios().getFirst().getNombre()));
//        }
//    }
//
//    public static void filterContenido() {
//        while (true) {
//            System.out.println("\u001B[33m+---------------------------------------------------------------+\u001B[0m");
//            System.out.println("Escribe 'exit' para salir.");
//            System.out.print("Introduce el HashTag del contenido que quieras buscar:");
//            String hashtags = sc.nextLine();
//
//            if (hashtags.equalsIgnoreCase("exit")) break;
//            hashtags = UtilsCheck.checkIsHashTag(hashtags);
//
//
//            System.out.println("Publicaciones con el hashtag a buscar...");
//            for (int i=0; i<DataBase.getPublicaciones().size(); i++) {
//                int id = i;
//                if (DataBase.getPublicaciones().get(i).getHashtag().equals(hashtags)) {
//                    System.out.println("\u001B[33m+---------------------------------------------------------------+\u001B[0m");
//                    UtilsShow.showPublicacionesById(DataBase.getPublicaciones(),id);
//                }
//            }
//        }
//
//    }
//}
