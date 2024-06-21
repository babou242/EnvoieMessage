package com.example.envoiemessage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.envoiemessage.model.LocationManager
import com.example.envoiemessage.ui.theme.EnvoieMessageTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class MainActivity : ComponentActivity() {
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager = LocationManager(this)
        enableEdgeToEdge()
        setContent {
            var location by remember { mutableStateOf<Pair<Double,Double>?>(null) }

            val locationCallback = object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult){
                    locationResult.locations.firstOrNull()?.let{
                        location = Pair(it.latitude,it.longitude)
                    }
                }
            }
            DisposableEffect(Unit) {
                locationManager.startLocationUpdates(locationCallback)
                onDispose {
                    locationManager.stopLocationUpdates(locationCallback)
                }
            }
            EnvoieMessageTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding),location)
                }
            }
        }
    }
}

