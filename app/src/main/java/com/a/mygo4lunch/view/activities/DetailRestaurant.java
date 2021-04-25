package com.a.mygo4lunch.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.a.mygo4lunch.R.id;
import com.a.mygo4lunch.models.RestaurantDetail;
import com.a.mygo4lunch.tools.Constant;

import butterknife.OnClick;

import static com.a.mygo4lunch.tools.Constant.PLACE_KEY;
import static com.a.mygo4lunch.view.activities.ConnectionActivity.getCurrentUser;

public class DetailRestaurant extends AppCompatActivity {
    private static final int PERMISSION_CALL = 100;
    private static final String GOOGLE_MAP_KEY = "AIzaSyDfYXOP1vSQ1ReiNxEE45o1_m4imoGd0OA";
    @butterknife.BindView(id.coordinator_detail)
    androidx.coordinatorlayout.widget.CoordinatorLayout mCoordinatorLayout;
    @butterknife.BindView(id.toolbarDetails)
    androidx.appcompat.widget.Toolbar mToolbar;
    @butterknife.BindView(id.imageRestaurant)
    android.widget.ImageView mImageView;
    @butterknife.BindView(id.restaurant_text_name)
    android.widget.TextView mRestaurantTextname;
    @butterknife.BindView(id.restaurant_text_adress)
    android.widget.TextView mRestaurantTextadress;
    @butterknife.BindView(id.text_Like)
    android.widget.TextView mTextLike;
    @butterknife.BindView(id.text_no_worker)
    android.widget.TextView mTextNoWorker;
    @butterknife.BindView(id.text_favorite)
    android.widget.TextView mTextFavorite;
    @butterknife.BindView(id.restaurant_detail_star1)
    android.widget.ImageView mRestaurantStar1;
    @butterknife.BindView(id.restaurant_detail_star2)
    android.widget.ImageView mRestaurantStar2;
    @butterknife.BindView(id.restaurant_detail_star3)
    android.widget.ImageView mRestaurantStar3;
    @butterknife.BindView(id.call_image)
    android.widget.ImageButton callPhone;
    @butterknife.BindView(id.website)
    android.widget.ImageButton websiteButton;
    @butterknife.BindView(id.like)
    android.widget.ImageButton likeButton;
    @butterknife.BindView(id.fab_restaurant_detail)
    com.google.android.material.floatingactionbutton.FloatingActionButton mFloatingActionButton;
    @butterknife.BindView(id.favorite_restaurant)
    android.widget.ImageButton favoriteButton;
    @butterknife.BindView(id.recyclerView_workers_restaurant_detail)
    androidx.recyclerview.widget.RecyclerView mRecyclerView;
    private String phoneNumber;
    private String websiteUrl;
    private String placeId;
    private String nameResto;
    private java.util.ArrayList<com.a.mygo4lunch.models.User> mUsers;
    private java.util.ArrayList<com.a.mygo4lunch.models.RestaurantFavoris> mRestaurantFavorises;
    private com.google.firebase.firestore.ListenerRegistration mListenerRegistration = null;
    private com.a.mygo4lunch.models.RestaurantFavoris mRestaurantFavoris;
    private com.google.firebase.firestore.Query query;
    private boolean isLiked = false;
    private RestaurantDetail mRestaurantDetail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (com.a.mygo4lunch.R.layout.activity_detail_restaurant);


        //get placeId and name extra intent
        placeId = getIntent ().getStringExtra ("placeId");
        nameResto = getIntent ().getStringExtra ("restaurantName");

        query = com.a.mygo4lunch.firebase.UserHelper.getAllUsers ().whereEqualTo ("name",
                java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        getFavoriteRestaurant ();

        this.updateView(mRestaurantDetail);
    }


    private void updateView(com.a.mygo4lunch.models.RestaurantDetail mRestaurantDetail) {
        //listen change on worker list
        listenChangeWorkersList ();
        //path for photo url
        photoReferencePath (mRestaurantDetail.getPhoto ());
        //set name and address
        setNameRestaurant (mRestaurantDetail.getName ());
        mRestaurantTextadress.setText (mRestaurantDetail.getAddress ());
        //stars method according to rating
        Constant.starsView (Constant.starsAccordingToRating (mRestaurantDetail.getRating ()), mRestaurantStar1, mRestaurantStar2, mRestaurantStar3);
        // Call restaurant if possible
        callPhone.setOnClickListener (v -> setCallPhoneIfPossible (mRestaurantDetail.getPhone_number ()));

        // go to website if there is one
        websiteButton.setOnClickListener (view -> {
            if (mRestaurantDetail.getWebsite () == null) {
                android.widget.Toast.makeText (this, com.a.mygo4lunch.R.string.no_website_for_restaurant, android.widget.Toast.LENGTH_SHORT).show ();
            } else {
                websiteUrl = mRestaurantDetail.getWebsite ();
                callWebsiteUrl ();
            }
        });

        mRestaurantFavoris = new com.a.mygo4lunch.models.RestaurantFavoris ("", mRestaurantDetail.getName (), placeId, mRestaurantDetail.getAddress (),
                mRestaurantDetail.getPhoto (), mRestaurantDetail.getRating ());
        //configure click on like star
        if (mRestaurantFavorises != null) {
            for (com.a.mygo4lunch.models.RestaurantFavoris restaurantFavoris : mRestaurantFavorises) {
                if (restaurantFavoris.getPlaceId ().equalsIgnoreCase (placeId)) {
                    isLiked = true;
                    updateButtonLike ();
                }
            }
        }
        fabButtonColor ();
    }



    private void listenChangeWorkersList() {
        final com.google.firebase.firestore.CollectionReference usersRef = com.a.mygo4lunch.firebase.UserHelper.getUsersCollection ();
        mListenerRegistration = usersRef.addSnapshotListener ((queryDocumentSnapshots, e) -> {
            mUsers = new java.util.ArrayList<> ();
            if (queryDocumentSnapshots != null) {
                for (com.google.firebase.firestore.DocumentSnapshot data : java.util.Objects.requireNonNull (queryDocumentSnapshots).getDocuments ()) {

                    if (data.get ("placeId") != null && java.util.Objects.requireNonNull (data.get ("placeId")).toString ().equals (placeId)) {

                        com.a.mygo4lunch.models.User mUser = data.toObject (com.a.mygo4lunch.models.User.class);
                        mUsers.add (mUser);
                        timber.log.Timber.i ("snap workers : %s", mUsers.size ());
                    }
                }
            }
            initAdapter (mUsers);
        });
    }

    /**
     * get photo path if able and show it in glide
     *
     * @param photoText photo path
     */
    private void photoReferencePath(String photoText) {
        String path;
        if (photoText == null) {
            path = "https://www.chilhoweerv.com/storage/app/public/blog/noimage930.png";
        } else {
            path = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                    + photoText +
                    "&key=" + PLACE_KEY;

        }

        com.bumptech.glide.Glide.with (this)
                .load (path)
                .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
                .into (mImageView);
    }

    private void setNameRestaurant(String restoText) {

        String name;
        if (restoText.length () > 23) {
            name = restoText.substring (0, 23) + " ...";
        } else {
            name = restoText;
        }
        mRestaurantTextname.setText (name);


        }
    /**
     * check if phone permission is able
     *
     * @param callPhoneNumber call number
     */
    private void setCallPhoneIfPossible(String callPhoneNumber) {
        if (callPhoneNumber == null) {
            android.widget.Toast.makeText (this, com.a.mygo4lunch.R.string.no_telephone_number_for_this_restaurant, android.widget.Toast.LENGTH_SHORT).show ();
        } else {
            phoneNumber = callPhoneNumber;
        }

        if (androidx.core.content.ContextCompat.checkSelfPermission (this, android.Manifest.permission.CALL_PHONE)
                != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            androidx.core.app.ActivityCompat.requestPermissions (this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    PERMISSION_CALL);
        } else {
            callPhone ();
        }
    }


    /**
     * set the color of FAB button
     */
    private void fabButtonColor() {
        this.query.get ().addOnCompleteListener (task -> {
            if (task.isSuccessful ()) {
                for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                    if (placeId.equalsIgnoreCase (java.util.Objects.requireNonNull (document.get ("placeId")).toString ())) {
                        mFloatingActionButton.setImageResource (com.a.mygo4lunch.R.drawable.ic_check_checked);
                    } else {
                        mFloatingActionButton.setImageResource (com.a.mygo4lunch.R.drawable.ic_baseline_check_circle_24);
                    }
                }
            }
        });
    }


    /**
     * init adapter with list of workers
     *
     * @param workers list
     */
    private void initAdapter(java.util.ArrayList<com.a.mygo4lunch.models.User> workers) {
        if (workers.isEmpty ()) {
            mTextNoWorker.setVisibility (android.view.View.VISIBLE);
            mRecyclerView.setVisibility (android.view.View.GONE);
        } else {
            com.a.mygo4lunch.view.adapter.DetailWorkerAdapter adapter = new com.a.mygo4lunch.view.adapter.DetailWorkerAdapter (workers);
            mRecyclerView.setLayoutManager (new androidx.recyclerview.widget.LinearLayoutManager (getBaseContext ()));
            mRecyclerView.setAdapter (adapter);
            mRecyclerView.setVisibility (android.view.View.VISIBLE);
            mTextNoWorker.setVisibility (android.view.View.GONE);
        }
    }

    /**
     * intent to call
     */
    @android.annotation.SuppressLint("MissingPermission")
    private void callPhone() {
        android.content.Intent intentCall = new android.content.Intent (android.content.Intent.ACTION_CALL, android.net.Uri.parse ("tel:" + phoneNumber));
        startActivity (intentCall);
    }

    /**
     * intent to go to website
     */
    private void callWebsiteUrl() {
        android.content.Intent intentWebsite = new android.content.Intent (android.content.Intent.ACTION_VIEW, android.net.Uri.parse (websiteUrl));
        startActivity (intentWebsite);
    }


    @OnClick({id.fab_restaurant_detail, id.like, id.favorite_restaurant})
    public void onClickFabButton(android.view.View view) {
        switch (view.getId ()) {
            case id.fab_restaurant_detail:
                this.updateFab ();
                break;
            case id.like:
                this.likeRestaurant ();
                break;
            case id.favorite_restaurant:
                this.deleteFavoriteRestaurant ();
                break;
        }
    }


    private void updateFab() {

        this.query.get ().addOnCompleteListener (task -> {

            if (task.isSuccessful ()) {

                for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                    String id = document.getId ();
                    if (placeId.equalsIgnoreCase (java.util.Objects.requireNonNull (document.get ("placeId")).toString ())) {
                        updateRestaurantChoice (id, "", "",
                                getString (com.a.mygo4lunch.R.string.you_haven_t_choice_restaurant), false);


                    } else {


                        updateRestaurantChoice (id, nameResto,
                                placeId, getString (com.a.mygo4lunch.R.string.chosen_restaurant), true);

                    }
                }

            }

        });

    }

    private void likeRestaurant() {
        saveRestaurantToFavorite (mRestaurantFavoris);

    }

    private void deleteFavoriteRestaurant() {
        getFavoriteRestaurant ();
        for (com.a.mygo4lunch.models.RestaurantFavoris restaurantFavoris : mRestaurantFavorises) {
            if (restaurantFavoris.getPlaceId ().equalsIgnoreCase (placeId)) {
                deleteRestaurantToFavorite (restaurantFavoris.getUid ());
            }
        }

    }


    private void updateRestaurantChoice(String id, String restoName, String placeId, String message, boolean isChosen) {
        com.a.mygo4lunch.firebase.UserHelper.updateRestaurantChoice (id, restoName, placeId);
        showSnackBar (this.mCoordinatorLayout, message);
        mFloatingActionButton.setImageResource (isChosen
                ? com.a.mygo4lunch.R.drawable.ic_check_checked
                : com.a.mygo4lunch.R.drawable.ic_baseline_check_circle_24);
    }

    private void saveRestaurantToFavorite(com.a.mygo4lunch.models.RestaurantFavoris resto) {
        com.a.mygo4lunch.firebase.RestaurantsFavorisHelper.createFavoriteRestaurant (java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName (),
                resto.getUid (), resto.getName (), resto.getPlaceId (),
                resto.getAddress (), resto.getPhotoReference (),
                resto.getRating ()).addOnFailureListener (this.onFailureListener ());

        showSnackBar (this.mCoordinatorLayout, getString (com.a.mygo4lunch.R.string.favorite_restaurant_message));
        isLiked = true;
        updateButtonLike ();
        getFavoriteRestaurant ();
    }

    /**
     * set the button like appearance
     */
    private void updateButtonLike() {
        if (isLiked) {
            favoriteButton.setVisibility (android.view.View.VISIBLE);
            mTextFavorite.setVisibility (android.view.View.VISIBLE);
            likeButton.setVisibility (android.view.View.GONE);
            mTextLike.setVisibility (android.view.View.GONE);
        } else {
            favoriteButton.setVisibility (android.view.View.GONE);
            mTextFavorite.setVisibility (android.view.View.GONE);
            likeButton.setVisibility (android.view.View.VISIBLE);
            mTextLike.setVisibility (android.view.View.VISIBLE);
        }
    }

    /**
     * create query to have restaurant favorite list
     */
    private void getFavoriteRestaurant() {
        final com.google.firebase.firestore.Query refResto = com.a.mygo4lunch.firebase.RestaurantsFavorisHelper.
                getAllRestaurantsFromWorkers (java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        refResto.get ()
                .addOnCompleteListener (task -> {
                    mRestaurantFavorises = new java.util.ArrayList<> ();
                    for (com.google.firebase.firestore.QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                        com.a.mygo4lunch.models.RestaurantFavoris resto = document.toObject (com.a.mygo4lunch.models.RestaurantFavoris.class);
                        resto.setUid (document.getId ());
                        timber.log.Timber.d ("resto uid = %s", resto.getUid ());
                        mRestaurantFavorises.add (resto);
                    }
                    timber.log.Timber.d ("restofav list : %s", mRestaurantFavorises.size ());
                });
    }

    /**
     * delete restaurant to favorite list
     *
     * @param uid restaurant id
     */
    private void deleteRestaurantToFavorite(String uid) {
        com.a.mygo4lunch.firebase.RestaurantsFavorisHelper.deleteRestaurant (java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName (), uid);
        showSnackBar (this.mCoordinatorLayout, getResources ().getString (com.a.mygo4lunch.R.string.restaurant_delete));
        isLiked = false;
        updateButtonLike ();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CALL) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                callPhone ();
            }
        }
    }



    public static void showSnackBar(android.view.View view, String message) {
        com.google.android.material.snackbar.Snackbar.make (view, message, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show ();
    }
    protected com.google.android.gms.tasks.OnFailureListener onFailureListener() {
        return e -> android.widget.Toast.makeText (getApplicationContext (), getString (com.a.mygo4lunch.R.string.error_unknown_error), android.widget.Toast.LENGTH_LONG).show ();
    }
}