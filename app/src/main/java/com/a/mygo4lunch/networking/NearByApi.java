package com.a.mygo4lunch.networking;

import com.a.mygo4lunch.models.NearByApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Romuald HOUNSA on 21/02/2021.
 */
public interface NearByApi {
@GET("api/place/nearbysearch/json")
Call<NearByApiResponse> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius,  @Query("key") String key);


    //https://maps.googleapis.com/maps/api/place/details/json?&fields=name,rating,formatted_phone_number,icon,geometry,vicinity,types&place_id=ChIJcc0bfjDsGGARhGn7xgEYgcs&key=AIzaSyDGFBPIUVLpd36GZCrt1LQVL4zCaSbMzxU
    //  @GET("maps/api/place/details/json?&fields=place_id,name,geometry,formatted_phone_number,formatted_address,vicinity,photos,website,opening_hours")
    @GET("api/place/details/json")
    Call<com.a.mygo4lunch.models.PlaceDetail> getPlace(
            @Query("place_id") String id,
            @Query("key") String key);

}
