package com.weather

import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.weather.ErrorScreen.ErrorScreenActivity
import com.weather.LoadingScreen.View.LoadingScreenActivity
import com.weather.WeatherInfo.View.WeatherActivity
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class ErrorActivityInstrumentationTest {

    @Rule
    @JvmField
    val mActivityRule: IntentsTestRule<ErrorScreenActivity> = IntentsTestRule(ErrorScreenActivity::class.java)

    @Test
    fun textCheck(){
        Espresso.onView((withId(R.id.retry)))
            .perform(ViewActions.click())
        intended(hasComponent(LoadingScreenActivity::class.java.getName()))
    }

}