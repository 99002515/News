package com.example.news.weatherFile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

//    ?q={city name}&appid={your api key}
    String Base_Url = "http://api.openweathermap.org/data/2.5/";
    String key = "280e7bae6c1e2a7b018275684c81bd9d";
    String Temp_Unit = "metric";

    @GET("weather")
    Call<WeatherPojo> getData(@Query("lat") String lat,
                              @Query("lon") String lon,
                              @Query("appid") String appid,
                              @Query("units") String units);

}
