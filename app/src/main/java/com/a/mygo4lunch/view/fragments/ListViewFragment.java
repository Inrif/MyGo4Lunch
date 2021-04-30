package com.a.mygo4lunch.view.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.a.mygo4lunch.R;
import com.a.mygo4lunch.R.id;
import com.a.mygo4lunch.models.RestaurantDetail;
import com.a.mygo4lunch.models.Result;
import com.a.mygo4lunch.view.activities.MainActivity;
import com.a.mygo4lunch.view.adapter.ListViewAdapter;
import com.a.mygo4lunch.view.adapter.ListViewAdapter.onClickRestaurantItemListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.Autocomplete.IntentBuilder;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.a.mygo4lunch.view.activities.MainActivity.*;


public class ListViewFragment extends Fragment implements onClickRestaurantItemListener{
    private static final int AUTOCOMPLETE_REQUEST_CODE = 90;
    private GoogleMap googleMap;
    private Location location;
    private static String LOCATION_TYPE = "restaurant";
    private LatLng defaultLocation = new LatLng (48.8534, 2.3488);
    public ArrayList<Marker> markers = new java.util.ArrayList<> ();

    private ListViewAdapter.onClickRestaurantItemListener mListener;






    public ListViewFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View view = inflater.inflate (R.layout.fragment_list_view, container, false);


// Add the following lines to create RecyclerView
        // Add RecyclerView member
        RecyclerView recyclerView = view.findViewById (id.recyclerView);
// 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager (getActivity()));

        // 3. create an adapter
        ListViewAdapter mAdapter = new ListViewAdapter (restaurants,getActivity (),mListener);

        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator ());


        android.util.Log.d (TAG, "Result: "+ restaurants);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                searchPlace();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void searchPlace() {
        java.util.List<Field> fields = java.util.Arrays.asList(Field.ID, Field.NAME);
        Intent intent = new IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setCountry("FR")
                //.setLocationBias(RectangularBounds.newInstance(setBounds(mLastKnownLocation, 1000)))
                .build(this.getContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                displayRestaurant(place);
            } else {
                //Display error message
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayRestaurant(Place place) {
        if (getResultFromPlace(place)!=null) {
            LatLng latLng = new LatLng (getResultFromPlace(place).getGeometry().getLocation().getLat(), getResultFromPlace(place).getGeometry().getLocation().getLng());
            getMarker(latLng).showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        } else {
            Toast.makeText(this.getContext(),"La place recherchée n'est pas dans la liste", Toast.LENGTH_LONG).show();
        }

    }

    public Marker getMarker(LatLng latLng) {
        for (Marker marker: markers){
            if(marker.getPosition().equals(latLng)) {
                return marker;
            }
        }
        return null;
    }
    private Result getResultFromPlace(Place place) {
        for (Result result: MainActivity.restaurants) {
            if (result.getPlaceId().equals(place.getId())){
                return result;
            }
        }
        return null;
    }

    @Override
    public void onClickRestaurantItem(int position) {
        timber.log.Timber.i ("onClickRestaurantItem: %s", restaurants);

        Intent intent = new Intent (getContext (), RestaurantDetail.class);
        intent.putExtra ("placeId", restaurants.get (position).getPlaceId ());
        intent.putExtra ("restaurantName", restaurants.get (position).getName ());
        startActivity (intent);
    }

    }

