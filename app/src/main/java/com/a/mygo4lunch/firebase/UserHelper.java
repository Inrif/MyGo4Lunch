package com.a.mygo4lunch.firebase;

import com.a.mygo4lunch.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;

/**
 * Created by Romuald Hounsa on 4/9/21.
 */
public class UserHelper {
    //FIELD
    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static com.google.firebase.firestore.CollectionReference getUsersCollection() {
        return com.google.firebase.firestore.FirebaseFirestore.getInstance ().collection (COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createUser(String name, String avatar, String resto, String placeId, String uid) {
        User userToCreate = new User (name, avatar, resto, placeId, uid);
        return UserHelper.getUsersCollection ().document (uid).set (userToCreate);
    }

    // -- GET ALL Workers --
    public static Query getAllUsers() {
        return UserHelper.getUsersCollection ();
    }

    // --- GET ---

    public static com.google.android.gms.tasks.Task<com.google.firebase.firestore.DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }


    // --- UPDATE ---
    public static void updateRestaurantChoice(String uid, String restoName, String placeId) {

        java.util.Map<String, Object> data = new java.util.HashMap<> ();
        data.put ("placeId", placeId);
        data.put ("restaurantName", restoName);
        UserHelper.getUsersCollection ().document (uid).update (data);
    }

    public static Query getQueryUsers(java.util.List<com.a.mygo4lunch.models.User> mUsers) {
        Query query = UserHelper.getUsersCollection ().orderBy ("restaurantName", Query.Direction.DESCENDING);
        query.get ()
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                            User u = document.toObject (User.class);
                            mUsers.add (u);
                            timber.log.Timber.d (document.getId () + " => " + document.getData ());
                        }
                    } else {
                        timber.log.Timber.w (String.valueOf (com.a.mygo4lunch.R.string.error_query), java.util.Objects.requireNonNull (task.getException ()).getMessage ());
                    }
                });
        return query;
    }
}
