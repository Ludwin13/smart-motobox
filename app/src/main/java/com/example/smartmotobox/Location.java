package com.example.smartmotobox;

public class Location {

    private String Time, Latitude, Longitude;

    public Location() {}

    public Location(String Latitude, String Longitude, String Time) {
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Time = Time;

    }


    public String getLatitude() { return Latitude; }

    public void setLatitude(String Latitude) { this.Latitude = Latitude; }

    public String getLongitude() { return Longitude; }

    public void setLongitude(String Longitude) { this.Longitude = Longitude; }

    public String getTime() { return Time; }

    public void setTime(String Time) { this.Time = Time; }

}
