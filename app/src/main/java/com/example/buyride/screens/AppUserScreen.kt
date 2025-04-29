package com.example.buyride.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

//data class for bottom nav item
data class BottomNavItem(val name:String,val route:String,val icon:Int)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppUserScreen() {

    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem("Home","HomeScreen", R.drawable.ic_home),
        BottomNavItem("Product","ProductScreen", R.drawable.ic_grocery),
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
        composable("ProductScreen"){ ProductScreen(navHostController) }
        composable("SettingsScreen"){ SettingsScreen() }
        composable(
            route = "BuyMotoScreen/{bikeName}/{bikePrice}/{bikeType}/{bikeImage}/{bikeInfo}"
        ) { backStackEntry ->

            val bikeName = backStackEntry.arguments?.getString("bikeName") ?: ""
            val bikePrice = backStackEntry.arguments?.getString("bikePrice") ?: ""
            val bikeType = backStackEntry.arguments?.getString("bikeType") ?: ""
            val bikeInfo = backStackEntry.arguments?.getString("bikeInfo") ?: ""
            val bikeImage = backStackEntry.arguments?.getString("bikeImage") ?: ""

            BuyMotoScreen(bikeName, bikePrice, bikeType, bikeImage,bikeInfo,navHostController)
        }
        composable ("ChargeScreen"){ ChargeScreen() }
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