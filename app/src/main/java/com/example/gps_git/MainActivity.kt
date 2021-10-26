package com.example.gps_git

import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationManager : LocationManager? = null
   private lateinit var cordButton:Button
    // in onCreate() initialize FusedLocationProviderClient


    // globally declare LocationRequest
    private lateinit var locationRequest: LocationRequest

    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init(){
        cordButton = findViewById(R.id.crdbtn)
        with(cordButton) {
           setOnClickListener {   getLocationUpdates()}
        }

    }
    private fun getLocationUpdates() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        LocationRequest().also { locationRequest = it }
        locationRequest.interval = 50000
        locationRequest.fastestInterval = 50000
        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
        locationRequest.priority =
            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {

                    val location =
                        locationResult.lastLocation
                    Toast.makeText(applicationContext,location.toString(),Toast.LENGTH_SHORT)


                }


            }
        }
        //start location updates
       fun startLocationUpdates() {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null

                )

                return
            }

        }


       fun stopLocationUpdates() {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }


          fun onPause() {
            super.onPause()
            stopLocationUpdates()
        }

        // start receiving location update when activity  visible/foreground
        fun onResume() {
            super.onResume()
            startLocationUpdates()
        }

    }
}

