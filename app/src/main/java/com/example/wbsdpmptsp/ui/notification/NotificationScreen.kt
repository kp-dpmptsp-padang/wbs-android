package com.example.wbsdpmptsp.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.component.NotifCard
import com.example.wbsdpmptsp.ui.theme.primaryBlue

@Composable
fun NotificationScreen(
    navController: NavHostController,
    viewModel: NotificationViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val notifications by viewModel.notification.observeAsState(initial = emptyList())
    val loading by viewModel.loading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = "")

    LaunchedEffect(Unit) {
        viewModel.getNotification()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
        ) {
            CustomTitle(title = stringResource(id = R.string.notif))

            if (!loading && error.isEmpty() && notifications?.isNotEmpty() == true) {
                Text(
                    text = stringResource(R.string.mark_as_read),
                    color = Color(0xFF4A90E2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, top = 8.dp)
                        .clickable(onClick = {
                            viewModel.readAllNotification()
                        }),
                    textAlign = TextAlign.End,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (error.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.getNotification() },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                        ) {
                            Text("Refresh")
                        }
                    }
                }
            } else if (notifications.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_data_notif),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notifications!!.size) { index ->
                        val item = notifications!![index]
                        item?.let {
                            NotifCard(
                                name = "Informasi",
                                description = it.message ?: "No details",
                                date = it.createdAt ?: "Unknown",
                                isRead = it.isRead == true,
                                onInfoClick = {
                                    viewModel.readNotification(it.id ?: 0)
                                }
                            )
                        }
                    }
                }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = NavHostController(LocalContext.current))
}