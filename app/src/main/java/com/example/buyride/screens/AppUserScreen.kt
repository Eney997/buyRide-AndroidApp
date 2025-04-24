package com.example.buyride.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center){
        Text(text= "Im in home screen",color = Color.White)
    }
}

@Composable
fun FavouritesScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center){
        Text(text= "Im in FavouritesScreen screen",color = Color.White)
    }
}

@Composable
fun ProductScreen() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center){
        Text(text= "Im in ProductScreen screen", color = Color.White)
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

    LaunchedEffect(Unit)
    {
        username?.let {
            user = db.getUserInfoToStoreInApp(it)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.TopStart)
    {
        Column(modifier = Modifier.fillMaxSize())
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
                    Text(text = "UserName: ${user?.username}", color = Color.White, fontSize = 17.sp)
                    Text(text = "Gmail: ${user?.gmail}", color = Color.White, fontSize = 17.sp)
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
                            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp)
                            Text("Total Orders", color = Color.White, fontSize = 17.sp)
                        }
                    }


                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp)
                            Text("Active Orders", color = Color.White, fontSize = 17.sp)
                        }
                    }


                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp) // Padding inside the box
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("0", color = Color.White, fontSize = 17.sp)
                            Text("Cancel Orders", color = Color.White, fontSize = 17.sp)
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
                    Text(text = "Change Password", color = Color.White, fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "Change password", tint = Color.White, modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            run {  }
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
                    Text(text = "About App", color = Color.White, fontSize = 20.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = "About App", tint = Color.White, modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            run {  }
                        })
                }

               HorizontalDivider(modifier = Modifier,thickness = 1.dp, color = Color.DarkGray)

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(), verticalAlignment = Alignment.CenterVertically)
                {
                    Icon(painter = painterResource(id = R.drawable.ic_exit), contentDescription = "Log out", tint = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Log out", color = Color.White, fontSize = 20.sp)
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