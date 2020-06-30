package com.example.zjl.kotlin_weather.logic.network




import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunntyWeatherNetwork {
    private val placeService=ServiceCreator.create(PlaceService::class.java)
    suspend fun searchPlaces(query:String)= placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await():T{
        return suspendCoroutine {
            continuation -> enqueue(object :Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body=response.body()
                if(body!=null) continuation.resume(body)
                else continuation.resumeWithException(
                        RuntimeException("response body is null")
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
        }
    }

    private val weatherService=ServiceCreator.create(WeatherService::class.java)
    suspend fun getDailyWeatther(lng:String,lat:String)= weatherService.getDailyWeather(lng,lat).await()
    suspend fun getRealtimeWeather(lng: String,lat:String)= weatherService.getRealitimeWeather(lng,lat).await()


}