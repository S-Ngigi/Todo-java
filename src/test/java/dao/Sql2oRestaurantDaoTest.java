package dao;

import models.Restaurant;

import org.sql2o.Sql2o;
import org.sql2o.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Sql2oRestaurantDaoTest {
    private Connection test_connection;
    private Sql2o sql2o;
    private Sql2oRestaurantDao restaurant_dao;

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        sql2o = new Sql2o(connection_string, "", "");
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        test_connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        test_connection.close();
    }

    /* Id being set properly once restaurant  is added to the db. */
    @Test
    public void addingRestaurantSetsId() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals(1, test_restaurant.getId());
    }
    /* Getting all restaurant instances stored in the db */
    @Test
    public void getAllRestaurantTest() throws Exception {
        Restaurant restaurant_1 = restaurantSetUp();
        Restaurant restaurant_2 = restaurantSetUp();
        assertNotEquals(restaurant_1.getId(), restaurant_2.getId());
        assertEquals(2, restaurant_dao.getAllRestaurants().size());
    }

    /* Getting a single instance of  a restaurant working well */
    @Test
    public void getRestaurantByIdTest() throws Exception {
        Restaurant restaurant_1 = restaurantSetUp();
        Restaurant restaurant_2 = restaurantSetUp();
        assertNotEquals(restaurant_1.getId(), restaurant_2.getId());
        assertEquals(
            2, restaurant_dao.getRestaurantById(restaurant_2.getId()).getId());
    }

    /* Updating an instance of a restuarant */
    @Test
    public void updateRestaurantTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        restaurant_dao.updateRestaurant(
                test_restaurant.getId(),
                " Eleven Madison Park",
                "11 Madison Ave, New York",
                "NY 10010",
                "+1 212-889-0905",
                "elevenmadisonpark.com",
                "info@elevenmadisonpark.com",
                "https://bit.ly/2KwknSj"
        );
        Restaurant updated_restaurant = restaurant_dao.getRestaurantById(test_restaurant.getId());
        System.out.println(updated_restaurant.getName());
        assertNotEquals("Gramercy Tavern", updated_restaurant.getName());
    }

    /* Deleting an instance of a restuarant by Id */
    @Test
    public void deleteRestaurantByIdTest() throws Exception {
        Restaurant restaurant_1 = restaurantSetUp();
        Restaurant restaurant_2 = restaurantSetUp();
        assertEquals(2, restaurant_dao.getAllRestaurants().size());
        assertNotEquals(restaurant_1.getId(), restaurant_2.getId());
        restaurant_dao.deleteRestaurant(restaurant_1.getId());
        assertEquals(1, restaurant_dao.getAllRestaurants().size());
    }

    @Test
    public void clearAllRestaurantTest() throws Exception {
        Restaurant restaurant_1 = restaurantSetUp();
        Restaurant restaurant_2 = restaurantSetUp();
        assertNotEquals(restaurant_1.getId(), restaurant_2.getId());
        assertEquals(2, restaurant_dao.getAllRestaurants().size());
        restaurant_dao.clearAllRestaurants();
        assertEquals(0, restaurant_dao.getAllRestaurants().size());
    }


    /* Helper functions */
    //  Full restaurant details
    public Restaurant restaurantSetUp() {
        Restaurant dummy_restaurant = new Restaurant(
                "Gramercy Tavern", 
                "42 E 20th St, New York", 
                "NY 10003", "+1 212-477-0777",
                "https://www.gramercytavern.com/", "events@gramercytavern.com", 
                "https://bit.ly/2Btwb4O"
        );
        restaurant_dao.addRestaurant(dummy_restaurant);
        return dummy_restaurant;
    }

    // Alt restaurant details
    public Restaurant restaurantAltSetUp(){
        Restaurant alt_restaurant = new Restaurant(
            "Gramercy Tavern", 
            "42 E 20th St, New York", 
            "NY 10003",
            "+1 212-477-0777"
            );
            restaurant_dao.addRestaurant(alt_restaurant);
        return alt_restaurant;
    }

}