package com.example.wbsdpmptsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wbsdpmptsp.ui.history.HistoryScreen
import com.example.wbsdpmptsp.ui.home.HomeScreen
import com.example.wbsdpmptsp.ui.notification.NotificationScreen
import com.example.wbsdpmptsp.ui.profile.ProfileScreen
import com.example.wbsdpmptsp.ui.report.ReportScreen
import com.example.wbsdpmptsp.ui.theme.WBSDPMPTSPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WBSDPMPTSPTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController, startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeScreen(navController = navController) }
                        composable("history") { HistoryScreen(navController = navController) }
                        composable("report") { ReportScreen(navController = navController) }
                        composable("notification") { NotificationScreen(navController = navController) }
                        composable("profile") { ProfileScreen(navController = navController) }
                    }
                }
            }
        }
    }
}