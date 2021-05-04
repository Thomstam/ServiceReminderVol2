package com.example.servicereminder.DrawerMainContents;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.example.servicereminder.R;

import java.io.File;

public class NavigationHeaderPreferences extends Preference {

    View.OnClickListener imageClickListener;
    View.OnClickListener companyNameClickListener;
    private TextView companyName;
    private ImageView profilePicture;

    public NavigationHeaderPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        profilePicture = (ImageView) holder.findViewById(R.id.ProfilePicture);
        companyName = (TextView) holder.findViewById(R.id.companyNameSetText);
        profilePicture.setOnClickListener(imageClickListener);
        companyName.setOnClickListener(companyNameClickListener);
        setCompanyName();
        loadProfilePicture();
    }

    public void setProfilePictureClickListener(View.OnClickListener onClickListener) {
        imageClickListener = onClickListener;
    }

    public void setCompanyNameClickListener(View.OnClickListener onClickListener) {
        companyNameClickListener = onClickListener;
    }

    public void setCompanyName() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        companyName.setText(preferences.getString("companyName", ""));
    }

    public void setCompanyImage(Bitmap image) {
        profilePicture.setImageBitmap(image);
    }

    public void loadProfilePicture() {
        File root = Environment.getExternalStorageDirectory();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String pathFile = root + "/saved_images/" + preferences.getString("profilePhotoUrl", "");
        Bitmap bMap = BitmapFactory.decodeFile(pathFile);
        DrawerHeaderFragment headerFragment = new DrawerHeaderFragment();
        bMap = headerFragment.getCroppedBitmap(bMap);
        if (bMap != null) {
            this.setCompanyImage(bMap);
        }
    }
}
