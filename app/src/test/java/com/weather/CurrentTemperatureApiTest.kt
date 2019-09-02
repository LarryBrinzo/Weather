package com.weather

import com.weather.DataClass.CurrentTemperatureDataClass
import com.weather.LoadingScreen.API.CurrentWeatherApi
import com.weather.LoadingScreen.EndPoints.CurrentTemperatureEndpoint
import com.weather.LoadingScreen.View.LoadingScreenActivity
import junit.framework.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import java.io.IOException

class CurrentTemperatureApiTest{

    private val loc: String="28.33,85,66"

    @Test
    fun CurrentTempApiTest() {

        val currentWeather: CurrentTemperatureEndpoint = CurrentWeatherApi.getCurrentWeather()!!.create(
            CurrentTemperatureEndpoint::class.java)

        val call: Call<CurrentTemperatureDataClass> = currentWeather.getCurrentTemp(
            LoadingScreenActivity.key,loc)

        try {
            //Magic is here at .execute() instead of .enqueue()
            val response = call.execute()
            val authResponse = response.body()?.current?.temp_c

            assertTrue(response.isSuccessful() && authResponse!=null)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}