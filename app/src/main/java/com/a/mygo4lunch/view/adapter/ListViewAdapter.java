package com.a.mygo4lunch.view.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.a.mygo4lunch.models.Result;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

/**
 * Created by Hounsa Romuald on 3/20/21.
 */
public class ListViewAdapter extends Adapter<RestaurantItemViewHolder> {


//    private static final String BASE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
//    private static final String API_KEY_PLACES = "AIzaSyDVJ9xYciyDlgV1gsLn7u2agRyuO_ye6GI";
//    public static java.util.List<Result> restaurants;
//    private final Context mContext;
//    private float[] distanceResults = new float[3];
   private com.google.android.gms.maps.model.LatLng myLatLng;

    //FILEDS
    private java.util.ArrayList<Result> mRestaurantList;
    private RequestManager glide;

    private onClickRestaurantItemListener mOnClickRestaurantitemListener;
    private String mLocation;
    private android.content.Context mContext;

    //constructor
    public ListViewAdapter(java.util.ArrayList<com.a.mygo4lunch.models.Result> restaurantList,
                           androidx.fragment.app.FragmentActivity activity,
                           com.a.mygo4lunch.view.adapter.ListViewAdapter.onClickRestaurantItemListener onClickRestaurantitemListener,
//                           String location,
//                           android.content.Context context
                           com.google.android.gms.maps.model.LatLng latLng,
                           RequestManager glide
    ) {

        mRestaurantList = restaurantList;
        this.glide = glide;
        this.myLatLng = latLng;
        this.mOnClickRestaurantitemListener = onClickRestaurantitemListener;
//        this.mContext = context;
    }

    @NonNull
    @Override
    public RestaurantItemViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from (parent.getContext ())
                .inflate (com.a.mygo4lunch.R.layout.restaurant_item, parent, false);
        return new RestaurantItemViewHolder (view,
//                mContext,
                mOnClickRestaurantitemListener,myLatLng);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemViewHolder holder, int position) {
        holder.updateWithDetailRestaurant (this.mRestaurantList.get (position), this.glide);
//        holder.updateWithDetailRestaurant (this.mRestaurantList.get (position), mLocation);
    }

    @Override
    public int getItemCount() {
        return ( mRestaurantList != null ) ? mRestaurantList.size () : 0;
    }

    //interface to listen the click
    public interface onClickRestaurantItemListener {
        void onClickRestaurantItem(int position);
    }

//    private final com.google.android.gms.maps.model.LatLng mCurrentLocation; //the current location of the user, used to determine the distance to the restaurant



//    public static class ItemRestaurantViewHolder extends ViewHolder implements android.view.View.OnClickListener{
//
//        private android.widget.TextView mRestaurantName;
//        private android.widget.TextView mRestaurantAddress;
//        private android.widget.TextView mRestaurantOpenHour;
//        private android.widget.TextView mRestaurantWorkersNumber;
//        private android.widget.TextView mRestaurantDistance;
//        private android.widget.ImageView mRestaurantImage;
//        private android.widget.ImageView mRestaurantStar1;
//        private android.widget.ImageView mRestaurantStar2;
//        private android.widget.ImageView mRestaurantStar3;
//
//
//
//        private ListViewAdapter.onClickRestaurantItemListener mListener;
//

////        public ItemRestaurantViewHolder(@androidx.annotation.NonNull android.view.View itemView,
////                                        onClickRestaurantItemListener listener) {
////            super (itemView);
////            this.mListener = listener;
////            butterknife.ButterKnife.bind (this, itemView);
////            itemView.setOnClickListener (this);
////
////            mRestaurantName = itemView.findViewById(id.item_restaurant_name);
////            mRestaurantAddress = itemView.findViewById(id.item_restaurant_category_and_adress);
////            mRestaurantOpenHour = itemView.findViewById(id.restaurant_hour);
////            //          mRestaurantWorkersNumber = itemView.findViewById(com.a.mygo4lunch.R.id.item_number_worker);
////            mRestaurantDistance = itemView.findViewById(id.item_restaurant_distance);
////            mRestaurantImage = itemView.findViewById(id.item_restaurant_image);
////            //          mRestaurantStar1 = itemView.findViewById(com.a.mygo4lunch.R.id.star1);
////            //        mRestaurantStar2 = itemView.findViewById(com.a.mygo4lunch.R.id.star2);
////            //      mRestaurantStar3  = itemView.findViewById(com.a.mygo4lunch.R.id.star3);
////
////
////
////        }
//
//
//        /**
//         * Called when a view has been clicked.
//         *
//         * @param v The view that was clicked.
//         */
//        @Override
//        public void onClick(android.view.View v) {
//            mListener.onClickRestaurantItem (getAdapterPosition ());
//        }
//    }
//
//
//    public ListViewAdapter(java.util.List<Result> exampleList, Context mContext,onClickRestaurantItemListener onClickRestaurantitemListener, com.google.android.gms.maps.model.LatLng currentLocation) {
//        restaurants = exampleList;
//        this.mContext = mContext;
//        this.mOnClickRestaurantitemListener = onClickRestaurantitemListener;
//        this.mCurrentLocation = currentLocation;
//    }
//
//    @Override
//    public ListViewAdapter.ItemRestaurantViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
//        android.view.View itemView = android.view.LayoutInflater.from(parent.getContext())
//                .inflate(com.a.mygo4lunch.R.layout.restaurant_item, parent, false);
//
////        ItemRestaurantViewHolder evh = new ItemRestaurantViewHolder (itemView);
////        return evh;
//
//        return new ItemRestaurantViewHolder (itemView, mOnClickRestaurantitemListener);
//
//
//    }
//
//
//    @android.annotation.SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull ListViewAdapter.ItemRestaurantViewHolder holder, int position) {
//
//       Result currentItem = restaurants.get(position);
//
//
//
//            holder.mRestaurantName.setText ( currentItem.getName ());
//            holder.mRestaurantAddress.setText (""+ currentItem.getVicinity ());
//
//
////        if (currentItem.getOpeningHours ().getOpenNow ()!=null) {
////            holder.mRestaurantOpenHour.setText (com.a.mygo4lunch.R.string.restaurant_open);
////        } else {
////            holder.mRestaurantOpenHour.setText (com.a.mygo4lunch.R.string.not_open_yet);
////        }
//
////distance
////        if (currentItem.getGeometry ().getLocation ().getLat () > 0) {
////            String dist = currentItem.getGeometry ().getLocation ().getLat ()  + " m";
////            holder.mRestaurantDistance.setText (dist);
////    }
//
//
//        //now determine the distance from the current users location to the location of the pojoPlace
//        double placeLat = currentItem.getGeometry ().getLocation().getLat ();
//        double placeLong = currentItem.getGeometry ().getLocation().getLng ();
//
//        try {
//            android.location.Location locA = new android.location.Location ("point A");
//            locA.setLatitude(mCurrentLocation.latitude);
//            locA.setLongitude(mCurrentLocation.longitude);
//
//            android.location.Location locB = new android.location.Location ("point B");
//            locB.setLatitude(placeLat);
//            locB.setLongitude(placeLong);
//
//            float mRestaurantDistance = locA.distanceTo(locB);
//            int distanceInt = (int) mRestaurantDistance;
//
//            holder.mRestaurantDistance.setText(String.format("%sm", String.valueOf(distanceInt)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            holder.mRestaurantDistance.setText(com.a.mygo4lunch.R.string.not_available);
//        }
//
//
////        com.google.android.gms.maps.model.LatLng location = currentItem.getGeometry ().getLocation ().getLng ();
////
////        int distance = com.a.mygo4lunch.tools.Constant.calculateDistance(mCurrentLocation, location);
////        holder.mRestaurantDistance.setText (distance);
//
//
////        // Distance
////        float[] results = new float[1];
////        android.location.Location.distanceBetween(com.a.mygo4lunch.models.Location.class, mCurrentLocation.longitude(),
////                Double.parseDouble(currentItem.getGeometry ().getLocation ().getLat ()),
////                Double.parseDouble(currentItem.getGeometry ().getLocation ().getLng()), results);
////        int distance = (int)results[0];
////        holder.mRestaurantDistance.setText((Integer.toString(distance))+"m");
////
//
//
//
////        //restaurant distance
////        //  restaurantDistance(distance , currentItem.getGeometry().getLocation());
////        String distance = Math.round(distanceResults[0]) + "m";
////        restaurantDistance(distance , currentItem.getGeometry().getLocation());
////        holder.mRestaurantDistance.setText (distance);
////        android.util.Log.d("TestDistance", distance);
//
////        holder.mRestaurantDistance.setText (""+ currentItem.getGeometry ());
//
//      // holder.mRestaurantImage.setImageResource (Integer.parseInt (String.valueOf (currentItem.getPhotos ())));
//
//            String path = getImageUrlFormat (currentItem);
//
//
//            com.bumptech.glide.Glide.with(mContext).load(path)
//                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_map_24)
//                    .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
//                    .into (holder.mRestaurantImage);
//
//    }
//
//
//    public static String getImageUrlFormat(Result photoReference) {
//        String imageReference = photoReference.getPhotos().get(0).getPhotoReference();
//        String endOfUrl = "&sensor=false&key=";
//        return BASE_PHOTO_URL + imageReference + endOfUrl + API_KEY_PLACES;
//    }
//
////    /**
////     * For calculate restaurant distance
////     *
////     * @param startLocation
////     * @param endLocation
////     */
////    private void restaurantDistance(String startLocation,  com.a.mygo4lunch.models.Location endLocation) {
////        String[] separatedStart = startLocation.split(",");
////        double startLatitude = Double.parseDouble(separatedStart[0]);
////        double startLongitude = Double.parseDouble(separatedStart[1]);
////        double endLatitude = endLocation.getLat();
////        double endLongitude = endLocation.getLng();
////        android.location.Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, distanceResults);
////    }
//
//
//
//    @Override
//    public int getItemCount() {
//        return restaurants.size ();
//    }
//
//
//    public interface onClickRestaurantItemListener {
//        void onClickRestaurantItem(int position);
//    }

}








