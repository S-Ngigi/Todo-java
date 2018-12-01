package models;

import java.util.Objects;

public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String zipcode;
    private String phone;
    private String website;
    private String email;
    private String img_url;

    /*  
    *  Here we are CONSTRUCTOR OVERLOADING. Here is were we
    * create a first constructor with the base attributes that we
    *  require.
    *
    * Beneath it we then create another constructor that takes in 
    * additional attributes that some restaurant do not have.
    *
    * This allows us to handle restaurants that may not have a 
    * website, email or image.
    *
    * The amount and type of arguments a method including a
    * constructor takes are called its METHOD SIGNATURE.
    *
    */
    public Restaurant(String name, String address, String zipcode, String phone) {
        this.name=name;
        this.address=address;
        this.zipcode=zipcode;
        this.phone=phone;
        this.website = "No website listed";
        this.email = "No email available";
        this.img_url = "No image available";
    }

    public Restaurant(
        String name, String address, String zipcode,
        String phone, String website, 
         String email, String img_url) {

        this.name = name;
        this.address = address;
        this.zipcode = zipcode;
        this.phone = phone;
        this.website = website;
        this.email = email;
        this.img_url= img_url;
    }

    /* Getters and Setters */

    public int getId(){
        return id;
    }

    public void setId(int restaurant_id){
        this.id = restaurant_id;
    }

    public String getName(){
        return name;
    }

    public void setName(String restaurant_name){
        this.name = restaurant_name;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String restaurant_address){
        this.address = restaurant_address;
    }

    public String getZipcode(){
        return zipcode;
    }

    public void setZipcode(String restaurant_zipcode){
        this.zipcode = restaurant_zipcode;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String restaurant_phone){
        this.phone = restaurant_phone;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite(String restaurant_website){
        this.website = restaurant_website;
    }
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String restaurant_email){
        this.email = restaurant_email;
    }

    public String getImg_url(){
        return img_url;
    }

    public void setImg_url(String restaurant_img){
        this.img_url = restaurant_img;
    }

    /*  
     * The methods below are to make sure that we can make 
     * accurate comparisons of objects when storing and
     * retrieving them from our databases.
     * 
     * We use the inbuilt Objects.equals() method by passing in
     * our an our restaurant_object as Object then we compare a
     *  current instance with the restaurant.
     * 
     * We also check if  our arg is null or our this instance and
     * our arg belong to the same class.
     * 
     * We then type cast our Object as a Restaurant class whereby
     * we check if our property attribute values match.
     * 
     * Note we are using Object.equals() here similar to when comparing string
     * objects. This is because we are comparing String values.
     * 
     * We finally also check the equality of our Restaurant
     * instance on a memory-based level to see if they match.
     *  
     *  Note the @Override annotation(??) allows us to tweak an
     * existing stock method to our liking.
     * */

    @Override
    public boolean equals(Object restaurant_object){
        if  (this == restaurant_object) return true;
        if (restaurant_object == null || getClass() != restaurant_object.getClass()) return false;
        Restaurant restaurant_instance = (Restaurant) restaurant_object;
        return id == restaurant_instance.id &&
                                Objects.equals(name, restaurant_instance.name) &&
                                Objects.equals(address, restaurant_instance.address) &&
                                Objects.equals(zipcode, restaurant_instance.zipcode) &&
                                Objects.equals(phone, restaurant_instance.phone) &&
                                Objects.equals(website, restaurant_instance.website) &&
                                Objects.equals(email, restaurant_instance.email) &&
                                Objects.equals(img_url, restaurant_instance.img_url);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, address, zipcode, phone, website, email, id, img_url);
    }

}