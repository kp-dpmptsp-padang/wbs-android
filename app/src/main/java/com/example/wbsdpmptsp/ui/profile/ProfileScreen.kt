package com.example.wbsdpmptsp.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.ui.component.InfoSection
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import com.example.wbsdpmptsp.ui.theme.secondaryBlue
import com.example.wbsdpmptsp.utils.Result

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val context = LocalContext.current
    val logoutResult: Result<MessageResponse>? = viewModel.logoutResult.observeAsState(initial = null).value
    val profileResult: Result<ProfileResponse> = viewModel.profileResult.observeAsState(initial = Result.Loading).value
    val isLoading: Boolean = viewModel.isLoading.observeAsState(initial = false).value
    val scrollState = rememberScrollState()

    val userData = when (profileResult) {
        is Result.Success -> profileResult.data.data?.user
        else -> null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (profileResult is Result.Error) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = profileResult.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.fetchProfile() },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Text("Refresh")
                }
            }
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_user_profile),
                    contentDescription = stringResource(R.string.profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                InfoSection(
                    title = stringResource(R.string.account_info),
                    items = listOf(
                        Triple(R.drawable.ic_name, stringResource(R.string.name), userData?.name ?: ""),
                        Triple(R.drawable.ic_email, stringResource(R.string.email), userData?.email ?: "")
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                InfoSection(
                    title = stringResource(R.string.report_history),
                    items = listOf(
                        Triple(R.drawable.ic_history_profile, stringResource(R.string.report_total),
                            userData?.reportCount.toString()
                        )
                    ),
                    onItemClick = {
                        navController.navigate("history")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                InfoSection(
                    items = listOf(
                        Triple(R.drawable.ic_anonym, stringResource(R.string.track_anonymous_report), stringResource(R.string.track_here))
                    ),
                    backgroundColor = colorResource(R.color.bluePrimary),
                    textColor = Color.White,
                    subtextColor = Color.White,
                    onItemClick = {
                        navController.navigate("track_anonym")
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("faq") }
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_faq),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.faq),
                            fontSize = 14.sp,
                            color = primaryBlue
                        )
                        Text(
                            text = stringResource(R.string.faq_text),
                            fontSize = 12.sp,
                            color = secondaryBlue
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("about") }
                        .padding(vertical = 8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_about),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = stringResource(R.string.about_us),
                            fontSize = 14.sp,
                            color = primaryBlue
                        )
                        Text(
                            text = stringResource(R.string.about_us_text),
                            fontSize = 12.sp,
                            color = secondaryBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LaunchedEffect(logoutResult) {
                    logoutResult?.let { result ->
                        when (result) {
                            is Result.Success<MessageResponse> -> {
                                Toast.makeText(context, context.getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
                                navController.navigate("login") {
                                    popUpTo("profile") { inclusive = true }
                                }
                            }
                            is Result.Error -> {
                                Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }
                }

                Image(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = stringResource(R.string.logout),
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(35.dp)
                        .clickable {
                            viewModel.logout()
                        }
                )
            }
        }

        BottomNav(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            navController = navController
        )
    }
}

@Preview (showSystemUi = true, showBackground = true, device = "spec:width=427dp,height=952dp")
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = NavHostController(LocalContext.current))
}
