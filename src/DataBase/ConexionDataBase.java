package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import DataBase.ConexioDataInfo;

public class ConexionDataBase {

    private static Connection connect;

    private ConexionDataBase() {}


    private static void openConection() {
        connect = null;
        try {
            connect = DriverManager.getConnection(ConexioDataInfo.getURL(), ConexioDataInfo.getUSR(), ConexioDataInfo.getPWD());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() throws SQLException {
        if (connect == null || connect.isClosed()) {
            openConection();
        }
        return connect;
    }

    public void closeConnection() {
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}