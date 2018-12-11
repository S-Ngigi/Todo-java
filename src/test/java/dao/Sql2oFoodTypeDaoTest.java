package dao;

import models.FoodType;

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

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        sql2o = new Sql2o(connection_string, "", "");
        foodtype_dao = new Sql2oFoodTypeDao(sql2o);
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

    @Test
    public void getAllFoodTypesTest() throws Exception {
        FoodType foodtype_1 = foodtypeSetUp();
        FoodType foodtype_2 = foodtypeSetUp();
        assertNotEquals(foodtype_1.getFoodId(), foodtype_2.getFoodId());
        assertEquals(2, foodtype_dao.getAllFoodTypes().size());
    }


    /* Helper functions */
    public FoodType foodtypeSetUp() {
        FoodType dummy_foodtype = new FoodType("Pizza");
        foodtype_dao.addFoodType(dummy_foodtype);
        return dummy_foodtype;
    }
}