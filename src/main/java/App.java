import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import views.RestaurantViews;

import static spark.Spark.*;
public class App {

    static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {
        
        BasicConfigurator.configure();

        staticFileLocation("public");

        RestaurantViews restaurant_view = new RestaurantViews();
        
        restaurant_view.restaurantRoutes();
    }
}