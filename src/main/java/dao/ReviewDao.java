package dao;

import java.util.List;

import models.Review;

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