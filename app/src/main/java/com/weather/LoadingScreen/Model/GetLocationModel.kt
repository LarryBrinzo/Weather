package com.weather.LoadingScreen.Model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import com.google.android.gms.common.ConnectionResult
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import android.location.Geocoder
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.weather.ErrorScreen.ErrorScreenActivity
import com.weather.LoadingScreen.View.LoadingScreenActivity
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("Registered")
open class GetLocationModel(_context: Context, _locationManager: LocationManager): AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    internal lateinit var mLastLocation: Location
    val context: Context=_context
    internal lateinit var mLocationCallback: LocationCallback
    internal var mGoogleApiClient: GoogleApiClient ?= null
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    internal var REQUEST_CHECK_SETTINGS = 1
    var locationManager: LocationManager=_locationManager
    private var mLocation: Location? = null

    companion object {
        private  var currentLocation : String ?= null
    }

    init {
        mGoogleApiClient = GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mGoogleApiClient!!.connect()
    }

    private fun enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()

                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)

                    if (mLocation == null) {
                        startLocationUpdates()
                    }
                    if (mLocation != null) {
                        startLocationUpdates()
                    } else {
                        startLocationUpdates()

                    }

                } else {
                }
                return
            }
        }
    }


    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {

        val locationRequest = LocationRequest.create()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> startLocationUpdates()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                    try {
                        status.startResolutionForResult(context as Activity?, REQUEST_CHECK_SETTINGS)
                    } catch (e: IntentSender.SendIntentException) {
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    mGoogleApiClient?.disconnect()
                    launchErrorActivity()
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onConnected(bundle: Bundle?) {

        enableMyLocationIfPermitted()
         checkLocation()
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }



    protected fun startLocationUpdates() {

        val UPDATE_INTERVAL = (2 * 1000).toLong()
        val FASTEST_INTERVAL: Long = 2000
        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        if (mGoogleApiClient == null) {
            launchErrorActivity()
            return
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient,
            mLocationRequest, this
        )

    }



    override fun onLocationChanged(location: Location) {

        mLastLocation = location

        getLocation(location)

        if (mGoogleApiClient != null) {
            mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        }
    }

    private fun getLocation(location: Location){

        try {

            val geo = Geocoder(context, Locale.getDefault())
            val addresses = geo.getFromLocation(location.latitude, location.longitude, 10)
            if (addresses.isEmpty()) {
            } else {

                for (adr in addresses) {
                    if (adr.locality != null && adr.locality.length > 0) {
                        currentLocation = adr.locality
                        break
                    }
                }

                if(currentLocation!=null){

                    mGoogleApiClient?.disconnect()

                    val pref = context.getSharedPreferences("MyPref", MODE_PRIVATE)
                    @SuppressLint("CommitPrefEdits") val locationsave = pref.edit()

                    locationsave.putString("CurrentLocation", currentLocation)
                    locationsave.putString("CurrentLatitude", location.latitude.toString())
                    locationsave.putString("CurrentLongitude", location.longitude.toString())
                    locationsave.apply()

                    LoadingScreenActivity.getCurrentTemp(location.latitude.toString(),location.longitude.toString(),context)

                }

                else{
                    mGoogleApiClient?.disconnect()
                    launchErrorActivity()
                }
            }
        } catch (e: IOException) {

        }

    }


    private fun launchErrorActivity(){
        Timer("SettingUp", false).schedule(300) {
            val intent = Intent(context, ErrorScreenActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finishAffinity()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()

        }
    }

    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient!!.isConnected()) {
              mGoogleApiClient?.disconnect()
        }
    }


}