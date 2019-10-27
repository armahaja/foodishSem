package edu.cmu.andrew.foodish.server.managers;


import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.foodish.server.utils.AppLogger;
import edu.cmu.andrew.foodish.server.utils.MongoPool;
import org.bson.Document;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class Manager {
    protected MongoCollection<Document> userCollection;
    protected MongoCollection<Document> sessionCollection;
    protected Connection con;

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String urlEnd = "?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";
    private static String usr = "root";
    private static String pwd = "caozihan59";
    private static String dbName = "Foodish";

    public Manager() throws ClassNotFoundException, SQLException {
        this.userCollection = MongoPool.getInstance().getCollection("users");
        Class.forName(driver);
        this.con = DriverManager.getConnection(url + dbName + urlEnd, usr, pwd);
    }

    protected AppException handleException(String message, Exception e){
        if (e instanceof AppException && !(e instanceof AppInternalServerException))
            return (AppException)e;
        AppLogger.error(message, e);
        return new AppInternalServerException(-1);
    }
}
