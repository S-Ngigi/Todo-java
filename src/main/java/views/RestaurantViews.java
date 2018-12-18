package views;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.Sql2oRestaurantDao;
import dao.Sql2oFoodTypeDao;

import models.Restaurant;
import models.FoodType;

import exceptions.ApiException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class RestaurantViews {

    public  void getRestaurantRoutes() {

        Sql2oRestaurantDao restaurant_dao;
        Sql2oFoodTypeDao foodtype_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);

        // * Accepting a POST request for a new RESTAURANT to the specified path in JSON format
        post("/restaurant/new", "application/json", (request, response) -> {

            // * Make Java Object from JSON using GSON
            Restaurant restaurant = gson.fromJson(request.body(), Restaurant.class);
            // * Add the restaurant object to our db using the restaurant dao
            restaurant_dao.addRestaurant(restaurant);
            // * Successful post request
            response.status(201);
            // * Lets display the posted restaurant data.
            return gson.toJson(restaurant);
        });

        // * Accepting a GET request to get all RESTAURANTS
        get("/restaurants", "application/json", (request, response) -> {
            // * Display the all the restaurants
           if(restaurant_dao.getAllRestaurants().size() > 0){
                return gson.toJson(restaurant_dao.getAllRestaurants());
           }
           else {
                throw new ApiException(404, "Sorry, no restaurants are currently listed.");
           }
        });

        // * Accepting a GET request to get a specific RESTAURANT
        get("/restaurant/:id", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("id"));
            Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            System.out.println("CAN YOU SEE ME: " + found_restaurant);
           if(found_restaurant == null) {
                throw new ApiException(404, String.format("Restaurant with the id:%s does not exists", request.params("id")));
           } 
           else {
                return gson.toJson(found_restaurant);
           }
        });

        // * Restaurant TO FoodType MANY TO MANY  
        post("/restaurant/:restaurant_id/foodtype/:foodtype_id", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            int foodtype_id = Integer.parseInt(request.params("foodtype_id"));

            Restaurant restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            FoodType foodtype = foodtype_dao.getFoodTypeById(foodtype_id);
            
            System.out.println("RESTAURANT FOUND: " + restaurant);
            System.out.println("FOODTYPE FOUND: " + foodtype);

            if(restaurant != null && foodtype != null) {
                restaurant_dao.addRestaurantToFoodType(restaurant, foodtype);
                response.status(201);
                return gson.toJson(
                    String.format(
                        "'%s' and '%s' have been associated",
                        restaurant.getName(), foodtype.getName()
                    )
                );
            }
            else {
                throw new ApiException(404, "Restaurant or FoodType does not exist");
            }
        });

        // * GET all FOODTYPES served by a RESTAURANT
        get("/restaurant/:restaurant_id/foodtypes", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            if(found_restaurant == null) {
                throw new ApiException(404, String.format(
                    "Restaurant with the id: %s does not exist",
                    request.params("restaurant_id")
                ));
            }
            else if (restaurant_dao.getAllFoodTypesByRestaurantId(restaurant_id).size() == 0 ) {
                return "{\"message\": \"I'm sorry no foodtypes are listed yet for this restaurant.\"}";
            }
            else {
                return gson.toJson(restaurant_dao.getAllFoodTypesByRestaurantId(restaurant_id));
            }
        });

        // * GET all RESTAURANTS that serve a particular FOODTYPE
        get("/foodtype/:foodtype_id/restaurants", "application/json", (request, response) -> {
            int foodtype_id = Integer.parseInt(request.params("foodtype_id"));
            FoodType foodtype_result = foodtype_dao.getFoodTypeById(foodtype_id);

            if(foodtype_result == null) {
                throw new ApiException(404, String.format(
                    "FoodType with the id : %s does not exist",
                    request.params("foodtype_id")
                ));
            }
            else if(foodtype_dao.getAllRestaurantsByFoodTypeId(foodtype_id).size() == 0) {
                return "{\"message\" : \"I'm sorry, but there are no restaurants serving that foodtype yet.\"}";
            }
            else {
                return gson.toJson(foodtype_dao.getAllRestaurantsByFoodTypeId(foodtype_id));
            }
        });

        /*
         * FILTERS - Allow us to always execute the code block within to execute after
         * recieving a request allowing for more DRY code.
         */

        exception(ApiException.class, (exception, request, response) -> {
            // * We type cast our exception to become our ApiException
            ApiException error = (ApiException) exception;
            // * Instantiate a gsonJsonMap hashmap to store our message and status
            Map<String, Object> gsonJsonMap = new HashMap<>();
            // * Adding the status to our gsonJsonMap hashmap
            gsonJsonMap.put("status", error.getStatusCode());
            // * Add the error_message to our gsonJsonMap hashMap
            gsonJsonMap.put("error_message", error.getMessage());
            response.type("application/json");
            // * Passing the error status code
            response.status(error.getStatusCode());
            // * Passing our gsonJsonMap as part of our request when exception is thrown.
            response.body(gson.toJson(gsonJsonMap));
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}