package com.example.serviceReminder.mainFragments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.serviceReminder.drawerMainContents.DrawerHeaderFragment;
import com.example.serviceReminder.formsPackage.EditForm;
import com.example.serviceReminder.formsPackage.FormSetup;
import com.example.serviceReminder.notificationSetup.ServiceNotification;
import com.example.serviceReminder.drawerMainContents.SettingsFragment;
import com.example.serviceReminder.R;
import com.example.serviceReminder.utilities.Vehicle;
import com.example.serviceReminder.database.VehicleViewModel;
import com.example.serviceReminder.utilities.VehicleRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    VehicleViewModel vehicleViewModelFromMain;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVehicleRecyclerViewFromMain();

        setVehicleViewModel();

        notificationChannel();

        newFormSetup();

        setMenu();

        setBottomNavigationViewMain();

        settingsInit();

        headerInit();

        openFragment(homeScreenFragments);
    }



    private void setVehicleRecyclerViewFromMain(){
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
                if (query.equals("delete")){
                    vehicleViewModelFromMain.delete(vehicle);
                }else {
                    vehicleViewModelFromMain.update(vehicle);
                }
            }
        }
    }

    private void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Channel For Service Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID_FOR_NOTIFICATION_CHANNEL, NAME_FOR_NOTIFICATION_CHANNEL, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void setBottomNavigationViewMain() {
        BottomNavigationView bottomNavigationViewMain = findViewById(R.id.bottomNavigationMain);
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

    public void RecyclersOnClick(Vehicle vehicle, Context context){
        Intent formEditActivity = new Intent(context, EditForm.class);
        ArrayList<Vehicle> tempList = (ArrayList<Vehicle>) vehicleRecyclerViewFromMain.getVehicles();
        if (tempList.size() > 0) {
            formEditActivity.putParcelableArrayListExtra("vehicles", tempList);
            formEditActivity.putExtra("vehicleBoolean", true);
        }
        formEditActivity.putExtra("isForEdit", true);
        formEditActivity.putExtra("vehicleForEdit", vehicle);
        startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
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

    public static List<Vehicle> vehicleList(){
        return  vehicleRecyclerViewFromMain.getVehicles();
    }
}