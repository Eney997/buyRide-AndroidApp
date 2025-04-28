package com.example.buyride.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.buyride.R
import com.example.buyride.database.DatabaseCon
import kotlinx.coroutines.launch
import androidx.core.content.edit

@Composable
fun AppLogInScreen(
    myNavController: NavController
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black))
    {
        //todo internet checker need to add

        val mySnackBarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current
        val snackBarColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val userName = remember { mutableStateOf("") }
            val userPassword = remember { mutableStateOf("") }
            var isPasswordVisible by remember { mutableStateOf(false) }

            TextField(
                value = userName.value,
                onValueChange = { userName.value = it },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Enter Username", color = Color.Gray, style = MaterialTheme.typography.titleLarge) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 20.sp
                )

            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                value = userPassword.value,
                onValueChange = { userPassword.value = it },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Enter Password", color = Color.Gray, style = MaterialTheme.typography.titleLarge) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 20.sp
                ),

                trailingIcon = {
                    TextButton(onClick = {isPasswordVisible = !isPasswordVisible}){
                        Text(text = if(isPasswordVisible) "Hide?" else "Show?", color = Color.Gray, style = MaterialTheme.typography.titleMedium)
                    }
                },
                visualTransformation = if(isPasswordVisible) androidx.compose.ui.text.input.VisualTransformation.None else androidx.compose.ui.text.input.PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick =
                {
                    val getUserName = userName.value
                    val getUserPassword = userPassword.value
                    val myDb = DatabaseCon(context)

                    if(getUserName.isEmpty() || getUserPassword.isEmpty())
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Empty Fields")
                        }
                        return@Button
                    }

                    if(myDb.logInAccountByUser(getUserName, getUserPassword))
                    {
                        //if we logged in app will save that shit making that shit true bitch mr white
                        val sharedPref = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                        sharedPref.edit { putBoolean("is_logged_in", true) }
                        sharedPref.edit { putString("username", getUserName) }

                        myNavController.navigate("AppUserScreen"){
                            popUpTo("AppLogInScreen"){inclusive = true}
                            launchSingleTop = true
                        }
                    }
                    else
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Invalid Credentials")
                        }
                        return@Button
                    }

                },
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp)
                    .border(
                        2.dp,
                        Color.Gray,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = "Log In", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(40.dp))

            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        style = androidx.compose.ui.text.SpanStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    ) {
                        append("Don't have an account?")
                    }

                    withStyle(
                        style = androidx.compose.ui.text.SpanStyle(
                            color = Color.White,
                            fontSize = 23.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    ) {
                        append(" ")
                        append("Create Now!")
                    }
                },
                onClick = { offset ->
                    if (offset in 23 until 35) {
                        myNavController.navigate("AppSignUpScreen")
                    }
                }
            )
        }

        SnackbarHost(
            hostState = mySnackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            snackbar = { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = snackBarColor,
                    contentColor = Color.White
                )
            }
        )

    }
}