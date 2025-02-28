package com.example.wbsdpmptsp.ui.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomDatePicker
import com.example.wbsdpmptsp.ui.component.CustomTextField
import com.example.wbsdpmptsp.ui.component.CustomTitle

@Composable
fun StepOne(
    judul: String,
    pelanggaran: String,
    lokasi: String,
    tanggal: String,
    pihakTerlibat: String,
    onJudulChange: (String) -> Unit,
    onPelanggaranChange: (String) -> Unit,
    onLokasiChange: (String) -> Unit,
    onTanggalChange: (String) -> Unit,
    onPihakTerlibatChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        CustomTitle(title = stringResource(id = R.string.form))

        CustomButton(
            text = stringResource(id = R.string.step),
            onClick = {

            },
            modifier = Modifier
                .align(Alignment.End),
            backgroundColor = Color(0xFF4A90E2)
        )
        CustomTextField(
            value = judul,
            onValueChange = onJudulChange,
            stringResource(R.string.judul),
            isRequired = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = pelanggaran,
            onValueChange = onPelanggaranChange,
            label = stringResource(R.string.pelanggaran),
            isRequired = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = lokasi,
            onValueChange = onLokasiChange,
            label = stringResource(R.string.lokasi),
            isRequired = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomDatePicker(
            value = tanggal,
            onValueChange = onTanggalChange,
            label = stringResource(R.string.tanggal),
            isRequired = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = pihakTerlibat,
            onValueChange = onPihakTerlibatChange,
            label = stringResource(R.string.pihakTerlibat),
            isRequired = true
        )
        Spacer(modifier = Modifier.height(14.dp))

        CustomButton(
            text = stringResource(R.string.next),
            onClick = onNext,
            enabled = judul.isNotBlank() && pelanggaran.isNotBlank() && lokasi.isNotBlank() && tanggal.isNotBlank() && pihakTerlibat.isNotBlank()
        )
    }
}
