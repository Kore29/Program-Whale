package PageModelNew;

import PageModel.Contenido;
import PageModel.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String contrasenya;
    private String email;
    private String creacion;
    private List<Usuario> amigos;
    private List<PageModel.Publicacion> publicaciones;


    public Usuario(String nombre, String contrasena, String email, String creacion, List<Usuario> amigos, List<Contenido> contenido) {
        this.nombre = nombre;
        this.contrasenya = contrasena;
        this.email = email;
        this.creacion = creacion;
        this.amigos = amigos != null ? amigos : new ArrayList<>();
        this.publicaciones = new ArrayList<>();

    }

    // Add Contenido
    public void addPublicacion(PageModel.Publicacion p) {publicaciones.add(p);}

    // Get Usuario
    public String getNombre() {return nombre;}
    public String getContrasena() {return contrasenya;}
    public String getEmail() {return email;}
    public String getCreacion() {return creacion;}
    public List<Usuario> getAmigos() {return amigos;}
    public List<PageModel.Publicacion> getPublicaciones() {return publicaciones;}

    // Set Usuario
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setContrasena(String contrasena) {this.contrasenya = contrasena;}
    public void setEmail(String email) {this.email = email;}
    public void setCreacion(String creacion) {this.creacion = creacion;}
    public void setAmigos(List<Usuario> amigos) {this.amigos = amigos;}
    public void setPublicaciones(List<Publicacion> publicaciones) {this.publicaciones = publicaciones;}

    // Modificar Amigos
    public void addAmigo(Usuario amigo) {if (!amigos.contains(amigo)) {amigos.add(amigo);}}
    public void removeAmigo(Usuario amigo) {amigos.remove(amigo);}
    public boolean isAmigo(Usuario amigo) {return amigos.contains(amigo);}
}
