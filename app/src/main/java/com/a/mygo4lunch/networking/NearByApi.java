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

}
