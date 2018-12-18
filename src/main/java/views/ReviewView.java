package views;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.Sql2oRestaurantDao;
import dao.Sql2oReviewDao;

import models.Restaurant;
import models.Review;

import exceptions.ApiException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class ReviewView {
    
    public void getReviewRoutes(){
        Sql2oRestaurantDao restaurant_dao;
        Sql2oReviewDao review_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yeplish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        review_dao = new Sql2oReviewDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);
        

        // *POST a new REVIEW to specific RESTAURANT
        post("/restaurant/:restaurant_id/review/new", "application/json", (request, response) -> {
            // * restaurant_id for restaurant we want to review.
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            // * our review for the above restaurant
            Review review = gson.fromJson(request.body(), Review.class);
            //  * Setting the restaurant_id property of our review object. One to Many relationship implementation here.
            review.setRestaurant_id(restaurant_id);
            // *Adding our review to the db.
            review_dao.addReview(review);
            // * returning the review object that we have just posted as json
            return gson.toJson(review);
        });

        // * GET all REVIEWS
        get("/reviews", "application/json", (request, response) -> {
            return gson.toJson(review_dao.getAllReviews());
        });

        // * GET all the REVIEWS for a RESTAURANT.
        get("restaurant/:restaurant_id/reviews", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);

            System.out.println("CAN YOU SEE ME: "+restaurant_id);
            System.out.println("CAN YOU SEE ME: "+ found_restaurant);
            return gson.toJson(review_dao.getAllReviewsForRestaurant(restaurant_id));
            
        });

        // * FILTERS
        exception(ApiException.class, (exception, request, response) -> {
            ApiException error = (ApiException) exception;
            Map <String, Object> gsonJsonMap = new HashMap<>();
            gsonJsonMap.put("status", error.getStatusCode());
            gsonJsonMap.put("error_message", error.getMessage());
            response.type("application/json");
            response.status(error.getStatusCode());
            response.body(gson.toJson(gsonJsonMap));
        });
        

        after((request, response) -> {
            response.type("application/json");
        });

    }

}