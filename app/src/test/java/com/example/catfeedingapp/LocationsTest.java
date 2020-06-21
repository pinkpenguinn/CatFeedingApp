package com.example.catfeedingapp;

import android.location.Location;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class LocationsTest {

    @Mock
    double latitude;

    @Mock
    double longitude;

    @Test
    public void getLatitudeTest() {
        latitude = 38.988190;
        longitude = -76.498110;
        Locations location = new Locations(latitude, longitude);
        assertEquals(latitude, location.getLatitude(), 0.01);
    }

    @Test
    public void setLatitudeTest() {
        Locations locations = new Locations(latitude, longitude);
        double newLat = 39.020117;
        locations.setLatitude(newLat);
        assertEquals(newLat, locations.getLatitude(), 0.01);
    }

    @Test
    public void getLongitudeTest() {
        latitude = 38.988190;
        longitude = -76.498110;
        Locations location = new Locations(latitude, longitude);
        assertEquals(longitude, location.getLongitude(), 0.01);
    }

    @Test
    public void setLongitudeTest() {
        Locations locations = new Locations(latitude, longitude);
        double newLng = -76.495914;
        locations.setLongitude(newLng);
        assertEquals(newLng, locations.getLongitude(), 0.01);
    }
}