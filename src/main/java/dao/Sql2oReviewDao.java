package dao;

import java.util.ArrayList;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class Sql2oReviewDao implements ReviewDao {
    private final Sql2o sql2o;
    public Sql2oReviewDao (Sql2o sql2o) {this.sql2o=sql2o;}

    /*
     * Method adds a Review instance to the database and sets the generated
     * primary key as the review's id
     * */
    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (content, written_by, rating, restaurant_id, created_at) VALUES (:content, :written_by, :rating, :restaurant_id, :created_at)";

        try(Connection connect = sql2o.open()) {
            int id = (int) connect.createQuery(sql, true)
            .bind(review).
            executeUpdate()
            .getKey();

            review.setReviewId(id);
        /* 568209 */
        } catch (Sql2oException error){
            System.out.println("ERROR WHEN ADDING REVIEW TO DB: " + error);
        }
    }

    /*
     * This returns all the review instances that have been added to the
     * db
     * */
    @Override
    public List<Review> getAllReviews() {
        try (Connection connect = sql2o.open()) {
            return connect.createQuery("SELECT * FROM reviews")
                          .executeAndFetch(Review.class);
        }
    }

    /*  
     * This returns all the reviews for a particular restaurant.
     * */
    @Override
    public List<Review> getAllReviewsForRestaurant(int restaurant_id) {
        try(Connection connect = sql2o.open()){
            return  connect.createQuery(
               " SELECT * FROM reviews WHERE restaurant_id = :restaurant_id"
            ).addParameter("restaurant_id", restaurant_id)
             .executeAndFetch(Review.class);
        }
    }

    // * SORT from New to Old
    public List<Review> getAllReviewsByRestaurantIdSortedNewestToOldest(int restaurant_id) {
        List<Review> unsorted_reviews = getAllReviewsForRestaurant(restaurant_id);
        List<Review> sorted_review = new ArrayList<>();

        int index = 1;

        for (Review rev : unsorted_reviews){
            // * This if statement is to deal with arrayIndex exception and handle the last element properly
            if(index == unsorted_reviews.size()){
                if(rev.compareTo(unsorted_reviews.get(index-1)) == -1) {
                    sorted_review.add(0, unsorted_reviews.get(index-1));
                }
                break;
            }
            else {
                if(rev.compareTo(unsorted_reviews.get(index)) == -1 ) {
                    sorted_review.add(0, unsorted_reviews.get(index));
                    index++;
                }
                else if (rev.compareTo(unsorted_reviews.get(index)) == 0) {
                    sorted_review.add(0, unsorted_reviews.get(index));
                    index++;
                }
                else {
                    sorted_review.add(0, unsorted_reviews.get(index));
                    index++;
                }
            }
        }

        return sorted_review;
    }

    /*  
     * This deletes a single review based on the review_id
     * */
    @Override
    public void deleteByReviewId(int review_id) {
        try(Connection connect = sql2o.open()) {
            connect.createQuery(
                "DELETE FROM reviews WHERE id=:review_id"
                ).addParameter("review_id", review_id)
                 .executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR IN DELETING REVIEW FROM DB: " + error);
        }
    }

    @Override
    public void clearAllReviews() {
        try(Connection connect = sql2o.open()) {
            connect.createQuery("DELETE FROM reviews").executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR IN CLEARING ALL REVIEWS: " + error);
        }
    }
}