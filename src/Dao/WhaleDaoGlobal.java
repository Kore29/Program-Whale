package Dao;

import PageModelNew.Comentario;
import PageModelNew.Publicacion;
import PageModelNew.Usuario;

import java.util.List;

import static Utils.UtilsColors.c;
import static Utils.UtilsColors.r;

public class WhaleDaoGlobal implements WhaleDao {
    private final WhaleDaoMySql daoMySql;
    private final WhaleDaoCSV daoCsv;
    private boolean mysqlUp;

    /**
     * Clase que implementa la interfaz WhaleDao unificando el acceso a datos
     * tanto desde una base de datos MySQL como desde ficheros CSV.
     * - Si la base de datos está disponible, se usará como fuente principal.
     * - En caso de fallo de conexión, se utilizan los datos en local (CSV).
     * También permite sincronizar datos entre ambas fuentes.
     */
    public WhaleDaoGlobal() {
        daoCsv = new WhaleDaoCSV();
        daoMySql = new WhaleDaoMySql();

        // Solo en caso de prueba
        // moveMySqlToCsv();

        mysqlUp = daoMySql.testConnection();
        if (mysqlUp) System.out.println(c[2]+"Conexión a la base de datos establecida correctamente."+r);
        else System.err.println(c[1]+"ERROR al conectar a la base de datos: "+c[2]+"\nDatos temporales de ficheros CSV en uso"+r);
    }

    /**
     * Verifica si hay conexión a MySQL.
     * Si se recupera la conexión, transfiere los datos del CSV a la base de datos.
     */
    private void checkConnection() {
        if (!mysqlUp){
            if(daoMySql.testConnection()){
                moveCsvToMySql();
                mysqlUp = true;
            }
        }
    }

    /**
     * Transfiere los datos desde los CSV hacia la base de datos MySQL.
     * Este proceso borra los datos existentes en la BBDD y los reemplaza.
     */
    public void moveCsvToMySql() {
        if (!daoMySql.testConnection()) {
            System.err.println(c[1] + "No se pudo conectar a MySQL. Operación cancelada." + r);
            return;
        }

        System.out.println(c[5]+"Conectando con la Base de datos... Espere..."+r);

        daoMySql.clearAllData();

        for (Usuario usuario : daoCsv.getAllUsuarios()) {
            daoMySql.insertUsuario(usuario);
        }

        for (String amigos : daoCsv.getAllAmigos()) {
            daoMySql.insertAmigo(amigos.split(":")[0],amigos.split(":")[1]);
        }

        for (Publicacion p : daoCsv.getAllContenido()) {
            daoMySql.insertPublicacion(p);
            if (!p.getComentarios().isEmpty()) {
                for (Comentario c : p.getComentarios()) {
                    daoMySql.insertComentario(c);
                }
            }
        }

        System.out.println(c[5] + "Transferencia completada." + r);
    }

    /**
     * Transfiere los datos desde la base de datos MySQL hacia los archivos CSV.
     * Este proceso borra los datos existentes en los ficheros y los reemplaza.
     */
    public void moveMySqlToCsv() {
        if (!daoMySql.testConnection()) {
            System.err.println(c[1] + "No se pudo conectar a MySQL. Operación cancelada." + r);
            return;
        }

        System.out.println(c[5] + "Transferencia MySql → CSV en curso... Puede tardar un rato..." + r);

        daoCsv.clearAllData();

        for (Usuario usuario : daoMySql.getAllUsuarios()) {
            daoCsv.insertUsuario(usuario);
            for (String amigo : daoMySql.getAmigosByUsuario(usuario.getNombre())) {
                daoCsv.insertAmigo(usuario.getNombre(), amigo);
            }
        }

        int total = daoMySql.getSizePublicaciones();
        for (int i = 0; i < total; i++) {
            List<Publicacion> publicaciones = daoMySql.getSixPublicaciones(i);
            for (Publicacion p : publicaciones) {
                daoCsv.insertPublicacion(p);
                daoCsv.updateLikes(p.getId());
                for (Comentario c : daoMySql.getComentariosByPublicacion(p.getId())) {
                    daoCsv.insertComentario(c);
                }
            }
        }

        System.out.println(c[5] + "Transferencia completada." + r);
    }

    /** Devuelve la lista de todos los usuarios existentes en Whale */
    @Override
    public List<Usuario> getAllUsuarios() {
        checkConnection();
        if(mysqlUp) {
            List<Usuario> usuarios = daoMySql.getAllUsuarios();
            if (usuarios == null) mysqlUp = false;
            return usuarios;
        }
        return daoCsv.getAllUsuarios();
    }

    /** Devuelve todas las publicaciones con sus comentarios */
    @Override
    public List<Publicacion> getAllContenido() {
        checkConnection();
        if(mysqlUp) {
            List<Publicacion> publicaciones = daoMySql.getAllContenido();
            if (publicaciones == null) mysqlUp = false;
            return publicaciones;
        }
        return daoCsv.getAllContenido();
    }

    /** Devuelve una lista de todos los pares usuario:amigo */
    @Override
    public List<String> getAllAmigos() {
        checkConnection();
        if(mysqlUp) {
            List<String> amigos = daoMySql.getAllAmigos();
            if (amigos == null) mysqlUp = false;
            return amigos;
        }
        return daoCsv.getAllAmigos();
    }

    /** Devuelve los amigos de un usuario dado su nombre */
    @Override
    public List<String> getAmigosByUsuario(String nombre) {
        checkConnection();
        if(mysqlUp) {
            List<String> amigos = daoMySql.getAmigosByUsuario(nombre);
            if (amigos == null) mysqlUp = false;
            return amigos;
        }
        return daoCsv.getAmigosByUsuario(nombre);
    }

    /** Añade un amigo a un usuario */
    @Override
    public void insertUsuario(Usuario usuario) {
        checkConnection();
        if(mysqlUp) daoMySql.insertUsuario(usuario);
        daoCsv.insertUsuario(usuario);
    }

    /** Devuelve un usuario por email */
    @Override
    public Usuario getUsuarioByEmail(String email) {
        checkConnection();
        Usuario usuario = null;

        if (mysqlUp) {
            usuario = daoMySql.getUsuarioByEmail(email);
            if (usuario == null) mysqlUp = false;
        }
        return daoCsv.getUsuarioByEmail(email);
    }

    /** Devuelve un usuario por nombre */
    @Override
    public Usuario getUsuarioByName(String name) {
        checkConnection();
        if(mysqlUp) return daoMySql.getUsuarioByName(name);
        return daoCsv.getUsuarioByName(name);
    }

    @Override
    public void changeName(Usuario usuario, String name) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.changeName(usuario, name);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.changeName(usuario, name);
    }

    /** Añade un amigo a un usuario */
    @Override
    public void insertAmigo(String usuario, String nombre) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.insertAmigo(usuario, nombre);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.insertAmigo(usuario, nombre);
    }

    /** Elimina un amigo de un usuario */
    @Override
    public void removeAmigo(String usuario, String nombre) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.removeAmigo(usuario, nombre);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.removeAmigo(usuario, nombre);
    }

    /** Inserta una publicación en la BBDD y CSV */
    @Override
    public void insertPublicacion(Publicacion publicacion) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.insertPublicacion(publicacion);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.insertPublicacion(publicacion);
    }

    /** Elimina una publicación */
    @Override
    public void removePublicacion(Publicacion publicacion) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.removePublicacion(publicacion);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.removePublicacion(publicacion);
    }

    /** Actualiza el número de likes de una publicación */
    @Override
    public void updateLikes(int id) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.updateLikes(id);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.updateLikes(id);
    }

    /** Devuelve 6 publicaciones por página (paginación) */
    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        checkConnection();
        if(mysqlUp) {
            List<Publicacion> publicaciones = daoMySql.getSixPublicaciones(page);
            if (publicaciones == null) mysqlUp = false;
            return publicaciones;
        }
        return daoCsv.getSixPublicaciones(page);
    }

    /** Devuelve las publicaciones que tienen un hashtag específico */
    @Override
    public List<Publicacion> getPublicacionesByHashTag(String hashtag) {
        checkConnection();
        if(mysqlUp) {
            List<Publicacion> publicaciones = daoMySql.getPublicacionesByHashTag(hashtag);
            if (publicaciones == null) mysqlUp = false;
            return publicaciones;
        }
        return daoCsv.getPublicacionesByHashTag(hashtag);
    }

    /** Devuelve las publicaciones hechas por un usuario */
    @Override
    public List<Publicacion> getPublicacionesByUsuario(String nombre) {
        checkConnection();
        if(mysqlUp) {
            List<Publicacion> publicaciones = daoMySql.getPublicacionesByUsuario(nombre);
            if (publicaciones == null) mysqlUp = false;
            return publicaciones;
        }
        return daoCsv.getPublicacionesByUsuario(nombre);
    }

    /** Devuelve una publicación específica por su ID */
    @Override
    public Publicacion getPublicacionById(int id) {
        checkConnection();
        if(mysqlUp) {
            Publicacion publicacion = daoMySql.getPublicacionById(id);
            if (publicacion == null) mysqlUp = false;
            return publicacion;
        }
        return daoCsv.getPublicacionById(id);
    }

    /** Devuelve el número total de publicaciones */
    @Override
    public int getSizePublicaciones() {
        checkConnection();
        if(mysqlUp) {
            int size = daoMySql.getSizePublicaciones();
            if (size == 0) mysqlUp = false;
            return size;
        }
        return daoCsv.getSizePublicaciones();
    }

    /** Inserta un comentario en la publicación correspondiente */
    @Override
    public void insertComentario(Comentario comentario) {
        checkConnection();
        if(mysqlUp) {
            try {
                daoMySql.insertComentario(comentario);
            } catch (Exception e) {
                mysqlUp = false;
            }
        }
        daoCsv.insertComentario(comentario);
    }

    /** Devuelve los comentarios asociados a una publicación */
    @Override
    public List<Comentario> getComentariosByPublicacion(int id) {
        checkConnection();
        if(mysqlUp) {
            List<Comentario> comentarios = daoMySql.getComentariosByPublicacion(id);
            if (comentarios == null) mysqlUp = false;
            return comentarios;
        }
        return daoCsv.getComentariosByPublicacion(id);
    }
}
