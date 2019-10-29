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
    @Path("/unitTest")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse dishTestPage(@Context HttpHeaders headers) {
        try {
            AppLogger.info("Got an API call");
            return new AppResponse("Dish Unit Test Page");
        } catch (Exception e) {
            throw handleException("GET /Dish Unit Test Page", e);
        }
    }

    // http://localhost:8080/api/dish
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetups(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                  @QueryParam("count") Integer count) {
        try {
            AppLogger.info("Got an API call");
            ArrayList<Dish> dishes = null;

            dishes = DishManager.getInstance().getDishList();
//            if(sortby != null)
//                meetups = MeetupManager.getInstance().getMeetupListSorted(sortby);
//            else if(offset != null && count != null)
//                meetups = MeetupManager.getInstance().getMeetupListPaginated(offset, count);
//            else
//                meetups = MeetupManager.getInstance().getMeetupList();

            if (dishes != null)
                return new AppResponse(dishes);
            else
                throw new HttpBadRequestException(0, "Problem with getting dishes");
        } catch (Exception e) {
            throw handleException("GET /dishes", e);
        }
    }

    // http://localhost:8080/api/dish/1
    @GET
    @Path("/{dishId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getMeetupById(@Context HttpHeaders headers, @PathParam("dishId") int dishId) {
        try {
            AppLogger.info("Got an API call");

            System.out.println("Step 1");
            Dish dish = DishManager.getInstance().getDishById(dishId);

            System.out.println("Step 4");
            if (dish != null)
            {
                ArrayList<Dish> dishes = new ArrayList<Dish>();
                dishes.add(dish);
                return new AppResponse(dishes);
            }
            else
                throw new HttpBadRequestException(0, "Problem with getting dish by id");

        } catch (Exception e) {
            throw handleException("GET /dish by id", e);
        }
    }

    // http://localhost:8080/api/dish
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postDish(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            Dish newDish = new Dish(
                    -1,
                    json.getInt("idUser"),
                    json.getString("DishName"),
                    json.getString("DishDescription"),
                    json.getString("DishTags")
            );

            System.out.println("Step 2");

            DishManager.getInstance().insertToDish(newDish);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST dishes", e);
        }
    }

    // http://localhost:8080/api/dish/1
    @PATCH
    @Path("/{dishId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchDish(Object request, @PathParam("dishId") int dishId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            System.out.println("Step 1");
            Dish newDish = new Dish(
                    dishId,
                    json.getInt("idUser"),
                    json.getString("DishName"),
                    json.getString("DishDescription"),
                    json.getString("DishTags")
            );

            System.out.println("Step 2");

            DishManager.getInstance().updateToDish(newDish);
            return new AppResponse("Update Successful");
        }catch (Exception e){
            throw handleException("PATCH dishes/{dishId}", e);
        }
    }

    // http://localhost:8080/api/dish/1
    @DELETE
    @Path("/{dishId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteDish(@PathParam("dishId") int dishId){

        try{
            DishManager.getInstance().deleteDish(dishId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE dishes/{dishId}", e);
        }

    }
}
