package com.example.wbsdpmptsp.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
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
import com.example.wbsdpmptsp.ui.component.DetailReportDialog
import com.example.wbsdpmptsp.ui.component.HistoryCard
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: HistoryViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val tabsPosition = listOf("Menunggu", "Diproses", "Ditolak", "Selesai")
    val pagerState = rememberPagerState(pageCount = { tabsPosition.size })
    val coroutineScope = rememberCoroutineScope()

    val history by viewModel.history.observeAsState(initial = emptyList())
    val loading by viewModel.loading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = "")

    val reportDetail by viewModel.reportDetail.observeAsState()
    val detailLoading by viewModel.detailLoading.observeAsState(initial = false)

    var showDetailDialog by remember { mutableStateOf(false) }

    LaunchedEffect(reportDetail) {
        if (reportDetail != null) {
            showDetailDialog = true
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val status = when (tabsPosition[pagerState.currentPage]) {
            "Menunggu" -> "menunggu-verifikasi"
            "Diproses" -> "diproses"
            "Ditolak" -> "ditolak"
            "Selesai" -> "selesai"
            else -> ""
        }
        viewModel.getHistory(status)
    }

    if (showDetailDialog) {
        DetailReportDialog(
            detailReport = reportDetail,
            onDismiss = {
                showDetailDialog = false
                viewModel.clearReportDetail()
            },
            isLoading = detailLoading
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            CustomTitle(title = stringResource(id = R.string.report_history))

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = primaryBlue
                    )
                }
            ) {
                tabsPosition.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, color = if (pagerState.currentPage == index) primaryBlue else Color.Black) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    )
                }
            }

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
                            text = stringResource(R.string.refresh_session),
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val status = when (tabsPosition[pagerState.currentPage]) {
                                    "Menunggu" -> "menunggu-verifikasi"
                                    "Diproses" -> "diproses"
                                    "Ditolak" -> "ditolak"
                                    "Selesai" -> "selesai"
                                    else -> ""
                                }
                                viewModel.getHistory(status)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                        ) {
                            Text(stringResource(R.string.refresh))
                        }
                    }
                }
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->
                    if (history.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_data_history),
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 32.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(history!!.size) { index ->
                                val item = history!![index]
                                item?.let {
                                    HistoryCard(
                                        id = it.id ?: 0,
                                        name = it.title ?: "Unknown",
                                        description = it.detail ?: "No details",
                                        date = it.date ?: "Unknown",
                                        onInfoClick = { reportId ->
                                            viewModel.getDetailReport(reportId)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        BottomNav(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            navController = navController
        )
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen(navController = NavHostController(LocalContext.current))
    }
}