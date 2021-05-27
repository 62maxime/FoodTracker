package io.api.nodes;


import io.app.App;
import io.entity.Food;
import io.entity.Fridge;
import io.entity.House;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The central node will receive request and will give instructions to the slave nodes
 */
@Path("/house")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CentralNode {

    private final App app = App.getApp();

    @POST
    @Path("/")
    public Response createHouse(House house) {
        Logger.getLogger("API").log(Level.INFO, "Creation of a house");

        // Test if there is another house with the same address and ZIP
        if (app.hasHouse(house)) {
            return Response.status(400).entity("A house with the same address exists").build();
        }

        // Retrieve an ID and set id in House object
        int houseId = App.getNextId();
        house.setId(houseId);
        app.getHouses().put(houseId, house);
        return Response.status(201).entity("{ \"id\":" + houseId+ "}").build();
    }

    @GET
    @Path("/")
    public Response getHouses() {
        return Response.status(201).entity(app.getHouses()).build();
    }

    @GET
    @Path("/{id}")
    public Response getHouse(@PathParam("id") Integer houseId) {
        if (app.hasHouse(houseId)) {
            House house = app.getHouses().get(houseId);
            return Response.status(200).entity(house).build();
        }
        return Response.status(400).entity("The house does not exist").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHouse(@PathParam("id") Integer houseId) {
        if (app.hasHouse(houseId)) {
            House house = app.getHouses().remove(houseId);
            return Response.status(200).entity(house).build();
        }
        return Response.status(400).entity("The house does not exist").build();

    }
    

}
