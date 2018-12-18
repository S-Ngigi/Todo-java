package views;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.Sql2oReviewDao;
import dao.Sql2oRestaurantDao;

import models.Review;
import models.Restaurant;

import exceptions.ApiException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class ReviewView {
    
    public void getReviewRoutes(){
        Sql2oReviewDao review_dao;
        Sql2oRestaurantDao restaurant_dao;
        Connection connect;
        
        String connection_string = "jdbc:h2:~/yeplish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        Gson gson = new Gson();
        
        review_dao = new Sql2oReviewDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);

        // *POST a new REVIEW to specific RESTAURANT
        post("/restaurant/:restaurant_id/review/new", "application/json", (request, response) -> {
            // * restaurant_id for restaurant we want to review.
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            if(found_restaurant == null) {
                throw new ApiException(404, String.format("Restaurant with id: %s does not exists", request.params("restaurant_id")));
            }
            else {
                // * our review for the above restaurant
                Review review = gson.fromJson(request.body(), Review.class);
                // * Setting the restaurant_id property of our review object. One to Many
                // relationship implementation here.
                review.setRestaurant_id(restaurant_id);
                // *Adding our review to the db.
                review_dao.addReview(review);
                // * returning the review object that we have just posted as json
                return gson.toJson(review);
            }

        });

        // * GET all the REVIEWS for a RESTAURANT.
        get("restaurant/:id/reviews", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("id"));
            return gson.toJson(review_dao.getAllReviewsForRestaurant(restaurant_id));
            // int restaurant_id = Integer.parseInt(request.params("id"));
            // Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            // int restaurant_id = Integer.parseInt(request.params("id"));
            // Restaurant found_restaurant = restaurant_dao.getRestaurantById(restaurant_id);
            // System.out.println("CAN YOU SEE ME: "+found_restaurant);
            // if(found_restaurant == null){
            //    throw new ApiException(404, String.format("Restaurant with id: %s does not exists", request.params("id")));
            // }
            // else if (review_dao.getAllReviewsForRestaurant(restaurant_id).size() == 0){
            //     throw new ApiException(404, "No reviews yet for this restaurant");
            // }
            // else {
            //     return gson.toJson(review_dao.getAllReviewsForRestaurant(restaurant_id));
                
            // }
           
        });

        // * GET all REVIEWS
        get("/reviews", "application/json", (request, respons) -> {
            if(review_dao.getAllReviews().size() == 0) {
                throw new ApiException(404, "No reviews posted yet");
            } 
            else {
                return gson.toJson(review_dao.getAllReviews());
            }
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