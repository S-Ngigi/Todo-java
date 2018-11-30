package dao;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
/* import java.util.List; */

public class Sql2oReviewDao implements ReviewDao {
    private final Sql2o sql2o;
    public Sql2oReviewDao (Sql2o sql2o) {this.sql2o=sql2o;}

    @Override
    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (content, written_by, rating, restaurant_id) VALUES (:content, :written_by, :rating, :restaurant_id)";

        try(Connection connect = sql2o.open()) {
            int id = (int) connect.createQuery(sql, true)
            .bind(review).
            executeUpdate()
            .getKey();

            review.setReviewId(id);
        /* 568209 */
        } catch (Sql2oException error){
            System.out.println(error);
        }
    }
}