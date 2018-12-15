import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import com.google.gson.Gson;

import dao.Sql2oRestaurantDao;

import models.Restaurant;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {

    static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {

        BasicConfigurator.configure();

        Sql2oRestaurantDao restaurant_dao;
        Connection connect;
        Gson gson = new Gson(); 
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        connect = sql2o.open();

        // staticFileLocation("public");

        // *  Accepting a POST request to the specified path in JSON format
        post("/restaurants/new", "application/json", (request, response) -> {
            
            //  * Make Java Object from JSON using GSON
            Restaurant restaurant = gson.fromJson(request.body(), Restaurant.class);
            //  * Add the restaurant object to our db using the restaurant dao
            restaurant_dao.addRestaurant(restaurant);
            //  * Successful post request
            response.status(201);
            response.type("application/json");
            //  * Lets display the posted restaurant data.
            return gson.toJson(restaurant);
        });

        // * Accepting a GET request to get all restaurants
        get("/restaurants", "application/json", (request, response) -> {
            response.type("application/json");
            //  * Display the all the restaurants
            return gson.toJson(restaurant_dao.getAllRestaurants());
        });
       
        //  * Accepting a GET request to get a specific restaurant
        get("/restaurant/:id", "application/json", (request, response) -> {
            response.type("application/json");
            int restaurant_id = Integer.parseInt(request.params("id"));
            response.type("application/json");
            return gson.toJson(restaurant_dao.getRestaurantById(restaurant_id));
        });
    }
}