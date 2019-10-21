package com.company;

import java.sql.*;

public class Main {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String urlEnd = "?characterEncoding=utf8&useSSL=false";
    private static String usr = "root";
    private static String pwd = "caozihan59";

    private static String getAllFromOneDatabase = "select * from ";
    private static final String insertToFoodBuddy = "insert into foodbuddy(FID, FNAME, AGE, GENDER) values(?, ?, ?, ?)";
    private static final String insertToChef = "insert into chef(CID, CNAME, AGE, GENDER) values(?, ?, ?, ?)";
    private static final String updateFromChef = "update chef set CNAME = ? where CID = ?";
    private static final String deleteFromChef = "delete from chef where CID = ?";

    public static Connection getConnection(String dbName) throws Exception {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url + dbName + urlEnd, usr, pwd);
        return con;
    }

    public static void main(String[] args) throws Exception {
        getAllFoodBuddy("sys");
        insertToChef("sys", "5", "Insert tester", "30", "M");
        getAllFoodBuddy("sys");
        updateFromChef("sys", "Update tester", "5");
        getAllFoodBuddy("sys");
        deleteFromChef("sys", "5");
        getAllFoodBuddy("sys");
    }

    public static void getAllFoodBuddy(String dbName) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = getConnection(dbName);
            String tableName = "Chef";
            PreparedStatement statement = con.prepareStatement(getAllFromOneDatabase + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            while(result.next()) {
                System.out.println("CID: " + result.getString("CID") +
                        " CNAME: " + result.getString("CNAME") +
                        " GENDER: " + result.getString("GENDER") +
                        " AGE: " + result.getString("AGE"));
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void insertToFoodBuddy(String dbName, String str1, String str2, String str3, String str4) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(insertToFoodBuddy);
            statement.setString(1, str1);
            statement.setString(2, str2);
            statement.setString(3, str3);
            statement.setString(4, str4);
            statement.execute();
            System.out.println("Insert food buddy executes successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void insertToChef(String dbName, String str1, String str2, String str3, String str4) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(insertToChef);
            statement.setString(1, str1);
            statement.setString(2, str2);
            statement.setString(3, str3);
            statement.setString(4, str4);
            statement.execute();
            System.out.println("Insert chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void updateFromChef(String dbName, String str1, String str2) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(updateFromChef);
            statement.setString(1, str1);
            statement.setString(2, str2);
            statement.execute();
            System.out.println("Update chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void deleteFromChef(String dbName, String str) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(deleteFromChef);
            statement.setString(1, str);
            statement.execute();
            System.out.println("Delete chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }
}
