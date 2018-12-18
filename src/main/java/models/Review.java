package models;

import java.util.Objects;

import java.text.SimpleDateFormat;
// import java.time.LocalDateTime;
// import java.util.Calendar;
import java.lang.Comparable;
import java.util.Date;

public class Review  implements Comparable<Review>{
    private int id;
    private String content;
    private String written_by;
    private int rating;
    private int restaurant_id;   // One to Many Relationship right here.
    private long created_at;
    private String formatted_created_at;

    /* Our Review Constructor */
    public Review(String content , String written_by, int rating, int restaurant_id){

        this.content = content;
        this.written_by = written_by;
        this.rating = rating;
        this.restaurant_id = restaurant_id;
        this.created_at = System.currentTimeMillis();
        setFormatted_created_at();
    }

    /* Review getters and Setters */

    public int getReviewId(){
        return id;
    }

    public void setReviewId(int review_id){
        this.id = review_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String restaurant_review){
        this.content =  restaurant_review;
    }

    public String getWritten_by(){
        return written_by;
    }

    public void setWritten_by(String review_writer){
        this.written_by = review_writer;
    }

    public int getRating(){
        return rating;
    }

    public void setRating(int reviewers_rating) {
        this.rating = reviewers_rating;
    }

    public int getRestaurant_id(){
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id){
        this.restaurant_id = restaurant_id;
    }

    // * TIME: getters & setters
    public long getCreated_at(){
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = System.currentTimeMillis();
    }

    public String getFormatted_created_at() {
        System.out.println(formatted_created_at);
        // * Making a date object out of the created at milliseconds
        Date date = new Date(created_at);
        // * Defining our pattern
        String date_pattern = "MM/dd/yyyy @ K:mm a";
        // *Instantiating a new simple date format with our desired pattern
        SimpleDateFormat sdf = new SimpleDateFormat(date_pattern);
        // * Formatting our date object using our pattern
        return sdf.format(date);
    }

    public void setFormatted_created_at(){
        // *Takes the current time we want to set created_at in seconds
        Date date = new Date(this.created_at);
        // * Defining the pattern
        String date_pattern = "MM/dd/yyyy @ K:mm a";
        // * new simple date format with our date_pattern
        SimpleDateFormat sdf = new SimpleDateFormat(date_pattern);
        // * Setting formatted_created_at to new formatted date.
        this.formatted_created_at = sdf.format(date);
    }

    // *TIME sort using COMPARATOR
    @Override
    public int compareTo(Review review_object) {
        if(this.created_at < review_object.created_at){
            // * Meaning "this" object was created earlier  than the rest.
            return -1;
        }
        else if (this.created_at > review_object.created_at) {
            // *Meaning that our "review_object" was created earlier 
            return 1;
        }
        else {
            // * Which means both instances were created at the same time
            return 0;
        }
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