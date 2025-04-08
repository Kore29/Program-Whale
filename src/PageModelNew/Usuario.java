package PageModelNew;

import PageModelNew.Contenido;
import PageModelNew.Publicacion;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private String contrasenya;
    private String email;
    private String creacion;
    private List<Usuario> amigos;
    private List<PageModelNew.Publicacion> publicaciones;


    public Usuario(String nombre, String contrasena, String email, String creacion, List<Usuario> amigos, List<Contenido> contenido) {
        this.nombre = nombre;
        this.contrasenya = contrasena;
        this.email = email;
        this.creacion = creacion;
        this.amigos = amigos != null ? amigos : new ArrayList<>();
        this.publicaciones = new ArrayList<>();

    }

    // Add Contenido
    public void addPublicacion(PageModelNew.Publicacion p) {publicaciones.add(p);}

    //Usuario
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    //Contraseña
    public String getContrasena() {return contrasenya;}
    public void setContrasena(String contrasena) {this.contrasenya = contrasena;}

    // Email
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    //Creacion
    public String getCreacion() {return creacion;}
    public void setCreacion(String creacion) {this.creacion = creacion;}

    //Amigos
    public List<Usuario> getAmigos() {return amigos;}
    public void setAmigos(List<Usuario> amigos) {this.amigos = amigos;}

    // Modificar Amigos
    public void addAmigo(Usuario amigo) {if (!amigos.contains(amigo)) {amigos.add(amigo);}}
    public void removeAmigo(Usuario amigo) {amigos.remove(amigo);}
    public boolean isAmigo(Usuario amigo) {return amigos.contains(amigo);}

    //Publicaciones
    public List<PageModelNew.Publicacion> getPublicaciones() {return publicaciones;}
    public void setPublicaciones(List<Publicacion> publicaciones) {this.publicaciones = publicaciones;}
}
