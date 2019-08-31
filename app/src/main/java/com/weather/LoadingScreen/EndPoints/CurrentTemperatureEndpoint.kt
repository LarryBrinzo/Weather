package com.weather.LoadingScreen.EndPoints

import com.weather.DataClass.CurrentTemperatureDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrentTemperatureEndpoint{

    @GET("current.json?")
    fun getCurrentTemp(@Query("key")key: String, @Query("q")location: String): Call<CurrentTemperatureDataClass>

}