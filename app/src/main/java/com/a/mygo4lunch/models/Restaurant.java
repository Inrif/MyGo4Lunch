package com.a.mygo4lunch.models;

/**
 * Created by Romuald Hounsa on 4/9/21.
 */
public class Restaurant  {

    private Location mLocation;
    private String name;
    private String placeId;
    private String address;
    private Boolean hour;
    private String photoReference;
    private int distance;
    private int user;
    private double rating;
    private boolean choice;

    public Restaurant(com.a.mygo4lunch.models.Location location, String name, String placeId, String address, Boolean hour, String photoReference, int distance, int user, double rating, boolean choice) {
        mLocation = location;
        this.name = name;
        this.placeId = placeId;
        this.address = address;
        this.hour = hour;
        this.photoReference = photoReference;
        this.distance = distance;
        this.user = user;
        this.rating = rating;
        this.choice = choice;
    }
    public com.a.mygo4lunch.models.Location getLocation() {
        return mLocation;
    }

    public void setLocation(com.a.mygo4lunch.models.Location location) {
        mLocation = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getHour() {
        return hour;
    }

    public void setHour(Boolean hour) {
        this.hour = hour;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }







        }
