package com.weather.WeatherInfo.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.weather.R
import com.weather.WeatherInfo.Contract.ContractInterface
import com.weather.WeatherInfo.WeatherPresenter.WeatherActivityPresenter

class WeatherActivity : AppCompatActivity(), ContractInterface.View {

    lateinit var currentTemprature : TextView
    lateinit var currentLocation : TextView
    lateinit var forecastRecycle : RecyclerView
    private var presenter : WeatherActivityPresenter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        presenter = WeatherActivityPresenter(this)
    }

    override fun initView() {

        currentTemprature = findViewById(R.id.currenttemprature)
        currentLocation = findViewById(R.id.currentlocation)
        forecastRecycle = findViewById(R.id.forecastrecycle)



    }

    override fun updateLocationData() {
    }

    override fun updateForecastData() {

    }
}
