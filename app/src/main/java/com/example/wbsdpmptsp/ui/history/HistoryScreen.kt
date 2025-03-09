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
    val statusMapping = mapOf(
        0 to "menunggu-verifikasi",
        1 to "diproses",
        2 to "ditolak",
        3 to "selesai"
    )

    val pagerState = rememberPagerState(pageCount = { tabsPosition.size })
    val coroutineScope = rememberCoroutineScope()

    var previousPage by remember { mutableIntStateOf(pagerState.currentPage) }

    val history by viewModel.history.observeAsState(initial = emptyList())
    val loading by viewModel.loading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = "")
    val currentStatus by viewModel.currentStatus.observeAsState(initial = "")

    val reportDetail by viewModel.reportDetail.observeAsState()
    val detailLoading by viewModel.detailLoading.observeAsState(initial = false)

    var showDetailDialog by remember { mutableStateOf(false) }

    LaunchedEffect(reportDetail) {
        if (reportDetail != null) {
            showDetailDialog = true
        }
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress && previousPage != pagerState.currentPage) {
            val status = statusMapping[pagerState.currentPage] ?: "menunggu-verifikasi"
            viewModel.setCurrentStatus(status)
            viewModel.getHistory(status)
            previousPage = pagerState.currentPage
        }
    }

    LaunchedEffect(Unit) {
        val initialStatus = statusMapping[0] ?: "menunggu-verifikasi"
        viewModel.setCurrentStatus(initialStatus)
        viewModel.getHistory(initialStatus)
    }

    if (showDetailDialog) {
        DetailReportDialog(
            detailReport = reportDetail,
            onDismiss = {
                showDetailDialog = false
                viewModel.clearReportDetail()
            },
            isLoading = detailLoading,
            navController = navController,
            reportId = reportDetail?.data?.id
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
                                if (pagerState.currentPage != index) {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                userScrollEnabled = !loading,
            ) { page ->

                if (page == pagerState.currentPage) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (loading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else if (error.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
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
                                            val status = statusMapping[page] ?: "menunggu-verifikasi"
                                            viewModel.getHistory(status)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                                    ) {
                                        Text(stringResource(R.string.refresh))
                                    }
                                }
                            }
                        } else if (history.isNullOrEmpty()) {
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
                                modifier = Modifier.fillMaxSize(),
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
                                        if (it.status == currentStatus) {
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
                } else {
                    Box(modifier = Modifier.fillMaxSize())
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