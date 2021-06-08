package com.a.mygo4lunch.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.a.mygo4lunch.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.content.ContextCompat;

import android.util.Log;

import android.widget.Toast;

import com.a.mygo4lunch.models.Result;
import com.a.mygo4lunch.tools.Constant;
import com.a.mygo4lunch.tools.Permission;
import com.a.mygo4lunch.view.activities.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.a.mygo4lunch.networking.*;

import com.a.mygo4lunch.models.NearByApiResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.a.mygo4lunch.view.activities.MainActivity.restaurants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 90;
    private GoogleMap googleMap;
    private Location location;
    private int PROXIMITY_RADIUS = 8000;
    //For Localisation
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static String LOCATION_TYPE = "restaurant";
    private LatLng defaultLocation = new LatLng(48.8534, 2.3488);
    private int DEFAULT_ZOOM = 10;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public ArrayList<Marker> markers = new ArrayList<>();
    java.util.Map<String, String> mMarkerMap = new java.util.HashMap<> ();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //To check permissions above M as below it making issue and gives permission denied on samsung and other phones.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
    }

    private void initMap() {
        SupportMapFragment mpFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mpFragment.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap();
        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        getLocation();
        googleMap.setOnMarkerClickListener (new com.google.android.gms.maps.GoogleMap.OnMarkerClickListener () {
            @Override
            public boolean onMarkerClick(com.google.android.gms.maps.model.Marker marker) {
//
                String placeId = mMarkerMap.get(marker.getId());


                Intent intent = new Intent (getContext (), com.a.mygo4lunch.view.activities.DetailRestaurant.class);
                intent.putExtra ("placeId", placeId);

                startActivity (intent);



//                com.a.mygo4lunch.models.PlaceDetailsResult positionMarkerList = ( com.a.mygo4lunch.models.PlaceDetailsResult ) marker.getTag();
//                Intent intent = new Intent(getContext(), com.a.mygo4lunch.view.activities.DetailRestaurant.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("placeDetailsResult", positionMarkerList);
//                intent.putExtras(bundle);
//                startActivity(intent);


                return false;






            }
        });
    }

    @android.annotation.SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (Permission.checkIfLocationPermissionGranted(getContext())) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    MapFragment.this.location = location;
                    updateLocationUI();
                }
            });
        } else {
            requestPermissions (new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (location != null) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(),
                                location.getLongitude()), 17));
             findPlaces(LOCATION_TYPE);
           } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void findPlaces(String placeType) {

        NearByApi nearByApi = WebServiceClient.getInstance().getClient().create(NearByApi.class);

        retrofit2.Call<NearByApiResponse> call = nearByApi.getNearbyPlaces(placeType, location.getLatitude() + "," + location.getLongitude(), PROXIMITY_RADIUS, Constant.PLACE_KEY);
        call.enqueue(new retrofit2.Callback<NearByApiResponse>() {
            @Override
            public void onResponse(retrofit2.Call<NearByApiResponse> call, retrofit2.Response<NearByApiResponse> response) {
                try {
                    googleMap.clear();
                    markers.clear();
                    displayMap(response.body().getResults());
                } catch (Exception e) {
                    android.util.Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<NearByApiResponse> call, Throwable t) {
                android.util.Log.d("onFailure", t.toString());
                t.printStackTrace();
                PROXIMITY_RADIUS += 10000;
            }
        });
    }

    public void displayMap(List<Result> results){
        MainActivity.restaurants = (ArrayList<Result>) results;
        // This loop will go through all the results and add marker on each location.
        for (int i = 0; i < results.size(); i++) {
              String placeName = results.get(i).getName();
            String vicinity = results.get(i).getVicinity();

            MarkerOptions markerOptions = new MarkerOptions();

            LatLng latLng = new LatLng(results.get(i).getGeometry().getLocation().getLat(), results.get(i).getGeometry().getLocation().getLng());
            // Location of Marker on Map
            markerOptions.position(latLng);
            // Title for Marker
            markerOptions.title(placeName + " : " + vicinity);

            // Color or drawable for marker
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            // add marker
            Marker marker = googleMap.addMarker(markerOptions);
            markers.add(marker);

            //Added a HashMap
            mMarkerMap.put(marker.getId(), results.get (i).getPlaceId ());

            // move map camera
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }


    }




    public boolean checkLocationPermission() {
        if (androidx.core.app.ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                androidx.core.app.ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // No explanation needed, we can request the permission.
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        Log.d("onRequestPerm"," init");
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            Log.d("onRequestPerm", " return");
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
                initMap();
                Log.d("onRequestPerm", " ok");
            }
        }
    }

    @Override
    public void onLocationChanged(@androidx.annotation.NonNull android.location.Location location) {
        if (location != null) {
            this.location = location;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

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
         List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
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
            LatLng latLng = new LatLng(getResultFromPlace(place).getGeometry().getLocation().getLat(), getResultFromPlace(place).getGeometry().getLocation().getLng());
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

}