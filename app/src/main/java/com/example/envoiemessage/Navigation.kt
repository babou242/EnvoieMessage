package com.example.envoiemessage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.envoiemessage.ui.Screen.MainScreen
import com.example.envoiemessage.ui.Screen.PermissionScreen


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
//        composable(route = Screen.ContactScreen.route) {
//            ContactScreen(navController = navController, modifier = modifier)
//        }

    }
}



sealed class Screen(val route: String){
    object PermissionScreen : Screen("permission_screen")
    object MainScreen : Screen("main_screen")
}