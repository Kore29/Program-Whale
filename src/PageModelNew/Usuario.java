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
    private List<String> amigos;
    private List<Publicacion> publicaciones;


    public Usuario(String nombre, String contrasena, String email, String creacion, List<String> amigos, List<Publicacion> publicaciones) {
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
    public List<String> getAmigos() {return amigos;}
    public void setAmigos(List<String> amigos) {this.amigos = amigos;}

    // Modificar Amigos
    public void addAmigo(String amigo) {if (!amigos.contains(amigo)) {amigos.add(amigo);}}
    public void removeAmigo(String amigo) {amigos.remove(amigo);}
    public boolean isAmigo(String amigo) {return amigos.contains(amigo);}

    //Publicaciones
    public List<Publicacion> getPublicaciones() {return publicaciones;}
    public void setPublicaciones(List<Publicacion> publicaciones) {this.publicaciones = publicaciones;}
}
