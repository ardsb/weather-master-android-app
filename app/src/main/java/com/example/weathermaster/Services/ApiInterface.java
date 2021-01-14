package com.example.weathermaster.Services;

import com.example.weathermaster.Model.ForecastResponse;
import com.example.weathermaster.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?units=metric&appid=69f90ea3d241ebdd75de3c406bd580d5")
    Call<WeatherResponse> getWeatherData(@Query("q") String name);

    @GET("forecast?units=metric&appid=69f90ea3d241ebdd75de3c406bd580d5")
    Call<ForecastResponse> getForecastData(@Query("q") String name);

    @GET("weather?units=metric&appid=69f90ea3d241ebdd75de3c406bd580d5")
    Call<WeatherResponse> getWeatherDataLatLon(@Query("lat") String lat,@Query("lon") String lon);

    @GET("forecast?units=metric&appid=69f90ea3d241ebdd75de3c406bd580d5")
    Call<ForecastResponse> getForecastDataLatLon(@Query("lat") String lat,@Query("lon") String lon);



}
