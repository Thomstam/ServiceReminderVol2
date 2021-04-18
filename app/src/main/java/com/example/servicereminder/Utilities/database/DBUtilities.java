package com.example.servicereminder.Utilities.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.servicereminder.Utilities.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DBUtilities {

    public static LiveData<List<Vehicle>> loadAllVehicles(Context appContext){
        AppDatabase db = AppDatabase.getDBInstance(appContext);
        LiveData<List<Vehicle>> vehicleList = db.vehicleDao().getAllVehicles();
        return vehicleList;
    }

    public static void saveVehicle(Vehicle vehicle,Context appContext) {
        AppDatabase db  = AppDatabase.getDBInstance(appContext);
        db.vehicleDao().insertVehicle(vehicle);
    }


    public static Vehicle findVehicleByPlates(String plates,Context appContext){
        AppDatabase db  = AppDatabase.getDBInstance(appContext);
        Vehicle vehicles = db.vehicleDao().findVehicleWithPlates(plates);
        if (vehicles==null)return null;
        db.close();
        return vehicles;
    }



    public static boolean deleteVehicleByPlates(String plates,Context appContext){
        AppDatabase db  = AppDatabase.getDBInstance(appContext);
        Vehicle v = findVehicleByPlates(plates,appContext);
        if (v==null)return false;
        db.vehicleDao().deleteVehicle(v);
        db.close();
        return true;
    }
}
