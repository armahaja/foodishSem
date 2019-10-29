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
    private static final String tableName = "MeetupAttendee";

    private static final String Q_getMeetuAttendeeList = "select * from ";
    private static final String Q_getMeetUpAttendeeById = "select * from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";
    private static final String Q_insertMeetUpAttendeeLessParameters = "insert into MeetupAttendee(idBuddyUser, idMeetup) values(?, ?)";
    private static final String Q_insertMeetUpAttendee = "insert into MeetupAttendee(idBuddyUser, idMeetup, MissingBuddy, BuddyRating, SuggestionToBuddy) values(?, ?, ?, ?, ?)";
    private static final String Q_updateToMeetupAttendee = "update MeetupAttendee set MissingBuddy = ?, BuddyRating = ?, SuggestionToBuddy = ? where idBuddyUser = ? and idMeetup = ?";
    private static final String Q_deleteFromMeetupAttendee = "delete from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";

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

    public void insertToMeetupAttendee(MeetupAttendee newMeetupAttendee) throws Exception{
        try{
            if ((newMeetupAttendee.getMissingBuddy() ==  -1)||(newMeetupAttendee.getBuddyRating() ==  -1)||
                    (newMeetupAttendee.getSuggestionToBuddy() ==  null)) {
//                System.out.println(newMeetupAttendee.getMissingBuddy());
//                System.out.println(newMeetupAttendee.getBuddyRating());
//                System.out.println(newMeetupAttendee.getSuggestionToBuddy());
                System.out.println("Step 4");
                insertToMeetupAttendeeLessParameters(newMeetupAttendee);
                return;
            }
            PreparedStatement statement = con.prepareStatement(Q_insertMeetUpAttendee);
            statement.setInt(1, newMeetupAttendee.getIdBuddyUser());
            statement.setInt(2, newMeetupAttendee.getIdMeetup());
            statement.setInt(3, newMeetupAttendee.getMissingBuddy());
            statement.setInt(4, newMeetupAttendee.getBuddyRating());
            statement.setString(5, newMeetupAttendee.getSuggestionToBuddy());
            statement.execute();
            System.out.println("Insert MeetupAttendee executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void insertToMeetupAttendeeLessParameters(MeetupAttendee newMeetupAttendee) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_insertMeetUpAttendeeLessParameters);
            statement.setInt(1, newMeetupAttendee.getIdBuddyUser());
            statement.setInt(2, newMeetupAttendee.getIdMeetup());
            statement.execute();
            System.out.println("Insert MeetupAttendee executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void updateToMeetupAttendee(MeetupAttendee newMeetupAttendee) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_updateToMeetupAttendee);
            statement.setInt(1, newMeetupAttendee.getMissingBuddy());
            statement.setInt(2, newMeetupAttendee.getBuddyRating());
            statement.setString(3, newMeetupAttendee.getSuggestionToBuddy());
            statement.setInt(4, newMeetupAttendee.getIdBuddyUser());
            statement.setInt(5, newMeetupAttendee.getIdMeetup());
            statement.execute();
            System.out.println("Update MeetupAttendee executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void deleteMeetupAttendee(int idBuddyUser, int idMeetup) throws AppException {
        try {
            PreparedStatement statement = con.prepareStatement(Q_deleteFromMeetupAttendee);
            statement.setInt(1, idBuddyUser);
            statement.setInt(2, idMeetup);
            if (statement.executeUpdate() == 1)
                System.out.println("Delete MeetupAttendee executes successfully.");
            else
                throw new Exception("Delete MeetupAttendee false");
        }catch (Exception e){
            throw handleException("Delete MeetupAttendee", e);
        }
    }
}
