package com.example.smartmotobox;

public class Location {

    String Location, Date, Latitude, Longitude, Time;

    public Location() {}

    public Location(String Date, String Latitude, String Longitude, String Time) {
        this.Date = Date;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.Time = Time;

    }

    public String getDate() { return Date; }

    public void setDate(String Date) { this.Date = Date; }

    public String getLatitude() { return Latitude; }

    public void setLatitude(String Latitude) { this.Latitude = Latitude; }

    public String getLongitude() { return Longitude; }

    public void setLongitude(String Longitude) { this.Longitude = Longitude; }

    public String getTime() { return Time; }

    public void setTime(String Time) { this.Time = Time; }

}
