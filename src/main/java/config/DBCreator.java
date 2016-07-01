package config;

import org.sql2o.Sql2o;

/**
 * Created by Gi Wah Davalos on 1/07/2016.
 */
public class DBCreator {
    private final Sql2o sql2o = new Sql2o(DBInfo.getURL(), DBInfo.DB_USER, DBInfo.DB_PASSWORD);

    public static void create() {
        
    }
}
