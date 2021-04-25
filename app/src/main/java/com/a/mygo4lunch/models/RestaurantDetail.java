package com.a.mygo4lunch.models;

/**
 * Created by Romuald Hounsa on 4/23/21.
 */
public class RestaurantDetail {

    private String address;
    private String phone_number;
    private String name;
    private String photo;
    private double rating;
    private String website;

    public RestaurantDetail(String address, String phone_number, String name, String photo, double rating) {
        this.address = address;
        this.phone_number = phone_number;
        this.name = name;
        this.photo = photo;
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }




}
