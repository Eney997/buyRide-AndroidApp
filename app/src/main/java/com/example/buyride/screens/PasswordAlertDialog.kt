package com.example.buyride.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.buyride.R
import com.example.buyride.database.DatabaseCon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PasswordAlertDialog
(
    username:String,
    db:DatabaseCon,
    onDismiss:()->Unit
)
{
    val txt = remember { mutableStateOf("") }
    val context = LocalContext.current
    val alertColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val errorMessage = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize())
    {
        Dialog(onDismissRequest = onDismiss)
        {
            Column(
                modifier = Modifier
                    .height(260.dp)
                    .background(alertColor, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(21.dp)
            ) {
                Text(
                    text = "Privacy Policy",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = when {
                        errorMessage.value.isEmpty() -> "Please enter your new password"
                        errorMessage.value == "Password changed successfully!" -> errorMessage.value
                        else -> errorMessage.value
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        errorMessage.value == "Password changed successfully!" -> Color(0xFF4CAF50) // Green
                        errorMessage.value.isNotEmpty() -> Color.Red // Error
                        else -> Color.White
                    }
                )

                TextField(
                    value = txt.value,
                    onValueChange = { txt.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                    ),
                    placeholder = {
                        Text(text = "Change Password", color = Color.White, fontSize = 19.sp)
                    },
                    maxLines = 1,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    )
                )

                Button(
                    onClick = {
                        val newPassword = txt.value.trim()

                        when {
                            newPassword.isEmpty() -> {
                                errorMessage.value = "Password can't be empty"
                                return@Button
                            }
                            newPassword.length <= 6 -> {
                                errorMessage.value = "Password must be 7 characters long!"
                                return@Button
                            }
                            newPassword.contains(" ") -> {
                                errorMessage.value = "Password can't contain spaces!"
                                return@Button
                            }
                            else -> {
                                val success = db.changePassword(username, newPassword)
                                if (success) {
                                    errorMessage.value = "Password changed successfully!"
                                    coroutineScope.launch {
                                        delay(2000)
                                        onDismiss()
                                    }
                                } else {
                                    errorMessage.value = "Failed to change password!"
                                    return@Button
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(50.dp).width(120.dp)
                        .border(1.dp, Color.LightGray, shape = RoundedCornerShape(10.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = alertColor),
                ) {
                    Text(text = "Confirm", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}