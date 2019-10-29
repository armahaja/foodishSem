package edu.cmu.andrew.foodish.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.foodish.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.foodish.server.http.responses.AppResponse;
import edu.cmu.andrew.foodish.server.http.utils.PATCH;
import edu.cmu.andrew.foodish.server.managers.MeetupManager;
import edu.cmu.andrew.foodish.server.models.Meetup;
import edu.cmu.andrew.foodish.server.managers.MeetupAttendeeManager;
import edu.cmu.andrew.foodish.server.models.MeetupAttendee;
import edu.cmu.andrew.foodish.server.managers.UserManager;
import edu.cmu.andrew.foodish.server.models.User;
import edu.cmu.andrew.foodish.server.managers.DishManager;
import edu.cmu.andrew.foodish.server.models.Dish;
import edu.cmu.andrew.foodish.server.utils.*;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/user")

public class UserHttpInterface extends HttpInterface {

    private ObjectWriter ow;

    public UserHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Path("/unitTest")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse userTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("User Unit Test Page");
        } catch (Exception e) {
            throw handleException("GET /User Unit Test Page", e);
        }
    }

//    // http://localhost:8080/api/user
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getUsers(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                  @QueryParam("count") Integer count) {
        try {
            AppLogger.info("Got an API call");
            ArrayList<User> users = null;

            users = UserManager.getInstance().getUserList();
//            if(sortby != null)
//                meetups = MeetupManager.getInstance().getMeetupListSorted(sortby);
//            else if(offset != null && count != null)
//                meetups = MeetupManager.getInstance().getMeetupListPaginated(offset, count);
//            else
//                meetups = MeetupManager.getInstance().getMeetupList();

            if (users != null)
                return new AppResponse(users);
            else
                throw new HttpBadRequestException(0, "Problem with getting users");
        } catch (Exception e) {
            throw handleException("GET /users", e);
        }
    }

//    // http://localhost:8080/api/user/1
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetupById(@Context HttpHeaders headers, @PathParam("userId") int userId) {
        try {
            AppLogger.info("Got an API call");

            System.out.println("Step 1");
            User user = UserManager.getInstance().getUserById(userId);

            System.out.println("Step 4");
            if (user != null)
            {
                ArrayList<User> users = new ArrayList<User>();
                users.add(user);
                return new AppResponse(users);
            }
            else
                throw new HttpBadRequestException(0, "Problem with getting user by id");

        } catch (Exception e) {
            throw handleException("GET /user by id", e);
        }
    }

    // http://localhost:8080/api/user
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postUser(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            User newUser = new User(
                    -1,
                    json.getString("FullName"),
                    json.getString("Email"),
                    json.getString("VerificationToken"),
                    json.getString("Password"),
                    json.getInt("IsVerified"),
                    json.getString("phone")
            );
            System.out.println("Step 2");

            UserManager.getInstance().insertToUser(newUser);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }
    }

    // http://localhost:8080/api/user/1
    @PATCH
    @Path("/{userId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchDish(Object request, @PathParam("userId") int userId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            User newUser = new User(
                    userId,
                    json.getString("FullName"),
                    json.getString("Email"),
                    json.getString("VerificationToken"),
                    json.getString("Password"),
                    json.getInt("IsVerified"),
                    json.getString("phone")
            );

            System.out.println("Step 2");

            UserManager.getInstance().updateToUser(newUser);
            return new AppResponse("Update Successful");
        }catch (Exception e){
            throw handleException("PATCH users/{userId}", e);
        }
    }

    // http://localhost:8080/api/user/1
    @DELETE
    @Path("/{userId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteDish(@PathParam("userId") int userId){

        try{
            UserManager.getInstance().deleteUser(userId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE users/{userId}", e);
        }

    }
}
