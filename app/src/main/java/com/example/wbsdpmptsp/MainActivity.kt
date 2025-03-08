package com.example.wbsdpmptsp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.ui.about.AboutScreen
import com.example.wbsdpmptsp.ui.auth.forgotPassword.ForgotPasswordScreen
import com.example.wbsdpmptsp.ui.auth.forgotPassword.InputCodeScreen
import com.example.wbsdpmptsp.ui.auth.forgotPassword.NewPasswordScreen
import com.example.wbsdpmptsp.ui.auth.login.LoginScreen
import com.example.wbsdpmptsp.ui.auth.register.RegisterScreen
import com.example.wbsdpmptsp.ui.faq.FaqScreen
import com.example.wbsdpmptsp.ui.history.HistoryScreen
import com.example.wbsdpmptsp.ui.home.HomeScreen
import com.example.wbsdpmptsp.ui.notification.NotificationScreen
import com.example.wbsdpmptsp.ui.profile.ProfileScreen
import com.example.wbsdpmptsp.ui.report.ReportScreen
import com.example.wbsdpmptsp.ui.report.success.SuccessAnonimScreen
import com.example.wbsdpmptsp.ui.report.success.SuccessScreen
import com.example.wbsdpmptsp.ui.theme.WBSDPMPTSPTheme
import com.example.wbsdpmptsp.ui.track.TrackReportScreen
import com.example.wbsdpmptsp.ui.welcome.WelcomeScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreference = UserPreference(this)

        lifecycleScope.launch {
            val onboardingCompleted = userPreference.getOnboardingCompleted().first()
            val accessToken = userPreference.getAccessToken().first()

            Log.d("MainActivity", "onboardingCompleted: $onboardingCompleted")
            Log.d("MainActivity", "accessToken: $accessToken")

            val startDestination = when {
                !onboardingCompleted -> "welcome"
                !accessToken.isNullOrEmpty() -> "home"
                else -> "login"
            }

            setContent {
                WBSDPMPTSPTheme {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0)
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = startDestination,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("welcome") {
                                WelcomeScreen(navController = navController)
                            }

                            composable("login") { LoginScreen(navController = navController) }
                            composable("register") { RegisterScreen(navController = navController) }
                            composable("home") { HomeScreen(navController = navController) }
                            composable("history") { HistoryScreen(navController = navController) }
                            composable("report") { ReportScreen(navController = navController) }
                            composable("notification") { NotificationScreen(navController = navController) }
                            composable("profile") { ProfileScreen(navController = navController) }
                            composable("success") { SuccessScreen(navController = navController) }
                            composable("success_anonim/{uniqueCode}") { backStackEntry ->
                                val uniqueCode = backStackEntry.arguments?.getString("uniqueCode") ?: ""
                                SuccessAnonimScreen(navController, uniqueCode)
                            }
                            composable("faq") { FaqScreen(onBack = { navController.popBackStack() }) }
                            composable("about") { AboutScreen(onBack = { navController.popBackStack() }) }
                            composable("forgot_password") {
                                ForgotPasswordScreen(
                                    navController = navController,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable(
                                route = "input_code?email={email}",
                                arguments = listOf(
                                    navArgument("email") {
                                        type = NavType.StringType
                                        nullable = true
                                        defaultValue = null
                                    }
                                )
                            ) { backStackEntry ->
                                val email = backStackEntry.arguments?.getString("email") ?: ""
                                InputCodeScreen(
                                    navController = navController,
                                    onBack = { navController.popBackStack() },
                                    email = email
                                )
                            }
                            composable(
                                route = "reset_password?email={email}&code={code}",
                                arguments = listOf(
                                    navArgument("email") { type = NavType.StringType },
                                    navArgument("code") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val email = backStackEntry.arguments?.getString("email") ?: ""
                                val code = backStackEntry.arguments?.getString("code") ?: ""
                                NewPasswordScreen(
                                    onBack = { navController.popBackStack() },
                                    email = email,
                                    code = code,
                                    navController = navController
                                )
                            }
                            composable("track_anonym") { TrackReportScreen(navController = navController) }
                        }
                    }
                }
            }
        }
    }
}