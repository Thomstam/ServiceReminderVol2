package com.example.serviceReminder.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SetLanguage {


    public static void setLanguageStrings(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
