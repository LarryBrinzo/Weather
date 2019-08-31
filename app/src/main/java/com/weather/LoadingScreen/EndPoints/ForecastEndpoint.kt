package com.weather.LoadingScreen.EndPoints

import com.weather.DataClass.ForecastDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastEndpoint{

    @GET("forecast.json?")
    fun getForecastData(@Query("key")key: String, @Query("q")location: String, @Query("days")days: String): Call<ForecastDataClass>

}