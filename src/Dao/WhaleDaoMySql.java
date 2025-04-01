package Dao;

import DataBase.ConexionDataBase;
import PageModel.Publicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class WhaleDaoMySql implements WhaleDao {
    @Override
    public List<Publicacion> getPublicaciones() {
        try(Connection con = ConexionDataBase.getInstance()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM PUBLICACIONES");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("texto"));
                System.out.println(sb.toString());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return List.of();
    }


}
