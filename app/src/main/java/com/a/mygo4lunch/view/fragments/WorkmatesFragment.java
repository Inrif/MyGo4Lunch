package com.a.mygo4lunch.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a.mygo4lunch.R;
import com.a.mygo4lunch.firebase.UserHelper;
import com.a.mygo4lunch.models.User;
import com.a.mygo4lunch.view.adapter.WorkmateAdaper;
import com.a.mygo4lunch.view.adapter.WorkmateAdaper.userClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions.Builder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.a.mygo4lunch.view.activities.MainActivity.restaurants;


public class WorkmatesFragment extends Fragment  implements userClickListener{

    @butterknife.BindView(R.id.workmate_recyclerview)
    RecyclerView mRecyclerView;

    private List<User> mWorkers = new java.util.ArrayList<> ();
    private WorkmateAdaper adapter;

    public WorkmatesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setHasOptionsMenu(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance ();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder ()
                .build ();
        db.setFirestoreSettings (settings);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate (R.layout.fragment_workmates, container, false);

        mRecyclerView = view.findViewById (R.id.workmate_recyclerview);
        mRecyclerView.setLayoutManager (new LinearLayoutManager (view.getContext ()));

        initListAdapter ();
//        timber.log.Timber.i ("onCreate: %s", mWorkers);

        android.util.Log.d (TAG, "Result: "+ mWorkers);

        return view;
    }

    private void initListAdapter() {
        Query query = UserHelper.getQueryUsers (mWorkers);
        FirestoreRecyclerOptions<User> options = new Builder<User> ()
                .setQuery (query, User.class)
                .build ();

        adapter = new WorkmateAdaper (options, this);
        mRecyclerView.setAdapter (adapter);
    }


    @Override
    public void onClickItemWorkmate(int position) {
        if (mWorkers.get (position).getPlaceId ().trim ().equals ("")) {
            showSnackBar (this.mRecyclerView, getString (R.string.no_choice_restaurant_workers));
        } else {
            android.content.Intent intent = new android.content.Intent (getContext (), com.a.mygo4lunch.models.RestaurantDetail.class);
            intent.putExtra ("placeId", mWorkers.get (position).getPlaceId ());
            intent.putExtra ("restaurantName", mWorkers.get (position).getRestaurantName ());
            startActivity (intent);
        }
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    public static void showSnackBar(View view, String message) {
        com.google.android.material.snackbar.Snackbar.make (view, message, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show ();
    }


}