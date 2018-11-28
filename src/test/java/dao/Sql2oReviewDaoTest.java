package dao;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import models.Restaurant;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.sql2o.Connection;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


import static org.junit.Assert.*;

public class Sql2oReviewDaoTest{
    static Logger logger = Logger.getLogger(Sql2oReviewDaoTest.class);

    private Connection connect_test;
    private Sql2oReviewDao review_dao;
    private Sql2oRestaurantDao restaurant_dao;

   /*  @Before
    public void setUp() throws Exception{
        String connection_string = "jdbc:h2:~/yelpish_test.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string,"sa","");
        review_dao = new Sql2oReviewDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);
       connect_test = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connect_test.close();
    } */

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:postgresql://localhost:5432/yelpish_test";
        Sql2o sql2o = new Sql2o(connection_string, "jedi", "test54321");
        review_dao = new Sql2oReviewDao(sql2o);
        String create_review = "CREATE TABLE reviews (" + 
        "id serial PRIMARY KEY NOT NULL," + 
        "content VARCHAR NOT NULL," + 
        "written_by VARCHAR NOT NULL," + 
        "rating INTEGER NOT NULL,"  +
        "restaurant_id INTEGER NOT NULL)";
        connect_test = sql2o.open();
        connect_test.createQuery(create_review).executeUpdate();
    }

    // @After
    // public void tearDown() throws Exception {
    //     String delete_ArticlesQuery = "DROP TABLE reviews;";
    //     connect_test.createQuery(delete_ArticlesQuery).executeUpdate();
    //     // /* connect_test.close();*/
    // }



    @Test
    public void addingReviewSetsIdTest() throws Exception {
        BasicConfigurator.configure();

        /*Review test_review = new Review("Testing", "Test", 1, 1);
        review_dao.addReview(test_review);
        assertEquals(1, test_review.getReviewId());*/
        String connection_string = "jdbc:postgresql://localhost:5432/yelpish_test";
        Sql2o sql2o = new Sql2o(connection_string, "jedi", "test54321");
        review_dao = new Sql2oReviewDao(sql2o);
        Review test_review = new Review("Whaaat is this!", "@$?!!", 1, 77);
        String insert_command = "INSERT INTO reviews (content, written_by,  rating, restaurant_id) VALUES (:content, :written_by,  :rating, :restaurant_id)";
        try(Connection connect_test = sql2o.open()){
            int id = (int) connect_test.createQuery(insert_command, true)
                    .bind(test_review)
                    .executeUpdate()
                    .getKey(Review.class);
            test_review.setReviewId(id);
        } catch (Sql2oException error) {
            System.out.println("CAN YOU SEE ME!!: " + error);
        }

        assertEquals(1, test_review.getReviewId());
    }

    /* Helper functions */
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

}