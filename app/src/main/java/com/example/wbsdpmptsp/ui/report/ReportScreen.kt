package com.example.wbsdpmptsp.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.data.remote.request.ReportRequest
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.utils.Result

@Composable
fun ReportScreen(
    navController: NavHostController,
    viewModel: ReportViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext
        .current))
) {
    var currentStep by remember { mutableIntStateOf(0) }
    var judul by remember { mutableStateOf("") }
    var pelanggaran by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var pihakTerlibat by remember { mutableStateOf("") }
    var rincianKejadian by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }
    var uploadedFileName by remember { mutableStateOf<String?>(null) }

    val reportResult by viewModel.reportResult.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (currentStep) {
            0 -> StepOne(
                judul = judul,
                pelanggaran = pelanggaran,
                lokasi = lokasi,
                tanggal = tanggal,
                pihakTerlibat = pihakTerlibat,
                onJudulChange = { judul = it },
                onPelanggaranChange = { pelanggaran = it },
                onLokasiChange = { lokasi = it },
                onTanggalChange = { tanggal = it },
                onPihakTerlibatChange = { pihakTerlibat = it },
                onNext = { currentStep++ }
            )
            1 -> StepTwo(
                rincianKejadian = rincianKejadian,
                onRincianKejadianChange = { rincianKejadian = it },
                isAnonymous = isAnonymous,
                onAnonymousChange = { isAnonymous = it },
                uploadedFileName = uploadedFileName ?: "",
                onUploadFile = {  },
                onBack = { currentStep-- },
                onNext = { currentStep++ }
            )
            2 -> StepThree(
                judul = judul,
                pelanggaran = pelanggaran,
                lokasi = lokasi,
                tanggal = tanggal,
                pihakTerlibat = pihakTerlibat,
                rincianKejadian = rincianKejadian,
                isAnonymous = isAnonymous,
                uploadedFileName = uploadedFileName,
                onBack = { currentStep-- },
                onSubmit = {
                    val reportRequest = ReportRequest(
                        title = judul,
                        violation = pelanggaran,
                        location = lokasi,
                        date = tanggal,
                        actors = pihakTerlibat,
                        detail = rincianKejadian,
                        isAnonymous = isAnonymous,
                        evidence = uploadedFileName
                    )
                    viewModel.createReport(reportRequest)
                }
            )
        }

        if (currentStep == 0) {
            BottomNav(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                navController = navController
            )
        }
    }

    reportResult?.let { result ->
        when (result) {
            is Result.Loading -> {
                CircularProgressIndicator()
            }
            is Result.Success -> {
                navController.navigate("home")
            }
            is Result.Error -> {
                Text(
                    text = result.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {
    ReportScreen(navController = NavHostController(LocalContext.current))
}