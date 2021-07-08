package com.a.mygo4lunch.view.activities;


import com.a.mygo4lunch.R;

import com.a.mygo4lunch.models.RestaurantFavoris;
import com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter;

import static com.a.mygo4lunch.view.activities.ConnectionActivity.getCurrentUser;

public class FavoritesRestaurant extends androidx.appcompat.app.AppCompatActivity implements RestaurantFavoritesAdapter.favoritesClickListener {

    @butterknife.BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar mToolbar;
    //FIELDS
    private androidx.recyclerview.widget.RecyclerView mRecyclerView;
    private RestaurantFavoritesAdapter adapter;
    private java.util.ArrayList<RestaurantFavoris> mRestaurantFavorisList = new java.util.ArrayList<> ();



    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (com.a.mygo4lunch.R.layout.activity_favorites_restaurant);
        butterknife.ButterKnife.bind (this); //Configure Butterknife

        mRecyclerView = findViewById (R.id.recyclerView_favoris);
        mRecyclerView.setLayoutManager (new androidx.recyclerview.widget.LinearLayoutManager (this.getBaseContext ()));

        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
            java.util.Objects.requireNonNull (getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);
            getSupportActionBar ().setDisplayShowHomeEnabled (true);
        }


        initListAdapter ();


    }

    @Override
    public void onStart() {
        super.onStart ();
        adapter.startListening ();
    }

    @Override
    public void onStop() {
        super.onStop ();
        adapter.stopListening ();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Creates a MenuInflater to add the menu xml file into the Toolbar
        getMenuInflater ().inflate (R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId () == R.id.toolbar_search) {
            timber.log.Timber.i ("Search");
        } else {
            return super.onOptionsItemSelected (item);
        }
        return true;
    }

    /**
     * get only user favorite restaurant
     */
    private void initListAdapter() {
        com.google.firebase.firestore.Query query = com.a.mygo4lunch.firebase.RepositoryFirebase.getQueryFavoritesRestaurant (mRestaurantFavorisList,
                java.util.Objects.requireNonNull (getCurrentUser ()).getDisplayName ());

        com.firebase.ui.firestore.FirestoreRecyclerOptions<RestaurantFavoris> options = new com.firebase.ui.firestore.FirestoreRecyclerOptions.Builder<RestaurantFavoris> ()
                .setQuery (query, RestaurantFavoris.class)
                .build ();

        adapter = new RestaurantFavoritesAdapter (options, this);
        mRecyclerView.setAdapter (adapter);
    }

    @Override
    public void onClickItemResto(int position) {
        android.content.Intent intent = new android.content.Intent (this, com.a.mygo4lunch.models.RestaurantDetail.class);
        intent.putExtra ("placeId", mRestaurantFavorisList.get (position).getPlaceId ());
        intent.putExtra ("restaurantName", mRestaurantFavorisList.get (position).getName ());
        startActivity (intent);
    }
}
