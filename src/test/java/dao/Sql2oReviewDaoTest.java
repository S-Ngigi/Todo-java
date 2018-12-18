package dao;

import models.Restaurant;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.sql2o.Connection;
import org.junit.Test;
import org.sql2o.Sql2o;


import static org.junit.Assert.*;

public class Sql2oReviewDaoTest{

    private Connection connect_test;
    private Sql2oReviewDao review_dao;
    private Sql2oRestaurantDao restaurant_dao;

     @Before
    public void setUp() throws Exception{
        String connection_string =  "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string,"sa","");
        review_dao = new Sql2oReviewDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
       connect_test = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connect_test.close();
    }


    @Test
    public void addingReviewSetsIdTest() throws Exception {
        Review test_review = reviewSetUp();
        assertEquals(1, test_review.getReviewId());
    }

    @Test
    public void getAllReviewTest() throws Exception {
         Review one = reviewSetUp();
         Review two = reviewSetUp();
         System.out.println(one.getContent() + two.getContent());
         assertEquals(2, review_dao.getAllReviews().size());
    }

    @Test
    public void getReviewByRestaurantTest() throws Exception {
            Restaurant test_restaurant = restaurantSetUp();
            Review review_one = reviewByRestaurantSetUp(test_restaurant);
            Review review_two = reviewByRestaurantSetUp(test_restaurant);
            System.out.println(review_one.getContent() +" "+ review_two.getContent());
            assertNotEquals(0, test_restaurant.getId());
            assertEquals(2, review_dao.getAllReviewsForRestaurant(test_restaurant.getId()).size());
    }

    @Test
    public void deletingReviewByIdTest() throws Exception {
        Review test_review = reviewSetUp();
        Review another_review = reviewSetUp();
        System.out.println(another_review.getContent());
        review_dao.deleteByReviewId(test_review.getReviewId());
        assertEquals(1, review_dao.getAllReviews().size());
    }

    @Test
    public void clearAllReviewTest() throws Exception {
        Review review_one = reviewSetUp();
        Review review_two = reviewSetUp();
        System.out.println(review_one.getReviewId() + " " + review_two.getReviewId());
        review_dao.clearAllReviews();
        assertEquals(0, review_dao.getAllReviews().size() );
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        Review test_review = reviewSetUp();

        long creation_time = test_review.getCreated_at();
        long db_creation_time = review_dao.getAllReviews().get(0).getCreated_at();

        String formatted_creation_time = test_review.getFormatted_created_at();
        String formatted_db_creation_time = review_dao.getAllReviews().get(0).getFormatted_created_at(); 

        assertEquals(creation_time, db_creation_time);
        assertEquals(formatted_creation_time, formatted_db_creation_time);
    }

    @Test
    public void reviewsSortedByCreatedAtCorrectly() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();
        Review test_review = new Review("Dope manenos", "DevOps", 3, test_restaurant.getId());
        review_dao.addReview(test_review);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException error) {
            error.printStackTrace();
        }

        Review second_review = new Review("Mambo Mbaya", "That Guy", 4, test_restaurant.getId());
        review_dao.addReview(second_review);

        assertEquals(
            "Mambo Mbaya", review_dao.getAllReviewsByRestaurantIdSortedNewestToOldest(test_review.getRestaurant_id()).get(0).getContent()
        );
    }

    @Test
    public void altSortedReviewTest() throws Exception {
        Restaurant test_restaurant = restaurantSetUp();

        //  * Adding 4 review objects and simulated time delay in adding them.
        Review review_1 = reviewByRestaurantSetUp(test_restaurant);

        System.out.println(review_1.getRestaurant_id());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }

        Review review_2 = new Review("Not Bad", "Him", 3, test_restaurant.getId());
        review_dao.addReview(review_2);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }

        Review review_3 = new Review("Dope manenos", "DevOps", 3, test_restaurant.getId());
        review_dao.addReview(review_3);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }

        Review review_4 = new Review("Mambo Mbaya", "That Guy", 4, test_restaurant.getId());
        review_dao.addReview(review_4);

        assertEquals(4, review_dao.getAllReviewsForRestaurant(test_restaurant.getId()).size());

        assertEquals(
            "Mambo Mbaya", 
            review_dao.getAllReviewsByRestaurantIdSortedNewestToOldest(test_restaurant.getId()).get(0).getContent()
        );
    }

     /*Helper functions */
    public Review reviewSetUp () {
        Review dummy_review = new Review("Fancy and Affordable", "Phill", 4, 1);
        review_dao.addReview(dummy_review);
        return dummy_review;
    }

    public Restaurant restaurantSetUp(){
        Restaurant dummy_restaurant = new  Restaurant (
                "Eleven Madison Park",
                "11 Madison Ave, New York",
                "NY 10010",
                "+1 212-889-0905",
                "elevenmadisonpark.com",
                "info@elevenmadisonpark.com",
                "https://bit.ly/2KwknSj"
        );
        restaurant_dao.addRestaurant(dummy_restaurant);
        return dummy_restaurant;
    }

    public Review reviewByRestaurantSetUp(Restaurant restaurant) {
        Review dummy_review = new Review("Best duck breast ever", "Phill", 4, restaurant.getId());
        review_dao.addReview(dummy_review);
        return dummy_review;
    }

}