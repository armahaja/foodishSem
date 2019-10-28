package edu.cmu.andrew.foodish.server.managers;

import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.models.MeetupAttendee;
import edu.cmu.andrew.foodish.server.utils.MySQLPool;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class MeetupAttendeeManager extends Manager {
    public static MeetupAttendeeManager _self;

    protected Connection con;
    private static String tableName = "MeetupAttendee";



    public MeetupAttendeeManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static MeetupAttendeeManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new MeetupAttendeeManager();

        return _self;
    }
}
