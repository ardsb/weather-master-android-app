package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("coord")
    private Cordinate coord;

    @SerializedName("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Cordinate getCoord() {
        return coord;
    }

    public void setCoord(Cordinate coord) {
        this.coord = coord;
    }
}
