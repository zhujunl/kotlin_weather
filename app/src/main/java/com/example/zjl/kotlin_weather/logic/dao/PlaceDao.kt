package com.example.zjl.kotlin_weather.logic.dao


import android.content.Context
import android.provider.Settings.Secure.putString
import androidx.core.content.edit
import com.example.zjl.kotlin_weather.SunnyweatherApplication
import com.example.zjl.kotlin_weather.logic.model.Place
import com.google.gson.Gson

object PlaceDao{
    fun savePlace(place:Place){
        sharedPreferences().edit {
            putString("place",Gson().toJson(place))
        }
    }
    private fun sharedPreferences()=SunnyweatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
    fun getSavePlace():Place{
        val placeJson= sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }
    fun isPlaceSaved()= sharedPreferences().contains("place")
}