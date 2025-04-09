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

    // Public static method to get the instance of the class
    public static Connection getInstance() {
        if (connect == null) {
            openConection();
        }
        return connect;
    }

    // Public method to close the connection
    public void closeConnection() {
        try {
            connect.close();
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}