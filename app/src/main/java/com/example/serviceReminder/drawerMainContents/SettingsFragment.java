package com.example.serviceReminder.drawerMainContents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.serviceReminder.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private int mSelected = -1;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);

        setSoundPicker();

    }

    private void setSoundPicker() {
        Preference preference = findPreference("notificationSounds");
        assert preference != null;
        EditTextPreference editTextPreference = findPreference("SoundPreference");
        final int[] sounds = {R.raw.got_it_done, R.raw.hasty_ba_dum_tss};
        final String[] soundsName = {"Got It Done", "Hasty Ba Dum Tss"};
        preference.setOnPreferenceClickListener(preference1 -> {


            AlertDialog.Builder build = new AlertDialog.Builder(getContext());
            build.setTitle("Choose Notification Sound");
            build.setCancelable(true);
            mSelected = -1;

            final DialogInterface.OnMultiChoiceClickListener onClick =
                    (dialog, which, isChecked) -> {

                        if (isChecked) {
                            if ((mSelected != -1) && (mSelected != which)) {
                                final int oldVal = mSelected;
                                final AlertDialog alert = (AlertDialog) dialog;
                                final ListView list = alert.getListView();
                                list.setItemChecked(oldVal, false);
                            }
                            mSelected = which;
                            playSound(sounds[which]);

                            assert editTextPreference != null;
                            editTextPreference.setText(soundsName[which]);
                        } else
                            mSelected = -1;
                    };
            build.setMultiChoiceItems(soundsName, null, onClick);
            build.setPositiveButton("Done", (dialog, which) -> {
                if (mSelected == -1) {
                    Toast.makeText(getContext(), "You didn't select anything.", Toast.LENGTH_SHORT).show();
                }
            });
            build.setNegativeButton("Cancel", null);
            build.show();
            return false;
        });
    }

    private void playSound(int soundTrack) {
        MediaPlayer mPlayer = MediaPlayer.create(getActivity(), soundTrack);
        mPlayer.start();
    }
}

