package dao;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import models.Review;

public class Sql2oReviewDao implements ReviewDao {

    /* We create a Sql2o constant. */
    private final Sql2o sql2o;
    // ? Sql2oReviewDao Constructor? I think so.
    public Sql2oReviewDao(Sql2o sql2o){this.sql2o = sql2o;}

    /* Adding the Review Object to out database. */
    @Override
    public void addReview(Review review){
        //  ! If we change our model we will need to change values here as well.
        /* The sql command */
        String sql_command = "INSERT INTO reviews (content, written_by, rating,"+
                                                    " restaurant_id) VALUES (:content, :written_by,"+
                                                    " rating, :restaurant_id)";
        /* Connecting to our Db */
        try  (Connection connect = sql2o.open()){
        /* Persisting our review object to the db ad returning the generated id */
                int review_id = (int) connect.createQuery(sql_command, true)
                                                        .bind(review)
                                                        .executeUpdate()
                                                        .getKey();
                /* Setting the generated pk as our review object's id. */
                review.setReviewId(review_id);
        } catch (Sql2oException error) {
            /* Catch any error and print to terminal */
            System.out.println("ERROR IN ADDING REVIEW TO DB: " + error);
        }
    }

    @Override
    public List<Review> getAllReviews(){
        try(Connection connect = sql2o.open()){
            return connect.createQuery("SELECT * FROM reivews")
                                         .executeAndFetch(Review.class);
        }
    }

    @Override
    public List<Review> getAllReviewsByRestaurant(int restaurant_id) {
        try(Connection connect = sql2o.open()) {
            return connect.createQuery(
                "SELECT * FROM reviews WHERE restaurant_id = :restaurant_id"
                ).addParameter("restaurant_id", restaurant_id)
                 .executeAndFetch(Review.class);
        }
    }

    @Override
    public void deleteReviewById(int review_id) {
        try (Connection connect = sql2o.open()){
                connect.createQuery(
                    "DELETE FROM reviews WHERE id = :review_id"
                    ).addParameter("review_id", review_id)
                     .executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR IN DELETING A REVIEW FROM DB: " +  error);
        }
    }

    @Override
    public void clearAllReviews(){
        try(Connection connect = sql2o.open()) {
            connect.createQuery("DELETE FROM reviews").executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR IN DELETING ALL REVIEWS FROM DB" + error);
        }
    }

}