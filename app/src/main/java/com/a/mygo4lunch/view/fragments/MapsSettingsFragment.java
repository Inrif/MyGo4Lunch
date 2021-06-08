package com.a.mygo4lunch.view.fragments;


import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.a.mygo4lunch.R;


public class MapsSettingsFragment extends PreferenceFragmentCompat {

    //FIELDS
    private static final String PREF_ZOOM_KEY = "zoom_key";
    private static final String PREF_RADIUS_KEY = "radius_key";
    private static final String PREF_TYPE_KEY = "type_key";

    private android.content.SharedPreferences.OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener;

    @Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource (R.xml.map_preferences, rootKey);

        mOnSharedPreferenceChangeListener = (sharedPreferences, key) -> {

            if (key.equals (PREF_ZOOM_KEY)) {
                Preference preference = findPreference (key);
                java.util.Objects.requireNonNull (preference)
                        .setSummary (sharedPreferences.getString (key, "High") + getResources ().getString (R.string.zoom_level));
            }

            if (key.equals (PREF_RADIUS_KEY)) {
                Preference preference = findPreference (key);
                java.util.Objects.requireNonNull (preference)
                        .setSummary (sharedPreferences.getString (key, "500") + getResources ().getString (R.string.meters_radius));
            }

            if (key.equals (PREF_TYPE_KEY)) {
                Preference preference = findPreference (key);
                java.util.Objects.requireNonNull (preference)
                        .setSummary (sharedPreferences.getString (key, "restaurant"));
            }

        };
    }

    @Override
    public void onResume() {
        super.onResume ();
        getPreferenceScreen ()
                .getSharedPreferences ()
                .registerOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);

        Preference zoomPreference = findPreference (PREF_ZOOM_KEY);
        java.util.Objects.requireNonNull (zoomPreference).setSummary (getPreferenceScreen ()
                .getSharedPreferences ()
                .getString (PREF_ZOOM_KEY, "") + getResources ().getString (R.string.zoom_level));

        Preference radiusPreference = findPreference (PREF_RADIUS_KEY);
        java.util.Objects.requireNonNull (radiusPreference).setSummary (getPreferenceScreen ()
                .getSharedPreferences ()
                .getString (PREF_RADIUS_KEY, "") + getResources ().getString (R.string.meters_radius));

        Preference typePreference = findPreference (PREF_TYPE_KEY);
        java.util.Objects.requireNonNull (typePreference).setSummary (getPreferenceScreen ()
                .getSharedPreferences ()
                .getString (PREF_TYPE_KEY, ""));

    }

    @Override
    public void onPause() {
        super.onPause ();
        getPreferenceScreen ()
                .getSharedPreferences ()
                .unregisterOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);
    }
}
