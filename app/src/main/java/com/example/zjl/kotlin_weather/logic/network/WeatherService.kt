package com.example.zjl.kotlin_weather.logic.network

import com.example.zjl.kotlin_weather.SunnyweatherApplication
import com.example.zjl.kotlin_weather.logic.model.DailyResponse
import com.example.zjl.kotlin_weather.logic.model.RealtimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call



interface WeatherService{
    @GET("v2.5/${SunnyweatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealitimeWeather(@Path("lng") lng :String,@Path("lat") lat:String):
            Call<RealtimeResponse>

    @GET("v2.5/${SunnyweatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng :String,@Path("lat") lat:String):
            Call<DailyResponse>
}