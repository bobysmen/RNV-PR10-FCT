package com.example.rnv_pr10_fct.ui.preference;

import android.os.Bundle;

import com.example.rnv_pr10_fct.R;

import androidx.preference.PreferenceFragmentCompat;

public class Preference extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
