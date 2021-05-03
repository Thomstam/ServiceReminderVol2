package com.example.servicereminder.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.servicereminder.Utilities.Vehicle;

import java.util.List;

@Dao
public interface VehicleDao {

    @Query("SELECT * FROM vehicle")
    LiveData<List<Vehicle>> getAllVehicles();

    @Insert
    void insertVehicle(Vehicle vehicle);

    @Delete
    void deleteVehicle(Vehicle vehicle);

    @Update
    void update(Vehicle vehicle);

}
