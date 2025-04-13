package Dao;

import DataBase.ConexionDataBase;
import PageModelNew.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class WhaleDaoMySql implements WhaleDao {

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Usuario mainUsuario = null;
    
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE email = ?")) {
    
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mainUsuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("contrasenya"),
                        rs.getString("email"),
                        rs.getString("creacion"),
                        null,
                        null
                    );
                }
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por email", e);
        }
    
        return mainUsuario;
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        Usuario mainUsuario = null;
    
        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE nombre = ?")) {
    
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mainUsuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("contrasenya"),
                        rs.getString("email"),
                        rs.getString("creacion"),
                        null,
                        null
                    );
                }
            }
    
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por nombre", e);
        }
    
        return mainUsuario;
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
    public int sizePublicaciones() {
        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(c.id_contenido) FROM CONTENIDO c");
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        int offset = (page-1)*6;
        List<Publicacion> publicaciones = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = ConexionDataBase.getInstance();

            stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_referencia IS NULL ORDER BY creacion DESC LIMIT 6 OFFSET ?");
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
    public Publicacion getPublicacionById(int id) {
        Publicacion tempPubl = null;

        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO WHERE id_contenido = ?");
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
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tempPubl;
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
            e.printStackTrace();
        }

        return comentarios;
    }

    @Override
    public List<String> getAmigos(String nombre) {
        List<String> amigos = new ArrayList<>();
        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT AMIGOS FROM AMIGOS WHERE usuario = ?");
            stmt.setString(1, nombre);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                amigos.add(rs.getString("amigo"));
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return amigos;
    }
}
