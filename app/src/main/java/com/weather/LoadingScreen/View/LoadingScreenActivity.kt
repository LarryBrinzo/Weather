package com.weather.LoadingScreen.View

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.weather.DataClass.CurrentTemperatureDataClass
import com.weather.ErrorScreen.ErrorScreenActivity
import com.weather.LoadingScreen.API.CurrentWeatherApi
import com.weather.LoadingScreen.EndPoints.CurrentTemperatureEndpoint
import com.weather.LoadingScreen.Model.GetLocationModel
import com.weather.R
import com.weather.WeatherInfo.View.WeatherActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class LoadingScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val loadimg: ImageView=findViewById(R.id.loadimg)

        loadimg.startAnimation(
            AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_indefinitely))

        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        GetLocationModel(this, locationManager)
    }

    companion object {

        val key = "b37b5819be4b4c3fb2e175517192808"
        val currentWeather: CurrentTemperatureEndpoint = CurrentWeatherApi.getCurrentWeather()!!.create(
            CurrentTemperatureEndpoint::class.java)

        fun getCurrentTemp(lat: String, lon: String, context: Context){

            val loc = "$lat , $lon"

            val call: Call<CurrentTemperatureDataClass> = currentWeather.getCurrentTemp(key,loc)

            call.enqueue(object : Callback<CurrentTemperatureDataClass> {
                override fun onResponse(call: Call<CurrentTemperatureDataClass>, response: Response<CurrentTemperatureDataClass>) {

                    var currentTemperature=response.body()?.current?.temp_c
                    currentTemperature=currentTemperature!!.substring(0, currentTemperature.indexOf('.'))
                    currentTemperature="$currentTemperature°"

                    val pref = context.getSharedPreferences("MyPref", MODE_PRIVATE)
                    @SuppressLint("CommitPrefEdits") val tempsave = pref.edit()

                    tempsave.putString("CurrentTemperature", currentTemperature)
                    tempsave.apply()
                    launchWeatherActivity(context)
                }

                override fun onFailure(call: Call<CurrentTemperatureDataClass>, t: Throwable) {
                    launchErrorActivity(context)
                }

            })
        }

        private fun launchWeatherActivity(context: Context){
            Timer("SettingUp", false).schedule(300) {
                val intent = Intent(context, WeatherActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finishAffinity()
            }
        }

        private fun launchErrorActivity(context: Context){
            Timer("SettingUp", false).schedule(300) {
                val intent = Intent(context, ErrorScreenActivity::class.java)
                context.startActivity(intent)
                (context as Activity).finishAffinity()
            }
        }

    }


}
