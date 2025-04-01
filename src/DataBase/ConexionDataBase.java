package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDataBase {
    private static Connection connect;

    private void ConnectionDB() {

    }

    private static void openConnection() {
        connect = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(ConexioDataInfo.getURL(),ConexioDataInfo.getUSR(),ConexioDataInfo.getPWD());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getInstance() {
        if (connect == null) {
            openConnection();
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
