package views;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.Sql2oFoodTypeDao;
import dao.Sql2oRestaurantDao;

import models.FoodType;
import models.Restaurant;

import exceptions.ApiException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class FoodTypeView {

    public void getFoodtypeRoutes() {
        Sql2oFoodTypeDao foodtype_dao;
        Sql2oRestaurantDao restaurant_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

        Sql2o sql2o = new Sql2o(connection_string, "", "");
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);

        // * POSTING a FOODTYPE
        post("/foodtypes/new", "application/json", (request, response) -> {
            FoodType foodtype = gson.fromJson(request.body(), FoodType.class);
            foodtype_dao.addFoodType(foodtype);
            response.status(201);
            return gson.toJson(foodtype);
        });

        // *GET all FOODTYPES
        get("/foodtypes", "application/json", (request, response) -> {
            return gson.toJson(foodtype_dao.getAllFoodTypes());
        });

        // * GET foodtype based on Id
        get("/foodtype/:foodtype_id", "application/json", (request, response) -> {
            int foodtype_id = Integer.parseInt(request.params("foodtype_id"));
            return gson.toJson(foodtype_dao.getFoodTypeById(foodtype_id));
        });

        // * Restaurant TO FoodType MANY TO MANY
        post("/restaurant/:restaurant_id/foodtype/:foodtype_id", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            int foodtype_id = Integer.parseInt(request.params("foodtype_id"));

            Restaurant restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            FoodType foodtype = foodtype_dao.getFoodTypeById(foodtype_id);

            System.out.println("RESTAURANT FOUND: " + restaurant);
            System.out.println("FOODTYPE FOUND: " + foodtype);

            if (restaurant != null && foodtype != null) {
                restaurant_dao.addRestaurantToFoodType(restaurant, foodtype);
                response.status(201);
                return gson.toJson(
                        String.format("'%s' and '%s' have been associated", restaurant.getName(), foodtype.getName()));
            } else {
                throw new ApiException(404, "Restaurant or FoodType does not exist");
            }
        });

        // * GET all FOODTYPES served by a RESTAURANT
        get("/restaurant/:restaurant_id/foodtypes", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            if (found_restaurant == null) {
                throw new ApiException(404,
                        String.format("Restaurant with the id: %s does not exist", request.params("restaurant_id")));
            } else if (restaurant_dao.getAllFoodTypesByRestaurantId(restaurant_id).size() == 0) {
                return "{\"message\": \"I'm sorry no foodtypes are listed yet for this restaurant.\"}";
            } else {
                return gson.toJson(restaurant_dao.getAllFoodTypesByRestaurantId(restaurant_id));
            }
        });

        // * GET all RESTAURANTS that serve a particular FOODTYPE
        get("/foodtype/:foodtype_id/restaurants", "application/json", (request, response) -> {
            int foodtype_id = Integer.parseInt(request.params("foodtype_id"));
            FoodType foodtype_result = foodtype_dao.getFoodTypeById(foodtype_id);

            if (foodtype_result == null) {
                throw new ApiException(404,
                        String.format("FoodType with the id : %s does not exist", request.params("foodtype_id")));
            } else if (foodtype_dao.getAllRestaurantsByFoodTypeId(foodtype_id).size() == 0) {
                return "{\"message\" : \"I'm sorry, but there are no restaurants serving that foodtype yet.\"}";
            } else {
                return gson.toJson(foodtype_dao.getAllRestaurantsByFoodTypeId(foodtype_id));
            }
        });

        // * FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiException error = (ApiException) exception;
            Map<String, Object> gsonJsonMap = new HashMap<>();
            gsonJsonMap.put("status", error.getStatusCode());
            gsonJsonMap.put("error_message", error.getMessage());
            response.type("application/json");
            response.status(error.getStatusCode());
            response.body(gson.toJson(gsonJsonMap));
        });

        after((request,  response) -> {
            response.type("application/json");
        });
    }
}

