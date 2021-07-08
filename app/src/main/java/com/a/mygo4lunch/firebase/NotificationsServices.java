package com.a.mygo4lunch.firebase;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.a.mygo4lunch.R;
import com.a.mygo4lunch.models.User;
import com.a.mygo4lunch.view.activities.MainActivity;


public class NotificationsServices extends FirebaseMessagingService {

    //FIELDS
    private static final String PREF_NOTIFICATION = "notification_firebase";
    private String restaurant;
    private java.util.ArrayList<com.a.mygo4lunch.models.User> mUsers;

    @Override
    public void onMessageReceived(@androidx.annotation.NonNull RemoteMessage remoteMessage) {
        android.content.SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences (getApplicationContext ());

        //get restaurant name
        getRestaurantUser ();
        //get workers list
        mUsers = getRestaurantUserAndWorkersAdd ();
        //put Thread on sleep for 2 sec to make sure that the query are finish
        try {
            Thread.sleep (2000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        // send message if able
        if (remoteMessage.getNotification () != null &&
                sharedPreferences.getBoolean (PREF_NOTIFICATION, true) &&
                restaurant != null) {
            // Get message sent by Firebase
            String message = remoteMessage.getNotification ().getBody ();
            message += "It's time to go to " + restaurant + " with ";
            String message2 = createMessageNotification ();
            // Show message
            this.sendVisualNotification (message, message2);
        }
    }

    /**
     * Create Notification
     *
     * @param messageBody message receive by firebase
     */
    private void sendVisualNotification(String messageBody, String message) {

        //Create an Intent that will be shown when user will click on the Notification
        android.content.Intent intent = new android.content.Intent (this, MainActivity.class);
        android.app.PendingIntent pendingIntent = android.app.PendingIntent.getActivity (this.getApplicationContext (),
                0, intent, android.app.PendingIntent.FLAG_ONE_SHOT);

        //Create a Style for the Notification
        androidx.core.app.NotificationCompat.InboxStyle inboxStyle = new androidx.core.app.NotificationCompat.InboxStyle ();
        inboxStyle.setBigContentTitle (getString (R.string.app_name));
        inboxStyle.addLine (messageBody).addLine (message);

        // Create a Channel (Android 8)
        String channelId = getString (R.string.default_notification_channel_id);

        //Build a Notification object
        androidx.core.app.NotificationCompat.Builder notificationBuilder =
                new androidx.core.app.NotificationCompat.Builder (this, channelId)
                        .setSmallIcon (com.a.mygo4lunch.R.drawable.ic_baseline_restaurant_24)
                        .setContentTitle (getString (R.string.app_name))
                        .setContentText (getString (R.string.app_name))
                        .setAutoCancel (true)
                        .setSound (android.media.RingtoneManager.getDefaultUri (android.media.RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent (pendingIntent)
                        .setStyle (inboxStyle);

        //Add the Notification to the Notification Manager and show it.
        android.app.NotificationManager notificationManager = ( android.app.NotificationManager) getSystemService (android.content.Context.NOTIFICATION_SERVICE);

        // Support Version >= Android 8
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence channelName = getResources ().getString (R.string.message_from_go4lunch);
            int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
            android.app.NotificationChannel mChannel = new android.app.NotificationChannel (channelId, channelName, importance);
            java.util.Objects.requireNonNull (notificationManager).createNotificationChannel (mChannel);
        }

        //Show notification
        int NOTIFICATION_ID = 7;
        String NOTIFICATION_TAG = "Go4lunch";
        java.util.Objects.requireNonNull (notificationManager).notify (NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build ());

    }

    /**
     * get restaurant choice
     */
    private void getRestaurantUser() {
        com.google.firebase.firestore.Query query = UserHelper.getAllUsers ().whereEqualTo ("name",
                java.util.Objects.requireNonNull (com.google.firebase.auth.FirebaseAuth.getInstance ().getCurrentUser ()).getDisplayName ());
        query.get ().addOnCompleteListener (task -> {
            restaurant = "";
            if (task.isSuccessful ()) {
                for (com.google.firebase.firestore.QueryDocumentSnapshot documentSnapshot : java.util.Objects.requireNonNull (task.getResult ())) {
                    restaurant = java.util.Objects.requireNonNull (documentSnapshot.get ("restaurantName")).toString ();
                }
            }
        });
        try {
            Thread.sleep (2000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
    }

    /**
     * get ArrayList of workers who are chosen the same restaurant
     *
     * @return Workers ArrayList
     */
    private java.util.ArrayList<User> getRestaurantUserAndWorkersAdd() {
        com.google.firebase.firestore.Query query = UserHelper.getAllUsers ();
        java.util.ArrayList<User> users = new java.util.ArrayList<> ();
        query.get ().addOnCompleteListener (task -> {
            if (task.isSuccessful ()) {
                for (com.google.firebase.firestore.QueryDocumentSnapshot data : java.util.Objects.requireNonNull (task.getResult ())) {
                    if (java.util.Objects.requireNonNull (data.get ("restaurantName")).toString ().equals (restaurant)) {
                        User u = data.toObject (User.class);
                        users.add (u);
                    }
                }
            }
        });
        return users;
    }

    /**
     * Create a string with the workers name
     *
     * @return String message
     */
    private String createMessageNotification() {
        StringBuilder message = new StringBuilder ();
        for (int i = 0; i < mUsers.size (); i++) {
            if (!mUsers.get (i).getUsername ().
                    equals (java.util.Objects.requireNonNull (com.google.firebase.auth.FirebaseAuth.getInstance ().getCurrentUser ()).getDisplayName ())) {
                message.append (mUsers.get (i).getUsername ());
                if (i < mUsers.size () - 1) {
                    message.append (" and ");
                }
            }

        }
        return message.toString ();
    }
}
