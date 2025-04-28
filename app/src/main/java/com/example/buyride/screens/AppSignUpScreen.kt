package com.example.buyride.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.buyride.R
import com.example.buyride.database.DatabaseCon
import com.example.buyride.database.UserClass
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSignUpScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    )
    {
        val mySnackBarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current
        val snackBarColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
        val scope = rememberCoroutineScope()
        val scroll = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scroll),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            val userName = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val gmail = remember { mutableStateOf("") }
            val selectedLocation = remember { mutableStateOf("") }
            val locations = listOf("New York", "Los Angeles", "Chicago", "Houston", "Miami")
            var expanded by remember { mutableStateOf(false) }
            var expandedGender by remember { mutableStateOf(false) }
            val gender = listOf("Male","Female","Other")
            val selectedGender = remember { mutableStateOf("") }

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
                placeholder = { Text("Enter UserName", color = Color.Gray, style = MaterialTheme.typography.titleLarge) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = password.value,
                onValueChange = { password.value = it },
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
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = gmail.value,
                onValueChange = { gmail.value = it },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Enter Gmail", color = Color.Gray,  style = MaterialTheme.typography.titleLarge) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 20.sp
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp)
            ) {
                TextField(
                    value = selectedLocation.value,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text("Select Location", color = Color.Gray, style = MaterialTheme.typography.titleLarge)
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    locations.forEach { location ->
                        DropdownMenuItem(
                            text = { Text(location) },
                            onClick = {
                                selectedLocation.value = location
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = expandedGender,
                onExpandedChange = { expandedGender = !expandedGender },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp)
            ) {
                TextField(
                    value = selectedGender.value,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text("Select Gender", color = Color.Gray, style = MaterialTheme.typography.titleLarge)
                    },
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow"
                        )
                    },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expandedGender,
                    onDismissRequest = { expandedGender = false }
                ) {
                    gender.forEach { gender ->
                        DropdownMenuItem(
                            text = { Text(gender) },
                            onClick = {
                                selectedGender.value = gender
                                expandedGender = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Button(
                onClick = {
                    val getUsername = userName.value
                    val getPassword = password.value
                    val getGmail = gmail.value
                    val getLocation = selectedLocation.value
                    val getGender = selectedGender.value
                    val myDb = DatabaseCon(context)


                    //handle all empty inputs
                    if(getUsername.isEmpty() || getPassword.isEmpty() || getGmail.isEmpty() || getLocation.isEmpty() || getGender.isEmpty()) {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Empty input detected!")
                        }
                        return@Button
                    }

                    //handle username length
                    if(getUsername.length > 20)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Username too long!")
                        }
                        return@Button
                    }

                    //handle username minimum length
                    if(getUsername.length <= 6 )
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Username too short!")
                            }
                        return@Button
                    }

                    //handle username containing letters only
                    if (!getUsername.all { it.isLetter() }) {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Username must contain letters only!")
                        }
                        return@Button
                    }

                    //handle password length
                    if(getPassword.length > 20)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Password too long!")
                        }
                        return@Button
                    }

                    //handle password minimum length
                    if(getPassword.length <= 6 )
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Password too short!")
                        }
                        return@Button
                    }

//                    if (!getGmail.endsWith("@gmail.com")) also if not ends with @gmail.com
                    //handle gmail
                    val gmailRegex = Regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")

                    if (!gmailRegex.matches(getGmail)) {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Invalid Gmail address!")
                        }
                        return@Button
                    }

                    //handle gmail length
                    if(getGmail.length > 35)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Gmail too long!")
                        }
                        return@Button
                    }

                    //handle username duplication
                    if(myDb.isUserNameTaken(getUsername))
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Username already taken!")
                        }
                        return@Button
                    }

                    //insert user information in database
                    val user = UserClass(getUsername, getPassword, getGmail, getLocation,getGender)
                    myDb.insertUserInformation(user)


                    scope.launch {
                        mySnackBarHostState.showSnackbar("Sign Up Successful!")
                    }

                    //clear inputs
                    userName.value = ""
                    password.value = ""
                    gmail.value = ""
                    selectedLocation.value = ""
                    selectedGender.value = ""

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
            )
            {
                Text(text = "Sign Up", color = Color.Gray, fontSize = 20.sp)
            }
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