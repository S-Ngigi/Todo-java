package models;

import java.util.Objects;

public class FoodType {
    private int id;
    private String name;

    public FoodType (String name) {
        this.name = name;
    }

    public int getFoodId() { return id; }

    public void setFoodId(int food_id) { this.id = food_id; }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    @Override
    public boolean equals(Object foodtype_object){
        if (this == foodtype_object) return true;
        if( foodtype_object == null || getClass() != foodtype_object.getClass()) return false;

        FoodType foodtype_instance = (FoodType) foodtype_object;
        return id == foodtype_instance.id &&
                     Objects.equals(name, foodtype_instance.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }    
}