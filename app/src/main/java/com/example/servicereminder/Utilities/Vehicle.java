package com.example.servicereminder.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity
        (indices = {@Index(value = {"platesOfVehicle"},
                unique = true)})
public class Vehicle implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String platesOfVehicle;
    private String typeOfVehicle;
    private int brandIcon;
    private int currentKms;
    private int serviceKms;
    private int kmsPerDay;
    private int usagePerWeek;
    private long notificationTime;
    private String notificationSpinnerTimeSelection;
    private String dateOfTheService;
    private boolean isFavorite;


    public Vehicle(@NonNull String platesOfVehicle, int brandIcon, String typeOfVehicle, int currentKms, int serviceKms, int kmsPerDay, int usagePerWeek, long notificationTime, String notificationSpinnerTimeSelection, String dateOfTheService, boolean isFavorite) {
        this.platesOfVehicle = platesOfVehicle;
        this.brandIcon = brandIcon;
        this.typeOfVehicle = typeOfVehicle;
        this.currentKms = currentKms;
        this.serviceKms = serviceKms;
        this.kmsPerDay = kmsPerDay;
        this.usagePerWeek = usagePerWeek;
        this.notificationTime = notificationTime;
        this.notificationSpinnerTimeSelection = notificationSpinnerTimeSelection;
        this.dateOfTheService = dateOfTheService;
        this.isFavorite = isFavorite;
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
        notificationSpinnerTimeSelection = in.readString();
        dateOfTheService = in.readString();
        isFavorite = in.readInt() == 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(platesOfVehicle);
        dest.writeString(typeOfVehicle);
        dest.writeInt(brandIcon);
        dest.writeInt(currentKms);
        dest.writeInt(serviceKms);
        dest.writeInt(kmsPerDay);
        dest.writeInt(usagePerWeek);
        dest.writeLong(notificationTime);
        dest.writeString(notificationSpinnerTimeSelection);
        dest.writeString(dateOfTheService);
        dest.writeInt(isFavorite ? 0 : 1);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    public String getPlatesOfVehicle() {
        return platesOfVehicle;
    }

    public void setPlatesOfVehicle(@NonNull String platesOfVehicle) {
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

    public String getNotificationSpinnerTimeSelection() {
        return notificationSpinnerTimeSelection;
    }

    public void setNotificationSpinnerTimeSelection(String notificationSpinnerTimeSelection) {
        this.notificationSpinnerTimeSelection = notificationSpinnerTimeSelection;
    }

    public int getBrandIcon() {
        return brandIcon;
    }

    public void setBrandIcon(int brandIcon) {
        this.brandIcon = brandIcon;
    }

    public String getDateOfTheService() {
        return dateOfTheService;
    }

    public void setDateOfTheService(String dateOfTheService) {
        this.dateOfTheService = dateOfTheService;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
