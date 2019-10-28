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
    private static String tableName = "Dish";



    public DishManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static DishManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new DishManager();

        return _self;
    }
}
