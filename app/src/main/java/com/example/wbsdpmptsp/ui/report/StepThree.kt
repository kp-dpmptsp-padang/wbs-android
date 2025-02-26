package com.example.wbsdpmptsp.ui.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.component.SummaryItem

@Composable
fun StepThree(
    judul: String,
    pelanggaran: String,
    lokasi: String,
    tanggal: String,
    pihakTerlibat: String,
    rincianKejadian: String,
    isAnonymous: Boolean,
    uploadedFileName: String?,
    onPreviewFile: (() -> Unit)? = null,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    Column(modifier = Modifier.padding(24.dp)) {
        Row{
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 16.dp, end = 8.dp)
                    .clickable { onBack() }
            )
            CustomTitle(title = stringResource(id = R.string.form3))
        }

        Spacer(modifier = Modifier.height(16.dp))

        SummaryItem(label = stringResource(R.string.judul), value = judul)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.pelanggaran), value = pelanggaran)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.lokasi), value = lokasi)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.tanggal), value = tanggal)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.pihakTerlibat), value = pihakTerlibat)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.rincian_kejadian), value = rincianKejadian, isMultiline = true)

        Spacer(modifier = Modifier.height(8.dp))

        uploadedFileName?.let {
            SummaryItem(
                label = stringResource(R.string.bukti_pelanggaran),
                value = it,
                isFile = true,
                onFilePreview = onPreviewFile
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = if (isAnonymous) stringResource(R.string.ya) else stringResource(R.string.tidak),
            onValueChange = {},
            label = { Text(stringResource(R.string.lapor_anonim), color = Color.Black) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color(0x801A237E)
            ),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                Checkbox(
                    checked = isAnonymous,
                    onCheckedChange = null
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = stringResource(R.string.kirim_laporan),
            onClick = onSubmit
        )

    }
}

@Preview(showBackground = true)
@Composable
fun StepThreePreview() {
    StepThree(
        judul = "Korupsi Dana Publik",
        pelanggaran = "Penyalahgunaan Anggaran",
        lokasi = "Kantor DPMPTSP Kota Padang",
        tanggal = "25 Februari 2025",
        pihakTerlibat = "Oknum Pegawai",
        rincianKejadian = "Dana anggaran proyek fiktif ditemukan...",
        isAnonymous = true,
        uploadedFileName = "bukti_foto.jpg",
        onBack = { },
        onSubmit = { }
    )
}
