package com.example.zjl.kotlin_weather.ui.weather

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.zjl.kotlin_weather.R
import com.example.zjl.kotlin_weather.logic.model.getSky
import com.example.zjl.kotlin_weather.logic.model.weather
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy{
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView=window.decorView
        decorView.systemUiVisibility=
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor= Color.TRANSPARENT
        setContentView(R.layout.activity_weather)
        if(viewModel.locationlng.isEmpty()){
            viewModel.locationlng=intent.getStringExtra("location_lng")?:""
        }
        if(viewModel.locationLat.isEmpty()){
            viewModel.locationLat=intent.getStringExtra("location_lat")?:""
        }
        if(viewModel.placeName.isEmpty()){
            viewModel.placeName=intent.getStringExtra("place_name")?:""
        }
        Log.d("location_lng",viewModel.locationlng)
        Log.d("location_lat",viewModel.locationLat)
        Log.d("place_name",viewModel.placeName)
        viewModel.weatherLiveData.observe(this, Observer { result->
            val weather=result.getOrNull()
            if (weather!=null){
                showWeatherInfo(weather)
            }else{
                Toast.makeText(this,"无法获取天气信息",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing=false
        })
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        viewModel.refreshWeather(viewModel.locationlng,viewModel.locationLat)
        navBtn.setOnClickListener{
            drawerlayout.openDrawer(GravityCompat.START)
        }
        drawerlayout.addDrawerListener(object :DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                val manager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow((drawerView.windowToken),
                InputMethodManager.HIDE_NOT_ALWAYS)
            }
        })
    }
    fun refreshWeather(){
        viewModel.refreshWeather(viewModel.locationlng,viewModel.locationLat)
        swipeRefresh.isRefreshing=true
    }
    private fun showWeatherInfo(weather:weather){
        PlaceName.text=viewModel.placeName
        val realtime =weather.realtime
        val daily=weather.daily
        val currentTempText="${realtime.temperature.toInt()}°C"
        currentTemp.text=currentTempText
        currentSky.text= getSky(realtime.skycon).info
        val currentPM25Text="${realtime.airQuality.aqi.chn.toInt()}"
        currentAQI.text=currentPM25Text
        nowlayout.setBackgroundResource(getSky(realtime.skycon).bg)
        forecastLayout.removeAllViews()
        val days=daily.skycon.size
        for (i in 0 until days){
            val skycon=daily.skycon[i]
            val temperature=daily.temperature[i]
            val view =LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false)
            val dateinfo=view.findViewById(R.id.dataInfo) as TextView
            val skyIcon=view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo=view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo=view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateinfo.text=simpleDateFormat.format(skycon.date)
            val sky= getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text=sky.info
            val tempText="${temperature.min.toInt()} ~ ${temperature.max.toInt()} °C"
            temperatureInfo.text=tempText
            forecastLayout.addView(view)
        }
        val lifeIndex=daily.lifeIndex
        coldRiskText.text=lifeIndex.coldRisk[0].desc
        dressingText.text=lifeIndex.dressing[0].desc
        ultravioletText.text=lifeIndex.ultraviolet[0].desc
        carWashingText.text=lifeIndex.carWashing[0].desc
        weatherlayout.visibility= View.VISIBLE
    }
}

