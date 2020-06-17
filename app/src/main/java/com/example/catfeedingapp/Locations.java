package com.example.catfeedingapp;



public class Locations {

    private double latitude;
    private double longitude;

    public Locations (double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Locations () {

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
}
