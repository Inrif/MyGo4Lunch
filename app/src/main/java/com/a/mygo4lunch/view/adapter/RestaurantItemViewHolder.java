package com.a.mygo4lunch.view.adapter;

import android.content.Context;
import android.view.View;

import com.a.mygo4lunch.models.Result;
import com.a.mygo4lunch.view.adapter.ListViewAdapter.onClickRestaurantItemListener;
import com.bumptech.glide.RequestManager;

import butterknife.BindView;

import static com.a.mygo4lunch.tools.Constant.starsAccordingToRating;
import static com.a.mygo4lunch.tools.Constant.starsView;
import static java.util.Objects.requireNonNull;

/**
 * Created by  on 24/06/21.
 */
public class RestaurantItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder  implements View.OnClickListener{

    @BindView(com.a.mygo4lunch.R.id.item_restaurant_name)
    android.widget.TextView mRestaurantName;
    @BindView(com.a.mygo4lunch.R.id.item_restaurant_category_and_adress)
    android.widget.TextView mAddress;
    @BindView(com.a.mygo4lunch.R.id.restaurant_hour)
    android.widget.TextView mHourRestaurant;
    @BindView(com.a.mygo4lunch.R.id.item_restaurant_distance)
    android.widget.TextView mDistance;
    @BindView(com.a.mygo4lunch.R.id.item_restaurant_image)
    android.widget.ImageView mRestaurantImage;
    @BindView(com.a.mygo4lunch.R.id.worker_icon_item_restaurant)
    android.widget.LinearLayout mLinearLayout;
    @BindView(com.a.mygo4lunch.R.id.workers_number)
    android.widget.TextView getWorkersNumbers;
    @BindView(com.a.mygo4lunch.R.id.star_1)
    android.widget.ImageView mStars1;
    @BindView(com.a.mygo4lunch.R.id.star_2)
    android.widget.ImageView mStars2;
    @BindView(com.a.mygo4lunch.R.id.star_3)
    android.widget.ImageView mStars3;

    private static final String BASE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private static final String API_KEY_PLACES = "AIzaSyDVJ9xYciyDlgV1gsLn7u2agRyuO_ye6GI";
    private float[] distanceResults = new float[3];
//    private final Context mContext;
    private com.google.android.gms.maps.model.LatLng myLatLng;




    private ListViewAdapter.onClickRestaurantItemListener mListener;


    RestaurantItemViewHolder(@androidx.annotation.NonNull View itemView,
//                             Context context,
                             onClickRestaurantItemListener listener,com.google.android.gms.maps.model.LatLng latLng ) {
        super (itemView);
//        mContext = context;
        this.mListener = listener;
        butterknife.ButterKnife.bind (this, itemView);
        itemView.setOnClickListener (this);
        this.myLatLng = latLng;
    }


    void updateWithDetailRestaurant(Result restaurantDetail, RequestManager glide
//                                    String userLocation
//    com.google.android.gms.maps.model.LatLng latLng
    ) {

        String name;
        if (restaurantDetail.getName ().length () > 20) {
            name = restaurantDetail.getName ().substring (0, 20) + " ...";
        } else {
            name = restaurantDetail.getName ();
        }

        this.mRestaurantName.setText (name);

        this.mAddress.setText (restaurantDetail.getVicinity ());


        //Distance
        float distance;
        float results[] = new float[10];
        double restoLat = restaurantDetail.getGeometry().getLocation().getLat();
        double restoLng = restaurantDetail.getGeometry().getLocation().getLng();
        double myLatitude = myLatLng.latitude;
        double myLongitude = myLatLng.longitude;
        android.location.Location.distanceBetween(myLatitude, myLongitude, restoLat, restoLng,results);
        distance = results[0];
        String dist =  Math.round(distance)+"m";
        mDistance.setText(dist);

//
//        if (( restaurantDetail.getOpeningHours ().getOpenNow ()))
//            this.mHourRestaurant.setText (com.a.mygo4lunch.R.string.restaurant_open);
//        else {
//            this.mHourRestaurant.setText (com.a.mygo4lunch.R.string.not_open_yet);
//        }

        // Restaurants images


        // Images
        if (restaurantDetail.getPhotos() != null && !restaurantDetail.getPhotos().isEmpty()){
            glide.load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+restaurantDetail.getPhotos().get(0).getPhotoReference()+"&key="+ API_KEY_PLACES).into(mRestaurantImage);
        } else {
            this.mRestaurantImage.setImageResource(com.a.mygo4lunch.R.drawable.ic_baseline_map_24);
        }


//
//// Restaurants images
//        if (restaurantDetail.getPhotos ().get (0).getPhotoReference ().isEmpty ()) {
//            this.mRestaurantImage.setImageResource (com.a.mygo4lunch.R.drawable.ic_baseline_map_24);
//        } else {
//            String path = getImageUrlFormat (restaurantDetail);
////            glide.with(mContext)
////                    .load (path)
////                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_map_24)
////                    .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
////                    .into (mRestaurantImage);
////
//
//      glide.load(path)
//                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_map_24)
//                    .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
//                    .into (this.mRestaurantImage);
//        }

//
//
//
//
//        //distance
//
//        // Display Distance
//        getDistance(userLocation,restaurantDetail.getGeometry().getLocation());
//        String distance = Integer.toString(Math.round(distanceResults[0]));
//        this.mDistance.setText(itemView.getResources().getString(com.a.mygo4lunch.R.string.list_unit_distance, distance));
//
//
//
////        //workers
//       if (restaurantDetail.getWorkers () > 0) {
//           mLinearLayout.setVisibility (android.view.View.VISIBLE);
//            String text = "(" + restaurantDetail.getWorkers () + ")";
//            getWorkersNumbers.setText (text);
//        } else {
//            mLinearLayout.setVisibility (android.view.View.INVISIBLE);
//       }

//        //restaurant distance
        //  restaurantDistance(distance , currentItem.getGeometry().getLocation());
//        String distance = Math.round(distanceResults[0]) + "m";
//        getDistance(distance , restaurantDetail.getGeometry ().getLocation ());
//        mDistance.setText (distance);




//        //Stars according to rating level
        int rating;
        if (restaurantDetail.getRating () > 0) {
            rating = starsAccordingToRating (restaurantDetail.getRating ());
        } else {
            rating = 0;
        }
        starsView (rating, mStars1, mStars2, mStars3);
    }


    public static String getImageUrlFormat(Result photoReference) {
        String imageReference = photoReference.getPhotos().get(0).getPhotoReference();
        String endOfUrl = "&sensor=false&key=";
        return BASE_PHOTO_URL + imageReference + endOfUrl + API_KEY_PLACES;
    }

    private void getDistance(String startLocation, com.a.mygo4lunch.models.Location endLocation){
        String[] separatedStart = startLocation.split(",");
        double startLatitude = Double.parseDouble(separatedStart[0]);
        double startLongitude = Double.parseDouble(separatedStart[1]);
        double endLatitude = endLocation.getLat();
        double endLongitude = endLocation.getLng();
        android.location.Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude,distanceResults);
    }


    @Override
    public void onClick(View v) {
        mListener.onClickRestaurantItem (getAdapterPosition ());


    }
}
