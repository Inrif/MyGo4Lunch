package com.a.mygo4lunch.view.adapter;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.a.mygo4lunch.R.layout;
import com.a.mygo4lunch.R.string;
import com.a.mygo4lunch.models.User;
import com.a.mygo4lunch.view.adapter.WorkmateAdaper.ItemWorkmateViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;

/**
 * Created by Romuald Hounsa on 4/9/21.
 */
public class WorkmateAdaper extends FirestoreRecyclerAdapter<User, ItemWorkmateViewHolder> {


    //FIELD
    private userClickListener mUserClickListener;

    //constructor

    public WorkmateAdaper(@androidx.annotation.NonNull FirestoreRecyclerOptions<User> options, userClickListener mUserClickListener) {
        super (options);
        this.mUserClickListener = mUserClickListener;
    }

    @NonNull
    @Override
    public WorkmateAdaper.ItemWorkmateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = android.view.LayoutInflater.from (parent.getContext ())
                .inflate (com.a.mygo4lunch.R.layout.user_item, parent, false);
        return new ItemWorkmateViewHolder (view, mUserClickListener);
    }




    @Override
    protected void onBindViewHolder(@NonNull ItemWorkmateViewHolder holder, int i, @NonNull User user) {
        android.content.res.Resources resource = holder.itemView.getContext ().getResources ();
        String text;

        if (user.getRestaurantName () != null) {
            if (!user.getRestaurantName ().trim ().equals ("")) {
                text = user.getUsername () + " " + resource.getString (string.is_eating_at) + user.getRestaurantName ();
                holder.mTextView.setTextColor (resource.getColor (android.R.color.white));
            } else {
                text = user.getUsername () + " " + resource.getString (string.hasn_t_decided);
                holder.mTextView.setTextColor (resource.getColor (android.R.color.darker_gray));
            }
            holder.mTextView.setText (text);

            com.bumptech.glide.Glide.with (holder.mImageView.getContext ())
                    .load (user.getUsername ())
                    .error (com.a.mygo4lunch.R.drawable.ic_baseline_restaurant_24)
                    .apply (com.bumptech.glide.request.RequestOptions.circleCropTransform ())
                    .into (holder.mImageView);
        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ItemWorkmateViewHolder extends ViewHolder implements View.OnClickListener{
        @BindView(com.a.mygo4lunch.R.id.user_avatar)
        ImageView mImageView;
        @BindView(com.a.mygo4lunch.R.id.user_name)
        TextView mTextView;

        userClickListener mUserClickListener;

        public ItemWorkmateViewHolder(@NonNull View itemView, userClickListener listener) {
            super (itemView);
            this.mUserClickListener = listener;
            butterknife.ButterKnife.bind (this, itemView);
            itemView.setOnClickListener (this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            mUserClickListener.onClickItemWorkmate (getAdapterPosition ());

        }
    }


    public interface userClickListener {
        void onClickItemWorkmate(int position);
    }



}



