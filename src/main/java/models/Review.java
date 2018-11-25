package models;

import java.util.Objects;

public class Review {
    private int id;
    private String content;
    private String written_by;
    private int rating;
    private int restaurant_id;  /* One to Many Relationship right here. */

    /* Our Review Constructor */
    public Review(String content, String written_by, int rating, int restaurant_id){
        this.content = content;
        this.written_by = written_by;
        this.rating = rating;
        this.restaurant_id = restaurant_id;
    }

    /* Review getters and Setters */

    public int getReviewId(){
        return id;
    }

    public void setReviewId(int review_id){
        this.id = review_id;
    }

    public String getReviewContent() {
        return content;
    }

    public void setReviewContent(String restaurant_review){
        this.content =  restaurant_review;
    }

    public String getWrittenBy(){
        return written_by;
    }

    public void setWrittenBy(String review_writter){
        this.written_by = review_writter;
    }

    public int getRestaurantRating(){
        return rating;
    }

    public void setRestaurantRating(int reviewers_rating) {
        this.rating = reviewers_rating;
    }

    public int getRestaurantId(){
        return restaurant_id;
    }

    public void setRestaurantId(int restaurant_id){
        this.restaurant_id = restaurant_id;
    }

    @Override
    public boolean equals(Object review_object){
        if (this == review_object) return true;
        if (review_object == null || getClass() != review_object.getClass()) return false;
        /* Type Casting our object to be of type Review */
        Review review_instance = (Review) review_object;
        return id == review_instance.id &&
                     rating == review_instance.rating &&
                     restaurant_id == review_instance.restaurant_id &&
                     Objects.equals(content, review_instance.content) &&
                     Objects.equals(written_by, review_instance.written_by);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, content, written_by, rating, restaurant_id);
    }

}