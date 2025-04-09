package Dao;

import DataBase.ConexionDataBase;
import PageModelNew.*;
import Utils.UtilsShow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WhaleDaoMySql implements WhaleDao {
    static Connection con;

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Usuario mainUsuario = null;

        try {
            String query = "SELECT * FROM USUARIOS WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                mainUsuario = new Usuario(
                    rs.getString("nombre"),
                    rs.getString("contrasenya"),
                    rs.getString("email"),
                    rs.getString("creacion"),
                    null,
                    null
                );
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mainUsuario;
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        Usuario mainUsuario = null;

        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE nombre = ?")) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

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

            rs.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mainUsuario;
    }

    @Override
    public List<Publicacion> getSixPublicaciones() {
        int pagiActual = 1; int offset = (pagiActual-1)*6;
        List<Publicacion> publicaciones = new ArrayList<>();

        String query = "SELECT * FROM CONTENIDO ORDER BY creacion DESC LIMIT ? OFFSET ?";

        try (Connection con = ConexionDataBase.getInstance();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Publicacion tempPubl = new Publicacion(
                    rs.getInt("id_contenido"),
                    rs.getString("autor"),
                    rs.getString("creacion"),
                    rs.getString("multimedia"),
                    rs.getString("texto"),
                    rs.getInt("likes"),
                    rs.getString("hashtag"),
                    getComentariosById(rs.getInt("id_contenido"))
                );

                publicaciones.add(tempPubl);

            }

            rs.close();
            stmt.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publicaciones;
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        Publicacion tempPubl = null;

        try {
            String query = "SELECT * FROM CONTENIDO WHERE id_contenido = ?";
            PreparedStatement stmt = con.prepareStatement(query);
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
            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tempPubl;
    }

    @Override
    public List<Comentario> getComentariosById(int id) {
        List<Comentario> comentarios = new ArrayList<>();

        try {
            String query = "SELECT * FROM CONTENIDO WHERE id_referencia = ? ORDER BY creacion ASC";
            PreparedStatement stmt = con.prepareStatement(query);
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

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return comentarios;
    }
}
