package com.weather.DataClass

data class ForecastDataClass(var forecast: Forecast)

data class Forecast(var forecastday:List<ForecastDay>)

data class ForecastDay(var date:String, var day: Day)

data class Day(var avgtemp: String)