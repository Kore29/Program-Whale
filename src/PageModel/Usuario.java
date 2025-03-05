package PageModel;

import java.util.List;

public class Usuario {
    private String nombre;
    private String contrasena;
    private String email;
    private String creacion;
    private String amigos;
    private List<Contenido> contenido;

    public Usuario(String nombre, String contrasena, String email, String craecion, String amigos, List<Contenido> contenido) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.creacion = creacion;
        this.amigos = amigos;
        this.contenido = contenido;
    }

    public String getNombre() {return nombre;}
    public String getContrasena() {return contrasena;}
    public String getEmail() {return email;}
    public String getCreacion() {return creacion;}
    public String getAmigos() {return amigos;}
    public List<Contenido> getContenido() {return contenido;}

}
