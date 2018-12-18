package views;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import dao.Sql2oFoodTypeDao;

import models.FoodType;

import exceptions.ApiException;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class FoodTypeView {

    public void getFoodtypeRoutes() {
        Sql2oFoodTypeDao foodtype_dao;
        Connection connect;
        Gson gson = new Gson();
        String connection_string = "jdbc:h2:~/yelpish_api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";

        Sql2o sql2o = new Sql2o(connection_string, "", "");
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
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

