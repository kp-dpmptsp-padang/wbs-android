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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.component.HistoryCard
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HistoryViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val context = LocalContext.current
    val tabsPosition = listOf("Menunggu", "Diproses", "Ditolak", "Selesai")
    val pagerState = rememberPagerState(pageCount = { tabsPosition.size })
    val coroutineScope = rememberCoroutineScope()

    val history by viewModel.history.observeAsState(initial = emptyList())
    val loading by viewModel.loading.observeAsState(initial = false)
    val error by viewModel.error.observeAsState(initial = "")

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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
                .background(Color.White)
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

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(history?.size ?: 0) { index ->
                        val item = history?.get(index)
                        item?.let {
                            HistoryCard(
                                name = it.title ?: "Unknown",
                                description = it.detail ?: "No details",
                                date = it.date ?: "Unknown",
                                onInfoClick = {  }
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

@Preview
@Composable
fun HistoryScreenPreview() {
    MaterialTheme {
        HistoryScreen(navController = NavHostController(LocalContext.current))
    }
}