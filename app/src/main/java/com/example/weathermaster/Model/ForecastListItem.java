package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class ForecastListItem {


    @SerializedName("main")
    private WeatherResults main;

    public WeatherResults getMain() {
        return main;
    }

    public void setMain(WeatherResults main) {
        this.main = main;
    }

    @SerializedName("dt_txt")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {        this.date = date;
    }
}
