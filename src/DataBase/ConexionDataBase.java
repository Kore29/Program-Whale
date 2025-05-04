package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDataBase {
    private static Connection connection;

    private ConexionDataBase() {}

    /**
     * Metodo para obtener una instancia de la conexión a la base de datos.
     * Implementa el patrón Singleton para asegurar una única conexión.
     *
     * @return Una conexión activa a la base de datos.
     */
    public static Connection getInstance() throws SQLException {
        if (!isConnectionActive()) {
            try {
                connection = DriverManager.getConnection(
                        ConexioDataInfo.getURL(),
                        ConexioDataInfo.getUSR(),
                        ConexioDataInfo.getPWD()
                );
            } catch (Exception _) {

            }
        }
        return connection;
    }

    /**
     * Metodo para cerrar la conexión a la base de datos.
     * Verifica si la conexión existe y está abierta antes de intentar cerrarla.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexión a la base de datos cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("ERROR al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para verificar si la conexión está activa.
     * @return true si la conexión está activa, false en caso contrario.
     */
    public static boolean isConnectionActive() {
        if (connection != null) {
            try {
                return !connection.isClosed() && connection.isValid(5);
            } catch (SQLException e) {
                System.err.println("ERROR al verificar la conexión: " + e.getMessage());
            }
        }
        return false;
    }
}