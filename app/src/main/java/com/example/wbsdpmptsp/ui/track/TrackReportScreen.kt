package com.example.wbsdpmptsp.ui.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomDetailReportDialog
import com.example.wbsdpmptsp.ui.component.CustomTextField
import com.example.wbsdpmptsp.ui.theme.secondaryBlue
import kotlinx.coroutines.launch

@Composable
fun TrackReportScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onBack : () -> Unit = {},
    viewModel: TrackReportViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val context = LocalContext.current
    var uniqueCode by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(null)
    val reportDetail by viewModel.reportDetail.observeAsState(null)

    var showDetailDialog by remember { mutableStateOf(false) }

    LaunchedEffect(reportDetail) {
        if (reportDetail != null) {
            showDetailDialog = true
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    if (showDetailDialog) {
        CustomDetailReportDialog(
            detailReport = reportDetail,
            onDismiss = {
                showDetailDialog = false
                viewModel.clearReportDetail()
            },
            isLoading = isLoading,
            navController = navController,
            uniqueCode = uniqueCode
        )
    }

    val handleTrackClick = {
        if (uniqueCode.isNotBlank()) {
            viewModel.trackReport(uniqueCode)
        } else {
            scope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.kode_unik_tidak_boleh_kosong))
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = secondaryBlue,
                    shape = RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
                .height(350.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(R.drawable.logo_clear),
                contentDescription = stringResource(R.string.logo_clear),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(100.dp)
                    .padding(bottom = 30.dp),
                alignment = Alignment.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_track_anon),
                contentDescription = null,
                modifier.size(125.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier.height(16.dp))
            Text(
                text = stringResource(R.string.track_anonymous_report),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1A237E)
            )
            Spacer(modifier.height(24.dp))

            Card(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.input_code_anon),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier.height(20.dp))

                    CustomTextField(
                        value = uniqueCode,
                        onValueChange = { uniqueCode = it },
                        label = stringResource(R.string.unique),
                    )

                    Spacer(modifier.height(20.dp))

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = secondaryBlue
                        )
                    } else {
                        CustomButton(
                            text = stringResource(R.string.track),
                            onClick = handleTrackClick as () -> Unit,
                            modifier.width(150.dp),
                            shape = RoundedCornerShape(10.dp),
                            textStyle = TextStyle(fontSize = 13.sp)
                        )
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(8.dp),
                    containerColor = Color(0xFFB00020),
                    contentColor = Color.White,
                ) {
                    Text(data.visuals.message)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrackReportScreenPreview() {
    TrackReportScreen(navController = NavHostController(LocalContext.current))
}