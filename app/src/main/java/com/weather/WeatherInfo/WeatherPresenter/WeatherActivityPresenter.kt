package com.weather.WeatherInfo.WeatherPresenter

import android.content.Context
import android.location.LocationManager
import com.weather.WeatherInfo.Contract.ContractInterface.*
import com.weather.WeatherInfo.Model.WeatherActivityModel

class WeatherActivityPresenter(context: Context): Presenter{

    private var model: Model = WeatherActivityModel(context)


    override fun getTemprature(): String {

        return model.getTemprature()
    }

    override fun getLocation() = model.getLocation()

}