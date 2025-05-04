package org.example;

public class City {
    private int id;
    private String name;
    private int countryId;
    private boolean isCapital;
    private double latitude;
    private double longitude;

    public City(int id, String name, int countryId, boolean isCapital, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return String.format("City{id=%d, name='%s', countryId=%d, capital=%b, lat=%.4f, lon=%.4f}",
                id, name, countryId, isCapital, latitude, longitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    // Getters & Setters
}
