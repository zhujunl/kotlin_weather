package com.example.zjl.kotlin_weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyweatherApplication : Application(){
    companion object{
        const val TOKEN="7CTPe3HB5Sn7d4mB"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }
}