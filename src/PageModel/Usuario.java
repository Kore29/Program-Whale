package PageModel;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String contrasena;
    private String email;
    private String creacion;
    private List<Usuario> amigos;
    private List<Contenido> contenido;

    public Usuario(String nombre, String contrasena, String email, String creacion, List<Usuario> amigos, List<Contenido> contenido) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.creacion = creacion;
        this.amigos = amigos != null ? amigos : new ArrayList<>();
        this.contenido = contenido != null ? contenido : new ArrayList<>();
    }

    // Get Usuario
    public String getNombre() {return nombre;}
    public String getContrasena() {return contrasena;}
    public String getEmail() {return email;}
    public String getCreacion() {return creacion;}
    public List<Usuario> getAmigos() {return amigos;}
    public List<Contenido> getContenido() {return contenido;}

    // Set Usuario
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}
    public void setEmail(String email) {this.email = email;}
    public void setCreacion(String creacion) {this.creacion = creacion;}
    public void setAmigos(List<Usuario> amigos) {this.amigos = amigos;}
    public void setContenido(List<Contenido> contenido) {this.contenido = contenido;}

    // Modificar Amigos
    public void addAmigo(Usuario amigo) {if (!amigos.contains(amigo)) {amigos.add(amigo);}}
    public void removeAmigo(Usuario amigo) {amigos.remove(amigo);}
    public boolean esAmigo(Usuario amigo) {return amigos.contains(amigo);}
}
