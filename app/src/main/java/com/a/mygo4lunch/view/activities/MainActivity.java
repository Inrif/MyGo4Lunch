package com.a.mygo4lunch.view.activities;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;


import com.a.mygo4lunch.R;
import com.a.mygo4lunch.models.Result;
import com.a.mygo4lunch.tools.Constant;
import com.a.mygo4lunch.view.fragments.*;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.*;
import com.a.mygo4lunch.R.*;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.a.mygo4lunch.view.activities.ConnectionActivity.mFirebaseAuth;


public class MainActivity extends AppCompatActivity {


    private MapFragment mMapFragment = new MapFragment();
    private ListViewFragment mListViewFragmentFragment = new ListViewFragment ();
    private WorkmatesFragment mWorkmatesFragment = new WorkmatesFragment ();
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static ArrayList<Result> restaurants = new ArrayList();
    private FirebaseUser user;
    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        //Initilaze Places
        Places.initialize(getApplicationContext(), Constant.PLACE_KEY);

        drawerLayout = findViewById(id.activity_main_layout_drawer);
        navigationView = findViewById(R.id.navigation_drawer);
        toolbar = findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        configureActionBarDrawer();


        user = this.getCurrentUser ();

        // 2 - Configure and show home fragment
        this.configureAndShowMapFragment(mMapFragment);



        BottomNavigationView navigation = ( BottomNavigationView) findViewById(id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.updateNavigationHeader ();

        //  toolbar.setTitle("I'm hungry");


    }



    private OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case id.action_map:
                                     toolbar.setTitle("I'm hungry");
                    configureAndShowMapFragment(mMapFragment);
                    return true;

                case id.action_list_view:
                    toolbar.setTitle("List View");
                    configureAndShowMapFragment(mListViewFragmentFragment);

                    return true;

                case id.action_workmates:
                                     toolbar.setTitle("Workmates");
                    configureAndShowMapFragment(mWorkmatesFragment);

                    return true;

                case id.logoutFragment:
                     signOutCurrentUser();

                    return true;


            }
            return false;
        }
    };


    // --------------
    // FRAGMENTS
    // --------------


    private void configureAndShowMapFragment(Fragment mFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(id.frame_main, mFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void configureActionBarDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }



    private void updateNavigationHeader() {

        final View headerNav;
        headerNav = navigationView.getHeaderView (0);


        ImageView imageViewNav = headerNav.findViewById (R.id.nav_header_profile_picture);
        TextView textViewNavName = headerNav.findViewById (id.nav_header_profile_name);
        TextView textViewNavMail = headerNav.findViewById (id.nav_header_profile_email);

        if (user != null) {
            // ImageView: User image
            if (user.getPhotoUrl () != null) {
                com.bumptech.glide.Glide.with (this)
                        .load (user.getPhotoUrl ())
                        .circleCrop ()
                        .into (imageViewNav);
            }

            // TextView: Username and email
            final String username = android.text.TextUtils.isEmpty (user.getDisplayName ()) ? getString (R.string.no_name_found) :
                    user.getDisplayName ();

            final String email = android.text.TextUtils.isEmpty (user.getEmail ()) ? getString (R.string.no_mail_found) :
                    user.getEmail ();

            textViewNavName.setText (username);
            textViewNavMail.setText (email);
        }
    }


    private void signOutCurrentUser() {
       showSnackBar (this.drawerLayout, "sign out");
        if (user != null) {
            com.google.firebase.auth.FirebaseAuth.getInstance ().signOut ();
            startAuthActivity ();
            finishAffinity ();
        }
    }

    // Intent used for navigation item
    private void startAuthActivity() {
        android.content.Intent intent = new android.content.Intent (this, ConnectionActivity.class);
        startActivity (intent);
    }
    public static void showSnackBar(View view, String message) {
        com.google.android.material.snackbar.Snackbar.make (view, message, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show ();
    }

    @androidx.annotation.Nullable
    protected com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser ();
    }

}