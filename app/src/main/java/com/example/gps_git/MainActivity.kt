package com.example.gps_git

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gps_git.database.MyDbManager
import com.example.gps_git.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val myDbManager = MyDbManager(this)
    private lateinit var crdbtn: Button
    private lateinit var coordText: TextView
    private var locationManager: LocationManager? = null
    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            println("new location received: $p0")

        }

        override fun onLocationAvailability(p0: LocationAvailability) {
            super.onLocationAvailability(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        println("JOOOPA")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        crdbtn = findViewById(R.id.receiveCoordButton)
        coordText = findViewById(R.id.coordTextView)
        binding = ActivityMainBinding.inflate(layoutInflater)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        setupViews()


    }


    private fun setupViews() {
        with(binding) {
            with(crdbtn) {
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
        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L,
            0F,
            object : LocationListener {
                override fun onLocationChanged(p0: Location) {
                    println("Your latitude is " + p0.latitude)
                    coordTextView.text = ""
                    myDbManager.openDB()
                    myDbManager.insertTo_Db(
                        coordText.text.toString(),
                        coordText.text.toString()
                    )
                    val datalist = myDbManager.readDbData()
                    for (p0 in datalist) {
                        coordText.append(p0)
                        coordText.append("\n")
                    }


                }
            }
        )
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                if (locationResult.locations.isNotEmpty()) {
                    val location =
                        locationResult.lastLocation


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

