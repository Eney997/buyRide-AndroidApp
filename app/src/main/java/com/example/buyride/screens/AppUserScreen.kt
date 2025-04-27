package com.example.buyride.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.buyride.R
import com.example.buyride.database.DatabaseCon
import com.example.buyride.database.UserClass
import androidx.core.content.edit
import com.example.buyride.MainActivity
import com.example.buyride.data.BikeScreen

//data class for bottom nav item
data class BottomNavItem(val name:String,val route:String,val icon:Int)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppUserScreen() {

    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem("Home","HomeScreen", R.drawable.ic_home),
        BottomNavItem("Product","ProductScreen", R.drawable.ic_grocery),
        BottomNavItem("Favourites","FavouritesScreen", R.drawable.ic_favorite),
        BottomNavItem("Settings","SettingsScreen", R.drawable.ic_settings)
    )

    Scaffold (
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                navController = navController,
                onItemClick = {item ->
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId){saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding())
        {
            Navigation(navController)
        }
    }
}


//navigation in bottom bar
@Composable
fun Navigation(navHostController: NavHostController)
{
    NavHost(startDestination = "HomeScreen", navController = navHostController)
    {
        composable("HomeScreen"){HomeScreen()}
        composable("ProductScreen"){ ProductScreen() }
        composable("FavouritesScreen"){ FavouritesScreen() }
        composable("SettingsScreen"){ SettingsScreen() }
    }

}

//create bottom nav design
@Composable
fun BottomNavigationBar
(
    items:List<BottomNavItem>,
    navController:NavController,
    onItemClick:(BottomNavItem) -> Unit
)
{

    val backStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val bottomBarBkColor = Color(ContextCompat.getColor(context, R.color.snackBarColor))

    NavigationBar(modifier = Modifier.height(100.dp),containerColor = bottomBarBkColor)
    {
        items.forEach{item ->
            val selected = item.route == backStackEntry?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name,
                            modifier = Modifier.size(32.dp),
                            tint = if(selected) Color.White else Color.Gray
                        )

                        if(selected)
                        {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        }

                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}



@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val boxCol = Color(ContextCompat.getColor(context, R.color.snackBarColor))
    val scroll = rememberScrollState()

    val motoObligationTitle = listOf(
        "Verify the Bike's Documents",
        "Inspect the Bike Physically",
        "Request a Test Ride",
        "Ask for Service History",
        "Check for Outstanding Loans",
    )

    val motoObligationDesc = listOf(
        "Always check the RC (Registration Certificate), insurance papers, and pollution certificate before purchasing.",
        "Ensure you check the bike’s condition, engine sound, tire wear, and brakes for any issues.",
        "Never buy without a test ride. Feel the handling, brakes, gear shift, and comfort.",
        "A well-maintained bike comes with service records. It helps you judge how well it’s been taken care of.",
        "Make sure the bike is not under any active loan or has pending traffic fines.",
    )

    val motoObligationIcons = listOf(
        R.drawable.moto_o,
        R.drawable.moto_t,
        R.drawable.moto_th,
        R.drawable.moto_f,
        R.drawable.moto_fv,
    )

    val obligationItems = motoObligationTitle.zip(motoObligationIcons)

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.Black)
        .padding(bottom = 110.dp, top = 50.dp)
    )
    {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scroll))
        {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = boxCol),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rider),
                        contentDescription = "rider",
                        modifier = Modifier.size(170.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "YOUR JOURNEY STARTS HERE!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                obligationItems.forEach { (obligation, iconResId) ->
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(230.dp)
                            .background(color = boxCol, shape = RoundedCornerShape(10.dp))
                            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Image(
                                painter = painterResource(id = iconResId),
                                contentDescription = obligation,
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = obligation,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = motoObligationDesc[motoObligationTitle.indexOf(obligation)],
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(start = 10.dp,end = 10.dp)) {
                Text(
                    "TOP SELLING BIKES",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column {
                Motorcycle.topSellingBikes.take(4).forEach { bike ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = boxCol)
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = bike.imageRes),
                                contentDescription = bike.name,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(text = bike.name, color = Color.White, fontSize = 23.sp)
                                Text(text = bike.type, color = Color.White, style = MaterialTheme.typography.bodyLarge)
                                Text(text = bike.description, color = Color.White,style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        BikeScreen(paddingValues = PaddingValues(top = 35.dp, bottom = 100.dp))
    }
}


@Composable
fun FavouritesScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center){
        Text(text= "Im in FavouritesScreen screen",color = Color.White)
    }
}

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

    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.TopStart)
    {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollIfNeeded).padding(bottom = 100.dp))
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
                    modifier = Modifier.width(50.dp).height(50.dp)
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

                Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween)
                {
                    Box(
                        modifier = Modifier
                            .background(color = counterBoxColors, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                            Text("Total Orders", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(color = counterBoxColors, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                            Text("Active Orders", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(color = counterBoxColors, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                            Text("Cancel Orders", color = Color.White, fontSize = 17.sp,fontWeight = FontWeight.Medium)
                        }
                    }

                }

            Spacer(modifier = Modifier.height(20.dp))

            Column (modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp))
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
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            context.startActivity(i)
                        })
                }
            }
        }
    }
}