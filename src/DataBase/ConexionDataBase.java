package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DataBase.ConexioDataInfo;

public class ConexionDataBase {

    private static Connection connect;

    private ConexionDataBase() {}


    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(
            ConexioDataInfo.getURL(),
            ConexioDataInfo.getUSR(),
            ConexioDataInfo.getPWD()
        );
    }

    public void closeConnection() {
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}