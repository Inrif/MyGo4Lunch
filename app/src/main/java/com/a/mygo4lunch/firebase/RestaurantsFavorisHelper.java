package com.a.mygo4lunch.firebase;

import com.a.mygo4lunch.models.RestaurantFavoris;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

/**
 * Created by  on 4/25/21.
 */
public class RestaurantsFavorisHelper {
    ///FIELD
    private static final String COLLECTION_NAME = "restaurants_favoris";

    // --- CREATE ---

    public static Task<DocumentReference> createFavoriteRestaurant(String user, String uid, String name, String placeId,
                                                                   String address, String photoReference, double rating) {
        RestaurantFavoris restoToCreate = new RestaurantFavoris (uid, name, placeId, address, photoReference, rating);

        //Store RestaurantFavoris to Firestore
        return com.a.mygo4lunch.firebase.UserHelper.getUsersCollection ()
                .document (user)
                .collection (COLLECTION_NAME)
                .add (restoToCreate);
    }

    // --- GET ---

    public static com.google.firebase.firestore.Query getAllRestaurantsFromWorkers(String name) {
        return UserHelper.getUsersCollection ()
                .document (name)
                .collection (COLLECTION_NAME);
    }

    // --- DELETE ---

    public static void deleteRestaurant(String user, String uid) {
        com.a.mygo4lunch.firebase.UserHelper.getUsersCollection ()
                .document (user)
                .collection (COLLECTION_NAME)
                .document (uid)
                .delete ();
    }
}
