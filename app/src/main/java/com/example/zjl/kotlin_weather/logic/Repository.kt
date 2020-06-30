package com.example.zjl.kotlin_weather.logic

import androidx.lifecycle.liveData
import com.example.zjl.kotlin_weather.SunnyweatherApplication
import com.example.zjl.kotlin_weather.logic.dao.PlaceDao
import com.example.zjl.kotlin_weather.logic.model.Place
import com.example.zjl.kotlin_weather.logic.model.weather
import com.example.zjl.kotlin_weather.logic.network.SunntyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
//    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
//        val result=try {
//            val placeResonse=SunntyWeatherNetwork.searchPlaces(query)
//            if (placeResonse.status=="ok"){
//                val places =placeResonse.places
//                Result.success(places)
//            }else{
//                Result.failure(RuntimeException("response status is ${placeResonse.status}"))
//            }
//        } catch(e:Exception) {
//                Result.failure<List<Place>>(e)
//            }
//        emit(result)
//    }
//
//    fun refreshWeather(lng:String,lat:String)= liveData(Dispatchers.IO){
//        val result=try {
//            coroutineScope {
//                val deferredRealtime=async {
//                    SunntyWeatherNetwork.getRealtimeWeather(lng,lat)
//                }
//                val deferredDaily=async {
//                    SunntyWeatherNetwork.getDailyWeatther(lng,lat)
//                }
//                val realtimeResponse=deferredRealtime.await()
//                val dailyResponse=deferredDaily.await()
//                if(realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
//                    val weather=weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
//                    Result.success(weather)
//                }else{
//                    Result.failure(
//                            RuntimeException(
//                                    "realtime response status is ${realtimeResponse.status}"+
//                            "daily response status is ${dailyResponse.status}"
//                            )
//                    )
//                }
//            }
//        }catch (e:java.lang.Exception){
//            Result.failure<weather>(e)
//        }
//        emit(result)
//    }


//////第二种优化
    fun searchPlaces(query:String)= fire(Dispatchers.IO) {
    val placeResponse = SunntyWeatherNetwork.searchPlaces(query)
    if (placeResponse.status == "ok") {
        val places = placeResponse.places
        Result.success(places)
    } else {
        Result.failure(RuntimeException("response status is ${placeResponse.status}"))
    }
}
    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO){
        coroutineScope {
            val deferredRealtime=async {
                SunntyWeatherNetwork.getRealtimeWeather(lng,lat)
            }
            val deferredDaily=async {
                SunntyWeatherNetwork.getDailyWeatther(lng,lat)
            }
            val realtimeResponse=deferredRealtime.await()
            val dailyResponse=deferredDaily.await()
            if (realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                val weather=weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(
                        RuntimeException(
                                "realtime response status is ${realtimeResponse.status}"+
                            "daily response status is ${dailyResponse.status}"
                        )
                )
            }
        }
    }
    private fun <T> fire(context:CoroutineContext,block:suspend()->Result<T>)=
            liveData<Result<T>> (context){
                val result=try {
                    block()
                }catch (e:java.lang.Exception){
                    Result.failure<T>(e)
                }
                emit(result)
            }
    fun savePlace(place:Place)= PlaceDao.savePlace(place)
    fun getSavePlace()=PlaceDao.getSavePlace()
    fun isPlaceSaved()=PlaceDao.isPlaceSaved()
}
