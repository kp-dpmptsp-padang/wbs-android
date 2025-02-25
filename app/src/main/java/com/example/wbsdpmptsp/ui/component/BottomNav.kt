package com.example.wbsdpmptsp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wbsdpmptsp.R

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
) {
    object Home : BottomNavItem("home", R.drawable.ic_home, "Home")
    object History : BottomNavItem("history", R.drawable.ic_history, "Riwayat")
    object Report : BottomNavItem("report", R.drawable.ic_report, "Laporan")
    object Notification : BottomNavItem("notification", R.drawable.ic_notification, "Notifikasi")
    object Profile : BottomNavItem("profile", R.drawable.ic_profile, "Profil")
}

@Composable
fun BottomNav(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Report,
        BottomNavItem.Notification,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val customBlue = Color(0xFF1A237E)
    val hoverBackground = Color(0xFFDCE7F4)

    Box(modifier = modifier) {
        NavigationBar(
            modifier = Modifier
                .height(80.dp)
                .background(Color.White),
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            items.forEachIndexed { index, item ->
                if (index == 2) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { },
                        label = { },
                        enabled = false,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(56.dp)
                            ) {
                                if (currentRoute == item.route) {
                                    Box(
                                        modifier = Modifier
                                            .size(56.dp)
                                            .background(hoverBackground, shape = CircleShape)
                                    )
                                }
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.label,
                                    modifier = Modifier.size(28.dp),
                                    tint = customBlue
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = customBlue,
                            unselectedIconColor = customBlue
                        )
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-35).dp)
                .size(72.dp)
                .clickable {
                    navController.navigate(BottomNavItem.Report.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (currentRoute == BottomNavItem.Report.route) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(hoverBackground, shape = CircleShape)
                )
            }

            Icon(
                painter = painterResource(id = BottomNavItem.Report.icon),
                contentDescription = BottomNavItem.Report.label,
                modifier = Modifier.size(58.dp),
                tint = customBlue,
            )
        }
    }
}