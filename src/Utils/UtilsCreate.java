package Utils;

import PageModel.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalTime;

public class UtilsCreate {
    public static Scanner sc = new Scanner(System.in);

    public static Publicacion createPublicacion() {
        System.out.println("Contenido: ");
        String tempText = sc.nextLine();
        System.out.println("Enlace de contenido: (opcional)");
        String tempMult = sc.nextLine();
        String tempFech = String.valueOf(LocalDate.now());

        if (tempMult.isEmpty()) tempMult = null;

        Publicacion tempPub = new Publicacion(0,tempText,tempFech,null,0,null,tempMult);
        return tempPub;
    }

    public static Usuario createRoot() {
        System.out.print("Como deseas llamarte? "); String tempNomb = sc.nextLine(); System.out.println();
        System.out.println("Escribe una contraseña para tu nuevo usuario: "); String tempCont = sc.nextLine(); System.out.println();
        System.out.println("Finalmente, escribe un email para asociarlo a tu cuenta: "); String tempEmail = sc.nextLine(); System.out.println();
        String tempCrea = String.valueOf(LocalTime.now());

        Usuario tempUsu = new Usuario(tempNomb,tempCont,tempEmail,tempCrea,null,null);
        return tempUsu;
    }
}
