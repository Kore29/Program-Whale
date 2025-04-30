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
                //TODO: restaurar BBDD desde CSV
                getBackDataBase();
                mysqlUp = true;
            }
        }
    }

    private void getBackDataBase() {

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
    public List<Usuario> getAllUsuarios() {
        checkConnection();
        if(mysqlUp) return daoMySql.getAllUsuarios();
        return daoCsv.getAllUsuarios();
    }

    @Override
    public List<String> getAmigos(String nombre) {
        checkConnection();
        if(mysqlUp) return daoMySql.getAmigos(nombre);
        return daoCsv.getAmigos(nombre);
    }

    @Override
    public void insertAmigo(Usuario usuario, String nombre) {
        checkConnection();
        if(mysqlUp) daoMySql.insertAmigo(usuario, nombre);
        daoCsv.insertAmigo(usuario, nombre);
    }

    @Override
    public void removeAmigo(Usuario usuario, String nombre) {
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
    public List<Publicacion> getFilterPublicaciones(String hashtag) {
        checkConnection();
        if(mysqlUp) return daoMySql.getFilterPublicaciones(hashtag);
        return daoCsv.getFilterPublicaciones(hashtag);
    }

    @Override
    public List<Publicacion> getUsuarioPublicaciones(String nombre) {
        checkConnection();
        if(mysqlUp) return daoMySql.getUsuarioPublicaciones(nombre);
        return daoCsv.getUsuarioPublicaciones(nombre);
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        checkConnection();
        if(mysqlUp) return daoMySql.getPublicacionById(id);
        return daoCsv.getPublicacionById(id);
    }

    @Override
    public int sizePublicaciones() {
        checkConnection();
        if(mysqlUp) return daoMySql.sizePublicaciones();
        return daoCsv.sizePublicaciones();
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
