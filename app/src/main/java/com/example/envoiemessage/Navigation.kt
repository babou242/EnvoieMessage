package com.example.envoiemessage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.envoiemessage.ui.Screen.ContactScreen
import com.example.envoiemessage.ui.Screen.MainScreen
import com.example.envoiemessage.ui.Screen.PermissionScreen


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Navigation(modifier : Modifier,location : Pair<Double,Double>?){




    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PermissionScreen.route){
        composable(route = Screen.PermissionScreen.route) {
            PermissionScreen(navController = navController, modifier = modifier)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(modifier = modifier,location)
        }
        composable(route = Screen.ContactScreen.route) {
            ContactScreen(navController = navController)
        }

    }
}



sealed class Screen(val route: String){
    object PermissionScreen : Screen("permission_screen")
    object MainScreen : Screen("main_screen")
    object ContactScreen : Screen("contact_screen")
}