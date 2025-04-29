package com.example.buyride.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.buyride.database.ChargeTransaction
import com.example.buyride.database.DatabaseCon
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargeScreen(){

    val scroll = rememberScrollState()
    val context = LocalContext.current
    val mySnackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val snackBarColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val cardHName = remember { mutableStateOf("") }
    val cardHLastName = remember { mutableStateOf("") }
    val digits = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }
    val expData = remember { mutableStateOf("") }
    val locations = listOf("New York", "Los Angeles", "Chicago", "Houston", "Miami")
    var expanded by remember { mutableStateOf(false) }
    val selectedLocation = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    )
    {
        Column ( modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scroll)
            .padding(start = 20.dp, end = 20.dp, bottom = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            TextField(
                value = cardHName.value,
                onValueChange = { val filteredName = it.filter { char -> char.isLetter()}.uppercase()
                    cardHName.value = filteredName
                },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Card Holder Name", color = Color.Gray, style = MaterialTheme.typography.titleMedium,fontSize = 19.sp) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 19.sp
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                value = cardHLastName.value,
                onValueChange = {
                    val filteredLastName = it.filter { char -> char.isLetter()}.uppercase()
                    cardHLastName.value = filteredLastName
                },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Card Holder Last Name", color = Color.Gray, style = MaterialTheme.typography.titleMedium,fontSize = 19.sp) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 19.sp
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                value = digits.value,
                onValueChange = {
                    val filteredDigits = it.filter { char -> char.isDigit() }
                    digits.value = filteredDigits
                },
                modifier = Modifier
                    .width(350.dp)
                    .height(65.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                ),
                placeholder = { Text("Card 16 digit number",color = Color.Gray,style = MaterialTheme.typography.titleMedium,fontSize = 19.sp) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontSize = 19.sp
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row (modifier = Modifier,horizontalArrangement = Arrangement.spacedBy(10.dp))
            {
                TextField(
                    value = cvv.value,
                    onValueChange = {
                        val filteredDigits = it.filter { char -> char.isDigit() }
                        cvv.value = filteredDigits
                    },
                    modifier = Modifier
                        .width(160.dp)
                        .height(65.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                    ),
                    placeholder = { Text("CVV/CVC", color = Color.Gray,style = MaterialTheme.typography.titleMedium,fontSize = 19.sp) },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 19.sp
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = expData.value,
                    onValueChange = {
                        val filteredDigits = it.filter { char -> char.isDigit() }
                        expData.value = filteredDigits
                    },
                    modifier = Modifier
                        .width(160.dp)
                        .height(65.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.White,
                    ),
                    placeholder = { Text("Exp.Year", color = Color.Gray,style = MaterialTheme.typography.titleMedium,fontSize = 19.sp) },
                    maxLines = 1,
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 19.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

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
                        Text("Select delivery location", color = Color.Gray, style = MaterialTheme.typography.titleMedium,fontSize = 19.sp)
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 19.sp
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

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {
                    val db = DatabaseCon(context)
                    val sharedPref = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    val takeUsername = sharedPref.getString("username", null)
                    val takeCardHolderName = cardHName.value
                    val takeCardHolderLastName = cardHLastName.value
                    val takeCarDigits = digits.value
                    val takeCardCvvCvc = cvv.value
                    val takeCardExpDate = expData.value
                    val takeLocation = selectedLocation.value

                    //handle empty inputs
                    if(takeCardHolderName.isEmpty() || takeCardHolderLastName.isEmpty() || takeCarDigits.isEmpty() || takeCardCvvCvc.isEmpty() || takeCardExpDate.isEmpty() || takeLocation.isEmpty())
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Please fill all fields")
                        }
                        return@Button
                    }

                    //handle long inputs(name lastname)
                    if(takeCardHolderName.length > 15 || takeCardHolderLastName.length > 15)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Name or LastName is too long")
                        }
                        return@Button
                    }

                    //handle  short name and last name
                    if(takeCardHolderName.length < 2 || takeCardHolderLastName.length < 2)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Name or LastName is too short")
                        }
                        return@Button
                    }

                    //handle 16 digit len
                    if(takeCarDigits.length != 16)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Please enter 16 digits")
                        }
                        return@Button
                    }

                    //handle exp year
                    if(takeCardExpDate.length != 4 || takeCardExpDate.toInt() !in 2025..2035)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Please enter a valid year")
                        }
                        return@Button
                    }

                    //handle cvv len
                    if(takeCardCvvCvc.length != 3)
                    {
                        scope.launch {
                            mySnackBarHostState.showSnackbar("Please enter 3 digits")
                        }
                        return@Button
                    }

                    //make stored prompts ready to save in database
                    if (takeUsername != null) {
                        val chargeTransaction = ChargeTransaction(
                            username = takeUsername,
                            holderName = takeCardHolderName,
                            holderLastName = takeCardHolderLastName,
                            cardDigits = takeCarDigits,
                            expDate = takeCardExpDate,
                            cvvCvc = takeCardCvvCvc,
                            deliveryLocation = takeLocation,
                            orderTime= System.currentTimeMillis()
                        )

                        //insert data in database
                        db.insertTransaction(chargeTransaction)

                        scope.launch {
                            mySnackBarHostState.showSnackbar("You got charged.You will get delivery notification on gmail account after 2 days.")
                        }

                        //clear fields
                        cardHName.value = ""
                        cardHLastName.value = ""
                        digits.value = ""
                        expData.value = ""
                        cvv.value = ""
                        selectedLocation.value = ""
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
            )
            {
                Text(text = "Purchase", color = Color.Gray, style = MaterialTheme.typography.titleMedium,fontSize = 19.sp)
            }
        }

        SnackbarHost(
            hostState = mySnackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 105.dp),
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