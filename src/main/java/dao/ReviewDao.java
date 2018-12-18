package dao;

import models.Review;
import java.util.List;

public interface ReviewDao {
    /* Create */
    void addReview(Review review);

    /* Read */
    List<Review> getAllReviews();
    List<Review> getAllReviewsForRestaurant(int restaurant_id);
    List<Review> getAllReviewsByRestaurantIdSortedNewestToOldest(int restaurant_id);
   
    /*  Todo Update */

    /* Delete */
    void deleteByReviewId(int review_id);
    void clearAllReviews();
}