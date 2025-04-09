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

    private static Connection con;
    public WhaleDaoMySql(){
        con  = ConexionDataBase.getInstance();
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Usuario mainUsuario = null;

        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE email = ?");
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mainUsuario;
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        Usuario mainUsuario = null;

        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM USUARIOS WHERE nombre = ?");
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

        } catch (Exception e) {
                throw new RuntimeException(e);
        }

        return mainUsuario;
    }

    @Override
    public List<Publicacion> getSixPublicaciones(int page) {
        int offset = (page-1)*6;
        List<Publicacion> publicaciones = new ArrayList<>();

        try {
            Connection con = ConexionDataBase.getInstance();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM CONTENIDO ORDER BY creacion DESC LIMIT 6 OFFSET ?");
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
                    getComentariosById(rs.getInt("id_contenido"))
                );

                publicaciones.add(tempPubl);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return comentarios;
    }
}
