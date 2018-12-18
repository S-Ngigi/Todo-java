package dao;

import java.util.List;

import models.Restaurant;
import models.FoodType;

public interface RestaurantDao{
    /* Create */
    void addRestaurant(Restaurant restaurant);
    // Many to Many implementation here.
    //   i.e. "This Restaurant now serves this foodtype" 
    void addRestaurantToFoodType(Restaurant restaurant, FoodType foodtype);

    /* Read */
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(int id);
    List<FoodType> getAllFoodTypesByRestaurantId(int restaurant_id);

    /* Update */
    void updateRestaurant(int id, String name, String address, String zipcode, String phone, String website, String email, String img_url);

    /* Delete */
    void deleteRestaurant(int id);
    void clearAllRestaurants();
}

