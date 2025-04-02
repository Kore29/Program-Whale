package Dao;

import DataBase.ConexionDataBase;
import PageModelNew.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WhaleDaoMySql implements WhaleDao {
    @Override
    public Publicacion getPublicacionById(int id) {
        Publicacion tempPubl = null;

        try (Connection con = ConexionDataBase.getInstance()) {
            String query = "SELECT * FROM CONTENIDO WHERE id_contenido = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Mapeamos los resultados al objeto Publicacion
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

    @Override
    public Comentario getComentarioByIds(int id_con, int id_ref) {
        Comentario tempCome = null;

        try (Connection con = ConexionDataBase.getInstance()) {
            String query = "SELECT * FROM CONTENIDO WHERE id_contenido = "+id_con+" AND id_referencia = "+id_ref;
            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idContenido = rs.getInt("id_contenido");
                String autor = rs.getString("autor");
                String creacion = rs.getString("creacion");
                String multimedia = rs.getString("multimedia");
                String texto = rs.getString("texto");
                int idReferencia = rs.getInt("id_referencia");

                tempCome = new Comentario(idContenido, autor, creacion, multimedia, texto, idReferencia);
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tempCome;
    }
}
