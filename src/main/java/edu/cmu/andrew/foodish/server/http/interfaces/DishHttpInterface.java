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

@Path("/dish")

public class DishHttpInterface extends HttpInterface {

    private ObjectWriter ow;

    public DishHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Path("/test")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse dishTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("Dish Test Page");
        } catch (Exception e) {
            throw handleException("GET /Dish Test Page", e);
        }
    }
}
