package dao;

import dao.Sql2oReviewDao;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest {
    private Connection connect_test;
    private Sql2oReviewDao review_dao;

    @Before
    public void setUp() throws Exception {
        String connection_string = "jdbc:h2:mem:test;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connection_string, "", "");
        review_dao = new Sql2oReviewDao(sql2o);
        connect_test = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        connect_test .close();
    }

    public Review reviewSetUp() {
        /*Review dummy_review = new Review("Testing testing", "Test", 4, 1);*/
        review_dao.add( new Review("Testing testing", "Test", 4, 1));
        return new Review("This is a test", "Test", 4, 1);
    }

    @Test
    public void addingReviewSetsIdTest() throws Exception {
        Review test_review = reviewSetUp();
        assertEquals(1, test_review.getReviewId());
    }

}