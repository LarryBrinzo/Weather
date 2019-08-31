package com.weather.WeatherInfo.Model

import android.content.Context
import android.location.LocationManager
import com.weather.LoadingScreen.Model.GetLocationModel
import com.weather.WeatherInfo.Contract.ContractInterface.*

class WeatherActivityModel(private val context: Context): Model{

    private lateinit var currentTemprature : String

    override fun getTemprature() = currentTemprature

    override fun getLocation(): String{
        val pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        return pref.getString("CurrentLocation", null)
    }

}