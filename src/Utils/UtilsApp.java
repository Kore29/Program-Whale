package Utils;

import PageModel.Usuario;

import java.util.List;

public class UtilsApp {

    // Con java no se puede limpiar un consola, pero si se puede hacer muchos espacios para simularlo.
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Usuario buscarUsuario(List<Usuario> usuarios, String nombre) {
        for (int i=0; i<usuarios.size(); i++) {
            if(usuarios.get(i).getNombre().equals(nombre)) {
                return usuarios.get(i);
            }
        }
        return usuarios.get(0);
    }
}
