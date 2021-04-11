package com.example.servicereminder.NotificationSetup;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.servicereminder.R;

import org.xmlpull.v1.XmlPullParser;

public class SettingsActivity extends AppCompatActivity {

    private final Integer[] soundArray = {R.raw.done_for_you, R.raw.goes_without_saying, R.raw.got_it_done, R.raw.hasty_ba_dum_tss, R.raw.juntos, R.raw.percussion_sound, R.raw.piece_of_cake, R.raw.pristine, R.raw.swiftly};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        soundList();
    }

//    private void soundList(){
//        XmlPullParser parser = .getXml(myResource);
//        AttributeSet attributes = Xml.asAttributeSet(parser);
//        ListPreference listPreference = new ListPreference(this, attributes, );
//        listPreference.setKey("notificationSounds");
//        listPreference.setOnPreferenceClickListener(preference -> {
//            playSound(preference.getOrder());
//            return false;
//        });
//    }

    private void playSound(int soundTrack){
        MediaPlayer mPlayer = MediaPlayer.create(this, soundTrack);
        mPlayer .start();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}