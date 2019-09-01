package com.weather.WeatherInfo.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.weather.DataClass.ForecastDataClass
import com.weather.DataClass.ForecastDay
import com.weather.ErrorScreen.ErrorScreenActivity
import com.weather.LoadingScreen.API.CurrentWeatherApi
import com.weather.LoadingScreen.EndPoints.ForecastEndpoint
import com.weather.R
import com.weather.WeatherInfo.Adapter.WeatherForecastAdapter
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
    lateinit var forecast_layout: View
    private var presenter : WeatherActivityPresenter ? = null
    lateinit var forecastData: MutableList<ForecastDay>
    lateinit var forecastAdapter: WeatherForecastAdapter
    lateinit var forecastrecycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        currentTemprature = findViewById(R.id.currenttemprature)
        currentLocation = findViewById(R.id.currentlocation)
        forecastRecycle = findViewById(R.id.forecastrecycle)
        forecastrecycle = findViewById(R.id.forecastrecycle)
        forecast_layout = findViewById(R.id.forecast_layout)

        presenter = WeatherActivityPresenter(this)

        initView()
    }

    override fun initView() {

        forecast_layout.setVisibility(View.INVISIBLE)
        currentLocation.text = presenter?.getLocation()
        currentTemprature.text = presenter?.getTemprature()

        val pref = applicationContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        latitude = pref.getString("CurrentLatitude", null)
        longitude = pref.getString("CurrentLongitude", null)

        getWeatherForecast(latitude, longitude)
    }


    fun getWeatherForecast(lat: String, lon: String){

        val loc = "$lat , $lon"
        val days = "5"
        val forecast: ForecastEndpoint = CurrentWeatherApi.getCurrentWeather()!!.create(
            ForecastEndpoint::class.java)

        val call: Call<ForecastDataClass> = forecast.getForecastData(
            key,loc,days)

        call.enqueue(object : Callback<ForecastDataClass> {
            override fun onResponse(call: Call<ForecastDataClass>, response: Response<ForecastDataClass>) {

                forecastData = (response.body()?.forecast?.forecastday as MutableList<ForecastDay>?)!!
                forecastData.removeAt(0)
                adapterSetup()
            }

            override fun onFailure(call: Call<ForecastDataClass>, t: Throwable) {
                launchErrorActivity()
            }

        })
    }

    private fun adapterSetup(){

        forecastAdapter = WeatherForecastAdapter(forecastData, applicationContext)
        val recycleForecast = GridLayoutManager(applicationContext, 1)
        forecastrecycle.setLayoutManager(recycleForecast)
        recycleForecast.isAutoMeasureEnabled = false
        forecastrecycle.setItemAnimator(DefaultItemAnimator())
        forecastrecycle .setAdapter(forecastAdapter)

        slideUp(forecast_layout)
    }

    fun slideUp(view: View) {
        view.setVisibility(View.VISIBLE)
        val animate: Animation = TranslateAnimation(
            0f,
            0f,
            500f,
            0f
        )
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
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
