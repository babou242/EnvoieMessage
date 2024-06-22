package com.example.envoiemessage.ui.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.envoiemessage.ui.viewmodel.SMSViewModel


@Composable
fun MainScreen(modifier: Modifier = Modifier,location : Pair<Double,Double>?) {
    val viewModel: SMSViewModel = viewModel()
    val phoneNumber = "0609160538"
    val message = "J'ai activé le bracelet Saf'Here, je suis dans une situation dangereuse voici ma localisation : "
    var messageSent by remember { mutableStateOf(false) }

    fun getGoogleMapsUrl(location: Pair<Double, Double>): String {
        return "https://www.google.com/maps?q=${location.first},${location.second}"
    }
    location?.let {
        if (!messageSent) {
            Column(modifier=Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "SMS not sent yet")
            }
            LaunchedEffect(it) {
                val url = getGoogleMapsUrl(it)
                viewModel.sendSMS(phoneNumber, message + url)
                messageSent = true
            }
        }else{
            Column(modifier=Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "SMS sent")
            }
        }
    }
        ?: Column(modifier=Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Searching for location")
        }

}