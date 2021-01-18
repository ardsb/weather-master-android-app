package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class ForecastListItem {


    @SerializedName("main")
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
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
