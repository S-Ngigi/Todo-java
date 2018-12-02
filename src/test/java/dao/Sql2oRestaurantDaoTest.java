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

    @Test
    public void addingRestaurantSetsId() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals(1, test_restaurant.getId());
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