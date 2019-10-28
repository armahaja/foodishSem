package edu.cmu.andrew.foodish.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class Config {

    private static Config config;
    private static HashMap<String, Object> currentConfig = new HashMap<>();


    public static String logFile = "/var/log/app.log";
    public static String logLevel = "ERROR";
    public static String logName = "AppLog";


    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String url = "jdbc:mysql://localhost:3306/";
    public static final String urlEnd = "?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";
    public static final String usr = "root";
    public static final String pwd = "caozihan59";
    public static final String dbName = "Foodish";


}
