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
            House house = app.getHouse(houseId);
            return Response.status(200).entity(house).build();
        }
        return Response.status(400).entity("The house does not exist").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHouse(@PathParam("id") Integer houseId) {
        if (app.hasHouse(houseId)) {
            House house = app.removeHouse(houseId);
            return Response.status(200).entity(house).build();
        }
        return Response.status(400).entity("The house does not exist").build();

    }

    @POST
    @Path("/{id}/fridge")
    public Response addFridge(@PathParam("id") Integer houseId, Fridge fridge) {
        System.out.println(houseId);
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("Cannot create the fridge because the house does not exist").build();

        }

        House house = app.getHouse(houseId);
        Logger.getLogger("API").log(Level.INFO, "Creation of a fridge");

        // Test if a fridge with the same id exists
        if (house.hasFridge(fridge.getId())) {
            return Response.status(400).entity("A fridge with the same id exists").build();
        }

        house.getFridges().add(fridge);
        return Response.status(201).entity(fridge).build();

    }

    @GET
    @Path("/{id}/fridge")
    public Response getFridges(@PathParam("id") Integer houseId) {
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);

        return Response.status(201).entity(house.getFridges()).build();

    }

    @GET
    @Path("/{id}/fridge/{f_id}")
    public Response getFridge(@PathParam("id") Integer houseId,
                              @PathParam("f_id") String fridgeId) {
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }
        Fridge fridge = house.getFridges().stream().filter((f) -> f.equals(fridgeId)).findFirst().get();

        return Response.status(201).entity(fridge).build();

    }

    @DELETE
    @Path("/{id}/fridge/{f_id}")
    public Response deleteFridge(@PathParam("id") Integer houseId,
                                 @PathParam("f_id") String fridgeId) {
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }
        Fridge fridge = house.getFridges().stream().filter((f) -> f.getId().equals(fridgeId)).findFirst().get();

        house.getFridges().remove(fridge);

        return Response.status(201).entity(fridge).build();

    }


}
