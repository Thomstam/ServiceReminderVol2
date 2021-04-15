package com.example.servicereminder.Utilities.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.servicereminder.Utilities.Vehicle;

@Database(entities = {Vehicle.class},version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VehicleDao vehicleDao();
    private static AppDatabase INSTANCE ;
    public static AppDatabase getDBInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"DB_NAME")
                    .allowMainThreadQueries().build();

        }
        return INSTANCE;
    }

}
