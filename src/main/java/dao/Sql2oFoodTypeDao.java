package dao;

import models.FoodType;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

/* import java.util.List; */

public class Sql2oFoodTypeDao implements FoodTypeDao {
    
    private final Sql2o sql2o;

    public  Sql2oFoodTypeDao (Sql2o sql2o) {   this.sql2o = sql2o; }

    public void addFoodType(FoodType foodtype){
        String sql_command = "INSERT INTO foodtypes (name) VALUES (:name)";
        try(Connection connect = sql2o.open()) {
            int foodtype_id = (int) connect.createQuery(sql_command, true)
                                                .bind(foodtype)
                                                .executeUpdate()
                                                .getKey();
            foodtype.setFoodId(foodtype_id);
        } catch (Sql2oException error) {
            System.out.println("ERROR ADDING FOODTYPE TO DB: " + error);
        }
    }

}