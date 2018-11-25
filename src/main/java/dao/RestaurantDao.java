package dao;

import java.util.List;

import models.Restaurant;

public interface RestaurantDao{
    /* Create */
    void addRestaurant(Restaurant restaurant);

    /* Read */
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(int id);

    /* Update */
    void updateRestaurant(int id, String name, String address, String zipcode, String phone, String website, String email, String img_url);

    /* Delete */
    void deleteRestaurant(int id);
    void clearAllRestaurants();
}

