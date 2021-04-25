package com.a.mygo4lunch.view.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.a.mygo4lunch.R.id;
import com.a.mygo4lunch.R.layout;
import com.a.mygo4lunch.R.string;
import com.a.mygo4lunch.firebase.UserHelper;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.internal.Utils;

import static butterknife.ButterKnife.*;
import static com.a.mygo4lunch.firebase.UserHelper.*;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class ConnectionActivity extends AppCompatActivity {

    public  static FirebaseAuth mFirebaseAuth;

    @androidx.annotation.Nullable
    public  static FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser ();
    }


    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;

    @BindView(id.design)
    ConstraintLayout mConstraintLayout;
    @BindView(id.email_login_button)
    android.widget.Button emailButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (layout.activity_connection);
        bind (this); //Configure Butterknife
        this.configureFirebaseAuth (); // configure firebase

    }



//    @Override
//    protected void onResume() {
//        super.onResume ();
//        // Checks if user is signed in (non-null)
//        if (FirebaseAuth.getInstance ().getCurrentUser () != null) {
//            this.startMainActivity ();
//        }
//    }

    private void configureFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance ();
    }

    @butterknife.OnClick({id.email_login_button,
            id.google_login_button,
            id.facebook_login_button,
            id.twitter_login_button})
    public void onClickLoginButton(View view) {
        switch (view.getId ()){
            case id.email_login_button:
                this.startSignInActivityEmail ();
                break;
            case id.google_login_button:
                this.startSignInActivityGoogle();
                break;
            case id.facebook_login_button:
                this.startSignInActivityFacebook ();
                break;
            case id.twitter_login_button:
                this.startSignInActivityTwitter ();
                break;
        }
       this.startSignInActivityEmail();


    }




    private void startSignInActivityEmail() {

        startActivityForResult(
                com.firebase.ui.auth.AuthUI.getInstance()
                        .createSignInIntentBuilder()
       //                 .setTheme(com.a.mygo4lunch.R.style.LoginTheme)
                        .setAvailableProviders(
                                java.util.Collections.singletonList (new IdpConfig.EmailBuilder ().build ()))
                        .setIsSmartLockEnabled(false, true)
          //              .setLogo(com.a.mygo4lunch.R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);
    }

    private void startSignInActivityGoogle() {

        startActivityForResult(
                com.firebase.ui.auth.AuthUI.getInstance()
                        .createSignInIntentBuilder()
       //                 .setTheme(com.a.mygo4lunch.R.style.LoginTheme)
                        .setAvailableProviders(
                                java.util.Collections.singletonList (new IdpConfig.GoogleBuilder ().build ()))
                        .setIsSmartLockEnabled(false, true)
          //              .setLogo(com.a.mygo4lunch.R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);

    }private void startSignInActivityFacebook() {

        startActivityForResult(
                com.firebase.ui.auth.AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                                java.util.Collections.singletonList (new IdpConfig.FacebookBuilder ().build ()))
                        .setIsSmartLockEnabled(false, true)
          //              .setLogo(com.a.mygo4lunch.R.drawable.ic_logo_auth)
                        .build(),
                RC_SIGN_IN);

    }

    private void startSignInActivityTwitter() {
        startActivityForResult (
                com.firebase.ui.auth.AuthUI.getInstance ()
                        .createSignInIntentBuilder ()
                        .setAvailableProviders (
                                java.util.Collections.singletonList (new com.firebase.ui.auth.AuthUI.IdpConfig.TwitterBuilder ().build ()))
                        .setIsSmartLockEnabled (false, true)
                        .build (),
                RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        // Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn (requestCode, resultCode, data);

    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {

        IdpResponse response = IdpResponse.fromResultIntent (data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK ) { // SUCCESS
              showSnackBar (this.mConstraintLayout, getString (string.connection_succeed));
//                this.startMainActivity ();

                if (getCurrentUser () != null) {
                    this.createUserInFirestore ();
                   this.startMainActivity ();

                }


            } else { // ERRORS
                if (response == null) {
                   showSnackBar (this.mConstraintLayout, getString (string.error_authentication_canceled));
                } else if (java.util.Objects.requireNonNull (response.getError ()).getErrorCode () == com.firebase.ui.auth.ErrorCodes.NO_NETWORK) {
                   showSnackBar (this.mConstraintLayout, getString (string.error_no_internet));
                } else if (response.getError ().getErrorCode () == com.firebase.ui.auth.ErrorCodes.UNKNOWN_ERROR) {
                   showSnackBar (this.mConstraintLayout, getString (string.error_unknown_error));
                }
            }
        }
    }

    /**
     * Http request that create user in firestore
     */
    private void createUserInFirestore() {

        if (getCurrentUser () != null) {

            String urlPicture = ( getCurrentUser ().getPhotoUrl () != null ) ? getCurrentUser ().getPhotoUrl ().toString () : null;
            String username = getCurrentUser ().getDisplayName ();
            String resto = "";
            String placeId = "";
            String uid = getCurrentUser ().getUid ();
            createUser (username, urlPicture, resto, placeId, uid).addOnFailureListener (this.onFailureListener ());
        }

    }


    private void startMainActivity() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity (intent);
    }
    //Show Snack Bar with a message
    private void showSnackBar(ConstraintLayout constraintLayout, String message) {
        mConstraintLayout = constraintLayout;
        Snackbar.make (constraintLayout, message, Snackbar.LENGTH_SHORT).show ();
    }




// Handler Errors

    protected com.google.android.gms.tasks.OnFailureListener onFailureListener() {
        return e -> android.widget.Toast.makeText (getApplicationContext (), getString (com.a.mygo4lunch.R.string.error_unknown_error), android.widget.Toast.LENGTH_LONG).show ();
    }


    }
