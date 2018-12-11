package dao;

import models.FoodType;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oFoodTypeDao implements FoodTypeDao {
    
    private final Sql2o sql2o;

    public  Sql2oFoodTypeDao (Sql2o sql2o) {   this.sql2o = sql2o; }

    /* Adding foodtype to db and setting the id(pk) */
    @Override
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

    /* Getting all the foodtypes that were committed to the database */
    @Override
    public List<FoodType> getAllFoodTypes() {
        try(Connection connect = sql2o.open()){
            return connect.createQuery("SELECT * FROM foodtypes")
                                         .executeAndFetch(FoodType.class);
        }
    }

    /* Deleting a foodtype by ID */
    @Override
    public void deleteFoodTypeById(int foodtype_id) {
        try(Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM foodtypes WHERE id = :foodtype_id")
                            .addParameter("foodtype_id", foodtype_id)
                            .executeUpdate();
        } catch (Sql2oException error) {
            System.out.println("ERROR WHEN DELETING RESTAURANT FROM DB: " + error);
        }
    }

    /* Deleing all FoodType instances */
    @Override
    public void clearAllFoodtypes(){
        try(Connection connect = sql2o.open()){
            connect.createQuery("DELETE FROM foodtypes")
                            .executeUpdate();
        } catch(Sql2oException error) {
            System.out.println("ERROR WHEN DELETING ALL FOODTYPES FROM DB: " + error);
        }
    }
}