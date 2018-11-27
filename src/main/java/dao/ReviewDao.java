package dao;

import models.Review;
import java.util.List;


public interface ReviewDao{
    /* Create */
    void  addReview(Review review);

    /* Read */
    List<Review> getAllReviews();
    List<Review> getAllReviewsByRestaurant(int restaurant_id);

    /* Update */
    // Todo: void updateReview()

    /* Delete */
    void deleteReviewById(int review_id);
    void clearAllReviews();

}