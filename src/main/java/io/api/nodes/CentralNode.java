package io.api.nodes;


import io.app.App;
import io.entity.Food;
import io.entity.Fridge;
import io.entity.House;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
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
    private final Logger logger = Logger.getLogger("API");

    @POST
    @Path("/")
    public Response createHouse(House house) {
        logger.log(Level.INFO, "Entering creation of a house");

        // Check that we have all fields
        if (house.getAddress() == null || house.getZip() == null) {
            return Response.status(400).entity("Wrong parameter").build();
        }

        // Test if there is another house with the same address and ZIP
        if (app.hasHouse(house)) {
            return Response.status(400).entity("A house with the same address exists").build();
        }

        // Retrieve an ID and set id in House object
        int houseId = App.getNextId();
        house.setId(houseId);

        // Add the house to the underlying storage
        app.getHouses().put(houseId, house);

        return Response.status(201).entity("{ \"id\":" + houseId + "}").build();
    }

    @GET
    @Path("/")
    public Response getHouses() {
        logger.log(Level.INFO, "Entering get all houses");

        return Response.status(200).entity(app.getHouses()).build();
    }

    @GET
    @Path("/{id}")
    public Response getHouse(@PathParam("id") Integer houseId) {
        logger.log(Level.INFO, "Entering get house");

        if (app.hasHouse(houseId)) {
            House house = app.getHouse(houseId);
            return Response.status(200).entity(house).build();
        }

        return Response.status(400).entity("The house does not exist").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteHouse(@PathParam("id") Integer houseId) {
        logger.log(Level.INFO, "Entering delete house");

        if (app.hasHouse(houseId)) {
            // Check that the house is empty
            if (!app.getHouse(houseId).getFridges().isEmpty()) {
                logger.info("The house is not empty");
                return Response.status(400).entity("The house is not empty").build();
            }
            // Remove the house if so
            House house = app.removeHouse(houseId);

            return Response.status(200).entity(house).build();
        }
        return Response.status(400).entity("The house does not exist").build();

    }

    @POST
    @Path("/{id}/fridge")
    public Response addFridge(@PathParam("id") Integer houseId, Fridge fridge) {
        logger.log(Level.INFO, "Entering creation fridge");

        // Check that fridge has all fields
        if (fridge.getId() == null || fridge.getMaxVolume() <= 0 || fridge.getState() == null) {
            return Response.status(400).entity("Wrong parameter").build();
        }

        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("Cannot create the fridge because the house does not exist").build();
        }

        House house = app.getHouse(houseId);

        // Test if a fridge with the same id exists
        if (house.hasFridge(fridge.getId())) {
            return Response.status(400).entity("A fridge with the same id exists").build();
        }

        // Generate an APIKey for the fridge
        String apiKey = app.generateApiKey();
        fridge.setApiKey(apiKey);

        // Add the fridge to the house
        house.getFridges().add(fridge);

        return Response.status(201).entity("{ \"apiKey\": \"" + apiKey + "\" }").build();

    }

    @GET
    @Path("/{id}/fridge")
    public Response getFridges(@PathParam("id") Integer houseId) {
        logger.log(Level.INFO, "Entering get all fridges");

        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }

        House house = app.getHouse(houseId);

        return Response.status(200).entity(house.getFridges()).build();

    }

    @GET
    @Path("/{id}/fridge/{f_id}")
    public Response getFridge(@PathParam("id") Integer houseId,
                              @PathParam("f_id") String fridgeId) {
        logger.log(Level.INFO, "Entering get fridge");
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }

        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }

        return Response.status(200).entity(fridge.get()).build();

    }

    @DELETE
    @Path("/{id}/fridge/{f_id}")
    public Response deleteFridge(@PathParam("id") Integer houseId,
                                 @PathParam("f_id") String fridgeId,
                                 @DefaultValue("NA") @HeaderParam("API-KEY") String apiKey) {
        logger.log(Level.INFO, "Entering delete fridge");
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }

        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }

        // Check that the APIKey is correct
        if (!apiKey.equals(fridge.get().getApiKey())) {
            return Response.status(403).entity("Access Denied").build();
        }

        house.getFridges().remove(fridge.get());

        return Response.status(200).entity(fridge.get()).build();

    }


    @POST
    @Path("/{id}/fridge/{f_id}/food")
    public Response addFood(@PathParam("id") Integer houseId,
                            @PathParam("f_id") String fridgeId,
                            @DefaultValue("NA") @HeaderParam("API-KEY") String apiKey,
                            Food food) {
        logger.log(Level.INFO, "Entering creation food");
        // Check that food has all fields
        if (food.getName() == null || food.getVolume() <= 0 || food.getExpireDate() == null) {
            return Response.status(400).entity("Wrong parameter").build();
        }

        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }
        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }

        // Check that the APIKey is correct
        if (!apiKey.equals(fridge.get().getApiKey())) {
            return Response.status(403).entity("Access Denied").build();
        }

        // Check if food with the same name exists
        if (fridge.get().getFood().stream().anyMatch((f) -> f.getName().equals(food.getName()))) {
            return Response.status(400).entity("Food with the same name exists").build();

        }

        // Check the volume of the fridge
        int currentVolume = fridge.get().getFood().stream().map(Food::getVolume).reduce(0, Integer::sum);
        if (currentVolume + food.getVolume() > fridge.get().getMaxVolume()) {
            return Response.status(400).entity("The fridge does not have enough places for the food").build();
        }

        // Add the food to the fridge
        fridge.get().getFood().add(food);

        return Response.status(201).entity(food).build();

    }

    @GET
    @Path("/{id}/fridge/{f_id}/food")
    public Response getFood(@PathParam("id") Integer houseId,
                            @PathParam("f_id") String fridgeId,
                            @DefaultValue("NA") @HeaderParam("API-KEY") String apiKey) {
        logger.log(Level.INFO, "Entering get all food");
        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }

        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }
        // Check that the APIKey is correct
        if (!apiKey.equals(fridge.get().getApiKey())) {
            return Response.status(403).entity("Access Denied").build();
        }

        return Response.status(200).entity(fridge.get().getFood()).build();

    }

    @GET
    @Path("/{id}/fridge/{f_id}/food/{name}")
    public Response getFoodByName(@PathParam("id") Integer houseId,
                                  @PathParam("f_id") String fridgeId,
                                  @DefaultValue("NA") @HeaderParam("API-KEY") String apiKey,
                                  @PathParam("name") String foodName) {
        logger.log(Level.INFO, "Entering get food");

        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }
        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }
        // Check that the APIKey is correct
        if (!apiKey.equals(fridge.get().getApiKey())) {
            return Response.status(403).entity("Access Denied").build();
        }

        if (!fridge.get().hasFood(foodName)) {
            return Response.status(400).entity("The food does not exist").build();
        }

        Food food = fridge.get().getFood().stream().filter((f) -> f.getName().equals(foodName)).findFirst().get();

        return Response.status(200).entity(food).build();

    }

    @DELETE
    @Path("/{id}/fridge/{f_id}/food/{name}")
    public Response deleteFoodByName(@PathParam("id") Integer houseId,
                                     @PathParam("f_id") String fridgeId,
                                     @DefaultValue("NA") @HeaderParam("API-KEY") String apiKey,
                                     @PathParam("name") String foodName) {
        logger.log(Level.INFO, "Entering delete food");

        if (!app.hasHouse(houseId)) {
            return Response.status(400).entity("The house does not exist").build();

        }
        House house = app.getHouse(houseId);
        if (!house.hasFridge(fridgeId)) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();

        }
        Optional<Fridge> fridge = app.getFridge(house, fridgeId);
        if (!fridge.isPresent()) {
            return Response.status(400).entity("The Fridge does not exist in the house").build();
        }
        // Check that the APIKey is correct
        if (!apiKey.equals(fridge.get().getApiKey())) {
            return Response.status(403).entity("Access Denied").build();
        }

        if (!fridge.get().hasFood(foodName))
            return Response.status(400).entity("The food does not exist").build();


        Food food = fridge.get().getFood().stream().filter((f) -> f.getName().equals(foodName)).findFirst().get();

        // Remove the food from the fridge
        fridge.get().getFood().remove(food);

        return Response.status(200).entity(food).build();

    }

}
