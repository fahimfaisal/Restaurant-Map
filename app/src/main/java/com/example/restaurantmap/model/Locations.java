package com.example.restaurantmap.model;

import android.location.Location;

public class Locations {

    private int ID;
    private String title;
    private double latitude;
    private double longitude;


    public Locations(String title, double latitude, double longitude) {

        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Locations(int id, String title, double latitude, double longitude) {
        this.ID = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Locations()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
