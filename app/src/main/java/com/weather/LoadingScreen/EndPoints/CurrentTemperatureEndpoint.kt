package com.weather.LoadingScreen.EndPoints

import com.weather.DataClass.CurrentTemperatureDataClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrentTemperatureEndpoint{

    @GET("current?")
    fun getCurrentTemp(@Query("access_key")access_key: String, @Query("query")location: String): Call<CurrentTemperatureDataClass>

}

