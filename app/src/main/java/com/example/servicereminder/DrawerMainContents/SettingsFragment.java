package com.example.servicereminder.DrawerMainContents;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.servicereminder.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        soundPreferences();
    }

    private void soundPreferences() {
        ListPreference listPreference = findPreference("notificationSounds");
        assert listPreference != null;
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals("Done for You")) {
                playSound(R.raw.done_for_you);
                listPreference.setValueIndex(0);
            } else if (newValue.equals("Goes Without You")) {
                playSound(R.raw.goes_without_saying);
                listPreference.setValueIndex(1);
            } else if (newValue.equals("Swiftly")) {
                playSound(R.raw.swiftly);
                listPreference.setValueIndex(2);
            } else {
                playSound(R.raw.piece_of_cake);
                listPreference.setValueIndex(3);
            }
            return false;
        });
    }

    private void playSound(int soundTrack) {
        MediaPlayer mPlayer = MediaPlayer.create(getActivity(), soundTrack);
        mPlayer.start();
    }
}

