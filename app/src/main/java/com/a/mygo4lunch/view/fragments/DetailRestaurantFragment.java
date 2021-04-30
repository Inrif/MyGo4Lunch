package com.a.mygo4lunch.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a.mygo4lunch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailRestaurantFragment extends Fragment {

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
    private java.util.ArrayList<com.a.mygo4lunch.models.User> mUsers;
    private java.util.ArrayList<com.a.mygo4lunch.models.RestaurantFavoris> mRestaurantFavorises;
    private com.google.firebase.firestore.ListenerRegistration mListenerRegistration = null;
    private com.a.mygo4lunch.models.RestaurantFavoris mRestaurantFavoris;
    private com.google.firebase.firestore.Query query;
    private boolean isLiked = false;
    private com.a.mygo4lunch.models.RestaurantDetail mRestaurantDetail;
    public static java.util.List<com.a.mygo4lunch.models.Result> restaurants = new java.util.ArrayList<> ();;

    public DetailRestaurantFragment() {
        // Required empty public constructor
    }


    public static DetailRestaurantFragment newInstance(String param1, String param2) {
        DetailRestaurantFragment fragment = new DetailRestaurantFragment ();
        Bundle args = new Bundle ();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        butterknife.ButterKnife.bind (java.util.Objects.requireNonNull (getActivity ())); /* Configure Butterknife */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       android.view.View view = inflater.inflate (R.layout.fragment_detail_restaurant, container, false);

         android.widget.TextView mRestaurantTextname = view.findViewById (com.a.mygo4lunch.R.id.restaurant_text_name);
         android.widget.ImageView ImageView = view.findViewById (com.a.mygo4lunch.R.id.imageRestaurant);
         android.widget.TextView mRestaurantTextadress = view.findViewById (com.a.mygo4lunch.R.id.restaurant_text_adress);

//         mRestaurantTextname.setText ("Le ROMUALDO");
////        mImageView.setImageResource (com.a.mygo4lunch.R.drawable.restaurant_detail);
//        mRestaurantTextadress.setText ("111 Via della Nocetta");
        updateDetailFragment();
       return view;

    }

    private void updateDetailFragment() {
        mRestaurantTextname.setText ("Le ROMUALDO");
//        mImageView.setImageResource (com.a.mygo4lunch.R.drawable.restaurant_detail);
        mRestaurantTextadress.setText ("111 Via della Nocetta");
    }
}