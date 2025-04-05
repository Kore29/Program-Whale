package App;

import Dao.WhaleDao;
import Dao.WhaleDaoMySql;
import PageModelNew.*;

public class Main {
    static WhaleDao whaleDao = new WhaleDaoMySql();

    public static void main(String[] args) {
        whaleDao.getAllPublicaciones();
    }
}
