package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import models.Restaurant;
import models.Review;

import static org.junit.Assert.*;

public class Sql2oReviewDaoTest{

    private Connection connecting;
    private Sql2oReviewDao review_dao;
    private Sql2oRestaurantDao restaurant_dao;

    @Before
    public void setUp() throws Exception{
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from classpath:db/create.sql";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        review_dao = new Sql2oReviewDao(sql2o);
        restaurant_dao = new Sql2oRestaurantDao(sql2o);

    }
}