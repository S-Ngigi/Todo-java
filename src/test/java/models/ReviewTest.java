package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest{
    @Before
    public void setUp() throws Exception{}

    @After
    public void tearDown() throws Exception{}

    @Test
    public void setReviewIdTest() throws Exception {
        Review test_review = reviewSetUp();
        test_review.setReviewId(7);
        assertEquals(7, test_review.getReviewId());
    }

    /* Testing Review's getReview method */
    @Test
    public void getRestaurantReviewContentTest(){
        Review test_review = reviewSetUp();
        assertEquals("Fancy and Affordable", test_review.getReviewContent()); 
    }

    /* Testing Review's setReview method */
    @Test
    public void setRestaurantReviewContentTest(){
        Review test_review = reviewSetUp();
        test_review.setReviewContent("Tis Bliss");
        assertNotEquals("Fancy and Affordable", test_review.getReviewContent());
    }

    /* Testing getReviewWrittenBy method */
    @Test
    public void getReviewWrittenByTest() throws Exception{
        Review test_review = reviewSetUp();
        assertEquals("Phill", test_review.getWrittenBy());
    }

    /* Testing setWrittenby */
    @Test
    public void setReviewWrittenByTest() throws Exception {
        Review test_review = reviewSetUp();
        test_review.setWrittenBy("Mr Damani");
        assertNotEquals("Phill", test_review.getWrittenBy());
    }
    
    /* Testing getRestaurantRating */
    @Test
    public void getRestaurantRatingTest() throws Exception {
        Review  test_review = reviewSetUp();
        assertEquals(4, test_review.getRestaurantRating());
    }

    /* TestinZg setRestaurantRating */
    @Test
    public void seRestauranttRatingTest() throws Exception{
        Review test_review = reviewSetUp();
        test_review.setRestaurantRating(5);
        assertNotEquals(4, test_review.getRestaurantRating());
    }

    /* Testing getRestaurantId method */
    @Test
    public void getRestaurantIdTest() throws Exception{
        Review test_review = reviewSetUp();
        assertEquals(1, test_review.getRestaurantId());
    }

    @Test
    public void setRestaurantIdTest() throws Exception{
        Review test_review = reviewSetUp();
        test_review.setRestaurantId(2);
        assertNotEquals(1,  test_review.getRestaurantId());
    }

    public Review reviewSetUp() {
        Review dummy_review = new Review("Fancy and Affordable", "Phill", 4, 1);
        return dummy_review;
    }
    

}