package com.example.buyride.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import com.example.buyride.MainActivity

@Composable
fun AppUserScreen() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

        val context = LocalContext.current

        Button(onClick = {

            val sharedPref = context.getSharedPreferences("user_pref", 0)
            sharedPref.edit { putBoolean("is_logged_in", false) }

            // Start LoginActivity and clear backstack
            val i = Intent(context, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)

        }) {
            Text(text = "Logout", color = Color.White)
        }

    }
}