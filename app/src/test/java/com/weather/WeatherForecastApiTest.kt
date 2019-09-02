package com.weather

import com.weather.DataClass.ForecastDataClass
import com.weather.LoadingScreen.API.CurrentWeatherApi
import com.weather.LoadingScreen.EndPoints.ForecastEndpoint
import com.weather.WeatherInfo.View.WeatherActivity
import junit.framework.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import java.io.IOException

class WeatherForecastApiTest{

    private val days: String="5"
    private val loc: String="28.33,85,66"

    @Test
    fun ForecastApiTesting() {

        val forecast: ForecastEndpoint = CurrentWeatherApi.getCurrentWeather()!!.create(ForecastEndpoint::class.java)

        val call: Call<ForecastDataClass> = forecast.getForecastData(
            WeatherActivity.key,loc,days)

        try {
            //Magic is here at .execute() instead of .enqueue()
            val response = call.execute()
            val authResponse = response.body()?.forecast?.forecastday

            assertTrue(response.isSuccessful() && authResponse!!.size==5)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}