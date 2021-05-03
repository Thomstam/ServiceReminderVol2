package com.example.servicereminder.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.servicereminder.Utilities.Vehicle;

@Database(entities = {Vehicle.class}, version = 2, exportSchema = false)
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
