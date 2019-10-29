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

@Path("/meetup")

public class MeetupHttpInterface extends HttpInterface {

    private ObjectWriter ow;

    public MeetupHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Path("/unitTest")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse meetupTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("Meetup Unit Test Page");
        } catch (Exception e) {
            throw handleException("GET /Meetup Unit Test Page", e);
        }
    }

    // http://localhost:8080/api/meetup
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetups(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                  @QueryParam("count") Integer count) {
        try {
            AppLogger.info("Got an API call");
            ArrayList<Meetup> meetups = null;

            meetups = MeetupManager.getInstance().getMeetupList();
//            if(sortby != null)
//                meetups = MeetupManager.getInstance().getMeetupListSorted(sortby);
//            else if(offset != null && count != null)
//                meetups = MeetupManager.getInstance().getMeetupListPaginated(offset, count);
//            else
//                meetups = MeetupManager.getInstance().getMeetupList();

            if (meetups != null)
                return new AppResponse(meetups);
            else
                throw new HttpBadRequestException(0, "Problem with getting meetups");
        } catch (Exception e) {
            throw handleException("GET /meetups", e);
        }
    }

    // http://localhost:8080/api/meetup/1
    @GET
    @Path("/{meetupId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetupById(@Context HttpHeaders headers, @PathParam("meetupId") int meetupId) {
        try {
            AppLogger.info("Got an API call");

            System.out.println("Step 1");
            Meetup meetup = MeetupManager.getInstance().getMeetupById(meetupId);

            System.out.println("Step 4");
            if (meetup != null)
            {
                ArrayList<Meetup> meetups = new ArrayList<Meetup>();
                meetups.add(meetup);
                return new AppResponse(meetups);
            }
            else
                throw new HttpBadRequestException(0, "Problem with getting meetup by id");
        } catch (Exception e) {
            throw handleException("GET /meetup by id", e);
        }
    }

    // http://localhost:8080/api/meetup
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postMeetup(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            Meetup newMeetup = new Meetup(
                    -1,
                    json.getInt("idDish"),
                    json.getInt("idChefUser"),
                    json.getString("Location"),
                    json.getString("Date"),
                    json.getString("StartTime"),
                    json.getInt("MaxGuestsAllowed")
            );

            System.out.println("Step 2");

            if (json.optInt("Feedback_FoodQuality", -1) != -1)
                newMeetup.setFeedback_FoodQuality(json.optInt("Feedback_FoodQuality", -1));
            if (json.optInt("Feedback_FoodQuantity", -1) != -1)
                newMeetup.setFeedback_FoodQuantity(json.optInt("Feedback_FoodQuantity", -1));
            if (json.optInt("Feedback_FoodTaste", -1) != -1)
                newMeetup.setFeedback_FoodTaste(json.optInt("Feedback_FoodTaste", -1));
            if (json.optInt("TotalFeedbackReceived", -1) != -1)
                newMeetup.setTotalFeedbackReceived(json.optInt("TotalFeedbackReceived", -1));
            if (json.optInt("MeetupRating", -1) != -1)
                newMeetup.setMeetupRating(json.optInt("MeetupRating", -1));

            System.out.println("Step 3");

            MeetupManager.getInstance().insertToMeetup(newMeetup);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }
    }

    // http://localhost:8080/api/meetup/1
    @PATCH
    @Path("/{meetupId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchMeetup(Object request, @PathParam("meetupId") int meetupId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            Meetup newMeetup = new Meetup(
                    meetupId,
                    json.getInt("idDish"),
                    json.getInt("idChefUser"),
                    json.getString("Location"),
                    json.getString("Date"),
                    json.getString("StartTime"),
                    json.getInt("MaxGuestsAllowed")
            );

            System.out.println("Step 2");
            //System.out.println(meetupId);

            if (json.optInt("Feedback_FoodQuality", -1) != -1)
                newMeetup.setFeedback_FoodQuality(json.optInt("Feedback_FoodQuality", -1));
            if (json.optInt("Feedback_FoodQuantity", -1) != -1)
                newMeetup.setFeedback_FoodQuantity(json.optInt("Feedback_FoodQuantity", -1));
            if (json.optInt("Feedback_FoodTaste", -1) != -1)
                newMeetup.setFeedback_FoodTaste(json.optInt("Feedback_FoodTaste", -1));
            if (json.optInt("TotalFeedbackReceived", -1) != -1)
                newMeetup.setTotalFeedbackReceived(json.optInt("TotalFeedbackReceived", -1));
            if (json.optInt("MeetupRating", -1) != -1)
                newMeetup.setMeetupRating(json.optInt("MeetupRating", -1));

            System.out.println("Step 3");

            MeetupManager.getInstance().updateToMeetup(newMeetup);
            return new AppResponse("Update Successful");
        }catch (Exception e){
            throw handleException("PATCH meetups/{meetupId}", e);
        }
    }

    // http://localhost:8080/api/meetup/1
    @DELETE
    @Path("/{meetupId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteMeetup(@PathParam("meetupId") int meetupId){

        try{
            MeetupManager.getInstance().deleteMeetup(meetupId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE meetups/{meetupId}", e);
        }

    }


}