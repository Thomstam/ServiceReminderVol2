package com.example.servicereminder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.servicereminder.FormsPackage.FormSetup;
import com.example.servicereminder.NotificationSetup.ServiceNotification;
import com.example.servicereminder.NotificationSetup.SettingsActivity;
import com.example.servicereminder.Utilities.Vehicle;
import com.example.servicereminder.Utilities.VehicleRecyclerView;
import com.example.servicereminder.Utilities.database.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_FORM_SETUP = 101;
    private final static int REQUEST_EDIT_FORM = 102;
    private final static String NAME_FOR_NOTIFICATION_CHANNEL = "DefaultNotificationChannel";
    private final static String ID_FOR_NOTIFICATION_CHANNEL = "ServiceReminder";
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private VehicleRecyclerView adapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationChannel();

        newFormSetup();

        setMenu();

        appDatabase = AppDatabase.getDBInstance(getApplicationContext());

        initRecyclerView();
        }

    private void newFormSetup() {
        FloatingActionButton floatingActionButton = findViewById(R.id.newFormButton);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormSetup.class);
            startActivityForResult(intent, REQUEST_FORM_SETUP);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FORM_SETUP) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                Vehicle vehicle = (Vehicle) data.getExtras().get("Vehicle");
                vehicles.add(vehicle);
                adapter.setVehicles(vehicles);
                setNotificationTime(vehicle);
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

    private void setMenu(){
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.inflateMenu(R.menu.main_activity_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.notificationSettings){
                openSettingPanel();
            }else if (item.getItemId() == R.id.about){
                Toast.makeText(this, "Open About Activity", Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }

    private void openSettingPanel(){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new VehicleRecyclerView();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase.vehicleDao().getAllVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(List<Vehicle> vehicles) {
                adapter.setVehicles(vehicles);
            }
        });
    }

    private void setRecyclerOnClick(){
        adapter.setOnItemClickListener(vehicle -> {
            Intent formEditActivity = new Intent(MainActivity.this, FormSetup.class);
            formEditActivity.putExtra("isForEdit", true);
            formEditActivity.putExtra("vehicleForEdit", vehicle);
            startActivityForResult(formEditActivity, REQUEST_EDIT_FORM);
        });
    }
}