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

public WhaleDaoMySql(){
    try {
        con = ConexionDataBase.getInstance();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

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

        try {
            String query = "SELECT * FROM USUARIOS WHERE name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);

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
    public void getAllPublicaciones() {
        try {
            String query = "SELECT * FROM CONTENIDO WHERE id_referencia IS NULL ORDER BY creacion DESC";
            PreparedStatement stmt = con.prepareStatement(query);
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
                    getComentariosById(rs.getInt("id_contenido"))
                );

                UtilsShow.showPublicacion(tempPubl, tempPubl.getComentarios());
            }

            rs.close();
            stmt.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
