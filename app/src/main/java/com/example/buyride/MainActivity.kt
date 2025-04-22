package com.example.buyride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buyride.screens.AppLogInScreen
import com.example.buyride.screens.AppSignUpScreen
import com.example.buyride.screens.AppUserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val myNavController = rememberNavController()

            NavHost(navController = myNavController, startDestination = "AppLogInScreen"){
                composable("AppLogInScreen"){AppLogInScreen(myNavController)}
                composable("AppSignUpScreen"){AppSignUpScreen()}
                composable("AppUserScreen"){ AppUserScreen() }
            }

        }
    }
}
