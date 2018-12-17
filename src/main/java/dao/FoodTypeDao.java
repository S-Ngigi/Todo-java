package dao;

import models.FoodType;
import models.Restaurant;
import java.util.List;

public interface FoodTypeDao {
    /*  
     * INTERFACES
     * 
     *  Interfaces are a group of methods that different/mutliple classes may
     * inherit from.
     * 
     * We use interfaces to define the "must" functionalities that a class is 
     * expected to implement an obligates us to implement those functionalities.
     * 
     * In my understanding they hold a dev and the app accountable to make sure we
     *  the core functionalities are predefined and are implemented.
     * 
     * They are likened to a "contracts" that devs "sign" to make sure a class
     * implements the defined features.
     * 
     *  DAO
     * 
     * Stands for Data Access Objects . 
     *  Using the initials to try and define that a "dao" is, in my own words
     * think that we create a data access object who's / which's sole purpose is
     * to acts as a bridge between our database and our app. 
     * 
     * These help separate the db logic from the core app logic.
     * */

     /* Create */
     void addFoodType(FoodType foodtype);
    //  Many to many implementation here.
    //  i.e. "This FoodType is now served at this restaurant"
     void addFoodTypeToRestaurant(FoodType foodtype, Restaurant restaurant);

     /* Read */
     List<FoodType> getAllFoodTypes();
     FoodType getFoodTypeById(int foodtype_id);
     List<Restaurant> getAllRestaurantsByFoodTypeId(int foodtype_id);

     // Todo: FoodType getFoodTypeById(int id);

     /* Update */
    
    //  Todo: FoodType updateFoodType(int id);

    /* Delete */
    void deleteFoodTypeById(int id);
    void clearAllFoodTypes();


}