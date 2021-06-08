
package com.a.mygo4lunch.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PlaceDetailsResult implements java.io.Serializable {

    @com.google.gson.annotations.SerializedName("name")
    private String mName;
    @com.google.gson.annotations.SerializedName("opening_hours")
    private OpeningHours mOpeningHours;
    @com.google.gson.annotations.SerializedName("photos")
    private java.util.List<com.a.mygo4lunch.models.Photo> mPhotos;
    @com.google.gson.annotations.SerializedName("rating")
    private Double mRating;
    @com.google.gson.annotations.SerializedName("vicinity")
    private String mVicinity;
    @com.google.gson.annotations.SerializedName("formatted_phone_number")
    private String mFormattedPhoneNumber;
    @com.google.gson.annotations.SerializedName("geometry")
    private Geometry mGeometry;
    @com.google.gson.annotations.SerializedName("website")
    private String mWebsite;
    @com.google.gson.annotations.SerializedName("place_id")
    private String mPlaceId;
    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    @com.google.gson.annotations.SerializedName("reference")
    private String mReference;

//    public PlaceDetailsResult(String name, String vicinity, String placeId) {
//       this.mName = name;
//       this.mVicinity = vicinity;
//       this.mPlaceId = placeId;
//
//
//
//    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public OpeningHours getOpeningHours() {
        return mOpeningHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        mOpeningHours = openingHours;
    }

    public java.util.List<com.a.mygo4lunch.models.Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(java.util.List<com.a.mygo4lunch.models.Photo> photos) {
        mPhotos = photos;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        mVicinity = vicinity;
    }

    public String getFormattedPhoneNumber() {
        return mFormattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        mFormattedPhoneNumber = formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        mGeometry = geometry;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId (String placeId) {
        mPlaceId = placeId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }
}


