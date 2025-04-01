package App;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;

public class Main {
    static WhaleDao whaleDao = new WhaleDaoMySql();

    public static void main(String[] args) {
        whaleDao.getPublicaciones();
    }
}
