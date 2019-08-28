package com.weather.WeatherInfo.Model

import com.weather.WeatherInfo.Contract.ContractInterface.*

class WeatherActivityModel: Model{

    private lateinit var currentTemprature : String
    private lateinit var currentLocation : String

    override fun getTemprature() = currentTemprature

    override fun getLocation(): String{

        return currentLocation
    }

}