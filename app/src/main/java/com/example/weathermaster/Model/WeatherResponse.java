package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("main")
    private WeatherResults main;

    public WeatherResults getMain() {
        return main;
    }

    public void setMain(WeatherResults main) {
        this.main = main;
    }







}
