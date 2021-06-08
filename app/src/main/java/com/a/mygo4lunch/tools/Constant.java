package com.a.mygo4lunch.tools;

import android.widget.Toast;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;

/**
 * Created by Romuald HOUNSA on 21/02/2021.
 */
public class Constant {
    public static String PLACE_API_BASE_URL = "https://maps.googleapis.com/maps/";
//    public static String PLACE_KEY = "AIzaSyDSJqc7yJL30pE2rJPmH1DvbJnIxWKPGb8";
    public static String PLACE_KEY = "AIzaSyDVJ9xYciyDlgV1gsLn7u2agRyuO_ye6GI";

    /**
     * create method to attribute stars
     *
     * @param rating restaurant rating
     * @return int to attribute stars
     */
    public static int starsAccordingToRating(double rating) {
        if (rating == 0) {
            return 0;
        } else if (rating > 0 && rating <= 2) {
            return 1;
        } else if (rating > 2 && rating < 3.7) {
            return 2;
        } else {
            return 3;
        }
    }



    /**
     * update star with rating
     *
     * @param rating rating
     * @param s1     star 1
     * @param s2     star 2
     * @param s3     star 3
     */
    public static void starsView(int rating, android.widget.ImageView s1, android.widget.ImageView s2, android.widget.ImageView s3) {
        switch (rating) {
            case 0:
                s1.setVisibility (android.view.View.GONE);
                s2.setVisibility (android.view.View.GONE);
                s3.setVisibility (android.view.View.GONE);
                break;
            case 1:
                s1.setVisibility (android.view.View.VISIBLE);
                s2.setVisibility (android.view.View.GONE);
                s3.setVisibility (android.view.View.GONE);
                break;
            case 2:
                s1.setVisibility (android.view.View.VISIBLE);
                s2.setVisibility (android.view.View.VISIBLE);
                s3.setVisibility (android.view.View.GONE);
                break;
            case 3:
                s1.setVisibility (android.view.View.VISIBLE);
                s2.setVisibility (android.view.View.VISIBLE);
                s3.setVisibility (android.view.View.VISIBLE);
                break;
        }
    }

    /**
     * set worker choice on restaurant list
     *
     * @param resto   restaurant list
     * @param workers workers list
     * @return arrayList
     */
    public static java.util.ArrayList<com.a.mygo4lunch.models.Restaurant> getChoicedRestaurants(java.util.List<com.a.mygo4lunch.models.Restaurant> resto, java.util.ArrayList<com.a.mygo4lunch.models.User> workers) {
        java.util.ArrayList<com.a.mygo4lunch.models.Restaurant> restaurants = new java.util.ArrayList<> ();
        restaurants.clear ();
        for (com.a.mygo4lunch.models.Restaurant r : resto) {
            int worker = 0;
            for (com.a.mygo4lunch.models.User w : workers) {
                if (r.getPlaceId ().equalsIgnoreCase (w.getPlaceId ())) {
                    worker++;
                    r.setChoice (true);
                    r.setUser (worker);
                }
            }
            restaurants.add (r);
        }
        return restaurants;
    }



//
//    public static getDistanceInMeters(com.google.android.gms.maps.model.LatLng userLocation){
//        double earthRadius =6371.0;
//
//         arg1:Double = sin(Math.toRadians(this.locationLat)) * sin(Math.toRadians(userLocation.latitude));
//         arg2:Double = cos(Math.toRadians(this.locationLat)) * cos(Math.toRadians(userLocation.latitude)) *
//                cos(Math.toRadians(userLocation.longitude-this.locationLng));
//         distanceInKMeters:Double=earthRadius* acos(arg1+arg2);
//        return (distanceInKMeters*1000).toInt()
//    }

//calculates the distance between two points defined by their latitude and longitude

    public static int calculateDistance(com.google.android.gms.maps.model.LatLng StartP, com.google.android.gms.maps.model.LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (int) Math.floor(Radius * c * 1000);
    }



}



