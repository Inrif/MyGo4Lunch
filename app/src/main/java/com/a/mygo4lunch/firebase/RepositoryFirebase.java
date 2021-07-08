package com.a.mygo4lunch.firebase;


import com.a.mygo4lunch.R;
import com.a.mygo4lunch.models.RestaurantFavoris;
import com.a.mygo4lunch.models.User;

//
// Created by Hounsa Romuald on 2020-03-16.
// Copyright (c) 2020 abbesolo.com.go4Lunch.apiFirebase. All rights reserved.
//
public class RepositoryFirebase {

    /**
     * create a query for workers BDD
     *
     * @param mWorkers list of workers
     * @return query for Firebase recyclerView options
     */
    public static com.google.firebase.firestore.Query getQueryWorkers(java.util.List<User> mWorkers) {
        com.google.firebase.firestore.Query query = UserHelper.getUsersCollection ().orderBy ("restaurantName", com.google.firebase.firestore.Query.Direction.DESCENDING);
        query.get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                            User u = document.toObject (User.class);
                            mWorkers.add (u);
                            timber.log.Timber.d (document.getId () + " => " + document.getData ());
                        }
                    } else {
                        timber.log.Timber.w (String.valueOf (R.string.error_query), java.util.Objects.requireNonNull (task.getException ()).getMessage ());
                    }
                });
        return query;
    }

    /**
     * create a query for favorite restaurant BDD
     *
     * @param favorises list of restaurant
     * @param user      user logged
     * @return query for firebase RV
     */
    public static com.google.firebase.firestore.Query getQueryFavoritesRestaurant(java.util.List<RestaurantFavoris> favorises, String user) {
        com.google.firebase.firestore.Query query = RestaurantsFavorisHelper.getAllRestaurantsFromWorkers (user);
        query.get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                            RestaurantFavoris restaurantFavoris = document.toObject (RestaurantFavoris.class);

                            favorises.add (restaurantFavoris);
                        }
                    } else {
                        timber.log.Timber.w (String.valueOf (R.string.error_query), java.util.Objects.requireNonNull (task.getException ()).getMessage ());
                    }
                });
        return query;
    }


}
