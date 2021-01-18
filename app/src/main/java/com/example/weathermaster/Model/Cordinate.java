package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class Cordinate {

    @SerializedName("lon")
    double lon;

    @SerializedName("lat")
    double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
