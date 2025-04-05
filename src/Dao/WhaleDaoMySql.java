package Dao;

import DataBase.ConexionDataBase;
import PageModelNew.*;
import Utils.UtilsShow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WhaleDaoMySql implements WhaleDao {

    @Override
    public void getAllPublicaciones() {
        try (Connection con = ConexionDataBase.getInstance()) {
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
                        rs.getString("hashtag")
                );

                UtilsShow.showPublicacion(tempPubl, getComentariosByReferencia(tempPubl.getId()));
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Publicacion getPublicacionById(int id) {
        Publicacion tempPubl = null;

        try (Connection con = ConexionDataBase.getInstance()) {
            String query = "SELECT * FROM CONTENIDO WHERE id_contenido = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idContenido = rs.getInt("id_contenido");
                String autor = rs.getString("autor");
                String creacion = rs.getString("creacion");
                Integer likes = rs.getInt("likes");
                String multimedia = rs.getString("multimedia");
                String hashtag = rs.getString("hashtag");
                String texto = rs.getString("texto");

                tempPubl = new Publicacion(idContenido, autor, creacion, multimedia, texto, likes, hashtag);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tempPubl;
    }

    public List<Comentario> getComentariosByReferencia(int idRef) {
        List<Comentario> comentarios = new ArrayList<>();

        try (Connection con = ConexionDataBase.getInstance()) {
            String query = "SELECT * FROM CONTENIDO WHERE id_referencia = ? ORDER BY creacion ASC";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idRef);
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
