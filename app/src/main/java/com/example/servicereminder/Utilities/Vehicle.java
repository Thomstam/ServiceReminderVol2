package com.example.servicereminder.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {

    private int id;
    private String platesOfVehicle;
    private String typeOfVehicle;
    private int brandIcon;
    private int currentKms;
    private int serviceKms;
    private int kmsPerDay;
    private int usagePerWeek;
    private long notificationTime;

    public Vehicle(String platesOfVehicle, int brandIcon, String typeOfVehicle, int currentKms, int serviceKms, int kmsPerDay, int usagePerWeek, long notificationTime) {
        this.platesOfVehicle = platesOfVehicle;
        this.brandIcon = brandIcon;
        this.typeOfVehicle = typeOfVehicle;
        this.currentKms = currentKms;
        this.serviceKms = serviceKms;
        this.kmsPerDay = kmsPerDay;
        this.usagePerWeek = usagePerWeek;
        this.notificationTime = notificationTime;
    }

    protected Vehicle(Parcel in) {
        id = in.readInt();
        platesOfVehicle = in.readString();
        typeOfVehicle = in.readString();
        brandIcon = in.readInt();
        currentKms = in.readInt();
        serviceKms = in.readInt();
        kmsPerDay = in.readInt();
        usagePerWeek = in.readInt();
        notificationTime = in.readLong();
    }


    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatesOfVehicle() {
        return platesOfVehicle;
    }

    public void setPlatesOfVehicle(String platesOfVehicle) {
        this.platesOfVehicle = platesOfVehicle;
    }

    public String getTypeOfVehicle() {
        return typeOfVehicle;
    }

    public void setTypeOfVehicle(String typeOfVehicle) {
        this.typeOfVehicle = typeOfVehicle;
    }

    public int getCurrentKms() {
        return currentKms;
    }

    public void setCurrentKms(int currentKms) {
        this.currentKms = currentKms;
    }

    public int getServiceKms() {
        return serviceKms;
    }

    public void setServiceKms(int serviceKms) {
        this.serviceKms = serviceKms;
    }

    public int getKmsPerDay() {
        return kmsPerDay;
    }

    public void setKmsPerDay(int kmsPerDay) {
        this.kmsPerDay = kmsPerDay;
    }

    public int getUsagePerWeek() {
        return usagePerWeek;
    }

    public void setUsagePerWeek(int usagePerWeek) {
        this.usagePerWeek = usagePerWeek;
    }

    public long getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(long notificationTime) {
        this.notificationTime = notificationTime;
    }

    public int getBrandIcon() {
        return brandIcon;
    }

    public void setBrandIcon(int brandIcon) {
        this.brandIcon = brandIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


}
