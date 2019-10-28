package edu.cmu.andrew.foodish.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLPool {

    private static MySQLPool mp;

    protected static Connection con;

    private MySQLPool() throws ClassNotFoundException, SQLException {
        Class.forName(Config.driver);
        this.con = DriverManager.getConnection(Config.url + Config.dbName + Config.urlEnd, Config.usr, Config.pwd);
    }

    public static MySQLPool getInstance() throws ClassNotFoundException, SQLException {
        if(mp == null){
            mp = new MySQLPool();
        }
        return mp;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return this.con;
    }
}
