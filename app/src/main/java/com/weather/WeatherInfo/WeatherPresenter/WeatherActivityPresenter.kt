package com.weather.WeatherInfo.WeatherPresenter

import com.weather.WeatherInfo.Contract.ContractInterface.*
import com.weather.WeatherInfo.Model.WeatherActivityModel

class WeatherActivityPresenter(_view: View): Presenter{

    private var view: View = _view
    private var model: Model = WeatherActivityModel()

    init {
        view.initView()
    }

    override fun getTemprature() = model.getTemprature()

    override fun getLocation() = model.getLocation()

}