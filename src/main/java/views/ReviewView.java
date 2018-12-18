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

    public void getReviewRoutes() {

        Sql2oReviewDao review_dao;
        Sql2oRestaurantDao restaurant_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        review_dao = new Sql2oReviewDao(sql2o);
        connect = sql2o.open();
        System.out.println(connect);

        // * POST a new REVIEW  for a specific RESTAURANT. ONE TO MANY.
        post("/restaurant/:restaurant_id/review/new", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant restaurant_result = restaurant_dao.getRestaurantById(restaurant_id);
            System.out.println("RESTAURANT RESULT: " + restaurant_result);
            if(restaurant_result == null){
                throw new ApiException(404, String.format(
                    "Restaurant %s does not exist",
                    request.params("restaurant_id")
                ));
            }
            else {
                //  * Extracting the review from the request
                Review review = gson.fromJson(request.body(), Review.class);
                review.setRestaurant_id(restaurant_id);
                review_dao.addReview(review);
                response.status(201);
                return gson.toJson(review);
            }
        });

        //  * GET all REVIEWS for a restaurant
        get("/restaurant/:restaurant_id/reviews", "application/json", (request, response) -> {
            int restaurant_id = Integer.parseInt(request.params("restaurant_id"));
            Restaurant restaurant_result = restaurant_dao.getRestaurantById(restaurant_id);
            System.out.println("RESTAURANT RESULT: "+restaurant_result);
            if(restaurant_result == null){
                throw new ApiException(404, String.format(
                    "Restaurant with id: %s does not exist",
                    request.params("restaurant_id")
                ));
            }
            else if (review_dao.getAllReviewsForRestaurant(restaurant_id).size() == 0) {
                return "{\" message\" : \"Sorry, no reviews posted for this restaurant yet.\"}";
            }
            else {
                return gson.toJson(review_dao.getAllReviewsForRestaurant(restaurant_id));
            }
        });

        // * GET all REVIEWS
        get("/reviews", "application/json", (request, response) -> {
            if (review_dao.getAllReviews().size() == 0){
                return "{\"message\": \"Sorry, no reviews posted yet\"}";
            }
            else {
                return gson.toJson(review_dao.getAllReviews());
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