package com.a.mygo4lunch.tools;

import android.widget.Toast;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * Created by Romuald HOUNSA on 21/02/2021.
 */
public class Constant {
    public static String PLACE_API_BASE_URL = "https://maps.googleapis.com/maps/";
    public static String PLACE_KEY = "AIzaSyDSJqc7yJL30pE2rJPmH1DvbJnIxWKPGb8";

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


}



