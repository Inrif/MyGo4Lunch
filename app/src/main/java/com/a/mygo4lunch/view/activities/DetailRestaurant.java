package com.a.mygo4lunch.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.a.mygo4lunch.R.id;
import com.a.mygo4lunch.models.PlaceDetail;
import com.a.mygo4lunch.models.PlaceDetailsResult;
import com.a.mygo4lunch.models.RestaurantDetail;
import com.a.mygo4lunch.networking.NearByApi;
import com.a.mygo4lunch.tools.Constant;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import butterknife.OnClick;

import static com.a.mygo4lunch.view.activities.ConnectionActivity.getCurrentUser;
import static com.a.mygo4lunch.view.activities.MainActivity.showSnackBar;

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
    @butterknife.BindView(com.a.mygo4lunch.R.id.starVisibility)
    android.widget.LinearLayout mStarVisibility;

    private com.bumptech.glide.RequestManager mglide;
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
    public static java.util.List<com.a.mygo4lunch.models.Result> restaurants = new java.util.ArrayList<> ();;
//    public static java.util.List<com.a.mygo4lunch.models.Result> restaurants;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (com.a.mygo4lunch.R.layout.activity_detail_restaurant);
        butterknife.ButterKnife.bind (this); //Configure Butterknife

        //get placeId and name extra intent
        placeId = getIntent ().getStringExtra ("placeId");
//       String title = getIntent ().getStringExtra ("title");

        query = com.a.mygo4lunch.firebase.UserHelper.getAllUsers ().whereEqualTo ("name",
                java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        getFavoriteRestaurant ();
       getPlace();

    }


    @OnClick({com.a.mygo4lunch.R.id.fab_restaurant_detail, com.a.mygo4lunch.R.id.like, com.a.mygo4lunch.R.id.favorite_restaurant})
    public void onClickFabButton(android.view.View view) {

        timber.log.Timber.e ("onClickFabButton: %s", favoriteButton);
        switch (view.getId ()) {
            case com.a.mygo4lunch.R.id.fab_restaurant_detail:
                this.updateFab ();
                break;
            case com.a.mygo4lunch.R.id.like:
                this.likeRestaurant ();
                break;
            case com.a.mygo4lunch.R.id.favorite_restaurant:
                this.deleteFavoriteRestaurant ();
                break;
        }
    }


    /**
     * update the FAB button when you click on
     */
    private void updateFab() {

        this.query.get ().addOnCompleteListener (task -> {

            if (task.isSuccessful ()) {

                for (QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
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

    /**
     * method to delete restaurant if it's in favorite list
     */
    private void deleteFavoriteRestaurant() {
        getFavoriteRestaurant ();
        for (com.a.mygo4lunch.models.RestaurantFavoris restaurantFavoris : mRestaurantFavorises) {
            if (restaurantFavoris.getPlaceId ().equalsIgnoreCase (placeId)) {
                deleteRestaurantToFavorite (restaurantFavoris.getUid ());
            }
        }
    }

    /**
     * method called if you click on like star
     */
    private void likeRestaurant() {
        saveRestaurantToFavorite (mRestaurantFavoris);
    }

    /**
     * save restaurant to favorite list
     *
     * @param resto Restaurant
     */
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
     * create query to have restaurant favorite list
     */
    private void getFavoriteRestaurant() {
        final com.google.firebase.firestore.Query refResto = com.a.mygo4lunch.firebase.RestaurantsFavorisHelper.
                getAllRestaurantsFromWorkers (java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        refResto.get ()
                .addOnCompleteListener (task -> {
                    mRestaurantFavorises = new java.util.ArrayList<> ();
                    for (QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                        com.a.mygo4lunch.models.RestaurantFavoris resto = document.toObject (com.a.mygo4lunch.models.RestaurantFavoris.class);
                        resto.setUid (document.getId ());
                        timber.log.Timber.d ("resto uid = %s", resto.getUid ());
                        mRestaurantFavorises.add (resto);
                    }
                    timber.log.Timber.d ("restofav list : %s", mRestaurantFavorises.size ());
                });
    }

    /**
     * update restaurant choice when star button is clicked
     *
     * @param id        id of the restaurant
     * @param restoName restaurant name
     * @param placeId   placeID
     * @param message   message to show in SnackBar
     * @param isChosen  boolean to configure FAB
     */
    private void updateRestaurantChoice(String id, String restoName, String placeId, String message, boolean isChosen) {
        com.a.mygo4lunch.firebase.UserHelper.updateRestaurantChoice (id, restoName, placeId);
        showSnackBar (this.mCoordinatorLayout, message);
        mFloatingActionButton.setImageResource (isChosen
                ? com.a.mygo4lunch.R.drawable.ic_check_checked
                : com.a.mygo4lunch.R.drawable.ic_check_circle_black_24dp);
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



    public void getPlace() {

        NearByApi nearByApi = com.a.mygo4lunch.networking.WebServiceClient.getInstance().getClient().create(NearByApi.class);

        retrofit2.Call<PlaceDetail> call = nearByApi.getPlace (placeId, Constant.PLACE_KEY);
        call.enqueue(new retrofit2.Callback<PlaceDetail>() {
            @Override
            public void onResponse(retrofit2.Call<PlaceDetail> call, @org.jetbrains.annotations.NotNull retrofit2.Response<PlaceDetail> placeDetailsResultResponse) {

                updateUI(placeDetailsResultResponse.body ().getResult (),mglide);

            }


            @Override
            public void onFailure(retrofit2.Call<PlaceDetail> call, Throwable t) {
                android.util.Log.d("onFailure", t.toString());
                t.printStackTrace();

            }

            });


        }



    private void updateUI(PlaceDetailsResult placeDetailsResult, com.bumptech.glide.RequestManager glide) {

       mglide = glide;

        timber.log.Timber.i ("updateUI: " + phoneNumber);
        //for add photos with Glide
        if (placeDetailsResult.getPhotos() != null && !placeDetailsResult.getPhotos().isEmpty()) {
            com.bumptech.glide.Glide.with(this)
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference=" + placeDetailsResult.getPhotos().get(0).getPhotoReference() + "&key=" + Constant.PLACE_KEY)
                    .apply(com.bumptech.glide.request.RequestOptions.centerCropTransform())
                    .into(mImageView);
        } else {
            mImageView.setImageResource(com.a.mygo4lunch.R.drawable.restaurant_detail);
        }
        //For Restaurant Name
        //   mRestaurantTextname.setText(placeDetailsResult.getName());

        setNameRestaurant (placeDetailsResult.getName ());

        //For Restaurant address
        mRestaurantTextadress.setText(placeDetailsResult.getVicinity());
        //For rating
//        restaurantRating(placeDetailsResult);
//        //For  restaurant telephone number
        callPhone.setOnClickListener (v -> setCallPhoneIfPossible (placeDetailsResult.getFormattedPhoneNumber ()));


        // go to website if there is one
        websiteButton.setOnClickListener (view -> {
            if (placeDetailsResult.getWebsite () == null) {
                android.widget.Toast.makeText (this, com.a.mygo4lunch.R.string.no_website_for_restaurant, android.widget.Toast.LENGTH_SHORT).show ();
            } else {
                websiteUrl = placeDetailsResult.getWebsite ();
                callWebsiteUrl ();
            }
        });

        listenChangeUserList();


        mRestaurantFavoris = new com.a.mygo4lunch.models.RestaurantFavoris ("", placeDetailsResult.getName (), placeId, placeDetailsResult.getVicinity (),
                placeDetailsResult.getReference (), placeDetailsResult.getRating ());
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


        //stars method according to rating
        Constant.starsView (Constant.starsAccordingToRating (placeDetailsResult.getRating ()), mRestaurantStar1, mRestaurantStar2, mRestaurantStar3);
    }


    /**
     * Format text restaurant name if it's too long
     *
     * @param restoText text of restaurant name
     */
    private void setNameRestaurant(String restoText) {
        String name;
        if (restoText.length () > 23) {
            name = restoText.substring (0, 23) + " ...";
        } else {
            name = restoText;
        }
        mRestaurantTextname.setText (name);
    }

    private void callWebsiteUrl() {

        android.content.Intent intentWebsite = new android.content.Intent (android.content.Intent.ACTION_VIEW, android.net.Uri.parse (websiteUrl));
        startActivity (intentWebsite);
    }

    private void listenChangeUserList() {



            final com.google.firebase.firestore.CollectionReference usersRef = com.a.mygo4lunch.firebase.UserHelper.getUsersCollection ();
            mListenerRegistration = usersRef.addSnapshotListener ((queryDocumentSnapshots, e) -> {
                mUsers = new java.util.ArrayList<> ();
                if (queryDocumentSnapshots != null) {
                    for (com.google.firebase.firestore.DocumentSnapshot data : java.util.Objects.requireNonNull (queryDocumentSnapshots).getDocuments ()) {

                        if (data.get ("placeId") != null && java.util.Objects.requireNonNull (data.get ("placeId")).toString ().equals (placeId)) {

                            com.a.mygo4lunch.models.User user = data.toObject (com.a.mygo4lunch.models.User.class);
                            mUsers.add (user);
                            timber.log.Timber.i ("snap workers : %s", mUsers.size ());
                        }
                    }
                }
                initAdapter (mUsers);
            });

    }

    private void initAdapter(java.util.ArrayList<com.a.mygo4lunch.models.User> users) {

        if (users.isEmpty ()) {
            mTextNoWorker.setVisibility (android.view.View.VISIBLE);
            mRecyclerView.setVisibility (android.view.View.GONE);
        } else {
            com.a.mygo4lunch.view.adapter.DetailWorkerAdapter adapter = new com.a.mygo4lunch.view.adapter.DetailWorkerAdapter (users);
            mRecyclerView.setLayoutManager (new androidx.recyclerview.widget.LinearLayoutManager (getBaseContext ()));
            mRecyclerView.setAdapter (adapter);
            mRecyclerView.setVisibility (android.view.View.VISIBLE);
            mTextNoWorker.setVisibility (android.view.View.GONE);
        }
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
     * intent to call
     */
    @android.annotation.SuppressLint("MissingPermission")
    private void callPhone() {
        android.content.Intent intentCall = new android.content.Intent (android.content.Intent.ACTION_CALL, android.net.Uri.parse ("tel:" + phoneNumber));
        startActivity (intentCall);
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
     * set the color of FAB button
     */
    private void fabButtonColor() {
        this.query.get ().addOnCompleteListener (task -> {
            if (task.isSuccessful ()) {
                for (QueryDocumentSnapshot document : java.util.Objects.requireNonNull (task.getResult ())) {
                    if (placeId.equalsIgnoreCase (java.util.Objects.requireNonNull (document.get ("placeId")).toString ())) {
                        mFloatingActionButton.setImageResource (com.a.mygo4lunch.R.drawable.ic_check_checked);
                    } else {
                        mFloatingActionButton.setImageResource (com.a.mygo4lunch.R.drawable.ic_check_circle_black_24dp);
                    }
                }
            }
        });
    }


    protected com.google.android.gms.tasks.OnFailureListener onFailureListener() {
        return e -> android.widget.Toast.makeText (getApplicationContext (), getString (com.a.mygo4lunch.R.string.error_unknown_error), android.widget.Toast.LENGTH_LONG).show ();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CALL) {
            if (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                callPhone ();
            }
        }
    }

}



















