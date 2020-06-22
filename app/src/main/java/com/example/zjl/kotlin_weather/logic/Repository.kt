package com.example.zjl.kotlin_weather.logic

import androidx.lifecycle.liveData
import com.example.zjl.kotlin_weather.logic.model.Place
import com.example.zjl.kotlin_weather.logic.network.SunntyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {
    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
        val result=try {
            val placeResonse=SunntyWeatherNetwork.searchPlaces(query)
            if (placeResonse.status=="ok"){
                val places =placeResonse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResonse.status}"))
            }
        } catch(e:Exception) {
                Result.failure<List<Place>>(e)
            }
        emit(result)
    }

}
