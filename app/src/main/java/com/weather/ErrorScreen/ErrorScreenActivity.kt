package com.weather.ErrorScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.weather.LoadingScreen.View.LoadingScreenActivity
import com.weather.R

class ErrorScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error_screen)

        val retry: Button = findViewById(R.id.retry)

        retry.setOnClickListener {
            launchWeatherActivity()
        }

    }

    private fun launchWeatherActivity(){
            val intent = Intent(applicationContext, LoadingScreenActivity::class.java)
            startActivity(intent)
            finishAffinity()
    }
}
