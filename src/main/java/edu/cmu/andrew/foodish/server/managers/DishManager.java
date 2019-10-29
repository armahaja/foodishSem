package edu.cmu.andrew.foodish.server.managers;

import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.models.Dish;
import edu.cmu.andrew.foodish.server.utils.MySQLPool;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class DishManager extends Manager {
    public static DishManager _self;

    protected Connection con;
    private static final String tableName = "Dish";

    private static final String Q_getAllDishes = "select * from ";
    private static final String Q_getDishById = "select * from Dish where idDish = ?";
    private static final String Q_insertDish = "insert into Dish(idUser, DishName, DishTags, DishDescription) values(?, ?, ?, ?)";
    private static final String Q_updateDish = "update Dish set idUser = ?, DishName = ?, DishTags = ?, DishDescription = ? where idDish = ?";
    private static final String Q_deleteFromDish = "delete from Dish where idDish = ?";

    public DishManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static DishManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new DishManager();

        return _self;
    }

    public ArrayList<Dish> getDishList() throws AppException {
        try{
            ArrayList<Dish> dishList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement(Q_getAllDishes + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            System.out.println("Step 1");

            while(result.next()) {
                System.out.println("Step 2.1");
                Dish newDish = new Dish(Integer.parseInt(result.getString("idDish")),
                        Integer.parseInt(result.getString("idUser")),
                        result.getString("DishName"),
                        result.getString("DishDescription"),
                        result.getString("DishTags")
                );
                System.out.println("Step 2.2");
                dishList.add(newDish);
            }
            System.out.println("Step 3");

            return new ArrayList<>(dishList);
        } catch(Exception e){
            throw handleException("Get Dishes List", e);
        }
    }

    public Dish getDishById(int dishId) throws AppException {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getDishById);
            statement.setInt(1, dishId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            Dish newDish = null;
            System.out.println("Step 2");

            while(result.next()){
                newDish = new Dish(Integer.parseInt(result.getString("idDish")),
                        Integer.parseInt(result.getString("idUser")),
                        result.getString("DishName"),
                        result.getString("DishDescription"),
                        result.getString("DishTags")
                );
            }
            System.out.println("Step 3");
            return newDish;

        } catch(Exception e){
            throw handleException("Get Dish By Id", e);
        }
    }

    public void insertToDish(Dish newDish) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_insertDish);
            statement.setInt(1, newDish.getIdUser());
            statement.setString(2, newDish.getDishName());
            statement.setString(3, newDish.getDishDescription());
            statement.setString(4, newDish.getDishTags());
            statement.execute();
            System.out.println("Insert Dish executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void updateToDish(Dish newDish) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_updateDish);
            statement.setInt(1, newDish.getIdUser());
            statement.setString(2, newDish.getDishName());
            statement.setString(3, newDish.getDishDescription());
            statement.setString(4, newDish.getDishTags());
            statement.setInt(5, newDish.getIdDish());
            statement.execute();
            System.out.println("Update Dish executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void deleteDish(int dishId) throws AppException {
        try {
            PreparedStatement statement = con.prepareStatement(Q_deleteFromDish);
            statement.setInt(1, dishId);
            System.out.println(statement.toString());
            if (statement.executeUpdate() == 1)
                System.out.println("Delete Dish executes successfully.");
            else
                throw new Exception("Delete Dish false");
        }catch (Exception e){
            throw handleException("Delete Dish", e);
        }
    }
}
