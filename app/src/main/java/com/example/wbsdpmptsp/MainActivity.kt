package com.example.wbsdpmptsp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.ui.auth.login.LoginScreen
import com.example.wbsdpmptsp.ui.auth.register.RegisterScreen
import com.example.wbsdpmptsp.ui.history.HistoryScreen
import com.example.wbsdpmptsp.ui.home.HomeScreen
import com.example.wbsdpmptsp.ui.notification.NotificationScreen
import com.example.wbsdpmptsp.ui.profile.ProfileScreen
import com.example.wbsdpmptsp.ui.report.ReportScreen
import com.example.wbsdpmptsp.ui.theme.WBSDPMPTSPTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreference = UserPreference(this)

        var startDestination = "login"

        lifecycleScope.launch {
            val accessToken = userPreference.getAccessToken().first()
            if (!accessToken.isNullOrEmpty()) {
                startDestination = "home"
            }

            setContent {
                WBSDPMPTSPTheme {
                    val navController = rememberNavController()

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navController, startDestination = startDestination,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") { LoginScreen(navController = navController) }
                            composable("register") { RegisterScreen(navController = navController) }
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
}