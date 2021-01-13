package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {

    @SerializedName("list")
    private List<ForecastListItem> list;

    public List<ForecastListItem> getList() {
        return list;
    }

    public void setList(List<ForecastListItem> list) {
        this.list = list;
    }
}
