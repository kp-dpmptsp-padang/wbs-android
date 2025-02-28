package com.example.wbsdpmptsp.ui.report

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
    uploadedFileName: String,
    fileUri: Uri? = null,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
    onPreviewFile: (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(scrollState)
    ) {
        Row {
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

        SummaryItem(label = stringResource(R.string.judul), value = judul, isRequired = true)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.pelanggaran), value = pelanggaran, isRequired = true)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.lokasi), value = lokasi, isRequired = true)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.tanggal), value = tanggal, isRequired = true)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.pihakTerlibat), value = pihakTerlibat, isRequired = true)

        Spacer(modifier = Modifier.height(8.dp))

        SummaryItem(label = stringResource(R.string.rincian_kejadian), value = rincianKejadian, isMultiline = true, isRequired = true)

        Spacer(modifier = Modifier.height(16.dp))

        if (uploadedFileName.isNotEmpty()) {
            Text(
                text = stringResource(R.string.bukti),
                color = Color.Black,
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    if (fileUri != null && uploadedFileName.lowercase().matches(Regex(".+\\.(jpg|jpeg|png|gif)$"))) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(fileUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = stringResource(R.string.image_preview),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF5F5F5))
                        )
                    } else if (uploadedFileName.lowercase().endsWith(".pdf")) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onPreviewFile?.invoke() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_doc),
                                contentDescription = "PDF File",
                                modifier = Modifier.size(42.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = uploadedFileName,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = stringResource(R.string.tap_to_preview),
                                    color = Color(0xFF1976D2),
                                    fontSize = 14.sp,
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }
                    } else {
                        Text(
                            text = uploadedFileName,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
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

        Spacer(modifier = Modifier.height(32.dp))
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