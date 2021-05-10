package com.example.serviceReminderVol2.mainFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.serviceReminderVol2.R;
import com.example.serviceReminderVol2.database.VehicleViewModel;
import com.example.serviceReminderVol2.drawerMainContents.DrawerHeaderFragment;
import com.example.serviceReminderVol2.drawerMainContents.EditProfilePreferences;
import com.example.serviceReminderVol2.drawerMainContents.SettingsFragment;
import com.example.serviceReminderVol2.formsPackage.FormSetup;
import com.example.serviceReminderVol2.notificationSetup.ServiceNotification;
import com.example.serviceReminderVol2.utilities.Vehicle;
import com.example.serviceReminderVol2.utilities.VehicleRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static VehicleViewModel vehicleViewModelFromMain;
    public static VehicleRecyclerView vehicleRecyclerViewFromMain;
    private final static int REQUEST_FORM_SETUP = 101;
    private final static int REQUEST_EDIT_FORM = 102;
    private final static String NAME_FOR_NOTIFICATION_CHANNEL = "DefaultNotificationChannel";
    private final static String ID_FOR_NOTIFICATION_CHANNEL = "ServiceReminder";
    private final HomeScreenFragments homeScreenFragments = new HomeScreenFragments();
    private final FavoritesScreenFragment favoritesScreenFragment = new FavoritesScreenFragment();
    private final UpcomingServicesScreenFragment upcomingServicesScreenFragment = new UpcomingServicesScreenFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setProfilePreferences();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsInit();

        setVehicleRecyclerViewFromMain();

        setVehicleViewModel();

        notificationChannel(this);

        newFormSetup();

        setMenu();

        setBottomNavigationViewMain();

        headerInit();

    }

    private void setProfilePreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("splashScreen", false)){
            Intent profileSettings = new Intent(this, EditProfilePreferences.class);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("splashScreen",true);
            editor.apply();
            startActivityForResult(profileSettings, 103);
        }
    }

    private void setVehicleRecyclerViewFromMain() {
        vehicleRecyclerViewFromMain = new VehicleRecyclerView();
    }

    private void setVehicleViewModel() {
        vehicleViewModelFromMain = new ViewModelProvider(this).get(VehicleViewModel.class);
        vehicleViewModelFromMain.getStartScreenVehicles().observe(this, vehicles -> vehicleRecyclerViewFromMain.setVehicles(vehicles));
        vehicleViewModelFromMain.updateVehicle(vehicleRecyclerViewFromMain.getVehicles());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FORM_SETUP) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Vehicle vehicle = (Vehicle) data.getExtras().get("Vehicle");
                vehicleViewModelFromMain.insert(vehicle);
                setNotificationTime(vehicle);
            }
        }
        if (requestCode == REQUEST_EDIT_FORM) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                String query = data.getStringExtra("QueryToExecute");
                Vehicle vehicle = (Vehicle) data.getExtras().get("vehicle");
                if (query.equals("delete")) {
                    vehicleViewModelFromMain.delete(vehicle);
                } else {
                    vehicleViewModelFromMain.update(vehicle);
                }
            }
        }
        if (requestCode == 103) {
            if (resultCode == Activity.RESULT_OK) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_header_profile_settings, new DrawerHeaderFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }

    }

    public void notificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int sound = soundID(context);
            String description = "Channel For Service Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID_FOR_NOTIFICATION_CHANNEL, NAME_FOR_NOTIFICATION_CHANNEL, importance);
            channel.setDescription(description);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            channel.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +sound), audioAttributes);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private int soundID(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString("SoundPreference", "").equals("Got It Done")){
            return R.raw.got_it_done;
        }else {
            return R.raw.hasty_ba_dum_tss;
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigationViewMain() {
        BottomNavigationView bottomNavigationViewMain = findViewById(R.id.bottomNavigationMain);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString("startFragment", "Home Screen").equals("Home Screen")){
            bottomNavigationViewMain.setSelectedItemId(R.id.mainPanel);
            openFragment(homeScreenFragments);
        }else if (preferences.getString("startFragment", "Home Screen").equals("Favorite Screen")){
            bottomNavigationViewMain.setSelectedItemId(R.id.favoritesPanel);
            openFragment(favoritesScreenFragment);
        }else {
            bottomNavigationViewMain.setSelectedItemId(R.id.upcomingServices);
            openFragment(upcomingServicesScreenFragment);
        }
        bottomNavigationViewMain.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mainPanel:
                    openFragment(homeScreenFragments);
                    return true;
                case R.id.favoritesPanel:
                    openFragment(favoritesScreenFragment);
                    return true;
                case R.id.upcomingServices:
                    openFragment(upcomingServicesScreenFragment);
                    return true;
            }
            return false;
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void settingsInit() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.settingsFragment, new SettingsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void headerInit() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.drawer_header_profile_settings, new DrawerHeaderFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setNotificationTime(Vehicle vehicle) {

        Intent intent = new Intent(MainActivity.this, ServiceNotification.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("Vehicle", vehicle);
        intent.putExtra("bundle", bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long testingTimeToNotify = System.currentTimeMillis() + 1000 * 5;

        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                testingTimeToNotify,
                pendingIntent);
    }

    private void setMenu() {
        Toolbar toolbar = findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerMainActivity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }


    private void newFormSetup() {
        FloatingActionButton floatingActionButton = findViewById(R.id.newFormButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormSetup.class);
            ArrayList<Vehicle> tempList = (ArrayList<Vehicle>) vehicleRecyclerViewFromMain.getVehicles();
            if (tempList.size() > 0) {
                intent.putParcelableArrayListExtra("vehicles", tempList);
                intent.putExtra("vehicleBoolean", true);
            }
            intent.putExtra("isForEdit", false);
            startActivityForResult(intent, REQUEST_FORM_SETUP);
        });
    }

    public static List<Vehicle> vehicleList() {
        return vehicleRecyclerViewFromMain.getVehicles();
    }
}