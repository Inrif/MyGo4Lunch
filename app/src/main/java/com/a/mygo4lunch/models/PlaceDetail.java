
package com.a.mygo4lunch.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class PlaceDetail implements java.io.Serializable {

    @com.google.gson.annotations.SerializedName("html_attributions")
    private java.util.List<Object> mHtmlAttributions;
    @com.google.gson.annotations.SerializedName("result")
    private PlaceDetailsResult mResult;
    @com.google.gson.annotations.SerializedName("status")
    private String mStatus;

    public java.util.List<Object> getHtmlAttributions() {
        return mHtmlAttributions;
    }

    public void setHtmlAttributions(java.util.List<Object> htmlAttributions) {
        mHtmlAttributions = htmlAttributions;
    }

    public PlaceDetailsResult getResult() {
        return mResult;
    }

    public void setResult(PlaceDetailsResult result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
