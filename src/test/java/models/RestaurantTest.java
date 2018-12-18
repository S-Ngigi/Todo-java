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

    /* Testing setName method */
    @Test
    public void getCorrectRestaurantName() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("Gramercy Tavern", test_restaurant.getName());
    }

    /* Testing setName method */
    @Test
    public void setNameTest() throws Exception {
        Restaurant test_restaurant = restaurantAltSetUp();
        test_restaurant.setName(" Eleven Madison Park" );
        assertNotEquals("Gramercy Tavern",  test_restaurant.getName());
    }

    /* Testing the getAddress method */
    @Test
    public void getAddressTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("42 E 20th St, New York", test_restaurant.getAddress());
    }

    /* Testing the setAddress Method */
    @Test
    public void setAddressTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setAddress("11 Madison Ave, New York");
        assertNotEquals("42 E 20th St, New York", test_restaurant.getAddress());
    }

    /* Testing the getZipcode method */
    @Test
    public void getZipcodeTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("NY 10003",  test_restaurant.getZipcode());
    }

    /* Testing the getZipcode method */
    @Test
    public void setZipcodeTest(){
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setZipcode(" NY 10010");
        assertNotEquals("NY 10003", test_restaurant.getZipcode());
    }

    /* Testing getPhone */
    @Test
    public void getPhoneTest() throws Exception{
        Restaurant test_restaurant = restaurantSetUp();
        assertEquals("+1 212-477-0777", test_restaurant.getPhone());
    }

    /* Testing setPhone  */
    @Test
    public void setPhoneTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        test_restaurant.setPhone("+1 212-889-0905");
        assertNotEquals("+1 212-477-0777" , test_restaurant.getPhone());
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
    public void getEmailTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        assertEquals("No email available", test_restaurant.getEmail());
    }

    /* Testing setEmail */
    @Test
    public void setEmailTest() throws Exception{
        Restaurant test_restaurant  = restaurantAltSetUp();
        test_restaurant.setEmail("info@elevenmadisonpark.com");
        assertNotEquals("No email availabel", test_restaurant.getEmail());
    }

    @Test
    public void getImageTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        assertEquals("No image available", test_restaurant.getImg_url());
    }

    @Test
    public void setImageTest() throws Exception{
        Restaurant test_restaurant = restaurantAltSetUp();
        test_restaurant.setImg_url("https://bit.ly/2KwknSj");
        assertNotEquals( "https://bit.ly/2Btwb4O", test_restaurant.getImg_url());
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
        Restaurant alt_restaurant = new Restaurant(
            "Gramercy Tavern",
            "42 E 20th St, New York",
            "NY 10003",
            "+1 212-477-0777");
        return alt_restaurant;
    }
}