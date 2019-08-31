package com.weather.WeatherInfo.Model

import android.content.Context
import android.location.LocationManager
import com.weather.LoadingScreen.Model.GetLocationModel
import com.weather.WeatherInfo.Contract.ContractInterface.*

class WeatherActivityModel(private val context: Context): Model{

    private val pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    override fun getTemprature() = pref.getString("CurrentTemperature", null)

    override fun getLocation() = pref.getString("CurrentLocation", null)


}