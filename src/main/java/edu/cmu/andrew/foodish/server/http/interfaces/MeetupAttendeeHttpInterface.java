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

@Path("/meetupAttendee")

public class MeetupAttendeeHttpInterface extends HttpInterface {

    private ObjectWriter ow;

    public MeetupAttendeeHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Path("/unitTest")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse meetupAttendeeTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("MeetupAttendee Unit Test Page");
        } catch (Exception e) {
            throw handleException("GET /MeetupAttendee Unit Test Page", e);
        }
    }

    // http://localhost:8080/api/meetupAttendee
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetupAttendees(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                  @QueryParam("count") Integer count) {
        try {
            AppLogger.info("Got an API call");
            ArrayList<MeetupAttendee> meetupAttendees = null;

            meetupAttendees = MeetupAttendeeManager.getInstance().getMeetupAttendeeList();

            if (meetupAttendees != null)
                return new AppResponse(meetupAttendees);
            else
                throw new HttpBadRequestException(0, "Problem with getting meetupAttendees");
        } catch (Exception e) {
            throw handleException("GET /meetupAttendees", e);
        }
    }

    // http://localhost:8080/api/meetupAttendee/1/1
    @GET
    @Path("/{idBuddyUser}/{idMeetup}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetupAttendeeById(@Context HttpHeaders headers, @PathParam("idBuddyUser") int idBuddyUser,
                                          @PathParam("idMeetup") int idMeetup) {
        try {
            AppLogger.info("Got an API call");

            MeetupAttendee meetupAttendee = MeetupAttendeeManager.getInstance().getMeetupAttendeeById(idBuddyUser, idMeetup);

            if (meetupAttendee != null)
            {
                ArrayList<MeetupAttendee> meetupAttendees = new ArrayList<MeetupAttendee>();
                meetupAttendees.add(meetupAttendee);
                return new AppResponse(meetupAttendees);
            }
            else
                throw new HttpBadRequestException(0, "Problem with getting meetupAttendee by ID");
        } catch (Exception e) {
            throw handleException("GET /meetupAttendees", e);
        }
    }

    // http://localhost:8080/api/meetupAttendee
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postMeetup(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            MeetupAttendee newMeetupAttendee = new MeetupAttendee(
                    json.getInt("idBuddyUser"),
                    json.getInt("idMeetup")
            );

            System.out.println("Step 2");

            if (json.optInt("MissingBuddy", -1) != -1)
                newMeetupAttendee.setMissingBuddy(json.optInt("MissingBuddy", -1));
            if (json.optInt("BuddyRating", -1) != -1)
                newMeetupAttendee.setBuddyRating(json.optInt("BuddyRating", -1));
            if (!json.optString("SuggestionToBuddy", "#").equals("#"))
                newMeetupAttendee.setSuggestionToBuddy(json.optString("SuggestionToBuddy", "#"));

            System.out.println("Step 3");

            MeetupAttendeeManager.getInstance().insertToMeetupAttendee(newMeetupAttendee);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }
    }

    // http://localhost:8080/api/meetupAttendee/1/1
    @PATCH
    @Path("/{idBuddyUser}/{idMeetup}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchMeetup(Object request, @PathParam("idBuddyUser") int idBuddyUser, @PathParam("idMeetup") int idMeetup){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            MeetupAttendee newMeetupAttendee = new MeetupAttendee(
                    idBuddyUser,
                    idMeetup,
                    json.getInt("MissingBuddy"),
                    json.getInt("BuddyRating"),
                    json.getString("SuggestionToBuddy")
            );

            System.out.println("Step 2");

            MeetupAttendeeManager.getInstance().updateToMeetupAttendee(newMeetupAttendee);
            return new AppResponse("Update Successful");
        }catch (Exception e){
            throw handleException("PATCH meetups/{meetupId}", e);
        }
    }

    // http://localhost:8080/api/meetupAttendee/1/1
    @DELETE
    @Path("/{idBuddyUser}/{idMeetup}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteMeetup(@PathParam("idBuddyUser") int idBuddyUser, @PathParam("idMeetup") int idMeetup){

        try{
            MeetupAttendeeManager.getInstance().deleteMeetupAttendee(idBuddyUser, idMeetup);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE meetups/{meetupId}", e);
        }

    }

}
