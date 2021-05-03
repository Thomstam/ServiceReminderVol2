package com.example.servicereminder.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.servicereminder.Utilities.Vehicle;

import java.util.List;

public class VehicleRepository {

    private final VehicleDao vehicleDao;
    private final LiveData<List<Vehicle>> startScreenVehicles;

    public VehicleRepository(Application application) {
        VehicleDataBase vehicleDataBase = VehicleDataBase.getINSTANCE(application);
        vehicleDao = vehicleDataBase.vehicleDao();
        startScreenVehicles = vehicleDao.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getStartScreenVehicles() {
        return startScreenVehicles;
    }

    public void insert(Vehicle vehicle) {
        insertNewVehicle(vehicle);
    }

    public void delete(Vehicle vehicle) {
        deleteVehicle(vehicle);
    }

    public void update(Vehicle vehicle) {
        updateVehicle(vehicle);
    }

    private void insertNewVehicle(Vehicle vehicle) {
        new Thread(() -> vehicleDao.insertVehicle(vehicle)).start();
    }

    private void deleteVehicle(Vehicle vehicle) {
        new Thread(() -> vehicleDao.deleteVehicle(vehicle)).start();
    }

    private void updateVehicle(Vehicle vehicle) {
        new Thread(() -> vehicleDao.update(vehicle)).start();
    }
}
