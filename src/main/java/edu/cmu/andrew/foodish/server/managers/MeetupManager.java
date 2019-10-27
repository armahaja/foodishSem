package edu.cmu.andrew.foodish.server.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.foodish.server.exceptions.AppException;
import edu.cmu.andrew.foodish.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.foodish.server.models.Meetup;
import edu.cmu.andrew.foodish.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.util.*;
import java.lang.*;
import java.sql.*;

public class MeetupManager extends Manager {
    public static MeetupManager _self;
    private MongoCollection<Document> userCollection;

    protected Connection con;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String urlEnd = "?serverTimezone=UTC&characterEncoding=utf8&useSSL=false";
    private static String usr = "root";
    private static String pwd = "caozihan59";
    private static String dbName = "Foodish";
    private static String tableName = "Meetup";

    private static String Q_getMeetupList = "select * from ";
    private static String Q_getidChefUserFromMeetup = "select idChefUser from Foodish.Meetup where idMeetup = ?";
    private static String Q_getLocationFromMeetup = "select Location from Foodish.Meetup where idMeetup = ?";
    private static String Q_insertToMeetupLessParameters = "insert into Meetup(idMeetup, idDish, idChefUser, Location, Date, StartTime, MaxGuestsAllowed) values(?,?,?,?,?,?,?)";
    private static String Q_insertToMeetup = "insert into Meetup(idMeetup, idDish, idChefUser, Location, Date, StartTime, Feedback_FoodQuality,Feedback_FoodQuantity,Feedback_FoodTaste,TotalFeedbackReceived,MeetupRating,MaxGuestsAllowed) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String Q_updateToMeetupLessParameters = "update Meetup set idDish = ?, idChefUser = ?, Location = ?, Date = ?, StartTime = ?, MaxGuestsAllowed = ? where idMeetup = ?";
    private static String Q_updateToMeetup = "update Meetup set idDish = ?, idChefUser = ?, Location = ?, Date = ?, StartTime = ?, Feedback_FoodQuality = ?, Feedback_FoodQuantity = ?, Feedback_FoodTaste = ?, TotalFeedbackReceived = ?, MeetupRating = ?, MaxGuestsAllowed = ? where idMeetup = ?";
    private static String Q_deleteFromMeetup = "delete from Meetup where idMeetup = ?";

    public MeetupManager() throws ClassNotFoundException, SQLException {
        this.userCollection = MongoPool.getInstance().getCollection("users");
        Class.forName(driver);
        this.con = DriverManager.getConnection(url + dbName + urlEnd, usr, pwd);
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
            System.out.println("Test 1");

            while(result.next()) {
                System.out.println("Test 2.1");
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
                System.out.println("Test 2.2");
                meetupList.add(newMeetup);
            }
            System.out.println("Test 3");

            return new ArrayList<>(meetupList);
        } catch(Exception e){
            throw handleException("Get Meetup List", e);
        }
    }

    public int getidChefUserFromMeetup(int meetupId) throws Exception {
        try{
            PreparedStatement statement = con.prepareStatement(Q_getidChefUserFromMeetup);
            statement.setString(1, String.valueOf(meetupId));
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
            statement.setString(1, String.valueOf(meetupId));
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

    public void insertToMeetup(Meetup newMeetup) throws Exception{
        if ((newMeetup.getFeedback_FoodQuality() ==  -1)||(newMeetup.getFeedback_FoodQuantity() ==  -1)||
                (newMeetup.getFeedback_FoodTaste() ==  -1)||(newMeetup.getTotalFeedbackReceived() ==  -1)||
                (newMeetup.getMeetupRating() ==  -1)) {
            insertToMeetupLessParameters(newMeetup);
            return;
        }
        try{
            PreparedStatement statement = con.prepareStatement(Q_insertToMeetup);
            statement.setString(1, String.valueOf(newMeetup.getIdMeetup()));
            statement.setString(2, String.valueOf(newMeetup.getIdDish()));
            statement.setString(3, String.valueOf(newMeetup.getIdChefUser()));
            statement.setString(4, newMeetup.getLocation());
            statement.setString(5, newMeetup.getDate());
            statement.setString(6, newMeetup.getStartTime());
            statement.setString(7, String.valueOf(newMeetup.getFeedback_FoodQuality()));
            statement.setString(8, String.valueOf(newMeetup.getFeedback_FoodQuantity()));
            statement.setString(9, String.valueOf(newMeetup.getFeedback_FoodTaste()));
            statement.setString(10, String.valueOf(newMeetup.getTotalFeedbackReceived()));
            statement.setString(11, String.valueOf(newMeetup.getMeetupRating()));
            statement.setString(12, String.valueOf(newMeetup.getMaxGuestsAllowed()));
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
            statement.setString(1, String.valueOf(newMeetup.getIdMeetup()));
            statement.setString(2, String.valueOf(newMeetup.getIdDish()));
            statement.setString(3, String.valueOf(newMeetup.getIdChefUser()));
            statement.setString(4, newMeetup.getLocation());
            statement.setString(5, newMeetup.getDate());
            statement.setString(6, newMeetup.getStartTime());
            statement.setString(7, String.valueOf(newMeetup.getMaxGuestsAllowed()));
            statement.execute();
            System.out.println("Insert Meetup executes successfully.");
        }
        catch(Exception e){
            throw e;
        }
    }

//    public void updateToMeetupLessParameters(Meetup newMeetup) throws Exception{
//        try{
//            PreparedStatement statement = con.prepareStatement(Q_updateToMeetupLessParameters);
//            statement.setString(0, "idDish");
//            statement.setString(1, "idChefUser");
//            statement.setString(2, "Location");
//            statement.setString(3, "Date");
//            statement.setString(4, "StartTime");
//            statement.setString(5, "MaxGuestsAllowed");
//            statement.setString(6, "idMeetup");
//            statement.execute();
//            System.out.println("Update Meetup executes successfully.");
//        }
//        catch(Exception e){
//            throw e;
//        }
//    }

}
