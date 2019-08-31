package com.weather.ErrorScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.weather.R
import java.util.*
import kotlin.concurrent.schedule

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
        Timer("SettingUp", false).schedule(1000) {
            val intent = Intent(applicationContext, ErrorScreenActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}
