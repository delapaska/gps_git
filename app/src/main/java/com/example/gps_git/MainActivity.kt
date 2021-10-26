package com.example.gps_git

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult


class MainActivity : AppCompatActivity() {
//    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationManager: LocationManager? = null
    private lateinit var cordButton: Button

    // globally declare LocationCallback
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            println("new location received: $p0")
        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    private fun setupViews() {
        cordButton = findViewById(R.id.crdbtn)
        with(cordButton) {
            setOnClickListener {
                if (isLocationPermissionGranted()) {
                    println("permissions granted")
                    getLocationUpdates()
                } else {
                    println("permissions denied")
                    requestLocationPermission()
                }
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        ActivityCompat.requestPermissions(this, permissions, 0)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("requestCode: $requestCode")
        when (requestCode) {
            0 -> {
                println(grantResults.first())
                if (grantResults.firstOrNull() == -1) {
                    showToast("Can't request permissions")
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLocationUpdates() {
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        LocationRequest().also { locationRequest = it }
//        locationRequest.interval = 50000
//        locationRequest.fastestInterval = 50000
//        locationRequest.smallestDisplacement = 170f // 170 m = 0.1 mile
//        locationRequest.priority =
//            LocationRequest.PRIORITY_HIGH_ACCURACY //set according to your app function

        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L,
            0F,
            object : LocationListener {
                override fun onLocationChanged(p0: Location) {
                    println("new location received: $p0")
                }
            }
        )
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location =
                        locationResult.lastLocation
                    Toast.makeText(applicationContext, location.toString(), Toast.LENGTH_SHORT)


                }


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


//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                null
//
//            )

            return
        }

    }

    fun stopLocationUpdates() {
//        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    // start receiving location update when activity  visible/foreground
    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }
}

