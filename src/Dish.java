import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dish {

        private static String driver = "com.mysql.jdbc.Driver";
        private static String url = "jdbc:mysql://localhost:3306/";
        private static String urlEnd = "?characterEncoding=utf8&useSSL=false";
        private static String usr = "root";
        private static String pwd = "jrtian97";

        private static String getAllFromOneDatabase = "select * from ";
        private static String getDishById = "select * from Dish where idDish = ?";
        private static final String insertDish = "insert into Dish(idDish, idUser, DishName, DishTags, DishDescription) values(?, ?, ?, ?, ?)";
        private static final String updateDish = "update Dish set DishName = ?, DishTags = ?, DishDescription = ? where idDish = ?";
        private static final String updateDishNameFromDish = "update Dish set DishName = ? where idDish = ?";
        private static final String updateDishTagsFromDish = "update Dish set DishTags = ? where idDish = ?";
        private static final String updateDishDescriptionFromDish = "update Dish set DishDescription = ? where idDish = ?";
        private static final String deleteFromDish = "delete from Dish where idDish = ?";

        public static Connection getConnection(String dbName) throws Exception {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url + dbName + urlEnd, usr, pwd);
            return con;
        }

        public Dish(){

        }

        public static void main(String[] args) throws Exception {
//            getAllDish("Foodish");
//            insertDish("Foodish", "5", "2", "Ramen", "Japennese", "Similar with Chinese noodles");
//            getAllDish("Foodish");
//            updateDishNameFromDish("Foodish", "Kungpao Chicken", "3");
//            getAllDish("Foodish");
//            updateDishTagsFromDish("Foodish", "Chinese Type", "3");
//            getAllDish("Foodish");
//            insertDish("Foodish", "3","2","Kungpao Chicken","Chinese", "Stir-fried chicken with combination of sweer,salt & spicy flavour");
//            getAllDish("Foodish");
//            getDishName("3");
//            deleteFromDish("Foodish", "3");
//            getAllDish("Foodish");
//            insertDish("Foodish", "3","2","Kungpao Chicken","Chinese", "Stir-fried chicken with combination of sweer,salt & spicy flavour");
//            getAllDish("Foodish");
//            getIdUser("2");
//            getDishTags("2");
//            getDishDescription("2");
            updateDish("Foodish", "KungpaoChicken", "Chinese","Stir-fried chicken with combination of sweer,salt & spicy flavour","3");
            getAllDish("Foodish");
        }

        private static void insertDish(String dbName, String idDish, String idUser, String DishName, String DishTags, String DishDescription) throws Exception {
            try {
                Connection con = getConnection(dbName);
                PreparedStatement statement = con.prepareStatement(insertDish);
                statement.setString(1, idDish);
                statement.setString(2, idUser);
                statement.setString(3, DishName);
                statement.setString(4, DishTags);
                statement.setString(5,DishDescription);
                statement.execute();
                System.out.println("Insert food buddy executes successfully.");
            }
            catch (Exception e) {
                throw e;
            }
        }

        private static String getDishName(String idDish) throws Exception {
            try {
                Connection con = getConnection("Foodish");
                PreparedStatement statement = con.prepareStatement(getDishById);
                statement.setString(1, idDish);
                statement.execute();
                ResultSet result = statement.getResultSet();
                result.next();
                String dishName = result.getString("DishName");
                System.out.println(dishName);
                return dishName;
            }
            catch (Exception e) {
                throw e;
            }
        }

    private static String getIdUser(String idDish) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getDishById);
            statement.setString(1, idDish);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String idUser = result.getString("idUser");
            System.out.println(idUser);
            return idUser;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static String getDishTags(String idDish) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getDishById);
            statement.setString(1, idDish);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String DishTags = result.getString("DishTags");
            System.out.println(DishTags);
            return DishTags;
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static String getDishDescription(String idDish) throws Exception {
        try {
            Connection con = getConnection("Foodish");
            PreparedStatement statement = con.prepareStatement(getDishById);
            statement.setString(1, idDish);
            statement.execute();
            ResultSet result = statement.getResultSet();
            result.next();
            String DishDescription = result.getString("DishDescription");
            System.out.println(DishDescription);
            return DishDescription;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static List<List<String>> getAllDish(String dbName) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = getConnection(dbName);
            String tableName = "Dish";
            PreparedStatement statement = con.prepareStatement(getAllFromOneDatabase + tableName);
            statement.execute();
            ResultSet result = statement.getResultSet();
            List<List<String>> allDishes = new ArrayList<>();
            while(result.next()) {
                List<String> dishes = new ArrayList<>();
                dishes.add(result.getString("idDish"));
                dishes.add(result.getString("idUser"));
                dishes.add(result.getString("DishName"));
                dishes.add(result.getString("DishTags"));
                dishes.add(result.getString("DishDescription"));
                allDishes.add(dishes);
                System.out.println("idDish: " + dishes.get(0) +
                        " idUser: " + dishes.get(1) +
                        " DishName: " + dishes.get(2) +
                        " DishTags: " + dishes.get(3) +
                        " DishDescription: " + dishes.get(4));
            }
            return allDishes;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static void updateDish(String dbName, String dishName, String dishTags, String dishDescription,String idDish) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(updateDish);
            statement.setString(1, dishName);
            statement.setString(2, dishTags);
            statement.setString(3, dishDescription);
            statement.setString(4, idDish);
            statement.execute();
            System.out.println("Update chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }
    public static void updateDishNameFromDish(String dbName, String dishName, String idDish) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(updateDishNameFromDish);
            statement.setString(1, dishName);
            statement.setString(2, idDish);
            statement.execute();
            System.out.println("Update chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static void updateDishTagsFromDish(String dbName, String dishTags, String idDish) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(updateDishTagsFromDish);
            statement.setString(1, dishTags);
            statement.setString(2, idDish);
            statement.execute();
            System.out.println("Update chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }

    private static void updateDishDescriptionFromDish(String dbName, String dishDescription, String idDish) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement statement = con.prepareStatement(updateDishDescriptionFromDish);
            statement.setString(1, dishDescription);
            statement.setString(2, idDish);
            statement.execute();
            System.out.println("Update chef successfully.");
        }
        catch (Exception e) {
            throw e;
        }
    }
    private static void deleteFromDish(String dbName, String idDish) throws Exception {
        try {
            Connection con = getConnection(dbName);
            PreparedStatement s0 = con.prepareStatement("SET foreign_key_checks = 0;");
            PreparedStatement s1 = con.prepareStatement(deleteFromDish);
            PreparedStatement s2 = con.prepareStatement("SET foreign_key_checks = 1;");
            s1.setString(1, idDish);
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
