package com.example.zjl.kotlin_weather.ui.place

//import androidx.lifecycle.*
//import com.example.zjl.kotlin_weather.logic.Repository
//import com.example.zjl.kotlin_weather.logic.model.Place
//
//class PlaceViewModel : ViewModel(){
//    private val searchLiveData = MutableLiveData<String>()
//
//    val placeList = ArrayList<Place>()
//
//    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
//        Repository.searchPlaces(query)
//    }
//
//    fun searchPlaces(query: String) {
//        searchLiveData.value = query
//    }
//}


import androidx.lifecycle.*
import com.example.zjl.kotlin_weather.logic.Repository
import com.example.zjl.kotlin_weather.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }


}