package com.example.wbsdpmptsp.ui.report

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomTextAreaField
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.component.StepBottomSheetDialog

@Composable
fun StepTwo(
    rincianKejadian: String,
    onRincianKejadianChange: (String) -> Unit,
    isAnonymous: Boolean,
    onAnonymousChange: (Boolean) -> Unit,
    uploadedFileName: String,
    fileUri: Uri? = null,
    onUploadFile: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onPreviewFile: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

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
            CustomTitle(title = stringResource(id = R.string.form2))
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = stringResource(id = R.string.step),
            onClick = {
                showDialog.value = true
            },
            modifier = Modifier
                .align(Alignment.End),
            backgroundColor = Color(0xFF4A90E2)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextAreaField(
            value = rincianKejadian,
            onValueChange = onRincianKejadianChange,
            singleLine = false,
            label = stringResource(R.string.rincian_kejadian),
            isRequired = true,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 200.dp),
            maxLines = 8,
            maxChar = 2000
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onUploadFile() },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            if (uploadedFileName.isNotEmpty()) {
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

                        Text(
                            text = stringResource(R.string.tap_to_change_file),
                            color = Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.CenterHorizontally),
                        )
                    } else if (uploadedFileName.lowercase().endsWith(".pdf")) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                                    text = stringResource(R.string.tap_to_change_file),
                                    color = Color.Gray,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        CustomButton(
                            text = stringResource(R.string.preview_pdf),
                            onClick = { onPreviewFile?.invoke() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            backgroundColor = Color(0xFF4A90E2)
                        )
                    } else {
                        Text(
                            text = uploadedFileName,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Text(
                            text = stringResource(R.string.tap_to_change_file),
                            color = Color.Gray,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_doc),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                        .size(64.dp),
                )
                Text(
                    text = stringResource(R.string.bukti_pelanggaran),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isAnonymous,
                onCheckedChange = onAnonymousChange,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.lapor_anonim))
        }

        Spacer(modifier = Modifier.height(14.dp))
        CustomButton(
            text = stringResource(R.string.next),
            onClick = onNext,
            enabled = rincianKejadian.isNotEmpty()
        )
    }
    val stepsList = listOf(
        stringResource(R.string.step_1_7),
        stringResource(R.string.step_1_8),
    )

    if (showDialog.value) {
        StepBottomSheetDialog(
            showDialog = showDialog.value,
            onDismiss = { showDialog.value = false },
            steps = stepsList
        )
    }
}

@Composable
fun rememberFilePicker(onFilePicked: (Uri?) -> Unit): () -> Unit {
    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            if (uri != null) {
                val fileName = getFileName(context, uri)
                if (fileName?.matches(Regex(".*\\.(jpeg|jpg|png|pdf)$", RegexOption.IGNORE_CASE)) == true) {
                    onFilePicked(uri)
                }
            }
        }
    }

    return {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "application/pdf"))
        }
        filePickerLauncher.launch(intent)
    }
}

fun getFileName(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        }
    }
    return name
}

@Preview(showBackground = true)
@Composable
fun StepTwoPreview() {
    StepTwo(
        rincianKejadian = "Rincian kejadian",
        onRincianKejadianChange = { },
        isAnonymous = false,
        onAnonymousChange = { },
        uploadedFileName = "",
        onUploadFile = { },
        onBack = { },
        onNext = { }
    )
}
