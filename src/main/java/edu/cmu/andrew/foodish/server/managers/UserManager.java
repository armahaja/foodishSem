package edu.cmu.andrew.foodish.server.managers;

import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.models.User;
import edu.cmu.andrew.foodish.server.utils.MySQLPool;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class UserManager extends Manager {
    public static UserManager _self;

    protected Connection con;
    private static final String tableName = "User";

    private static final String Q_getAllUsers = "select * from ";
    private static final String Q_getUserById = "select * from User where idUser = ?";
    private static final String Q_insertUser = "insert into User(FullName, Email, VerificationToken, Password, IsVerified, phone) values(?, ?, ?, ?, ?, ?)";
    private static final String Q_updateUser = "update User set FullName = ?, Email = ?, VerificationToken = ?, Password = ?, IsVerified = ?, phone = ? where idUser = ?";
    private static final String Q_deleteFromUser = "delete from User where idUser = ?";

    public UserManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static UserManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new UserManager();

        return _self;
    }

    public ArrayList<User> getUserList() throws AppException {
        try{
            ArrayList<User> userList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement(Q_getAllUsers + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            System.out.println("Step 1");
            while(result.next()) {
                System.out.println("Step 2.1");
                User newUser = new User(Integer.parseInt(result.getString("idUser")),
                        result.getString("FullName"),
                        result.getString("Email"),
                        result.getString("VerificationToken"),
                        result.getString("Password"),
                        Integer.parseInt(result.getString("IsVerified")),
                        result.getString("phone")
                );
                System.out.println("Step 2.2");
                userList.add(newUser);
            }
            System.out.println("Step 3");

            return new ArrayList<>(userList);
        } catch(Exception e){
            throw handleException("Get Users List", e);
        }
    }

    public User getUserById(int userId) throws AppException {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getUserById);
            statement.setInt(1, userId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            User newUser = null;
            System.out.println("Step 2");

            while(result.next()){
                newUser = new User(Integer.parseInt(result.getString("idUser")),
                        result.getString("FullName"),
                        result.getString("Email"),
                        result.getString("VerificationToken"),
                        result.getString("Password"),
                        Integer.parseInt(result.getString("IsVerified")),
                        result.getString("phone")
                );
            }
            System.out.println("Step 3");
            return newUser;

        } catch(Exception e){
            throw handleException("Get Dish By Id", e);
        }
    }

    public void insertToUser(User newUser) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_insertUser);
            statement.setString(1, newUser.getFullName());
            statement.setString(2, newUser.getEmail());
            statement.setString(3, newUser.getVerificationToken());
            statement.setString(4, newUser.getPassword());
            statement.setInt(5, newUser.getIsVerified());
            statement.setString(6, newUser.getPhone());
            statement.execute();
            System.out.println("Insert User executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void updateToUser(User newUser) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_updateUser);
            statement.setString(1, newUser.getFullName());
            statement.setString(2, newUser.getEmail());
            statement.setString(3, newUser.getVerificationToken());
            statement.setString(4, newUser.getPassword());
            statement.setInt(5, newUser.getIsVerified());
            statement.setString(6, newUser.getPhone());
            statement.setInt(7, newUser.getIdUser());
            System.out.println(statement.toString());
            statement.execute();
            System.out.println("Update user executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void deleteUser(int userId) throws AppException {
        try {
            PreparedStatement statement = con.prepareStatement(Q_deleteFromUser);
            statement.setInt(1, userId);
            System.out.println(statement.toString());
            if (statement.executeUpdate() == 1)
                System.out.println("Delete User executes successfully.");
            else
                throw new Exception("Delete User false");
        }catch (Exception e){
            throw handleException("Delete User", e);
        }
    }
}