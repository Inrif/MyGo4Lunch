package com.a.mygo4lunch.models;

import androidx.annotation.Nullable;

/**
 * Created by Romuald Hounsa on 4/9/21.
 */
 public class User {
    private String uid;
    private String username;
    private String restaurantName;
    private String placeId;
    @Nullable
    private String urlPicture;

    public User(String uid, String username, String restaurantName, String placeId, @Nullable String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.restaurantName = restaurantName;
        this.placeId = placeId;
        this.urlPicture = urlPicture;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }





}
