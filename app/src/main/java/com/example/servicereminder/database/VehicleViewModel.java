package com.example.servicereminder.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.servicereminder.Utilities.Vehicle;

import java.util.List;

public class VehicleViewModel extends AndroidViewModel {

    private final VehicleRepository vehicleRepository;
    private final LiveData<List<Vehicle>> startScreenVehicles;
    private final LiveData<List<Vehicle>> favoritesScreenVehicles;

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        vehicleRepository = new VehicleRepository(application);
        startScreenVehicles = vehicleRepository.getStartScreenVehicles();
        favoritesScreenVehicles = vehicleRepository.getFavoritesScreenVehicle();
    }

    public LiveData<List<Vehicle>> getStartScreenVehicles() {
        return startScreenVehicles;
    }

    public LiveData<List<Vehicle>> getFavoritesScreenVehicles() {
        return favoritesScreenVehicles;
    }

    public void insert(Vehicle vehicle) {
        vehicleRepository.insert(vehicle);
    }

    public void update(Vehicle vehicle) {
        vehicleRepository.update(vehicle);
    }

    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }
}
