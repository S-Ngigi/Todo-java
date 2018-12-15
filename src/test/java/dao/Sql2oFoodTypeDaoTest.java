package dao;

import models.FoodType;
import models.Restaurant;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Sql2oFoodTypeDaoTest {

    private Connection test_connection;
    private Sql2o sql2o;
    private Sql2oFoodTypeDao foodtype_dao;
    private Sql2oRestaurantDao restaurant_dao;

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        sql2o = new Sql2o(connection_string, "", "");
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
        test_connection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        test_connection.close();
    }

    @Test
    public void addingFoodTypeSetsIdTest() throws Exception {
        FoodType test_foodtype = foodtypeSetUp();
        assertEquals(1, test_foodtype.getFoodId());
    }

    // * Testing that adding our a foodtype to a restaurant works.
    @Test
    public void addFoodTypeToRestaurantTest() throws Exception {
        //  The restaurants
        Restaurant dummy_restaurant = restaurantSetUp();
        Restaurant alt_restaurant = altRestaurantSetUp();
        //  The food type
        FoodType test_foodtype = foodtypeSetUp();
        // Our many to many method 
        foodtype_dao.addFoodTypeToRestaurant(test_foodtype, dummy_restaurant);
        assertEquals( 1, foodtype_dao.getAllRestaurantsByFoodTypeId(test_foodtype.getFoodId()).size());
        foodtype_dao.addFoodTypeToRestaurant(test_foodtype, alt_restaurant);
        assertEquals(2, foodtype_dao.getAllRestaurantsByFoodTypeId(test_foodtype.getFoodId()).size());
    }

    @Test
    public void getAllFoodTypesTest() throws Exception {
        FoodType foodtype_1 = foodtypeSetUp();
        FoodType foodtype_2 = foodtypeSetUp();
        assertNotEquals(foodtype_1.getFoodId(), foodtype_2.getFoodId());
        assertEquals(2, foodtype_dao.getAllFoodTypes().size());
    }

    @Test
    public void deleteFoodTypeByIdTest() throws Exception {
        FoodType foodtype_1 = foodtypeSetUp();
        FoodType foodtype_2 = foodtypeSetUp();
        assertNotEquals(foodtype_1.getFoodId(), foodtype_2.getFoodId());
        assertEquals(2, foodtype_dao.getAllFoodTypes().size());
        foodtype_dao.deleteFoodTypeById(foodtype_1.getFoodId());
        assertEquals(1, foodtype_dao.getAllFoodTypes().size());
    }

    @Test
    public void clearAllFoodtypesTest() throws Exception {
        FoodType foodtype_1 = foodtypeSetUp();
        FoodType foodtype_2 = foodtypeSetUp();
        assertNotEquals(foodtype_1, foodtype_2);
        assertEquals(2, foodtype_dao.getAllFoodTypes().size());
        foodtype_dao.clearAllFoodTypes();
        assertEquals(0, foodtype_dao.getAllFoodTypes().size());
    }

    /* 
    We make sure that when a restaurant is removed the relationship it has with a
    foodtype is also removed. 
    */
    @Test
    public void deletingRestaurantUpdatesJoinTableTest() throws Exception {
                FoodType test_foodtype = foodtypeSetUp();
                Restaurant restaurant_1 = restaurantSetUp();
                Restaurant restaurant_2 = altRestaurantSetUp();
                restaurant_dao.addRestaurantToFoodType(restaurant_1, test_foodtype);
                restaurant_dao.addRestaurantToFoodType(restaurant_2, test_foodtype);
                assertEquals(2, foodtype_dao.getAllRestaurantsByFoodTypeId(test_foodtype.getFoodId()).size());
                restaurant_dao.deleteRestaurant(restaurant_1.getId());
                assertEquals(1, foodtype_dao.getAllRestaurantsByFoodTypeId(test_foodtype.getFoodId()).size());
    }


    /* Helper functions */
    // * FoodType set up
    public FoodType foodtypeSetUp() {
        FoodType dummy_foodtype = new FoodType("Pizza");
        foodtype_dao.addFoodType(dummy_foodtype);
        return dummy_foodtype;
    }
    public FoodType altFoodtypeSetUp() {
        FoodType dummy_foodtype = new FoodType("Barbecue");
        foodtype_dao.addFoodType(dummy_foodtype);
        return dummy_foodtype;
    }
    //  * Full Restaurant set up.
    public Restaurant restaurantSetUp() {
        Restaurant dummy_restaurant = new Restaurant (
                "Gramercy Tavern", 
                "42 E 20th St, New York", 
                "NY 10003", 
                "+1 212-477-0777",
                "https://www.gramercytavern.com/", 
                "events@gramercytavern.com", 
                "https://bit.ly/2Btwb4O"
            );
        restaurant_dao.addRestaurant(dummy_restaurant);
        return dummy_restaurant;
    }

    // * Minimal Restaurant set up
    public Restaurant altRestaurantSetUp(){
        Restaurant alt_restaurant  = new Restaurant (
                " Eleven Madison Park",
                "11 Madison Ave, New York",
                "NY 10010",
                "+1 212-889-0905"
            );
        restaurant_dao.addRestaurant(alt_restaurant);
        return alt_restaurant; 
    }
}