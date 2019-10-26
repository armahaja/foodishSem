import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MeetUpAttendee {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String urlEnd = "?characterEncoding=utf8&useSSL=false";
    private static String usr = "root";
    private static String pwd = "jrtian97";

    private static String getAllFromOneDatabase = "select * from ";
    private static String getMeetUpAttendeeById = "select * from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";
    private static final String deleteFromMeetupAttendee = "delete from MeetupAttendee where idBuddyUser = ? and idMeetup = ?";
    private static final String insertMeetUpAttendee = "insert into MeetupAttendee(idBuddyUser, idMeetup, MissingBuddy, BuddyRating, SuggestionToBuddy) values(?, ?, ?, ?, ?)";

    public static Connection getConnection(String dbName) throws Exception {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url + dbName + urlEnd, usr, pwd);
        return con;
    }


    public static void main(String[] args) throws Exception {
//        insertMeetupAttendee("Foodish", "2", "1", "0", "4", "Food can be more");
//        getAllMeetupAttendee("Foodish");
//        insertMeetupAttendee("Foodish", "3", "1", "0", "3", "Food can be more tasty");
//        getAllMeetupAttendee("Foodish");
//        getMissingBuddy("3","1");
//        getBuddyRating("3","1");
//        getSuggestionToBuddy("3","1");
//        deleteFromMeetupAttendee("Foodish", "2","1");
//        getAllMeetupAttendee("Foodish");
//        insertMeetupAttendee("Foodish", "2", "1", "0", "4", "Food can be more");
        getAllMeetupAttendee("Foodish");
    }

    private static void insertMeetupAttendee(String dbName, String idBuddyUser, String idMeetup, String MissingBuddy, String BuddyRating, String SuggestionToBuddy) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(insertMeetUpAttendee);
            statement.setString(1, idBuddyUser);
            statement.setString(2, idMeetup);
            statement.setString(3, MissingBuddy);
            statement.setString(4, BuddyRating);
            statement.setString(5, SuggestionToBuddy);
            statement.execute();
            System.out.println("Insert food buddy executes successfully.");
        } catch (Exception e) {
            throw e;
        }
    }

    private static String getMissingBuddy(String idBuddyUser, String idMeetup) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getMeetUpAttendeeById);
            statement.setString(1, idBuddyUser);
            statement.setString(2, idMeetup);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String missingBuddy = result.getString("MissingBuddy");
            System.out.println(missingBuddy);
            return missingBuddy;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static String getBuddyRating(String idBuddyUser, String idMeetup) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getMeetUpAttendeeById);
            statement.setString(1, idBuddyUser);
            statement.setString(2, idMeetup);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String buddyRating = result.getString("BuddyRating");
            System.out.println(buddyRating);
            return buddyRating;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static String getSuggestionToBuddy(String idBuddyUser, String idMeetup) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getMeetUpAttendeeById);
            statement.setString(1, idBuddyUser);
            statement.setString(2, idMeetup);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String suggestionToBuddy = result.getString("SuggestionToBuddy");
            System.out.println(suggestionToBuddy);
            return suggestionToBuddy;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void getAllMeetupAttendee(String dbName) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = getConnection(dbName);
            String tableName = "MeetupAttendee";
            PreparedStatement statement = con.prepareStatement(getAllFromOneDatabase + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            while (result.next()) {
                System.out.println("idBuddyUser: " + result.getString("idBuddyUser") +
                        " idMeetup: " + result.getString("idMeetup") +
                        " MissingBuddy: " + result.getString("MissingBuddy") +
                        " BuddyRating: " + result.getString("BuddyRating") +
                        " SuggestionToBuddy: " + result.getString("SuggestionToBuddy"));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private static void deleteFromMeetupAttendee (String dbName, String idBuddyUser, String idMeetup) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement s0 = con.prepareStatement("SET foreign_key_checks = 0;");
            PreparedStatement s1 = con.prepareStatement(deleteFromMeetupAttendee);
            PreparedStatement s2 = con.prepareStatement("SET foreign_key_checks = 1;");
            s1.setString(1, idBuddyUser);
            s1.setString(2,idMeetup);
            s0.execute();
            s1.execute();
            s2.execute();
            System.out.println("Delete dish successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }
}
