package com.example.tomse.tomtom;

public class Earthquake {

    private double magnitude;
    private String place;
    private long time;
    private String url;

    public Earthquake(double Magnitude, String Location, long TimeInMilliseconds, String Url) {
        this.magnitude = Magnitude;
        this.place = Location;
        this.time = TimeInMilliseconds;
        this.url = Url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return place;
    }

    public long getTimeInMilliseconds() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "Magnitude=" + magnitude +
                ", Location='" + place + '\'' +
                ", TimeInMilliseconds=" + time +
                ", Url='" + url + '\'' +
                '}';
    }

}
