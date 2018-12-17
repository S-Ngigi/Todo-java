import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import views.RestaurantViews;
import views.FoodTypeView;
import views.ReviewView;

import static spark.Spark.*;
public class App {

    static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {
        
        BasicConfigurator.configure();

        staticFileLocation("public");

        RestaurantViews restaurant_view = new RestaurantViews();
        FoodTypeView foodtype_view = new FoodTypeView();
        ReviewView review_view = new ReviewView();
        
        restaurant_view.getRestaurantRoutes();
        foodtype_view.getFoodtypeRoutes();
        review_view.getReviewRoutes();

    }
}