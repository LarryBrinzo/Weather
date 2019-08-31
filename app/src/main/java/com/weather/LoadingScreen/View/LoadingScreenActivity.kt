package com.weather.LoadingScreen.View

import android.content.Context
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.weather.LoadingScreen.Model.GetLocationModel
import com.weather.R

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
}
