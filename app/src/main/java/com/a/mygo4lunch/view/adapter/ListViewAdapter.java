package com.a.mygo4lunch.view.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.a.mygo4lunch.models.Result;
import com.a.mygo4lunch.view.adapter.ListViewAdapter.ItemRestaurantViewHolder;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Hounsa Romuald on 3/20/21.
 */
public class ListViewAdapter extends Adapter<ItemRestaurantViewHolder> {


    private static final String BASE_PHOTO_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    private static final String API_KEY_PLACES = "AIzaSyDSJqc7yJL30pE2rJPmH1DvbJnIxWKPGb8";
    public static java.util.List<Result> restaurants;
    private final android.content.Context mContext;

    public interface onClickRestaurantItemListener {
        void onClickRestaurantItem(int position);
    }

    public static class ItemRestaurantViewHolder extends ViewHolder {

        private android.widget.TextView mRestaurantName;
        private android.widget.TextView mRestaurantAddress;
        private android.widget.TextView mRestaurantOpenHour;
        private android.widget.TextView mRestaurantWorkersNumber;
        private android.widget.TextView mRestaurantDistance;
        private android.widget.ImageView mRestaurantImage;
        private android.widget.ImageView mRestaurantStar1;
        private android.widget.ImageView mRestaurantStar2;
        private android.widget.ImageView mRestaurantStar3;

        public ItemRestaurantViewHolder(@NonNull android.view.View itemView) {
            super (itemView);
            mRestaurantName = itemView.findViewById(com.a.mygo4lunch.R.id.item_restaurant_name);
            mRestaurantAddress = itemView.findViewById(com.a.mygo4lunch.R.id.item_restaurant_category_and_adress);
            mRestaurantOpenHour = itemView.findViewById(com.a.mygo4lunch.R.id.restaurant_hour);
  //          mRestaurantWorkersNumber = itemView.findViewById(com.a.mygo4lunch.R.id.item_number_worker);
            mRestaurantDistance = itemView.findViewById(com.a.mygo4lunch.R.id.item_restaurant_distance);
            mRestaurantImage = itemView.findViewById(com.a.mygo4lunch.R.id.item_restaurant_image);
  //          mRestaurantStar1 = itemView.findViewById(com.a.mygo4lunch.R.id.star1);
    //        mRestaurantStar2 = itemView.findViewById(com.a.mygo4lunch.R.id.star2);
      //      mRestaurantStar3  = itemView.findViewById(com.a.mygo4lunch.R.id.star3);

        }


    }


    public ListViewAdapter(java.util.List<Result> exampleList, android.content.Context mContext) {
        restaurants = exampleList;
        this.mContext = mContext;
    }

    @Override
    public ListViewAdapter.ItemRestaurantViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View itemView = android.view.LayoutInflater.from(parent.getContext())
                .inflate(com.a.mygo4lunch.R.layout.restaurant_item, parent, false);

        ItemRestaurantViewHolder evh = new ItemRestaurantViewHolder (itemView);
        return evh;

    }


    @android.annotation.SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ItemRestaurantViewHolder holder, int position) {

       Result currentItem = restaurants.get(position);



            holder.mRestaurantName.setText ( currentItem.getName ());
            holder.mRestaurantAddress.setText (""+ currentItem.getVicinity ());


//        if (currentItem.getOpeningHours ().getOpenNow ()!=null) {
//            holder.mRestaurantOpenHour.setText (com.a.mygo4lunch.R.string.restaurant_open);
//        } else {
//            holder.mRestaurantOpenHour.setText (com.a.mygo4lunch.R.string.not_open_yet);
//        }

//distance
        if (currentItem.getGeometry ().getLocation ().getLat () > 0) {
            String dist = currentItem.getGeometry ().getLocation ().getLat ()  + " m";
            holder.mRestaurantDistance.setText (dist);
        }

//        holder.mRestaurantDistance.setText (""+ currentItem.getGeometry ());

      // holder.mRestaurantImage.setImageResource (Integer.parseInt (String.valueOf (currentItem.getPhotos ())));

            String path = getImageUrlFormat (currentItem);


            com.bumptech.glide.Glide.with(mContext).load(path)
                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_map_24)
                    .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
                    .into (holder.mRestaurantImage);

    }


    public static String getImageUrlFormat(Result photoReference) {
        String imageReference = photoReference.getPhotos().get(0).getPhotoReference();
        String endOfUrl = "&sensor=false&key=";
        return BASE_PHOTO_URL + imageReference + endOfUrl + API_KEY_PLACES;
    }



    @Override
    public int getItemCount() {
        return restaurants.size ();
    }

}




