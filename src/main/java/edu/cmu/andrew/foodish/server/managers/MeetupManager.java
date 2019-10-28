package edu.cmu.andrew.foodish.server.managers;

import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.models.Meetup;
import edu.cmu.andrew.foodish.server.utils.MySQLPool;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class MeetupManager extends Manager {
    public static MeetupManager _self;

    protected Connection con;
    private static String tableName = "Meetup";

    private static String Q_getMeetupList = "select * from ";
    private static String Q_getMeetupById = "select * from Foodish.Meetup where idMeetup = ?";
    private static String Q_getidChefUserFromMeetup = "select idChefUser from Foodish.Meetup where idMeetup = ?";
    private static String Q_getLocationFromMeetup = "select Location from Foodish.Meetup where idMeetup = ?";
    private static String Q_insertToMeetupLessParameters = "insert into Meetup(idDish, idChefUser, Location, Date, StartTime, MaxGuestsAllowed) values(?,?,?,?,?,?)";
    private static String Q_insertToMeetup = "insert into Meetup(idDish, idChefUser, Location, Date, StartTime, Feedback_FoodQuality,Feedback_FoodQuantity,Feedback_FoodTaste,TotalFeedbackReceived,MeetupRating,MaxGuestsAllowed) values(?,?,?,?,?,?,?,?,?,?,?)";
    private static String Q_updateToMeetupLessParameters = "update Meetup set idDish = ?, idChefUser = ?, Location = ?, Date = ?, StartTime = ?, MaxGuestsAllowed = ? where idMeetup = ?";
    private static String Q_updateToMeetup = "update Meetup set idDish = ?, idChefUser = ?, Location = ?, Date = ?, StartTime = ?, Feedback_FoodQuality = ?, Feedback_FoodQuantity = ?, Feedback_FoodTaste = ?, TotalFeedbackReceived = ?, MeetupRating = ?, MaxGuestsAllowed = ? where idMeetup = ?";
    private static String Q_deleteFromMeetup = "delete from Meetup where idMeetup = ?";

    public MeetupManager() throws ClassNotFoundException, SQLException {
        this.con = MySQLPool.getInstance().getConnection();
    }

    public static MeetupManager getInstance() throws ClassNotFoundException, SQLException {
        if (_self == null)
            _self = new MeetupManager();

        return _self;
    }

    public ArrayList<Meetup> getMeetupList() throws AppException {
        try{
            ArrayList<Meetup> meetupList = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement(Q_getMeetupList + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            System.out.println("Step 1");

            while(result.next()) {
                System.out.println("Step 2.1");
                Meetup newMeetup = new Meetup(Integer.parseInt(result.getString("idMeetup")),
                        Integer.parseInt(result.getString("idDish")),
                        Integer.parseInt(result.getString("idChefUser")),
                        result.getString("Location"),
                        result.getString("Date"),
                        result.getString("StartTime"),
                        Integer.parseInt(result.getString("MaxGuestsAllowed"))
                );
                if (result.getString("Feedback_FoodQuality") != null)
                    newMeetup.setFeedback_FoodQuality(Integer.parseInt(result.getString("Feedback_FoodQuality")));
                if (result.getString("Feedback_FoodQuantity") != null)
                    newMeetup.setFeedback_FoodQuantity(Integer.parseInt(result.getString("Feedback_FoodQuantity")));
                if (result.getString("Feedback_FoodTaste") != null)
                    newMeetup.setFeedback_FoodTaste(Integer.parseInt(result.getString("Feedback_FoodTaste")));
                if (result.getString("TotalFeedbackReceived") != null)
                    newMeetup.setTotalFeedbackReceived(Integer.parseInt(result.getString("TotalFeedbackReceived")));
                if (result.getString("MeetupRating") != null)
                    newMeetup.setMeetupRating(Integer.parseInt(result.getString("MeetupRating")));
                System.out.println("Step 2.2");
                meetupList.add(newMeetup);
            }
            System.out.println("Step 3");

            return new ArrayList<>(meetupList);
        } catch(Exception e){
            throw handleException("Get Meetup List", e);
        }
    }

    public Meetup getMeetupById(int meetupId) throws AppException {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getMeetupById);
            statement.setInt(1, meetupId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            Meetup meetup = null;
            System.out.println("Step 2");
            while(result.next()){
                meetup = new Meetup(Integer.parseInt(result.getString("idMeetup")),
                        Integer.parseInt(result.getString("idDish")),
                        Integer.parseInt(result.getString("idChefUser")),
                        result.getString("Location"),
                        result.getString("Date"),
                        result.getString("StartTime"),
                        Integer.parseInt(result.getString("MaxGuestsAllowed"))
                );
                if (result.getString("Feedback_FoodQuality") != null)
                    meetup.setFeedback_FoodQuality(Integer.parseInt(result.getString("Feedback_FoodQuality")));
                if (result.getString("Feedback_FoodQuantity") != null)
                    meetup.setFeedback_FoodQuantity(Integer.parseInt(result.getString("Feedback_FoodQuantity")));
                if (result.getString("Feedback_FoodTaste") != null)
                    meetup.setFeedback_FoodTaste(Integer.parseInt(result.getString("Feedback_FoodTaste")));
                if (result.getString("TotalFeedbackReceived") != null)
                    meetup.setTotalFeedbackReceived(Integer.parseInt(result.getString("TotalFeedbackReceived")));
                if (result.getString("MeetupRating") != null)
                    meetup.setMeetupRating(Integer.parseInt(result.getString("MeetupRating")));
            }
            System.out.println("Step 3");
            return meetup;

        } catch(Exception e){
            throw handleException("Get Meetup List", e);
        }
    }

    public void insertToMeetup(Meetup newMeetup) throws Exception{
        try{
            if ((newMeetup.getFeedback_FoodQuality() ==  -1)||(newMeetup.getFeedback_FoodQuantity() ==  -1)||
                    (newMeetup.getFeedback_FoodTaste() ==  -1)||(newMeetup.getTotalFeedbackReceived() ==  -1)||
                    (newMeetup.getMeetupRating() ==  -1)) {
                System.out.println("Step 4");
                insertToMeetupLessParameters(newMeetup);
                return;
            }
            PreparedStatement statement = con.prepareStatement(Q_insertToMeetup);
            statement.setInt(1, newMeetup.getIdDish());
            statement.setInt(2, newMeetup.getIdChefUser());
            statement.setString(3, newMeetup.getLocation());
            statement.setString(4, newMeetup.getDate());
            statement.setString(5, newMeetup.getStartTime());
            statement.setInt(6, newMeetup.getFeedback_FoodQuality());
            statement.setInt(7, newMeetup.getFeedback_FoodQuantity());
            statement.setInt(8, newMeetup.getFeedback_FoodTaste());
            statement.setInt(9, newMeetup.getTotalFeedbackReceived());
            statement.setInt(10, newMeetup.getMeetupRating());
            statement.setInt(11, newMeetup.getMaxGuestsAllowed());
            statement.execute();
            System.out.println("Insert Meetup executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void insertToMeetupLessParameters(Meetup newMeetup) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_insertToMeetupLessParameters);
            statement.setInt(1, newMeetup.getIdDish());
            statement.setInt(2, newMeetup.getIdChefUser());
            statement.setString(3, newMeetup.getLocation());
            statement.setString(4, newMeetup.getDate());
            statement.setString(5, newMeetup.getStartTime());
            statement.setInt(6, newMeetup.getMaxGuestsAllowed());
            //System.out.println(statement.toString());
            statement.execute();
            System.out.println("Insert Meetup executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void updateToMeetup(Meetup newMeetup) throws Exception{
        try{
            if ((newMeetup.getFeedback_FoodQuality() ==  -1)||(newMeetup.getFeedback_FoodQuantity() ==  -1)||
                    (newMeetup.getFeedback_FoodTaste() ==  -1)||(newMeetup.getTotalFeedbackReceived() ==  -1)||
                    (newMeetup.getMeetupRating() ==  -1)) {
                System.out.println("Step 4");
                updateToMeetupLessParameters(newMeetup);
                return;
            }
            PreparedStatement statement = con.prepareStatement(Q_updateToMeetup);
            statement.setInt(1, newMeetup.getIdDish());
            statement.setInt(2, newMeetup.getIdChefUser());
            statement.setString(3, newMeetup.getLocation());
            statement.setString(4, newMeetup.getDate());
            statement.setString(5, newMeetup.getStartTime());
            statement.setInt(6, newMeetup.getFeedback_FoodQuality());
            statement.setInt(7, newMeetup.getFeedback_FoodQuantity());
            statement.setInt(8, newMeetup.getFeedback_FoodTaste());
            statement.setInt(9, newMeetup.getTotalFeedbackReceived());
            statement.setInt(10, newMeetup.getMeetupRating());
            statement.setInt(11, newMeetup.getMaxGuestsAllowed());
            statement.setInt(12, newMeetup.getIdMeetup());
            statement.execute();
            System.out.println("Update Meetup executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void updateToMeetupLessParameters(Meetup newMeetup) throws Exception{
        try{
            PreparedStatement statement = con.prepareStatement(Q_updateToMeetupLessParameters);
            statement.setInt(1, newMeetup.getIdDish());
            statement.setInt(2, newMeetup.getIdChefUser());
            statement.setString(3, newMeetup.getLocation());
            statement.setString(4, newMeetup.getDate());
            statement.setString(5, newMeetup.getStartTime());
            statement.setInt(6, newMeetup.getMaxGuestsAllowed());
            statement.setInt(7, newMeetup.getIdMeetup());
            System.out.println(statement.toString());
            statement.execute();
            System.out.println("Update Meetup executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

    public void deleteMeetup(int meetupId) throws AppException {
        try {
            PreparedStatement statement = con.prepareStatement(Q_deleteFromMeetup);
            statement.setInt(1, meetupId);
            statement.execute();
            System.out.println("Delete Meetup executes successfully.");
        }catch (Exception e){
            throw handleException("Delete User", e);
        }
    }



    //Backup methods for future use

    public int getidChefUserFromMeetup(int meetupId) throws Exception {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getidChefUserFromMeetup);
            statement.setInt(1, meetupId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            String res = null;
            while(result.next()){
                res = result.getString("idChefUser");
            }
            if (res != null)
                return Integer.parseInt(res);
            else
                return -1;
        }
        catch(Exception e){
            throw e;
        }
    }

    public String getLocationFromMeetup(int meetupId) throws Exception {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getLocationFromMeetup);
            statement.setInt(1, meetupId);
            statement.execute();
            ResultSet result = statement.getResultSet();
            String res = null;
            while(result.next()){
                res = result.getString("Location");
            }
            if (res != null)
                return res;
            else
                return "Warning: No such location.";
        }
        catch(Exception e){
            throw e;
        }
    }

}
