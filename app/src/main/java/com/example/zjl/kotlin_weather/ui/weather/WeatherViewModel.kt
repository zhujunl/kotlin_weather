package com.example.zjl.kotlin_weather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.zjl.kotlin_weather.logic.Repository
import com.example.zjl.kotlin_weather.logic.model.Location

class WeatherViewModel :ViewModel(){
    private val locationLiveData= MutableLiveData<Location>()
    var locationlng=""
    var locationLat=""
    var placeName=""

    val weatherLiveData= Transformations.switchMap(locationLiveData){location->
        Repository.refreshWeather(location.lng,location.lat)
    }
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value= Location(lng,lat)
    }
}