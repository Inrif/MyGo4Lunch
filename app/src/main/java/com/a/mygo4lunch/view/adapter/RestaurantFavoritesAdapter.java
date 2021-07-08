package com.a.mygo4lunch.view.adapter;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;


import com.a.mygo4lunch.R;
import com.a.mygo4lunch.models.RestaurantFavoris;


import static com.a.mygo4lunch.tools.Constant.starsAccordingToRating;
import static com.a.mygo4lunch.tools.Constant.starsView;


public class RestaurantFavoritesAdapter extends com.firebase.ui.firestore.FirestoreRecyclerAdapter<RestaurantFavoris,
        com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.FavorisItemViewholder> {

    //FIELD
    private com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.favoritesClickListener mFavoritesClickListener;
    private static final String API_KEY_PLACES = "AIzaSyDVJ9xYciyDlgV1gsLn7u2agRyuO_ye6GI";


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options configuration
     */
    public RestaurantFavoritesAdapter(@androidx.annotation.NonNull com.firebase.ui.firestore.FirestoreRecyclerOptions<RestaurantFavoris> options, com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.favoritesClickListener listener) {
        super (options);
        this.mFavoritesClickListener = listener;
    }

    @androidx.annotation.NonNull
    @Override
    public com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.FavorisItemViewholder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.item_restaurant_favoris, parent, false);
        return new com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.FavorisItemViewholder (view, mFavoritesClickListener);
    }

    @Override
    protected void onBindViewHolder(@androidx.annotation.NonNull com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.FavorisItemViewholder holder, int i,
                                    @androidx.annotation.NonNull RestaurantFavoris restaurantFavoris) {

        holder.mRestaurantName.setText (restaurantFavoris.getName ());
        holder.mAddress.setText (restaurantFavoris.getAddress ());

        // Restaurants images
        if (restaurantFavoris.getPhotoReference ().isEmpty ()) {
            holder.mRestaurantImage.setImageResource (com.a.mygo4lunch.R.drawable.ic_baseline_perm_identity_24);
        } else {
            String path = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="
                    + restaurantFavoris.getPhotoReference () +
                    "&key=" + API_KEY_PLACES;

            com.bumptech.glide.Glide.with (holder.mRestaurantImage.getContext ())
                    .load (path)
                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_restaurant_24)
                    .apply (com.bumptech.glide.request.RequestOptions.centerCropTransform ())
                    .into (holder.mRestaurantImage);
        }

        //Stars according to rating level
        int rating;
        if (restaurantFavoris.getRating () > 0) {
            rating = starsAccordingToRating (restaurantFavoris.getRating ());
        } else {
            rating = 0;
        }

        starsView (rating, holder.mStars1, holder.mStars2, holder.mStars3);
    }

    //interface to set the click
    public interface favoritesClickListener {
        void onClickItemResto(int position);
    }

    //ViewHolder
    public class FavorisItemViewholder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {

        @butterknife.BindView(R.id.restaurant_name)
        android.widget.TextView mRestaurantName;
        @butterknife.BindView(R.id.restaurant_category_and_adress)
        android.widget.TextView mAddress;
        @butterknife.BindView(R.id.restaurant_image)
        android.widget.ImageView mRestaurantImage;
        @butterknife.BindView(R.id.star_1)
        android.widget.ImageView mStars1;
        @butterknife.BindView(R.id.star_2)
        android.widget.ImageView mStars2;
        @butterknife.BindView(R.id.star_3)
        android.widget.ImageView mStars3;

        com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.favoritesClickListener mFavoritesClickListener;

        FavorisItemViewholder(@androidx.annotation.NonNull android.view.View itemView, com.a.mygo4lunch.view.adapter.RestaurantFavoritesAdapter.favoritesClickListener listener) {
            super (itemView);
            this.mFavoritesClickListener = listener;
            butterknife.ButterKnife.bind (this, itemView);
            itemView.setOnClickListener (this);
        }

        @Override
        public void onClick(android.view.View v) {
            mFavoritesClickListener.onClickItemResto (getAdapterPosition ());
        }
    }

}
