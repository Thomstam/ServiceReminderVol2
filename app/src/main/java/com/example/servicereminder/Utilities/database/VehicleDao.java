package com.example.servicereminder.Utilities.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.servicereminder.Utilities.Vehicle;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    List<Vehicle> getAllVehicles();

    @Insert
    void insertVehicle(Vehicle...vehicles);

    @Delete
    void deleteVehicle(Vehicle vehicle);

    @Query("SELECT * FROM vehicle WHERE id=:id")
    Vehicle findVehicle(int id);

    @Query("SELECT * FROM vehicle WHERE platesOfVehicle= :plates")
    Vehicle findVehicleWithPlates(String plates);

}
