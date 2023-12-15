package com.example.fooddeliveryapp;

public class Product {
    private int Restaurant_id;
    private String Restaurant_name;
    private String Restaurant_address;
    private String food_name;
    private String food_type;//eg: pizza ,burger,sandwich etc;

    private String food_rating;
    private String food_price;
    private String image_id;

    public Product(int restaurant_id, String restaurant_name, String restaurant_address,String food_type, String food_name,String food_rating, String food_price, String imageid) {
        this.Restaurant_id = restaurant_id;
        this.Restaurant_name = restaurant_name;
        this.Restaurant_address = restaurant_address;
        this.food_type=food_type;
        this.food_name = food_name;
        this.food_rating = food_rating;
        this.food_price = food_price;
        this.image_id=imageid;
    }

 public Product(String restaurant_name, String restaurant_address, String food_type,String food_name, String food_rating, String food_price,String imageid) {

        this.Restaurant_name = restaurant_name;
        this.Restaurant_address = restaurant_address;
        this.food_type=food_type;
        this.food_name = food_name;
        this.food_rating = food_rating;
        this.food_price = food_price;
        this.image_id=imageid;


    }



    public int getRestaurant_id() {
        return Restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        Restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return this.Restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.Restaurant_name = restaurant_name;
    }

    public String getRestaurant_address() {
        return this.Restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.Restaurant_address = restaurant_address;
    }

    public String getFood_name() {
        return this.food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_rating() {
        return this.food_rating;
    }

    public void setFood_rating(String food_rating) {
        this.food_rating = food_rating;
    }

    public String getFood_price() {
        return this.food_price;
    }

    public void setFood_price(String food_price) {
        this.food_price = food_price;
    }

    public String getImage_id() {
        return this.image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }
}
