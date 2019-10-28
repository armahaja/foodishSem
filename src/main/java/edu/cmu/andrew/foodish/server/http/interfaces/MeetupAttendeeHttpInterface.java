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
    @Path("/test")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse meetupAttendeeTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("MeetupAttendee Test Page");
        } catch (Exception e) {
            throw handleException("GET /MeetupAttendee Test Page", e);
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

}
