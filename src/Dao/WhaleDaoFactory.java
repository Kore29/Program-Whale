package Dao;

public class WhaleDaoFactory {
    public enum TipoDao {
        MYSQL,
        FICHERO,
        GLOBAL
    }

    public static WhaleDao getDao(TipoDao tipo) {
        return switch (tipo) {
            case MYSQL -> new WhaleDaoMySql();
            case FICHERO -> new WhaleDaoCSV();
            case GLOBAL -> new WhaleDaoGlobal();
        };
    }
}
