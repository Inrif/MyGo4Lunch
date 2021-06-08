package com.a.mygo4lunch.view.fragments;


import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.a.mygo4lunch.R;


public class NotificationsFragment extends PreferenceFragmentCompat {

    //FIELDS
    private static final String PREF_NOTIFICATION_KEY = "notification_firebase";

    private android.content.SharedPreferences.OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener;

    @Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource (R.xml.notifications_settings, rootKey);

        mOnSharedPreferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals (PREF_NOTIFICATION_KEY)) {
                SwitchPreferenceCompat switchPreference = findPreference (key);
                java.util.Objects.requireNonNull (switchPreference)
                        .setSwitchTextOn ("Notifications ok");
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume ();

        getPreferenceScreen ()
                .getSharedPreferences ()
                .registerOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);

        SwitchPreferenceCompat switchPreferenceCompat = findPreference (PREF_NOTIFICATION_KEY);
        java.util.Objects.requireNonNull (switchPreferenceCompat).setSwitchTextOn ("Notifications ok");
    }

    @Override
    public void onPause() {
        super.onPause ();
        getPreferenceScreen ()
                .getSharedPreferences ()
                .unregisterOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);
    }
}
