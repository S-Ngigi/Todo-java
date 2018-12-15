package dao;

import models.FoodType;
import models.Restaurant;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
import java.util.ArrayList;

public class Sql2oFoodTypeDao implements FoodTypeDao {
    
    private final Sql2o sql2o;

    public  Sql2oFoodTypeDao (Sql2o sql2o) {   this.sql2o = sql2o; }

    /* Adding foodtype to db and setting the id(pk) */
    @Override
    public void addFoodType(FoodType foodtype){
        String sql_command = "INSERT INTO foodtypes (name) VALUES (:name)";
        try(Connection connect = sql2o.open()) {
            int foodtype_id = (int) connect.createQuery(sql_command, true)
                                                .bind(foodtype)
                                                .executeUpdate()
                                                .getKey();
            foodtype.setFoodId(foodtype_id);
        } catch (Sql2oException error) {
            System.out.println("ERROR ADDING FOODTYPE TO DB: " + error);
        }
    }

    // * Many to Many implementation here when adding a foodtype to a restaurant
    @Override
    public void addFoodTypeToRestaurant(FoodType foodtype, Restaurant restaurant) {
        String sql_command = "INSERT INTO restaurants_foodtypes (foodtype_id, restaurant_id) VALUES (:foodtype_id, :restaurant_id )";
        try(Connection connect = sql2o.open()){
            connect.createQuery(sql_command)
                            .addParameter("foodtype_id", foodtype.getFoodId())
                            .addParameter("restaurant_id", restaurant.getId())
                            .executeUpdate();
        } catch(Sql2oException error) {
            System.out.println("ERROR WHEN ADDING TO RESTAURANTS_FOODTYPES: " + error);
        }
    }

    /* Getting all the foodtypes that were committed to the database */
    @Override
    public List<FoodType> getAllFoodTypes() {
        try(Connection connect = sql2o.open()){
            return connect.createQuery("SELECT * FROM foodtypes")
                                         .executeAndFetch(FoodType.class);
        }
    }

    // * Many to Many implementation here when getting all the restaurant that serves a particular foodtype
    @Override
    public List<Restaurant> getAllRestaurantsByFoodTypeId(int foodtype_id){

        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        String join_query = "SELECT restaurant_id FROM restaurants_foodtypes WHERE  foodtype_id = :foodtype_id";

        try(Connection connect = sql2o.open()) {
            /* We query all the ids that match a foodtype_id */
            List<Integer> restaurant_ids = connect.createQuery(join_query).addParameter("foodtype_id", foodtype_id)
            .executeAndFetch(Integer.class);
            /* 
            We loop through our restaurant_ids and add  the matching data restaurants array list 
            */
            for(Integer restaurant_id: restaurant_ids) {
                String restaurant_query = "SELECT * FROM restaurants WHERE id = :restaurant_id";
                restaurants.add(
                    connect.createQuery(restaurant_query)
                                    .addParameter("restaurant_id", restaurant_id)
                                    .executeAndFetchFirst(Restaurant.class)
                );
            }
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN FETCHING RESTAURANTS BY FOODTYPE_ID: " + error);
        }
        return restaurants;
    }

    /* Deleting a foodtype by ID */
    @Override
    public void deleteFoodTypeById(int foodtype_id) {
        String delete_join = "DELETE FROM restaurants_foodtypes WHERE foodtype_id = :foodtype_id";
        try(Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM foodtypes WHERE id = :foodtype_id")
                            .addParameter("foodtype_id", foodtype_id)
                            .executeUpdate();
            connect.createQuery(delete_join)
                            .addParameter("foodtype_id", foodtype_id)
                            .executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN DELETING RESTAURANT FROM DB: " + error);
        }
    }

    /* Deleing all FoodType instances */
    @Override
    public void clearAllFoodTypes(){
        try(Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM foodtypes").executeUpdate();
            connect.createQuery("DELETE FROM restaurants_foodtypes")
                            .executeUpdate();         
        } catch(Sql2oException error) {
            System.out.println("ERROR WHEN DELETING ALL FOODTYPES FROM DB: " + error);
        }
    }
}