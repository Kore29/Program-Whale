package DataBase;

public class ConexioDataInfo {
    private static String USR = "a24biecalcol_whale";
    private static String PWD = "12345aA!";
    private static String URL = "jdbc:mysql://dam.inspedralbes.cat:3306/a24biecalcol_Whale";

    public static String getUSR() {
        return USR;
    }

    public static String getPWD() {
        return PWD;
    }

    public static String getURL() {
        return URL;
    }
}
