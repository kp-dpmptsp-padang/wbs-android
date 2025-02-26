package com.example.wbsdpmptsp.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.ui.component.BottomNav

@Composable
fun ReportScreen(navController: NavHostController) {
    var currentStep by remember { mutableIntStateOf(0) }
    var judul by remember { mutableStateOf("") }
    var pelanggaran by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var pihakTerlibat by remember { mutableStateOf("") }
    var rincianKejadian by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }
    var uploadedFileName by remember { mutableStateOf<String?>(null) }

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
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {
    ReportScreen(navController = NavHostController(LocalContext.current))
}