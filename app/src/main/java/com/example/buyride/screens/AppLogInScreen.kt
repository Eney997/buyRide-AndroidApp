package com.example.buyride.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLogInScreen(
    myNavController: NavController
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val userName = remember { mutableStateOf("") }
            val userPassword = remember { mutableStateOf("") }

            TextField(
                value = userName.value,
                onValueChange = { userName.value = it },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                ),
                placeholder = { Text("Enter Username", color = Color.Gray, fontSize = 20.sp) },
                maxLines = 1,
                textStyle = TextStyle(
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
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.Gray,
                ),
                placeholder = { Text("Enter Password", color = Color.Gray, fontSize = 20.sp) },
                maxLines = 1,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    myNavController.navigate("AppUserScreen") // you can test if navigation works now
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
                Text(text = "LogIn", fontSize = 20.sp, color = Color.White)
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
    }
}