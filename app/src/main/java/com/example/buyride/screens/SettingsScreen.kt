package com.example.buyride.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.buyride.MainActivity
import com.example.buyride.R
import com.example.buyride.database.DatabaseCon
import com.example.buyride.database.UserClass

@Composable
fun SettingsScreen()
{
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
    val username = sharedPref.getString("username",null)
    var user by remember {mutableStateOf<UserClass?>(null)}
    val db = remember {DatabaseCon(context)}
    var isTextVisible by remember { mutableStateOf(false) }
    val counterBoxColors = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val scrollIfNeeded = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }
    var totalOrderCount by remember { mutableIntStateOf(0) }
    var activeOrderCount by remember { mutableIntStateOf(0) }

    //take info from db to give notification user that his moto is already arrived
    LaunchedEffect(Unit) {
        username?.let {
            user = db.getUserInfoToStoreInApp(it)

            val orders = db.getOrdersForUser(it) // you need to create this function
            val currentTime = System.currentTimeMillis()
            val twoDaysInMillis = 2 * 24 * 60 * 60 * 1000L //2 days after ull get ur moto

            totalOrderCount = orders.size
            activeOrderCount = orders.count { order ->
                currentTime - order.orderTime < twoDaysInMillis
            }
        }
    }


    //show dialog box
    if (showDialog && username != null)
    {
        PasswordAlertDialog (username = username,db=db,onDismiss = {showDialog = false})
    }

    LaunchedEffect(Unit)
    {
        username?.let {
            user = db.getUserInfoToStoreInApp(it)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black), contentAlignment = Alignment.TopStart)
    {


        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollIfNeeded)
            .padding(bottom = 100.dp))
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Icon(painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = "user icon",
                    tint = Color.White,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.fillMaxWidth())
                {
                    Text(text = "UserName: ${user?.username}", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Gmail: ${user?.gmail}", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                }
            }

            HorizontalDivider(modifier = Modifier
                .padding(top = 10.dp,start = 20.dp,end = 20.dp),
                thickness = 1.dp,
                color = Color.DarkGray
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Box(
                    modifier = Modifier
                        .background(color = counterBoxColors, shape = RoundedCornerShape(12.dp))
                        .padding(10.dp) // Padding inside the box
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = totalOrderCount.toString(), color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                        Text("Total Orders", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                Box(
                    modifier = Modifier
                        .background(color = counterBoxColors, shape = RoundedCornerShape(12.dp))
                        .padding(10.dp) // Padding inside the box
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = activeOrderCount.toString(), color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                        Text("Active Orders", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp))
            {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(painter = painterResource(id = R.drawable.ic_password), contentDescription = "Change password", tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Change Password", color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Change password", tint = Color.White, modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            showDialog = true
                        })
                }

                HorizontalDivider(modifier = Modifier,thickness = 1.dp, color = Color.DarkGray)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(painter = painterResource(id = R.drawable.ic_app_shortcut), contentDescription = "App Story", tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "About App", color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "About App", tint = Color.White, modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            isTextVisible = !isTextVisible
                        })
                }

                AnimatedVisibility(visible = isTextVisible)
                {
                    Text(
                        text = "Welcome to BuyRide — your trusted destination for two-wheeled adventures.\n" +
                                "\n" +
                                "This app was crafted with a passion for motorcycles and a love for smooth, hassle-free shopping. Whether you're a seasoned rider or just getting started, BuyRide is here to help you find the perfect ride with ease.\n" +
                                "\n" +
                                "Every feature, every detail, and every interaction was thoughtfully designed by an independent developer with the goal of making your bike buying experience enjoyable and exciting.\n" +
                                "\n" +
                                "Browse, discover, and purchase your dream motorbike — all in one place.\n" +
                                "\n" +
                                "Thank you for choosing BuyRide.\n" +
                                "Your journey begins here.",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    )
                }

                HorizontalDivider(modifier = Modifier,thickness = 1.dp, color = Color.DarkGray)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(painter = painterResource(id = R.drawable.ic_exit), contentDescription = "Log out", tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Log out", color = Color.White, fontSize = 20.sp,fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Log out", tint = Color.White, modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            sharedPref.edit { clear() }
                            val i = Intent(context, MainActivity::class.java)
                            i.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(i)
                        })
                }
            }
        }
    }
}