package Dao;

import DataBase.ConexionDataBase;
import PageModelNew.*;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WhaleDaoMySql implements WhaleDao {

    public boolean testConnection() {
        try {
            Connection conn = ConexionDataBase.getInstance();

            if (conn != null && !conn.isClosed()) return true;
            else return false;

        } catch (SQLException e) {
            return false;
        }
    }

    public WhaleDaoMySql() {testConnection();}

    public void clearAllData() {
        try (Connection con = ConexionDataBase.getInstance();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate("DELETE FROM AMIGOS");
            stmt.executeUpdate("DELETE FROM CONTENIDO");
            stmt.executeUpdate("DELETE FROM USUARIOS");

        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar los datos", e);
        }
    }


    @Override
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        List<String> nombresUsuarios = new ArrayList<>();

        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("contrasenya"),
                        rs.getString("email"),
                        rs.getString("creacion"),
                        null,
                        null
                );
                usuarios.add(usuario);
                nombresUsuarios.add(usuario.getNombre());
            }

            for (int i = 0; i < usuarios.size(); i++) {
                Usuario usuario = usuarios.get(i);
                usuario.setAmigos(getAmigosByUsuario(nombresUsuarios.get(i)));
                usuario.setPublicaciones(getPublicacionesByUsuario(nombresUsuarios.get(i)));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    @Override
    public List<Publicacion> getAllContenido() {
        List<Publicacion> publicaciones = new ArrayList<>();
        Map<Integer, Publicacion> mapaPublicaciones = new HashMap<>();
        List<Comentario> comentariosPendientes = new ArrayList<>();

        String query = "SELECT * FROM CONTENIDO";

        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id_contenido = rs.getInt("id_contenido");
                String autor = rs.getString("autor");
                String creacion = rs.getString("creacion");
                String multimedia = rs.getString("multimedia");
                String texto = rs.getString("texto");
                Integer likes = rs.getObject("likes") != null ? rs.getInt("likes") : 0;
                String hashtag = rs.getString("hashtag");
                Integer id_referencia = rs.getObject("id_referencia") != null ? rs.getInt("id_referencia") : null;

                if (id_referencia == null) {
                    // Es una publicación
                    Publicacion pub = new Publicacion(id_contenido, autor, creacion, multimedia, texto, likes, hashtag, new ArrayList<>());
                    publicaciones.add(pub);
                    mapaPublicaciones.put(id_contenido, pub);
                } else {
                    // Es un comentario
                    Comentario comentario = new Comentario(id_contenido, autor, creacion, multimedia, texto, id_referencia);
                    comentariosPendientes.add(comentario);
                }
            }

            // Asociar los comentarios a las publicaciones correspondientes
            for (Comentario comentario : comentariosPendientes) {
                Publicacion pub = mapaPublicaciones.get(comentario.getIdReferencia());
                if (pub != null) {
                    pub.getComentarios().add(comentario);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al leer todo el contenido desde la base de datos MySQL");
            e.printStackTrace();
        }

        return publicaciones;
    }

    @Override
    public List<String> getAllAmigos() {
        return List.of();
    }


    @Override
    public List<String> getAmigosByUsuario(String nombre) {
        List<String> amigos = new ArrayList<>();
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT amigo FROM AMIGOS WHERE usuario = ?")) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                amigos.add(rs.getString("amigo"));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return amigos;
    }

    @Override
    public void insertUsuario(Usuario usuario) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO USUARIOS (nombre, contrasenya, email, creacion) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getCreacion());

            stmt.execute();


        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario", e);
        }
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Usuario usuario = null;
    
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE email = ?")) {
    
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("contrasenya"),
                        rs.getString("email"),
                        rs.getString("creacion"),
                        null,
                        null
                    );
                }
            }

            if (usuario != null) {
                usuario.setAmigos(getAmigosByUsuario(usuario.getNombre()));
                usuario.setPublicaciones(getPublicacionesByUsuario(usuario.getNombre()));
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por email", e);
        }
    
        return usuario;
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        Usuario usuario = null;
    
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE nombre = ?")) {
    
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("contrasenya"),
                        rs.getString("email"),
                        rs.getString("creacion"),
                        null,
                        null
                    );
                }
            }

            if (usuario != null) {
                usuario.setAmigos(getAmigosByUsuario(usuario.getNombre()));
                usuario.setPublicaciones(getPublicacionesByUsuario(usuario.getNombre()));
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por nombre", e);
        }
    
        return usuario;
    }

    @Override
    public void changeName(Usuario usuario, String name) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("UPDATE USUARIOS SET nombre = ? WHERE nombre = ?")) {
            stmt.setString(1,name);
            stmt.setString(2,usuario.getNombre());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAmigo(String usuario, String nombre) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM AMIGOS a WHERE a.usuario = ? AND a.amigo = ?")) {

            stmt.setString(1, usuario);
            stmt.setString(2, nombre);
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertAmigo(String usuario, String nombre) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO AMIGOS VALUES (?,?)")) {

            stmt.setString(1, usuario);
            stmt.setString(2, nombre);
            stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertPublicacion(Publicacion publicacion) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO CONTENIDO (autor,creacion,likes,multimedia,hashtag,texto) VALUES (?,?,?,?,?,?)")) {
            stmt.setString(1, publicacion.getAutor());
            stmt.setString(2, publicacion.getCreacion());
            stmt.setInt(3, publicacion.getLikes());
            stmt.setString(4, publicacion.getMultimedia());
            stmt.setString(5,publicacion.getHashtag());
            stmt.setString(6, publicacion.getTexto());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removePublicacion(Publicacion publicacion) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("DELETE FROM CONTENIDO WHERE id_contenido = ?")) {

            stmt.setInt(1, publicacion.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("No se encontró ninguna publicación con ese ID.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateLikes(int id) {
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("UPDATE CONTENIDO c SET c.likes = c.likes +1 WHERE c.id_contenido = ?")) {
            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertComentario(Comentario comentario) {
        try (Connection con = ConexionDataBase.getInstance();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO CONTENIDO (id_referencia,autor,creacion,likes,multimedia,texto) VALUES (?,?,?,null,?,?)")) {
            stmt.setInt(1, comentario.getIdReferencia());
            stmt.setString(2, comentario.getAutor());
            stmt.setString(3, comentario.getCreacion());
            stmt.setString(4,comentario.getMultimedia());
            stmt.setString(5, comentario.getTexto());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        int offset = (page-1)*6;
        List<Publicacion> publicaciones = new ArrayList<>();


        try (Connection con = ConexionDataBase.getInstance();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia IS NULL ORDER BY creacion DESC LIMIT 6 OFFSET ?")) {

            stmt.setInt(1, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Publicacion tempPubl = new Publicacion(
                        rs.getInt("id_contenido"),
                        rs.getString("autor"),
                        rs.getString("creacion"),
                        rs.getString("multimedia"),
                        rs.getString("texto"),
                        rs.getInt("likes"),
                        rs.getString("hashtag"),
                        null
                );

                publicaciones.add(tempPubl);

            }

            for (Publicacion activePublciacion : publicaciones) {
                List<Comentario> comentarios = getComentariosById(activePublciacion.getId());
                activePublciacion.addComentarios(comentarios);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publicaciones;
    }

    @Override
    public List<Publicacion> getPublicacionesByHashTag(String hashtag) {
        List<Publicacion> publicaciones = new ArrayList<>();

        try (Connection con = ConexionDataBase.getInstance();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia IS NULL AND hashtag = ?")) {

            stmt.setString(1, hashtag);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Publicacion tempPubl = new Publicacion(
                        rs.getInt("id_contenido"),
                        rs.getString("autor"),
                        rs.getString("creacion"),
                        rs.getString("multimedia"),
                        rs.getString("texto"),
                        rs.getInt("likes"),
                        rs.getString("hashtag"),
                        null
                );
                publicaciones.add(tempPubl);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return publicaciones;
    }

    @Override
    public List<Publicacion> getPublicacionesByUsuario(String nombre) {
        List<Publicacion> publicaciones = new ArrayList<>();

        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia IS NULL AND autor = ?")) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Publicacion tempPubl = new Publicacion(
                        rs.getInt("id_contenido"),
                        rs.getString("autor"),
                        rs.getString("creacion"),
                        rs.getString("multimedia"),
                        rs.getString("texto"),
                        rs.getInt("likes"),
                        rs.getString("hashtag"),
                        null
                );
                publicaciones.add(tempPubl);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return publicaciones;
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        Publicacion tempPubl = null;

        try (Connection con = ConexionDataBase.getInstance();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia IS NULL AND id_contenido = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tempPubl = new Publicacion(
                    rs.getInt("id_contenido"),
                    rs.getString("autor"),
                    rs.getString("creacion"),
                    rs.getString("multimedia"),
                    rs.getString("texto"),
                    rs.getInt("likes"),
                    rs.getString("hashtag"),
                    getComentariosById(rs.getInt("id_contenido"))
                );

                return tempPubl;
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSizePublicaciones() {
        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(c.id_contenido) FROM CONTENIDO c");
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Comentario> getComentariosById(int id) {
        List<Comentario> comentarios = new ArrayList<>();

        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia = ? ORDER BY creacion ASC");

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comentarios.add(new Comentario(
                    rs.getInt("id_contenido"),
                    rs.getString("autor"),
                    rs.getString("creacion"),
                    rs.getString("multimedia"),
                    rs.getString("texto"),
                    rs.getInt("id_referencia")
                ));
            }

            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return comentarios;
    }

}
