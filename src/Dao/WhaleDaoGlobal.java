package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

import static Utils.UtilsColors.c;
import static Utils.UtilsColors.r;

public class WhaleDaoGlobal implements WhaleDao {
    private WhaleDaoMySql daoMySql;
    private WhaleDaoCSV daoCsv;
    private boolean mysqlUp;

    public WhaleDaoGlobal() {
        daoCsv = new WhaleDaoCSV();
        daoMySql = new WhaleDaoMySql();

        mysqlUp = daoMySql.testConnection();
        if (mysqlUp) System.out.println(c[2]+"Conexión a la base de datos establecida correctamente."+r);
        else System.err.println(c[1]+"ERROR al conectar a la base de datos: "+r);
    }

    private void checkConnection() {
        if (!mysqlUp){
            if(daoMySql.testConnection()){
                moveCsvToMySql();
                mysqlUp = true;
            }
        }
    }

    public void moveCsvToMySql() {
        if (!daoMySql.testConnection()) {
            System.err.println(c[1] + "No se pudo conectar a MySQL. Operación cancelada." + r);
            return;
        }

        daoMySql.clearAllData();

        // Insertar usuarios y amigos
        for (Usuario usuario : daoCsv.getAllUsuarios()) {
            daoMySql.insertUsuario(usuario);
            for (String amigo : daoCsv.getAllAmigos(usuario.getNombre())) {
                daoMySql.insertAmigo(usuario.getNombre(), amigo);
            }
        }

        // Insertar publicaciones y comentarios
        int total = daoCsv.getSizePublicaciones();
        for (int i = 0; i < total; i++) {
            List<Publicacion> publicaciones = daoCsv.getSixPublicaciones(i);
            for (Publicacion p : publicaciones) {
                daoMySql.insertPublicacion(p);
                daoMySql.updateLikes(p.getId());
                for (Comentario c : daoCsv.getComentariosById(p.getId())) {
                    daoMySql.insertComentario(c);
                }
            }
        }

        System.out.println(c[5] + "Transferencia CSV → MySQL completada y datos originales de MySQL eliminados." + r);
    }

    public void moveMySqlToCsv() {
        if (!daoMySql.testConnection()) {
            System.err.println(c[1] + "No se pudo conectar a MySQL. Operación cancelada." + r);
            return;
        }

        daoCsv.clearAllData();

        // Insertar usuarios y amigos
        for (Usuario usuario : daoMySql.getAllUsuarios()) {
            daoCsv.insertUsuario(usuario);
            for (String amigo : daoMySql.getAllAmigos(usuario.getNombre())) {
                daoCsv.insertAmigo(usuario.getNombre(), amigo);
            }
        }

        // Insertar publicaciones y comentarios
        int total = daoMySql.getSizePublicaciones();
        for (int i = 0; i < total; i++) {
            List<Publicacion> publicaciones = daoMySql.getSixPublicaciones(i);
            for (Publicacion p : publicaciones) {
                daoCsv.insertPublicacion(p);
                daoCsv.updateLikes(p.getId());
                for (Comentario c : daoMySql.getComentariosById(p.getId())) {
                    daoCsv.insertComentario(c);
                }
            }
        }

        System.out.println(c[5] + "Transferencia MySQL → CSV completada y datos originales del CSV eliminados." + r);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        checkConnection();
        if(mysqlUp) return daoMySql.getAllUsuarios();
        return daoCsv.getAllUsuarios();
    }

    @Override
    public List<Publicacion> getAllContenido() {
        return List.of();
    }

    @Override
    public List<String> getAllAmigos(String nombre) {
        checkConnection();
        if(mysqlUp) return daoMySql.getAllAmigos(nombre);
        return daoCsv.getAllAmigos(nombre);
    }

    @Override
    public void insertUsuario(Usuario usuario) {
        checkConnection();
        if(mysqlUp) daoMySql.insertUsuario(usuario);
        daoCsv.insertUsuario(usuario);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        checkConnection();
        if(mysqlUp) return daoMySql.getUsuarioByEmail(email);
        return daoCsv.getUsuarioByEmail(email);
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        checkConnection();
        if(mysqlUp) return daoMySql.getUsuarioByName(name);
        return daoCsv.getUsuarioByName(name);
    }

    @Override
    public void changeName(Usuario usuario, String name) {
        checkConnection();
        if(mysqlUp) daoMySql.changeName(usuario, name);
        daoCsv.changeName(usuario, name);
    }

    @Override
    public void insertAmigo(String usuario, String nombre) {
        checkConnection();
        if(mysqlUp) daoMySql.insertAmigo(usuario, nombre);
        daoCsv.insertAmigo(usuario, nombre);
    }

    @Override
    public void removeAmigo(String usuario, String nombre) {
        checkConnection();
        if(mysqlUp) daoMySql.removeAmigo(usuario, nombre);
        daoCsv.removeAmigo(usuario, nombre);
    }

    @Override
    public void insertPublicacion(Publicacion publicacion) {
        checkConnection();
        if(mysqlUp) daoMySql.insertPublicacion(publicacion);
        daoCsv.insertPublicacion(publicacion);
    }

    @Override
    public void updateLikes(int id) {
        checkConnection();
        if(mysqlUp) daoMySql.updateLikes(id);
        daoCsv.updateLikes(id);
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        checkConnection();
        if(mysqlUp) return daoMySql.getSixPublicaciones(page);
        return daoCsv.getSixPublicaciones(page);
    }

    @Override
    public List<Publicacion> getPublicacionesByHashTag(String hashtag) {
        checkConnection();
        if(mysqlUp) return daoMySql.getPublicacionesByHashTag(hashtag);
        return daoCsv.getPublicacionesByHashTag(hashtag);
    }

    @Override
    public List<Publicacion> getPublicacionesByUsuario(String nombre) {
        checkConnection();
        if(mysqlUp) return daoMySql.getPublicacionesByUsuario(nombre);
        return daoCsv.getPublicacionesByUsuario(nombre);
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        checkConnection();
        if(mysqlUp) return daoMySql.getPublicacionById(id);
        return daoCsv.getPublicacionById(id);
    }

    @Override
    public int getSizePublicaciones() {
        checkConnection();
        if(mysqlUp) return daoMySql.getSizePublicaciones();
        return daoCsv.getSizePublicaciones();
    }

    @Override
    public void insertComentario(Comentario comentario) {
        checkConnection();
        if(mysqlUp) daoMySql.insertComentario(comentario);
        daoCsv.insertComentario(comentario);
    }

    @Override
    public List<Comentario> getComentariosById(int id) {
        checkConnection();
        if(mysqlUp) return daoMySql.getComentariosById(id);
        return daoCsv.getComentariosById(id);
    }
}
