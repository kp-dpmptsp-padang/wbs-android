package com.example.wbsdpmptsp.ui.report

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.ui.component.ErrorDialog
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

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

    val reportResult by viewModel.reportResult.observeAsState()

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }
    val pickFile = rememberFilePicker { uri ->
        selectedFileUri = uri
        selectedFileName = uri?.let { getFileName(context, it) } ?: ""
    }
    val coroutineScope = rememberCoroutineScope()

    val handlePreviewFile = {
        if (selectedFileUri != null) {
            if (selectedFileName.lowercase().endsWith(".pdf")) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(selectedFileUri, "application/pdf")
                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                    coroutineScope.launch {
                        showErrorDialog = true
                        errorMessage = "No PDF viewer found"
                    }
                }
            }
        }
    }


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
                fileUri = selectedFileUri,
                uploadedFileName = selectedFileName,
                onUploadFile = pickFile,
                onPreviewFile = handlePreviewFile,
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
                uploadedFileName = selectedFileName,
                fileUri = selectedFileUri,
                onPreviewFile = handlePreviewFile,
                onBack = { currentStep-- },
                onSubmit = {
                    viewModel.createReport(
                        context,
                        title = judul,
                        violation = pelanggaran,
                        location = lokasi,
                        date = tanggal,
                        actors = pihakTerlibat,
                        detail = rincianKejadian,
                        isAnonymous = isAnonymous,
                        fileUri = selectedFileUri
                    )
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Result.Success -> {
                val uniqueCode = result.data.data?.uniqueCode ?: ""
                navController.navigate(if (isAnonymous) "success_anonim/$uniqueCode" else
                    "success") {
                    popUpTo("report") { inclusive = true }
                }
            }
            is Result.Error -> {
                if (!showErrorDialog) {
                    errorMessage = result.error
                    showErrorDialog = true
                }
            }
            is Result.Empty -> {

            }
        }
    }

    if (showErrorDialog) {
        ErrorDialog(
            message = errorMessage,
            onDismiss = {
                showErrorDialog = false
                viewModel.resetReportResult()
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {
    ReportScreen(navController = NavHostController(LocalContext.current))
}