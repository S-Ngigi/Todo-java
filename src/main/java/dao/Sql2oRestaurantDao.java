package dao;

import java.util.List;
import java.util.ArrayList;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import models.Restaurant;
import models.FoodType;

public class Sql2oRestaurantDao implements RestaurantDao {
    
    private final Sql2o sql2o;
    public Sql2oRestaurantDao(Sql2o sql2o){this.sql2o=sql2o;}

    /* Adding a Restaurant Object to Db */
    @Override
    public void addRestaurant(Restaurant restaurant){
        String sql_command = "INSERT INTO restaurants (" +
        "name, address, zipcode, phone, website, email, img_url)" +
        " VALUES (:name, :address, :zipcode, :phone, :website, :email, :img_url)";

        try(Connection connect = sql2o.open()){
            int restaurant_id = (int) connect.createQuery(sql_command, true)
                                                        .bind(restaurant)
                                                        .executeUpdate()
                                                        .getKey();

            restaurant.setId(restaurant_id);
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN ADDING RESTAURANT TO DB: " + error);
        }
    }

    // * Many to many implementation here when adding a restaurant to a particular foodtype 
    @Override
    public void addRestaurantToFoodType(Restaurant restaurant, FoodType foodtype) {
        String sql_command = "INSERT INTO restaurants_foodtypes (foodtype_id, restaurant_id) VALUES (:foodtype_id, :restaurant_id )";

        try(Connection connect = sql2o.open()) {
            connect.createQuery(sql_command)
                            .addParameter("foodtype_id", foodtype.getFoodId())
                            .addParameter("restaurant_id", restaurant.getId())
                            .executeUpdate();
        } catch(Sql2oException error) {
            System.out.println("ERROR IN ADDING RESTAURANT TO FOODTYPE: " + error);
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        try(Connection connect = sql2o.open()){
            return connect.createQuery("SELECT * FROM restaurants")
                                         .executeAndFetch(Restaurant.class);
        }
    }

    @Override
    public Restaurant getRestaurantById(int restaurant_id){
        try(Connection connect = sql2o.open()){
            /*
             * When returning a restaurant by id we have to say SELECT *.
             * We can't just say SELECT FROM restaurants WHERE blah..
             */
            return connect.createQuery(
                "SELECT * FROM restaurants WHERE id = :restaurant_id"
            ).addParameter("restaurant_id", restaurant_id )
             .executeAndFetchFirst(Restaurant.class);
        }
    }

    // * Many to Many implementation here when getting all foodtypes a restaurant serves
    @Override
    public List<FoodType> getAllFoodTypesByRestaurantId( int restaurant_id){
        
        /* Create a List where we will store our foodtype objects */
        List<FoodType> foodtypes = new ArrayList<FoodType>();

        String join_query = "SELECT foodtype_id FROM restaurants_foodtypes WHERE restaurant_id = :restaurant_id";
        /*
            Using the query above we query the restaurants _foodtypes join table
            to get all the foodtypes matching the restaurant_id parameter 
        */
        try(Connection connect = sql2o.open()){
            List<Integer> foodtype_ids = connect.createQuery(join_query)
            .addParameter("restaurant_id", restaurant_id)
            .executeAndFetch(Integer.class);
        /* 
            We loop through the resulting foodtype_ids add matching data to our
            foodtypes_list
        */
            for(Integer foodtype_id : foodtype_ids) {
                String foodtype_query = "SELECT * FROM foodtypes WHERE id = :foodtype_id";
                foodtypes.add(
                        connect.createQuery(foodtype_query)
                                        .addParameter("foodtype_id", foodtype_id)
                                        .executeAndFetchFirst(FoodType.class)
                );
            }
        }
        return foodtypes;
    }

    @Override
    public void updateRestaurant(
        int id, String new_name, String new_address, String new_zipcode, 
        String new_phone, String new_website, String new_email, String new_img
        ){
        String sql_command = "UPDATE restaurants SET (name, address, zipcode, phone, website, email, img_url) = (:name, :address, :zipcode, :phone, :website, :email, :img_url) WHERE id=:id";

        try(Connection connect = sql2o.open()){
            connect.createQuery(sql_command)
                            .addParameter("name", new_name)
                            .addParameter("address", new_address)
                            .addParameter("zipcode", new_zipcode)
                            .addParameter("phone", new_phone)
                            .addParameter("website", new_website)
                            .addParameter("email", new_email)
                            .addParameter("img_url", new_img)
                            .addParameter("id", id)
                            .executeUpdate();
        } catch(Sql2oException error) {
            System.out.println("ERROR IN UPDATING RESTAURANT: " + error);
        }
    }

    @Override
    public void deleteRestaurant(int id){
        try(Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM restaurants WHERE id = :id")
                            .addParameter("id", id)
                            .executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN DELETING RESTAURANT FROM DB: " + error);
        }
    }

    @Override
    public void clearAllRestaurants(){
        try (Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM restaurants").executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN DELETING ALL RESTAURANTS" + error);
        }
    }

}