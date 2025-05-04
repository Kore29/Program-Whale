package PageModelNew;

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

    /** Añade una nueva publicación al usuario. */
    public void addPublicacion(PageModelNew.Publicacion p) { publicaciones.add(p); }

    /** Devuelve el nombre del usuario. */
    public String getNombre() { return nombre; }

    /** Cambia el nombre del usuario. */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** Devuelve la contraseña del usuario. */
    public String getContrasena() { return contrasenya; }

    /** Cambia la contraseña del usuario. */
    public void setContrasena(String contrasena) { this.contrasenya = contrasena; }

    /** Devuelve el email del usuario. */
    public String getEmail() { return email; }

    /** Cambia el email del usuario. */
    public void setEmail(String email) { this.email = email; }

    /** Devuelve la fecha de creación del usuario. */
    public String getCreacion() { return creacion; }

    /** Cambia la fecha de creación del usuario. */
    public void setCreacion(String creacion) { this.creacion = creacion; }

    /** Devuelve la lista de amigos del usuario. */
    public List<String> getAmigos() { return amigos; }

    /** Reemplaza la lista de amigos del usuario. */
    public void setAmigos(List<String> amigos) { this.amigos = amigos; }

    /** Añade un amigo si no estaba ya en la lista. */
    public void addAmigo(String amigo) { if (!amigos.contains(amigo)) amigos.add(amigo); }

    /** Elimina un amigo de la lista. */
    public void removeAmigo(String amigo) { amigos.remove(amigo); }

    /** Comprueba si un usuario es amigo. */
    public boolean isAmigo(String amigo) { return amigos.contains(amigo); }

    /** Devuelve la lista de publicaciones del usuario. */
    public List<Publicacion> getPublicaciones() { return publicaciones; }

    /** Reemplaza la lista de publicaciones del usuario. */
    public void setPublicaciones(List<Publicacion> publicaciones) { this.publicaciones = publicaciones; }

}
