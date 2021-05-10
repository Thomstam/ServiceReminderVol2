package com.example.serviceReminderVol2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.serviceReminderVol2.utilities.Vehicle;

@Database(entities = {Vehicle.class}, version = 8, exportSchema = false)
public abstract class VehicleDataBase extends RoomDatabase {
    public abstract VehicleDao vehicleDao();

    private static VehicleDataBase INSTANCE;

    public static synchronized VehicleDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    VehicleDataBase.class, "Vehicle")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
