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
    private static String tableName = "User";



    public UserManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static UserManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new UserManager();

        return _self;
    }
}