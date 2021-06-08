package com.a.mygo4lunch.view.fragments;

import androidx.preference.PreferenceFragmentCompat;

import com.a.mygo4lunch.R;



public class SettingsHeaders extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource (R.xml.settings_headers, rootKey);
    }
}
