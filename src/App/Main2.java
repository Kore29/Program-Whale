

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
//}
