package com.example.weathermaster.Model;

import com.google.gson.annotations.SerializedName;

public class Main {


    @SerializedName("temp")
    String temp;

    @SerializedName("humidity")
    String humidity;

    @SerializedName("feels_like")
    String feelsLike;

    @SerializedName("temp_min")
    String tempMin;

    @SerializedName("temp_max")
    String tempMax;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @SerializedName("pressure")
    String pressure;


}
