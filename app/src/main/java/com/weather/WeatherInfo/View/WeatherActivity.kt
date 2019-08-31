package com.weather.WeatherInfo.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.weather.DataClass.CurrentTemperatureDataClass
import com.weather.DataClass.ForecastDataClass
import com.weather.ErrorScreen.ErrorScreenActivity
import com.weather.LoadingScreen.API.CurrentWeatherApi
import com.weather.LoadingScreen.EndPoints.CurrentTemperatureEndpoint
import com.weather.LoadingScreen.EndPoints.ForecastEndpoint
import com.weather.R
import com.weather.WeatherInfo.Contract.ContractInterface
import com.weather.WeatherInfo.WeatherPresenter.WeatherActivityPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherActivity : AppCompatActivity(), ContractInterface.View {

    lateinit var currentTemprature : TextView
    lateinit var currentLocation : TextView
    lateinit var forecastRecycle : RecyclerView
    lateinit var latitude: String
    lateinit var longitude: String
    private var presenter : WeatherActivityPresenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        currentTemprature = findViewById(R.id.currenttemprature)
        currentLocation = findViewById(R.id.currentlocation)
        forecastRecycle = findViewById(R.id.forecastrecycle)

        presenter = WeatherActivityPresenter(this)

        initView()
    }

    override fun initView() {
        currentLocation.text = presenter?.getLocation()
        currentTemprature.text = presenter?.getTemprature()

        val pref = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        latitude = pref.getString("CurrentLatitude", null)
        longitude = pref.getString("CurrentLongitude", null)

        getWeatherForecast(latitude, longitude)
    }

    override fun updateLocationData() {
    }

    override fun updateForecastData() {

    }

    fun getWeatherForecast(lat: String, lon: String){

        val loc = "$lat , $lon"
        val days = "5"
        val forecast: ForecastEndpoint = CurrentWeatherApi.getCurrentWeather()!!.create(
            ForecastEndpoint::class.java)

        val call: Call<ForecastDataClass> = forecast.getForecastData(
            key,loc,days)

        Log.e("Call", call.toString())

        call.enqueue(object : Callback<ForecastDataClass> {
            override fun onResponse(call: Call<ForecastDataClass>, response: Response<ForecastDataClass>) {

                var forecastData: ForecastDataClass ?= response.body()
                Log.e("Result", forecastData.toString())

            }

            override fun onFailure(call: Call<ForecastDataClass>, t: Throwable) {
                launchErrorActivity()
            }

        })
    }

    private fun launchErrorActivity(){

            val intent = Intent(applicationContext, ErrorScreenActivity::class.java)
            startActivity(intent)
            finishAffinity()
    }

    companion object {
        val key = "b37b5819be4b4c3fb2e175517192808"
    }

}
