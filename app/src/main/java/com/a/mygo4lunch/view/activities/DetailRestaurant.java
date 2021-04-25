package com.a.mygo4lunch.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.a.mygo4lunch.view.activities.ConnectionActivity.getCurrentUser;

public class DetailRestaurant extends AppCompatActivity {
    private static final int PERMISSION_CALL = 100;
    private static final String GOOGLE_MAP_KEY = "AIzaSyDfYXOP1vSQ1ReiNxEE45o1_m4imoGd0OA";
    @butterknife.BindView(com.a.mygo4lunch.R.id.coordinator_detail)
    androidx.coordinatorlayout.widget.CoordinatorLayout mCoordinatorLayout;
    @butterknife.BindView(com.a.mygo4lunch.R.id.toolbarDetails)
    androidx.appcompat.widget.Toolbar mToolbar;
    @butterknife.BindView(com.a.mygo4lunch.R.id.imageRestaurant)
    android.widget.ImageView mImageView;
    @butterknife.BindView(com.a.mygo4lunch.R.id.restaurant_text_name)
    android.widget.TextView mRestaurantTextname;
    @butterknife.BindView(com.a.mygo4lunch.R.id.restaurant_text_adress)
    android.widget.TextView mRestaurantTextadress;
    @butterknife.BindView(com.a.mygo4lunch.R.id.text_Like)
    android.widget.TextView mTextLike;
    @butterknife.BindView(com.a.mygo4lunch.R.id.text_no_worker)
    android.widget.TextView mTextNoWorker;
    @butterknife.BindView(com.a.mygo4lunch.R.id.text_favorite)
    android.widget.TextView mTextFavorite;
    @butterknife.BindView(com.a.mygo4lunch.R.id.restaurant_detail_star1)
    android.widget.ImageView mRestaurantStar1;
    @butterknife.BindView(com.a.mygo4lunch.R.id.restaurant_detail_star2)
    android.widget.ImageView mRestaurantStar2;
    @butterknife.BindView(com.a.mygo4lunch.R.id.restaurant_detail_star3)
    android.widget.ImageView mRestaurantStar3;
    @butterknife.BindView(com.a.mygo4lunch.R.id.call_image)
    android.widget.ImageButton callPhone;
    @butterknife.BindView(com.a.mygo4lunch.R.id.website)
    android.widget.ImageButton websiteButton;
    @butterknife.BindView(com.a.mygo4lunch.R.id.like)
    android.widget.ImageButton likeButton;
    @butterknife.BindView(com.a.mygo4lunch.R.id.fab_restaurant_detail)
    com.google.android.material.floatingactionbutton.FloatingActionButton mFloatingActionButton;
    @butterknife.BindView(com.a.mygo4lunch.R.id.favorite_restaurant)
    android.widget.ImageButton favoriteButton;
    @butterknife.BindView(com.a.mygo4lunch.R.id.recyclerView_workers_restaurant_detail)
    androidx.recyclerview.widget.RecyclerView mRecyclerView;
    private String phoneNumber;
    private String websiteUrl;
    private String placeId;
    private String nameResto;
    private java.util.ArrayList<com.a.mygo4lunch.models.User> mWorkers;
//    private ArrayList<RestaurantFavoris> mRestaurantFavorises;
    private com.google.firebase.firestore.ListenerRegistration mListenerRegistration = null;
//    private RestaurantFavoris mRestaurantFavoris;
    private com.google.firebase.firestore.Query query;
    private boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (com.a.mygo4lunch.R.layout.activity_detail_restaurant);


        //get placeId and name extra intent
        placeId = getIntent ().getStringExtra ("placeId");
        nameResto = getIntent ().getStringExtra ("restaurantName");

        query = com.a.mygo4lunch.firebase.UserHelper.getAllUsers ().whereEqualTo ("name",
                java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());
    }
}