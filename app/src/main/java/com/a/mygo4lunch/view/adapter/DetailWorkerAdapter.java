package com.a.mygo4lunch.view.adapter;

import com.a.mygo4lunch.models.User;

/**
 * Created by  on 4/25/21.
 */
public class DetailWorkerAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<DetailWorkerAdapter.DetailViewHolder> {

    //FIELD
    private java.util.List<User> mUsers;

    //construtor
    public DetailWorkerAdapter(java.util.List<com.a.mygo4lunch.models.User> users) {
        mUsers = users;
    }

    @androidx.annotation.NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View view = android.view.LayoutInflater.from (parent.getContext ())
                .inflate (com.a.mygo4lunch.R.layout.user_item, parent, false);
        return new DetailViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull DetailViewHolder holder, int position) {
        User users = mUsers.get (position);
        android.content.res.Resources resources = holder.itemView.getResources ();

        String text = users.getUsername () + resources.getString (com.a.mygo4lunch.R.string.is_joining);
        holder.mTextView.setText (text);

        com.bumptech.glide.Glide.with (holder.mImageView.getContext ())
                .load (users.getUrlPicture ())
                .error (com.a.mygo4lunch.R.drawable.ic_baseline_perm_identity_24)
                .apply (com.bumptech.glide.request.RequestOptions.circleCropTransform ())
                .into (holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return ( mUsers != null ) ? mUsers.size () : 0;
    }

    //ViewHolder
    class DetailViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @butterknife.BindView(com.a.mygo4lunch.R.id.user_avatar)
        android.widget.ImageView mImageView;
        @butterknife.BindView(com.a.mygo4lunch.R.id.user_name)
        android.widget.TextView mTextView;

        DetailViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super (itemView);
            butterknife.ButterKnife.bind (this, itemView);
        }
    }
}
