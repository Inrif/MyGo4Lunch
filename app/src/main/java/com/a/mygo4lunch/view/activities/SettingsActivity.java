package com.a.mygo4lunch.view.activities;


import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.a.mygo4lunch.R;
import com.a.mygo4lunch.view.fragments.*;


public class SettingsActivity extends androidx.appcompat.app.AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @butterknife.BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar mToolbar;





    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView(com.a.mygo4lunch.R.layout.settings_activity);

        getSupportFragmentManager ()
                .beginTransaction ()
                .replace (R.id.settings, new SettingsHeaders ())
                .commit ();

        setSupportActionBar(mToolbar);



        this.configureToolBar (getResources ().getString (R.string.settings));
        if (mToolbar != null) {
            java.util.Objects.requireNonNull (getSupportActionBar ()).setDisplayHomeAsUpEnabled (true);
            getSupportActionBar ().setDisplayShowHomeEnabled (true);
        }

    }

    private void configureToolBar(String string) {
        if (this.getToolbar () != null) {
            getToolbar ().setTitle (string);
            setSupportActionBar (this.getToolbar ());
        }
    }


    @androidx.annotation.Nullable
   androidx.appcompat.widget.Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final android.os.Bundle args = pref.getExtras ();
        final androidx.fragment.app.Fragment fragment = getSupportFragmentManager ().getFragmentFactory ().instantiate (
                getClassLoader (),
                pref.getFragment ());
        fragment.setArguments (args);
        fragment.setTargetFragment (caller, 0);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager ().beginTransaction ()
                .replace (R.id.settings, fragment)
                .addToBackStack (null)
                .commit ();

       configureToolBar (pref.getTitle ().toString ());
        return true;
    }
}
