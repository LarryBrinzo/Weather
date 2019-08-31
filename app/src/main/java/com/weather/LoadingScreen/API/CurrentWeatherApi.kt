package com.weather.LoadingScreen.API

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class CurrentWeatherApi{

    companion object {

        private var retrofit: Retrofit ?=null
        private val Api_Url: String = "https://api.apixu.com/v1/"

        fun getCurrentWeather(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(Api_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}