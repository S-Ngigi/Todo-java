package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodTypeTest {

    @Before
    public void setUp() throws Exception{}

    @After
    public void tearDown() throws Exception{}

    /* Testing getName method */
    @Test
    public void getFoodtypeTest() throws Exception {
        FoodType test_foodtype = foodtypeSetUp();
        assertEquals("Dessert", test_foodtype.getName());
    }

    /* Testing setFoodType  working */
    @Test
    public void setFoodtypeTest() throws Exception {
        FoodType test_foodtype = foodtypeSetUp();
        test_foodtype.setName("Starter");
        assertNotEquals("Dessert", test_foodtype.getName());
    }

    public FoodType foodtypeSetUp() {
        FoodType dummy_foodtype = new FoodType("Dessert");
        return dummy_foodtype;
    }

}