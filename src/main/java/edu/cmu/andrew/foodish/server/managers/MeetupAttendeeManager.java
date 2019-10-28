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

    private static String Q_getMeetuAttendeeList = "select * from ";
    private static String Q_getMeetUpAttendeeById = "select * from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";
    private static String Q_insertMeetUpAttendeeLessParameters = "insert into MeetupAttendee(idBuddyUser, idMeetup) values(?, ?)";
    private static String Q_insertMeetUpAttendee = "insert into MeetupAttendee(idBuddyUser, idMeetup, MissingBuddy, BuddyRating, SuggestionToBuddy) values(?, ?, ?, ?, ?)";
    private static String Q_updateToMeetup = "update MeetupAttendee set MissingBuddy = ?, BuddyRating = ?, SuggestionToBuddy = ? where idBuddyUser = ? and idMeetup = ?";
    private static String Q_deleteFromMeetupAttendee = "delete from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";

    public MeetupAttendeeManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static MeetupAttendeeManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new MeetupAttendeeManager();

        return _self;
    }

    public ArrayList<MeetupAttendee> getMeetupAttendeeList() throws AppException {
        try{
            ArrayList<MeetupAttendee> meetupAttendeeList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement(Q_getMeetuAttendeeList + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            System.out.println("Step 1");

            while(result.next()) {
                System.out.println("Step 2.1");
                MeetupAttendee newMeetupAttendee = new MeetupAttendee(Integer.parseInt(result.getString("idBuddyUser")),
                        Integer.parseInt(result.getString("idMeetup"))
                );
                if (result.getString("MissingBuddy") != null)
                    newMeetupAttendee.setMissingBuddy(Integer.parseInt(result.getString("MissingBuddy")));
                if (result.getString("BuddyRating") != null)
                    newMeetupAttendee.setBuddyRating(Integer.parseInt(result.getString("BuddyRating")));
                if (result.getString("SuggestionToBuddy") != null)
                    newMeetupAttendee.setSuggestionToBuddy(result.getString("SuggestionToBuddy"));
                System.out.println("Step 2.2");
                meetupAttendeeList.add(newMeetupAttendee);
            }
            System.out.println("Step 3");

            return new ArrayList<>(meetupAttendeeList);
        } catch(Exception e){
            throw handleException("Get MeetupAttendee List", e);
        }
    }

    public MeetupAttendee getMeetupAttendeeById(int idBuddyUser, int idMeetup) throws AppException {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getMeetUpAttendeeById);
            statement.setInt(1, idBuddyUser);
            statement.setInt(2, idMeetup);
            statement.execute();
            ResultSet result = statement.getResultSet();
            MeetupAttendee meetupAttendee = null;
            while(result.next()){
                meetupAttendee = new MeetupAttendee(Integer.parseInt(result.getString("idBuddyUser")),
                        Integer.parseInt(result.getString("idMeetup"))
                );
                if (result.getString("MissingBuddy") != null)
                    meetupAttendee.setMissingBuddy(Integer.parseInt(result.getString("MissingBuddy")));
                if (result.getString("BuddyRating") != null)
                    meetupAttendee.setBuddyRating(Integer.parseInt(result.getString("BuddyRating")));
                if (result.getString("SuggestionToBuddy") != null)
                    meetupAttendee.setSuggestionToBuddy(result.getString("SuggestionToBuddy"));
            }
            return meetupAttendee;

        } catch(Exception e){
            throw handleException("Get Meetup By Id", e);
        }
    }
}
