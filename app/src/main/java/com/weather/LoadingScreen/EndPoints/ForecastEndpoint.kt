package com.weather.LoadingScreen.EndPoints

import com.weather.DataClass.ForecastDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastEndpoint{

    @GET("forecast?")
    fun getForecastData(@Query("access_key")access_key: String, @Query("query")location: String, @Query("forecast_days")days: String): Call<ForecastDataClass>

}