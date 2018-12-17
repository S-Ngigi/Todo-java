package views;

import com.google.gson.Gson;

import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;
import dao.Sql2oFoodTypeDao;

import models.Restaurant;
import models.FoodType;
import models.Review;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class RestaurantViews {

    public  void restaurantRoutes() {

        Sql2oRestaurantDao restaurant_dao;
        Sql2oFoodTypeDao foodtype_dao;
        Sql2oReviewDao review_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
        review_dao = new Sql2oReviewDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);

        // * Accepting a POST request for a new RESTAURANT to the specified path in JSON format
        post("/restaurants/new", "application/json", (request, response) -> {

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
            return gson.toJson(restaurant_dao.getAllRestaurants());
        });

        // * Accepting a GET request to get a specific RESTAURANT
        get("/restaurant/:id", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("id"));
            return gson.toJson(restaurant_dao.getRestaurantById(restaurant_id));
        });

        // * POSTING  a FOODTYPE
        post("/foodtypes/new", "application/json", (request, response)->{
            FoodType foodtype = gson.fromJson(request.body(), FoodType.class);
            foodtype_dao.addFoodType(foodtype);
            response.status(201);
            return gson.toJson(foodtype);
        });

        // *GET all FOODTYPES
        get("/foodtypes", "application/json", (request, response) -> {
            return gson.toJson(foodtype_dao.getAllFoodTypes());
            
        });

        // * POSTING a new REVIEW on a specific restaurant.
        post("/restaurant/:restaurant_id/review/new", "application/json", (request, response) -> {
            // * Extracting the restaurant_id that we want to review.
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            // * Extracting the reveiw json and converting it to an Object
            Review review = gson.fromJson(request.body(), Review.class);
            
            // * We set the restaurant _id to the restaurant_id property of a Review.
            review.setRestaurant_id(restaurant_id);
            review_dao.addReview(review);
            response.status(201);
            return gson.toJson(review);
            
        });

        // * GETTING all REVIEWS for a restaurant.
        get("/restaurant/:restaurant_id/reviews", "application/json", (request, response) -> {
            //  * Extracting the restaurant_id from the request
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
           return gson.toJson(review_dao.getAllReviewsForRestaurant(restaurant_id));

        });

        /*
         * FILTERS - Allow us to always execute the code block within to execute after
         * recieving a request allowing for more DRY code.
         */
        after((request, response) -> {
            response.type("application/json");
        });
    }
}