package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /* Testing getRestaurantName method */
    @Test
    public void getCorrectRestaurantName() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("Gramercy Tavern", test_restaurant.getRestaurantName());
    }

    /* Testing setRestaurantName method */
    @Test
    public void setRestaurantNameTest() throws Exception {
        Restaurant test_restaurant = restaurantAltSetUp();
        test_restaurant.setRestaurantName(" Eleven Madison Park" );
        assertNotEquals("Gramercy Tavern",  test_restaurant.getRestaurantName());
    }

    /* Testing the getRestaurantAddress method */
    @Test
    public void getRestaurantAddressTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("42 E 20th St, New York", test_restaurant.getRestaurantAddress());
    }

    /* Testing the setRestaurantAddress Method */
    @Test
    public void setRestaurantAddressTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setRestaurantAddress("11 Madison Ave, New York");
        assertNotEquals("42 E 20th St, New York", test_restaurant.getRestaurantAddress());
    }

    /* Testing the getRestaurantZipcode method */
    @Test
    public void getRestaurantZipcodeTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("NY 10003",  test_restaurant.getRestaurantZipcode());
    }

    /* Testing the setRestaurantZipcode method */
    @Test
    public void setRestaurantZipcodeTest(){
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setRestaurantZipcode(" NY 10010");
        assertNotEquals("NY 10003", test_restaurant.getRestaurantZipcode());
    }

    /* Testing getRestaurantPhone */
    @Test
    public void getRestaurantPhoneTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("+1 212-477-0777", test_restaurant.getRestaurantPhone());
    }

    /* Testing setRestaurantPhone  */
    @Test
    public void setRestaurantPhoneTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setRestaurantPhone("+1 212-889-0905");
        assertNotEquals("+1 212-477-0777" , test_restaurant.getRestaurantPhone());
    }

    /* Testing getWebsite  */
    @Test 
    public void getWebsiteTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("https://www.gramercytavern.com/", test_restaurant.getWebsite());
    }

    /* Testing setWebsite method */
    @Test
    public void setWebsiteTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setWebsite("elevenmadisonpark.com");
        assertNotEquals("https://www.gramercytavern.com/", test_restaurant.getWebsite());
    }

    /* Testing getRestaurant  email*/
    @Test
    public void getRestaurantEmailTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        assertEquals("No email available", test_restaurant.getRestaurantEmail());
    }

    /* Testing setRestaurantEmail */
    @Test
    public void setRestaurantEmailTest() throws Exception{
        Restaurant test_restaurant  = restaurantAltSetUp();
        test_restaurant.setRestaurantEmail("info@elevenmadisonpark.com");
        assertNotEquals("No email availabel", test_restaurant.getRestaurantEmail());
    }

    @Test
    public void getRestaurantImageTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        assertEquals("No image available", test_restaurant.getRestaurantImage());
    }

    @Test
    public void setRestaurantImageTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        test_restaurant.setRestaurantImage("https://bit.ly/2KwknSj");
        assertNotEquals( "https://bit.ly/2Btwb4O", test_restaurant.getRestaurantImage());
    }

    public Restaurant restaurantSetUp(){
        Restaurant dummy_restaurant = new Restaurant(
            "Gramercy Tavern",
            "42 E 20th St, New York",
            "NY 10003",
            "+1 212-477-0777",
            "https://www.gramercytavern.com/",
            "events@gramercytavern.com",
            "https://bit.ly/2Btwb4O"
        );

        return dummy_restaurant;
    }

    public Restaurant restaurantAltSetUp(){
        Restaurant brief_restaurant = new Restaurant(
            "Gramercy Tavern",
            "42 E 20th St, New York",
            "NY 10003",
            "+1 212-477-0777");
        return brief_restaurant;
    }
}