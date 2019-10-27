package edu.cmu.andrew.foodish.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.foodish.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.foodish.server.http.responses.AppResponse;
import edu.cmu.andrew.foodish.server.http.utils.PATCH;
import edu.cmu.andrew.foodish.server.managers.MeetupManager;
import edu.cmu.andrew.foodish.server.managers.UserManager;
import edu.cmu.andrew.foodish.server.models.Meetup;
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
    private MongoCollection<Document> userCollection = null;

    public MeetupHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetups(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                @QueryParam("count") Integer count){
        try{
            AppLogger.info("Got an API call");
            ArrayList<Meetup> meetups = null;

            meetups = MeetupManager.getInstance().getMeetupList();
//            if(sortby != null)
//                users = UserManager.getInstance().getUserListSorted(sortby);
//            else if(offset != null && count != null)
//                users = UserManager.getInstance().getUserListPaginated(offset, count);
//            else
//                users = UserManager.getInstance().getUserList();

            if(meetups != null)
                return new AppResponse(meetups);
            else
                throw new HttpBadRequestException(0, "Problem with getting meetups");
        }catch (Exception e){
            throw handleException("GET /users", e);
        }
    }

//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public AppResponse postUsers(Object request){
//
//        try{
////            JSONObject json = null;
////            json = new JSONObject(ow.writeValueAsString(request));
////
////            System.out.println("Test 1");
////            Meetup newMeetup = new Meetup(
////                    0,
////                    json.getInt("idDish"),
////                    json.getInt("idChefUser"),
////                    json.getString("Location"),
////                    json.getString("Date"),
////                    json.getString("StartTime"),
////                    json.getInt("MaxGuestsAllowed")
////            );
////            System.out.println("Test 2");
////            System.out.println(json.getString("Feedback_FoodQuality"));
////            private int Feedback_FoodQuality = -1;
////            private int Feedback_FoodQuantity = -1;
////            private int Feedback_FoodTaste = -1;
////            private int TotalFeedbackReceived = -1;
////            private int MeetupRating = -1;
////            MeetupManager.getInstance().insertToMeetup(newMeetup);
//            return new AppResponse("Insert Successful");
//
//        }catch (Exception e){
//            throw handleException("POST users", e);
//        }
//    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postUsers(Object request){

        try{

            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST users", e);
        }

    }
}
