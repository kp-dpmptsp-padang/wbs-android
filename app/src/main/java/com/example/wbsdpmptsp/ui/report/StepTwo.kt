package com.example.wbsdpmptsp.ui.report

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomTextAreaField
import com.example.wbsdpmptsp.ui.component.CustomTextField
import com.example.wbsdpmptsp.ui.component.CustomTitle

@Composable
fun StepTwo(
    rincianKejadian: String,
    onRincianKejadianChange: (String) -> Unit,
    isAnonymous: Boolean,
    onAnonymousChange: (Boolean) -> Unit,
    uploadedFileName: String,
    onUploadFile: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
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
            CustomTitle(title = stringResource(id = R.string.form2))
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = stringResource(id = R.string.step),
            onClick = {

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
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 200.dp),
            maxLines = 8,
            maxChar = 2000
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onUploadFile() },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            if (uploadedFileName.isNotEmpty()) {
                Text(
                    text = "File: $uploadedFileName",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
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
